package com.kseniyaa.headgehog.screens

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.kseniyaa.headgehog.MainPagesAdapter
import com.kseniyaa.headgehog.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_items.*

class MainActivity : AppCompatActivity() {
    private var viewPager: ViewPager? = null

    //declaring data arrays
    val navIcons = intArrayOf(R.drawable.ic_crown, R.drawable.ic_info)
    private val navLabels = intArrayOf(R.string.jokes, R.string.info)
    val navIconsActive = intArrayOf(R.drawable.ic_crown_active, R.drawable.ic_info_active)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //add adapter for viewPager
        setSupportActionBar(toolbar)

        val adapter = MainPagesAdapter(supportFragmentManager)

        viewPager = view_pager
        viewPager?.adapter = adapter

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    MainPagesAdapter.PAGE_JOKES -> {
                        toolbar.title = "Jokes"
                        count_et.setText("")
                    }
                    MainPagesAdapter.PAGE_INFO -> {
                        toolbar.title = "API info"
                        hideKeyboard(this@MainActivity)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        tab_layout.setupWithViewPager(viewPager)

        //settings for custom tabLayout
        for (i in 0 until tab_layout.tabCount) {
            val tab = LayoutInflater.from(this).inflate(R.layout.nav_tab, null) as LinearLayout

            val tabLabel = tab.findViewById(R.id.nav_label) as TextView
            val tabIcon = tab.findViewById(R.id.nav_icon) as ImageView

            tabLabel.text = resources.getString(navLabels[i])

            if (i == 0) {
                tabIcon.setImageResource(navIconsActive[i])
                tabLabel.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
            } else {
                tabIcon.setImageResource(navIcons[i])
            }

            tab_layout.getTabAt(i)?.customView = tab
        }

        tab_layout.addOnTabSelectedListener(
                object : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        super.onTabSelected(tab)

                        val tabView = tab.customView
                        val tabLabel = tabView!!.findViewById(R.id.nav_label) as TextView
                        val tabIcon = tabView.findViewById(R.id.nav_icon) as ImageView

                        tabLabel.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
                        tabIcon.setImageResource(navIconsActive[tab.position])
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        super.onTabUnselected(tab)

                        val tabView = tab!!.customView
                        val tabLabel = tabView!!.findViewById(R.id.nav_label) as TextView
                        val tabIcon = tabView.findViewById(R.id.nav_icon) as ImageView

                        tabLabel.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))

                        tabIcon.setImageResource(navIcons[tab.position])
                    }
                }
        )
    }

    //processing a configuration change
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        hideKeyboard(this@MainActivity)
    }

    //hide keyboard for info tab
    fun hideKeyboard(activity: Activity) {
        val view = activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    //back button
    override fun onBackPressed() {
        if (toolbar.title == "API info" && info_wv.canGoBack()) info_wv.goBack()
    }

    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }
}

