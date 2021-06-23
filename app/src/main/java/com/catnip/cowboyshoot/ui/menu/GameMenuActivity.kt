package com.catnip.cowboyshoot.ui.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.catnip.cowboyshoot.R
import com.catnip.cowboyshoot.data.preference.UserPreference
import com.catnip.cowboyshoot.databinding.ActivityGameMenuBinding
import com.catnip.cowboyshoot.ui.game.GameActivity

class GameMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityGameMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitlePage()
        setClickListeners()

    }

    private fun setTitlePage() {
        binding.tvTitleFieldName.text = String.format(
            getString(
                R.string.text_title_menu,
                UserPreference(this).userName
            )
        )
    }

    private fun setClickListeners() {
        binding.ivVsComputer.setOnClickListener {
            GameActivity.startThisActivity(this, GameActivity.PLAY_MODE_VS_COMPUTER)
        }
        binding.ivVsPlayer.setOnClickListener {
            GameActivity.startThisActivity(this, GameActivity.PLAY_MODE_VS_PLAYER)
        }
    }


}