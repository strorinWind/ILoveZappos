package com.strorin.zappos.ui.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.strorin.zappos.R
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class SettingsFragment: MvpAppCompatFragment(), SettingsView {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    private lateinit var priceEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.settings_fragment, container, false)
        priceEditText = view.findViewById(R.id.price_input)
        saveButton = view.findViewById(R.id.save_button)
        return view
    }

    override fun onStart() {
        super.onStart()
        saveButton.setOnClickListener { presenter.onSaveButtonClicked(priceEditText.text.toString().toFloat()) }
        priceEditText.addTextChangedListener(priceTextListener)
    }

    override fun setPrice(price: Float) {
        priceEditText.setText(price.toString())
        saveButton.isEnabled = true
    }

    private val priceTextListener = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveButton.isEnabled = p0.toString().toFloatOrNull() != null
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}