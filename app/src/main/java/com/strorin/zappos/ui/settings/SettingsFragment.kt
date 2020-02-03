package com.strorin.zappos.ui.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.strorin.zappos.R
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import moxy.presenter.ProvidePresenter


class SettingsFragment(
    private val applicationContext: Context
) : MvpAppCompatFragment(), SettingsView {

    companion object {
        fun newInstance(context: Context) = SettingsFragment(context.applicationContext)
    }

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter {
        return SettingsPresenter(applicationContext)
    }

    private lateinit var priceEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var notificationSwitch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.settings_fragment, container, false)
        findViews(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        saveButton.setOnClickListener { saveBtnClicked() }
        notificationSwitch.setOnCheckedChangeListener { _, p1 -> presenter.onFeatureSwitched(p1) }
        priceEditText.addTextChangedListener(priceTextListener)
        priceEditText.setOnKeyListener(priceTextKeysHandler)
    }

    override fun setPrice(price: Float) {
        priceEditText.setText(price.toString())
    }

    override fun setEnabled(enabled: Boolean) {
        notificationSwitch.isChecked = enabled
        saveButton.isEnabled = enabled
        priceEditText.isEnabled = enabled
    }

    override fun showSaved() {
        val c= context
        if (c != null) {
            Snackbar.make(
                saveButton,
                c.getString(R.string.str_data_saved_notification),
                Snackbar.LENGTH_SHORT
            )
                .setBackgroundTint(ContextCompat.getColor(c, R.color.colorAccent))
                .show()
        }
    }

    private fun saveBtnClicked() {
        presenter.onSaveButtonClicked(priceEditText.text.toString().toFloat())
        hideSoftKeyboard()
    }

    private fun hideSoftKeyboard() {
        (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun findViews(view: View){
        priceEditText = view.findViewById(R.id.price_input)
        saveButton = view.findViewById(R.id.save_button)
        notificationSwitch = view.findViewById(R.id.notification_switch)
    }

    private val priceTextListener = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveButton.isEnabled = p0.toString().toFloatOrNull() != null
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    private val priceTextKeysHandler = object: View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if ((event?.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (saveButton.isEnabled) {
                    saveBtnClicked()
                    return true;
                }
            }
            return false;
        }
    }
}