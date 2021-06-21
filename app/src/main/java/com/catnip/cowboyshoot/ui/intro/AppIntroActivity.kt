package com.catnip.cowboyshoot.ui.intro

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.catnip.cowboyshoot.R
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AppIntroActivity : AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        isSkipButtonEnabled = false
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.text_intro_title_page_1).uppercase(),
                description = getString(R.string.text_intro_desc_page_1).uppercase(),
                titleColor = Color.WHITE,
                descriptionColor = Color.WHITE,
                titleTypefaceFontRes = R.font.pixelated_font,
                descriptionTypefaceFontRes = R.font.pixelated_font,
                backgroundDrawable = R.drawable.bg_game,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.text_intro_title_page_2).uppercase(),
                description = getString(R.string.text_intro_desc_page_2).uppercase(),
                titleColor = Color.WHITE,
                descriptionColor = Color.WHITE,
                imageDrawable = R.drawable.ic_cowboy_left_shoot_false,
                titleTypefaceFontRes = R.font.pixelated_font,
                descriptionTypefaceFontRes = R.font.pixelated_font,
                backgroundColor = ContextCompat.getColor(this,R.color.cowboy_light),
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.text_intro_title_page_3).uppercase(),
                description = getString(R.string.text_intro_desc_page_3).uppercase(),
                titleColor = Color.WHITE,
                descriptionColor = Color.WHITE,
                imageDrawable = R.drawable.ic_cowboy_right_shoot_false,
                titleTypefaceFontRes = R.font.pixelated_font,
                descriptionTypefaceFontRes = R.font.pixelated_font,
                backgroundColor = ContextCompat.getColor(this,R.color.cowboy_light),
            )
        )
        addSlide(PlayerNameFormFragment())
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        if(currentFragment is PlayerNameFormFragment){
            currentFragment.navigateToMenuGame()
        }
    }
}