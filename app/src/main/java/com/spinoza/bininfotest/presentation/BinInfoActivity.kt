package com.spinoza.bininfotest.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.R
import com.spinoza.bininfotest.databinding.ActivityBinInfoBinding
import com.spinoza.bininfotest.di.DaggerApplicationComponent
import com.spinoza.bininfotest.domain.model.Bank
import com.spinoza.bininfotest.domain.model.BinInfo
import com.spinoza.bininfotest.domain.model.Country
import com.spinoza.bininfotest.domain.repository.BinState
import com.spinoza.bininfotest.presentation.viewmodel.BinInfoViewModel
import com.spinoza.bininfotest.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class BinInfoActivity : AppCompatActivity() {

    private val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityBinInfoBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BinInfoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.hasExtra(EXTRA_BIN)) {
            intent.getStringExtra(EXTRA_BIN)?.let {
                setObservers(it)
                viewModel.loadBinInfo(it)
            }
        } else {
            finish()
        }
    }

    private fun setObservers(binValue: String) {
        viewModel.state.observe(this) {
            if (it is BinState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                if (it is BinState.Error) {
                    showError(binValue, it.value)
                } else if (it is BinState.BinInfoData) {
                    setContent(binValue, it.value)
                }
            }
        }
    }

    private fun setContent(binValue: String, binInfo: BinInfo) {
        val notAvailable = getString(R.string.notAvailable)

        with(binInfo) {
            binding.textViewBin.text = String.format(getString(R.string.bin), binValue)
            binding.textViewScheme.text =
                String.format(getString(R.string.scheme), scheme ?: notAvailable)
            binding.textViewBrand.text =
                String.format(getString(R.string.brand), brand ?: notAvailable)
            if (number == null) {
                binding.textViewCardNumberLengthLuhn.visibility = View.GONE
            } else number?.let {
                binding.textViewCardNumberLengthLuhn.text =
                    String.format(
                        getString(R.string.length_luhn),
                        it.length?.toString() ?: notAvailable,
                        it.luhn?.toString() ?: notAvailable
                    )
            }

            binding.textViewTypePrepaid.text = String.format(
                getString(R.string.type_prepaid),
                type ?: notAvailable,
                prepaid?.toString() ?: notAvailable
            )

            country?.let { setCountry(it, notAvailable) }
            bank?.let { setBank(it, notAvailable) }
        }

    }

    private fun setCountry(country: Country, notAvailable: String) {
        with(binding) {
            turnOnVisibility(textViewCountry, textViewNumericAlpha2Emoji, textViewCurrency)
            val countryName = country.name ?: notAvailable
            textViewCountry.text = String.format(getString(R.string.country), countryName)

            if (country.latitude != null && country.longitude != null) {
                turnOnVisibility(textViewLatLon)
                val latitude = country.latitude.toString()
                val longitude = country.longitude.toString()
                textViewLatLon.text =
                    String.format(getString(R.string.lan_lon), latitude, longitude)
                textViewLatLon.setOnClickListener {
                    openLink(
                        Intent.ACTION_VIEW, "http://maps.google.com/maps?q=loc:" +
                                "$latitude,$longitude ($countryName)"
                    )
                }
                setTextViewUnderlined(textViewLatLon)
            }

            textViewNumericAlpha2Emoji.text = String.format(
                getString(R.string.numeric_alpha2_emoji),
                country.numeric ?: notAvailable,
                country.alpha2 ?: notAvailable,
                country.emoji ?: notAvailable
            )
            textViewCurrency.text = String.format(
                getString(R.string.currency),
                country.currency ?: notAvailable
            )
        }
    }

    private fun setBank(bank: Bank, notAvailable: String) {
        with(binding) {
            turnOnVisibility(textViewBank, textViewCity, textViewUrl, textViewPhone)
            textViewBank.text = String.format(getString(R.string.bank), bank.name ?: notAvailable)
            textViewCity.text = String.format(getString(R.string.city), bank.city ?: notAvailable)
            textViewUrl.text = String.format("Url: %s", bank.url ?: notAvailable)

            bank.url?.let { url ->
                setTextViewUnderlined(textViewUrl)
                textViewUrl.setOnClickListener {
                    openLink(Intent.ACTION_VIEW, "https://${url}")
                }
            }
            textViewPhone.text = String.format(
                getString(R.string.phone),
                bank.phone ?: notAvailable
            )
            bank.phone?.let { phone ->
                setTextViewUnderlined(textViewPhone)
                textViewPhone.setOnClickListener {
                    openLink(Intent.ACTION_DIAL, "tel:${phone}")
                }
            }
        }
    }

    private fun openLink(action: String, url: String) {
        startActivity(Intent(action, Uri.parse(url)))
    }

    private fun showError(binValue: String, errorMsg: String) {
        with(binding) {
            textViewBin.text = String.format(getString(R.string.bin), binValue)
            textViewScheme.text =
                String.format(getString(R.string.error), errorMsg)
            textViewScheme.setTextColor(
                ContextCompat.getColor(
                    this@BinInfoActivity,
                    android.R.color.holo_red_dark
                )
            )
        }
    }

    private fun setTextViewUnderlined(textView: TextView) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun turnOnVisibility(vararg views: View) {
        views.toList().forEach { it.visibility = View.VISIBLE }
    }

    companion object {
        private const val EXTRA_BIN = "bin"

        fun newIntent(context: Context, binValue: String): Intent {
            return Intent(context, BinInfoActivity::class.java).putExtra(EXTRA_BIN, binValue)
        }
    }
}