package checkvn.com.viettiepbhdt.presentation.ui.website

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.utils.Constants.ABOUT_US_URL
import checkvn.com.viettiepbhdt.utils.Constants.FAQ_URL
import checkvn.com.viettiepbhdt.utils.Constants.POLICY_URL
import checkvn.com.viettiepbhdt.utils.Constants.VIETTIEP_URL
import checkvn.com.viettiepbhdt.utils.ui
import kotlinx.android.synthetic.main.fragment_overview.toolbar
import kotlinx.android.synthetic.main.fragment_website.*

internal const val TAG_WEBSITE_FRAGMENT = "WebsiteFragment"
const val BUNDLE_URL = "BUNDLE_URL"

fun newInstance() = WebsiteFragment()
fun newInstance(url: String?): Fragment = WebsiteFragment().apply {
    arguments = Bundle(1).apply {
        putString(BUNDLE_URL, url)
    }
}

class WebsiteFragment : Fragment() {
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            url = getString(BUNDLE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_website, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListener()
        initUI()
    }

    private fun initUI() {
        refreshLayout.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorPrimary,
            R.color.colorPrimary
        )

        if (url.isNullOrEmpty()) {
            loadWebView(VIETTIEP_URL)
            url = VIETTIEP_URL
        } else {
            url?.let {
                when (it) {
                    VIETTIEP_URL -> setupToolbar(getString(R.string.about_app))
                    ABOUT_US_URL -> setupToolbar(getString(R.string.about_app))
                    POLICY_URL -> setupToolbar(getString(R.string.policy))
                    FAQ_URL -> setupToolbar(getString(R.string.q_and_a))
                }
                loadWebView(it)
            }
        }
    }

    private fun setupToolbar(title: String) {
        toolbar.setToolbarTitle(title)
        toolbar.showIconMenu(false)
    }

    private fun setListener() {
        toolbar.setOnClickBack(View.OnClickListener {
            // Navigation to overview
            (activity as MainActivity).apply {
                setSelectedNavigation(0)
            }
        })

        toolbar.setOnClickMenu(View.OnClickListener {
            (activity as MainActivity).apply {
                showDrawer()
            }
        })

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            url?.let { loadWebView(it) }
        }

        btn_try_again.setOnClickListener {
            url?.let { url -> loadWebView(url) }
        }
    }

    private fun loadWebView(url: String) {
        ui {
            showLoadingView()
            webview.webViewClient = getWebViewClient()
            webview.isHorizontalScrollBarEnabled = false
            webview.settings.javaScriptEnabled = true
            webview.settings.loadWithOverviewMode = true
            webview.settings.useWideViewPort = true
            webview.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            webview.loadUrl(url)
        }
    }

    private fun getWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                hideLoadingView()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showWebview()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideLoadingView()
            }
        }
    }

    private fun showWebview() {
        lost_connection?.isVisible = false
        refreshLayout?.isVisible = true
    }

    private fun hideWebview() {
        lost_connection?.isVisible = true
        refreshLayout?.isVisible = false
    }

    private fun showLoadingView() {
        loadingView?.isVisible = true
    }

    private fun hideLoadingView() {
        loadingView?.isVisible = false
    }
}
