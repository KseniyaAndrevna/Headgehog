package com.kseniyaa.headgehog.screens

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kseniyaa.headgehog.R

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_info, container, false)

        //possibility of scaling
        rootView.info_wv.settings.builtInZoomControls = true

        //loading the WebView state if it was saved
        if (savedInstanceState != null) {
            rootView.info_wv.restoreState(savedInstanceState)
        } else {
            rootView.info_wv.loadUrl("http://www.icndb.com/api/")
        }

        rootView.info_wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }
        }

        //back button
        rootView.fab_back.setOnClickListener {
            if (info_wv.canGoBack()) {
                info_wv.goBack()
            }
        }
        return rootView
    }

    //save webView state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        info_wv.saveState(outState)
    }

    //TAG for debug
    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }
}
