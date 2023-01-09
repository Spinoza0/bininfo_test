package com.spinoza.bininfotest.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.R
import com.spinoza.bininfotest.data.BinApiFactory
import com.spinoza.bininfotest.databinding.ActivityBinInfoBinding
import com.spinoza.bininfotest.domain.Bank
import com.spinoza.bininfotest.domain.BinInfo
import com.spinoza.bininfotest.domain.Country
import com.spinoza.bininfotest.presentation.viewmodel.BinInfoViewModel
import com.spinoza.bininfotest.presentation.viewmodel.BinInfoViewModelFactory

class BinInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBinInfoBinding
    private lateinit var viewModel: BinInfoViewModel
    private lateinit var notAvailable: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            BinInfoViewModelFactory(BinApiFactory.apiService)
        )[BinInfoViewModel::class.java]

        if (intent.hasExtra(EXTRA_BIN)) {
            intent.getStringExtra(EXTRA_BIN)?.let {
                setObservers(it)
                viewModel.load(it)
            }
        } else {
            finish()
        }
    }

    private fun setObservers(binValue: String) {
        viewModel.getIsLoading().observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.getBinInfo().observe(this) { setContent(binValue, it) }
        viewModel.getIsError().observe(this) { showError(binValue, it) }
    }

    private fun setContent(binValue: String, binInfo: BinInfo) {
        notAvailable = getString(R.string.notAvailable)
        with(binding) {
            with(binInfo) {
                textViewBin.text = String.format("BIN: %s", binValue)
                textViewScheme.text =
                    String.format(getString(R.string.scheme), scheme ?: notAvailable)
                textViewBrand.text = String.format(getString(R.string.brand), brand ?: notAvailable)
                if (number == null) {
                    textViewCardNumberLengthLuhn.visibility = View.GONE
                } else {
                    number?.let {
                        textViewCardNumberLengthLuhn.text =
                            String.format(getString(R.string.length_luhn),
                                it.length.toString(),
                                it.luhn.toString())
                    }
                }
                textViewTypePrepaid.text = String.format(
                    getString(R.string.type_prepaid),
                    type ?: notAvailable,
                    prepaid.toString()
                )

                country?.let { setCountry(it) }
                bank?.let { setBank(it) }
            }
        }
    }

    private fun setCountry(country: Country) {
        with(binding) {
            viewLanLonLine.visibility = View.VISIBLE
            textViewCountry.visibility = View.VISIBLE
            textViewLatLon.visibility = View.VISIBLE
            textViewNumericAlpha2Emoji.visibility = View.VISIBLE
            textViewCurrency.visibility = View.VISIBLE
            val countryName = country.name ?: notAvailable
            val latitude = country.latitude.toString()
            val longitude = country.longitude.toString()
            textViewCountry.text = String.format(getString(R.string.country), countryName)
            textViewLatLon.text =
                String.format(getString(R.string.lan_lon), latitude, longitude)
            textViewLatLon.setOnClickListener {
                openLink(Intent.ACTION_VIEW, "http://maps.google.com/maps?q=loc:" +
                        "$latitude,$longitude ($countryName)")
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

    private fun setBank(bank: Bank) {
        with(binding) {
            textViewBank.visibility = View.VISIBLE
            textViewCity.visibility = View.VISIBLE
            textViewUrl.visibility = View.VISIBLE
            textViewPhone.visibility = View.VISIBLE
            textViewBank.text =
                String.format(getString(R.string.bank), bank.name ?: notAvailable)
            textViewCity.text = String.format(
                getString(R.string.city),
                bank.city ?: notAvailable
            )

            textViewUrl.text = String.format("Url: %s", bank.url ?: notAvailable)
            bank.url?.let { url ->
                viewUrlLine.visibility = View.VISIBLE
                textViewUrl.setOnClickListener {
                    openLink(Intent.ACTION_VIEW, "https://${url}")
                }
            }
            textViewPhone.text = String.format(
                getString(R.string.phone),
                bank.phone ?: notAvailable
            )
            bank.phone?.let { phone ->
                viewPhoneLine.visibility = View.VISIBLE
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
            textViewBin.text = String.format("BIN: %s", binValue)
            textViewScheme.text =
                String.format("Error of getting info: %s", errorMsg)
            textViewScheme.setTextColor(ContextCompat.getColor(this@BinInfoActivity,
                android.R.color.holo_red_dark))
        }
    }

    companion object {
        private const val EXTRA_BIN = "bin"

        fun newIntent(context: Context, binValue: String): Intent {
            val intent = Intent(context, BinInfoActivity::class.java)
            intent.putExtra(EXTRA_BIN, binValue)
            return intent
        }
    }
}