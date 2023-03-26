package com.example.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivitySplashScreen2Binding

@Suppress("DEPRECATION", "COMPATIBILITY_WARNING")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreen2Binding
    private lateinit var ivShop: ImageView
    private lateinit var splashLayout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreen2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactorySetting(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            setThemeSplashScreen(isDarkModeActive)

        }

        splashLayout = binding.splashLayout
        ivShop = binding.ivShop

        ivShop.alpha = 0f
        ivShop.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }

    private fun setThemeSplashScreen(isDark: Boolean) {
        val drawable = if (isDark) R.drawable.github_logo_white else R.drawable.github_logo_black
        ivShop.setImageDrawable(ContextCompat.getDrawable(ivShop.context, drawable))

        val color = if(isDark) R.color.black else R.color.white
        splashLayout.setBackgroundColor(ContextCompat.getColor(splashLayout.context, color))

    }
}