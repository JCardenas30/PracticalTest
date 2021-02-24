package com.sunwise.practicaltest.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sunwise.practicaltest.R

abstract class BaseFragment: Fragment() {

    private var _hasToolbar: Boolean = false
    private var _toolbarResId: Int = R.layout.toolbar
    private var _toolbarId: Int = R.id.toolbar
    private lateinit var _parentView: View
    private val _parentActivity: AppCompatActivity? by lazy { (activity as AppCompatActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _hasToolbar = arguments?.getBoolean(HAS_TOOLBAR_KEY, false) ?: false
        _toolbarResId = arguments?.getInt(RES_ID_CUSTOM_TOOLBAR, R.layout.toolbar) ?: R.layout.toolbar
        _toolbarId = arguments?.getInt(ID_CUSTOM_TOOLBAR, R.id.toolbar) ?: R.id.toolbar
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _parentView = inflater.inflate(R.layout.fragment_base, container, false)
        val fragmentContainer = _parentView.findViewById<FrameLayout>(R.id.container_view)
        fragmentContainer?.let {
            val contentView = setContentView()
            it.addView(contentView)
            if (_hasToolbar) {
                addToolbar()
            }
        }
        return _parentView
    }

    abstract fun setContentView(container: ViewGroup? = null) : View
    abstract fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSystemUI()
        onViewFragmentCreated(view, savedInstanceState)
    }

    private fun addToolbar(resIdToolbar: Int = _toolbarResId, idToolbar: Int = _toolbarId) {
        val toolbarView = LayoutInflater.from(context).inflate(resIdToolbar, null, false)
        val toolbar = toolbarView.findViewById<Toolbar>(idToolbar)
        toolbar?.let {
            toolbarView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            (_parentView as LinearLayout).addView(toolbarView, 0)
            _parentActivity?.setSupportActionBar(it)
            setHasOptionsMenu(true)
            if (!_hasToolbar) { _hasToolbar = true }
        }
    }

    fun setTitle(title: String) {
        if (_hasToolbar) {
            _parentActivity?.supportActionBar?.title = title
        }
    }

    fun showBackIconOnToolbar() {
        if (_hasToolbar) {
            _parentActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showSystemUI() {
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    fun fullScreen() {
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    fun onBackPressed(listener:() -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                listener()
            }
        })
    }

    fun onBackPressed() {
        requireActivity().onBackPressed()
    }

    companion object {
        const val HAS_TOOLBAR_KEY = "_hasToolbar"
        const val RES_ID_CUSTOM_TOOLBAR = "customToolbarResId"
        const val ID_CUSTOM_TOOLBAR = "customToolbarId"
    }

}
