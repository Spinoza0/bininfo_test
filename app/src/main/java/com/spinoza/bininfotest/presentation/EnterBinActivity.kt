package com.spinoza.bininfotest.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.data.DataBase
import com.spinoza.bininfotest.databinding.ActivityEnterBinBinding
import com.spinoza.bininfotest.domain.Bin
import com.spinoza.bininfotest.presentation.adapters.HistoryAdapter
import com.spinoza.bininfotest.presentation.viewmodel.EnterBinViewModel
import com.spinoza.bininfotest.presentation.viewmodel.EnterBinViewModelFactory

class EnterBinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterBinBinding
    private lateinit var viewModel: EnterBinViewModel
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            EnterBinViewModelFactory(DataBase.getInstance(application).binInfoDao())
        )[EnterBinViewModel::class.java]

        historyAdapter = HistoryAdapter()
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
            val bin = Bin(binValue)
            viewModel.insertToHistory(bin)
            startActivity(BinInfoActivity.newIntent(this, binValue))
        }
    }
}