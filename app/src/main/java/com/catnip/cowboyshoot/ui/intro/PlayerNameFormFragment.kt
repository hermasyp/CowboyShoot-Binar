package com.catnip.cowboyshoot.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.catnip.cowboyshoot.R
import com.catnip.cowboyshoot.data.preference.UserPreference
import com.catnip.cowboyshoot.databinding.FragmentPlayerNameFormBinding
import com.catnip.cowboyshoot.ui.menu.GameMenuActivity
import com.google.android.material.snackbar.Snackbar


class PlayerNameFormFragment : Fragment() {
    private lateinit var binding: FragmentPlayerNameFormBinding
    private lateinit var userPreference: UserPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlayerNameFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefilledCurrentName()
    }

    private fun prefilledCurrentName() {
        //prefiiled form with existing name when page created
        context?.let {
            userPreference = UserPreference(it)
            binding.etPlayerName.setText(userPreference.userName.orEmpty())// or empty == ""
        }
    }

    private fun isFormFilled(): Boolean {
        val name = binding.etPlayerName.text.toString()
        var isFormValid = true

        if (name.isEmpty()) {
            //inform user that username is empty
            isFormValid = false
            //text_error_toast_name_empty = name should be filled !
            Snackbar.make(binding.root, getString(R.string.text_error_toast_name_empty),Snackbar.LENGTH_SHORT).show()
        }
        return isFormValid
    }

    fun navigateToMenuGame(){
        if(isFormFilled()){
            userPreference.userName = binding.etPlayerName.text.toString()
            context?.startActivity(Intent(context,GameMenuActivity::class.java))
        }
    }

}