package com.catnip.cowboyshoot.ui.game

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.catnip.cowboyshoot.R
import com.catnip.cowboyshoot.databinding.ActivityMainBinding
import com.catnip.cowboyshoot.enum.CharacterMovementPosition
import com.catnip.cowboyshoot.enum.CharacterPosition
import com.catnip.cowboyshoot.enum.CharacterShootState
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = GameActivity::class.java.simpleName

    private var posPlayerOne: Int = 0
    private var posPlayerTwo: Int = 0
    private var isGameFinished: Boolean = false

    private var playMode: Int = PLAY_MODE_VS_COMPUTER
    private var playTurn: CharacterPosition = CharacterPosition.LEFT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        getIntentExtras()
        setInitialState()
        setClickEvent()
    }

    private fun getIntentExtras(){
        playMode = intent.getIntExtra(ARG_EXTRA_PLAY_MODE, PLAY_MODE_VS_COMPUTER)
    }

    private fun showControllerPlayer(characterPosition: CharacterPosition,isVisible : Boolean){
        if(characterPosition == CharacterPosition.LEFT){
            binding.llPlayerLeft.visibility = if(isVisible) View.VISIBLE else View.GONE
        }else{
            binding.llPlayerRight.visibility = if(isVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setInitialState() {
        //set pos user for idle
        setCharacterMovement(posPlayerOne, CharacterPosition.LEFT, CharacterShootState.IDLE)
        //set pos for computer
        setCharacterMovement(posPlayerTwo, CharacterPosition.RIGHT, CharacterShootState.IDLE)

        if(playMode == PLAY_MODE_VS_PLAYER){
            Toast.makeText(this, "Player 1 Turn", Toast.LENGTH_SHORT).show()
            showControllerPlayer(CharacterPosition.LEFT,true)
            showControllerPlayer(CharacterPosition.RIGHT,false)
            playTurn = CharacterPosition.LEFT
            //LOCK PLAYER 1
            binding.tvActionGame.text = getString(R.string.text_lock_player_one)
        }else{
            //set text for inital start button
            binding.tvActionGame.text = getString(R.string.text_start_game)
        }
    }

    private fun setClickEvent() {
        binding.ivArrowTop.setOnClickListener {
            if(playTurn == CharacterPosition.LEFT){
                //for movement top player one
                if (!isGameFinished && posPlayerOne < 1) {
                    posPlayerOne++
                    Log.d(TAG, "setClickEvent: $posPlayerOne")
                    setCharacterMovement(posPlayerOne, CharacterPosition.LEFT, CharacterShootState.IDLE)
                }
            }else{
                //for movement top player two
                if (!isGameFinished && posPlayerTwo < 1) {
                    posPlayerTwo++
                    Log.d(TAG, "setClickEvent: $posPlayerTwo")
                    setCharacterMovement(posPlayerTwo, CharacterPosition.RIGHT, CharacterShootState.IDLE)
                }
            }
        }
        binding.ivArrowDown.setOnClickListener {
            //for movement bottom player one
            if(playTurn == CharacterPosition.LEFT){
                if (!isGameFinished && posPlayerOne > -1) {
                    posPlayerOne--
                    Log.d(TAG, "setClickEvent: $posPlayerOne")
                    setCharacterMovement(posPlayerOne, CharacterPosition.LEFT, CharacterShootState.IDLE)
                }
            }else{
                if (!isGameFinished && posPlayerTwo > -1) {
                    posPlayerTwo--
                    Log.d(TAG, "setClickEvent: $posPlayerTwo")
                    setCharacterMovement(posPlayerTwo, CharacterPosition.RIGHT, CharacterShootState.IDLE)
                }
            }
        }
        binding.tvActionGame.setOnClickListener {
            if (isGameFinished) {
                resetGame()
            } else {
                //game is not finished, and game is first run
                if(playMode == PLAY_MODE_VS_PLAYER){
                    //when player vs player
                    if(playTurn == CharacterPosition.LEFT){
                        //set playturn ke kanan
                        playTurn = CharacterPosition.RIGHT
                        Toast.makeText(this, "Player 2 Turn", Toast.LENGTH_SHORT).show()
                        //and show right player and hide left player
                        showControllerPlayer(CharacterPosition.LEFT,false)
                        showControllerPlayer(CharacterPosition.RIGHT,true)
                        //"FIRE !"
                        binding.tvActionGame.text = getString(R.string.text_fire_player)
                    }else{
                        //condition when playturn is RIGHT, game should be played
                        startGame()
                    }
                }else{
                    //when player vs computer
                    startGame()
                }
            }
        }
    }

    private fun resetGame() {
        Log.d(TAG, "resetGame: before pos user = {$posPlayerOne} pos comp = {$posPlayerOne} ")
        isGameFinished = false
        posPlayerOne = 0
        posPlayerTwo = 0
        binding.tvWinState.text = ""
        setInitialState()
        Log.d(TAG, "resetGame: after pos user = {$posPlayerOne} pos comp = {$posPlayerOne} ")
    }


    private fun startGame() {
        //show All Controller
        showControllerPlayer(CharacterPosition.LEFT,true)
        showControllerPlayer(CharacterPosition.RIGHT,true)

        if (playMode == PLAY_MODE_VS_COMPUTER){
            //set random position for computer
            posPlayerTwo = Random.nextInt(-1, 2)
        }
        //set shoot state user
        setCharacterMovement(posPlayerOne, CharacterPosition.LEFT, CharacterShootState.SHOOT)
        //set shoot state for enemy
        setCharacterMovement(posPlayerTwo, CharacterPosition.RIGHT, CharacterShootState.SHOOT)
        //logic for winners
        if (posPlayerOne == posPlayerTwo) {
            //user lose
            setCharacterMovement(posPlayerOne, CharacterPosition.LEFT, CharacterShootState.DEAD)
            binding.tvWinState.text = getString(R.string.text_user_lose)
        } else {
            //user win
            setCharacterMovement(posPlayerTwo, CharacterPosition.RIGHT, CharacterShootState.DEAD)
            binding.tvWinState.text = getString(R.string.text_user_win)
        }
        isGameFinished = true
        binding.tvActionGame.text = getString(R.string.text_reset_game)

    }

    private fun setCharacterMovement(
        position: Int,
        characterPosition: CharacterPosition,
        characterShootState: CharacterShootState
    ) {
        val ivCharTop: ImageView?
        val ivCharMiddle: ImageView?
        val ivCharBottom: ImageView?
        val imgResChar: Drawable?
        if (characterPosition == CharacterPosition.LEFT) {
            ivCharTop = binding.ivPlayerLeft0
            ivCharMiddle = binding.ivPlayerLeft1
            ivCharBottom = binding.ivPlayerLeft2
            imgResChar = ContextCompat.getDrawable(
                this,
                when (characterShootState) {
                    CharacterShootState.IDLE -> R.drawable.ic_cowboy_left_shoot_false
                    CharacterShootState.SHOOT -> R.drawable.ic_cowboy_left_shoot_true
                    CharacterShootState.DEAD -> R.drawable.ic_cowboy_left_dead
                }
            )
        } else {
            ivCharTop = binding.ivPlayerRight0
            ivCharMiddle = binding.ivPlayerRight1
            ivCharBottom = binding.ivPlayerRight2
            imgResChar = ContextCompat.getDrawable(
                this,
                when (characterShootState) {
                    CharacterShootState.IDLE -> R.drawable.ic_cowboy_right_shoot_false
                    CharacterShootState.SHOOT -> R.drawable.ic_cowboy_right_shoot_true
                    CharacterShootState.DEAD -> R.drawable.ic_cowboy_right_dead
                }
            )
        }

        when (CharacterMovementPosition.fromInt(position)) {
            CharacterMovementPosition.TOP -> {
                ivCharTop.visibility = View.VISIBLE
                ivCharMiddle.visibility = View.INVISIBLE
                ivCharBottom.visibility = View.INVISIBLE
                ivCharTop.setImageDrawable(imgResChar)
            }
            CharacterMovementPosition.MIDLLE -> {
                ivCharTop.visibility = View.INVISIBLE
                ivCharMiddle.visibility = View.VISIBLE
                ivCharBottom.visibility = View.INVISIBLE
                ivCharMiddle.setImageDrawable(imgResChar)
            }
            CharacterMovementPosition.BOTTOM -> {
                ivCharTop.visibility = View.INVISIBLE
                ivCharMiddle.visibility = View.INVISIBLE
                ivCharBottom.visibility = View.VISIBLE
                ivCharBottom.setImageDrawable(imgResChar)
            }
        }
    }

    companion object {
        const val ARG_EXTRA_PLAY_MODE = "ARG_EXTRA_PLAY_MODE"
        const val PLAY_MODE_VS_COMPUTER = 0
        const val PLAY_MODE_VS_PLAYER = 1

        fun startThisActivity(context: Context?, playMode: Int) {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(ARG_EXTRA_PLAY_MODE, playMode)
            context?.startActivity(intent)
        }
    }


}