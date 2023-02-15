package com.spinoza.bininfotest.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

    private val itemTouchHelper by lazy {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.removeFromHistory(historyAdapter.currentList[viewHolder.adapterPosition])
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHistory.adapter = historyAdapter
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewHistory)
        historyAdapter.onBinClickListener = { showInfoActivity(it.value) }
    }

    private fun setupListeners() {
        with(binding) {
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

    private fun setupObservers() {
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