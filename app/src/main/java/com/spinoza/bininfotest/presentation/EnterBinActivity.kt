package com.spinoza.bininfotest.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.databinding.ActivityEnterBinBinding
import com.spinoza.bininfotest.di.DaggerApplicationComponent
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.presentation.adapters.HistoryAdapter
import com.spinoza.bininfotest.presentation.viewmodel.EnterBinViewModel
import com.spinoza.bininfotest.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class EnterBinActivity : AppCompatActivity() {

    private val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var historyAdapter: HistoryAdapter

    private val binding by lazy {
        ActivityEnterBinBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EnterBinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerViewHistory.adapter = historyAdapter

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        with(binding) {
            historyAdapter.onBinClickListener = { showInfoActivity(it.value) }

            editTextBin.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showInfoActivity(editTextBin.text.toString())
                }
                return@setOnEditorActionListener false
            }

            buttonShowInfo.setOnClickListener { showInfoActivity(editTextBin.text.toString()) }

            buttonClearHistory.setOnClickListener {
                viewModel.clearHistory()
                setHistoryVisibility(false)
            }
        }
    }

    private fun setObservers() {
        viewModel.history.observe(this) {
            setHistoryVisibility(it.isEmpty())
            historyAdapter.submitList(it)
        }
        viewModel.isError.observe(this) {
            Toast.makeText(this@EnterBinActivity, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun setHistoryVisibility(visible: Boolean) {
        val visibility: Int = if (visible) View.GONE else View.VISIBLE
        binding.textViewHistory.visibility = visibility
        binding.buttonClearHistory.visibility = visibility
    }

    private fun showInfoActivity(binValue: String) {
        if (binValue.isNotEmpty()) {
            viewModel.insertToHistory(Bin(binValue))
            startActivity(BinInfoActivity.newIntent(this, binValue))
        }
    }
}