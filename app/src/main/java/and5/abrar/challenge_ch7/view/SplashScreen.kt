package and5.abrar.challenge_ch7.view

import and5.abrar.challenge_ch7.datastore.UserManager
import and5.abrar.challenge_ch7.R
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash_screen.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var userManager: UserManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)
        TopTextView.startAnimation(topAnimation)
        MiddleTextView.startAnimation(middleAnimation)
        BottomTextView.startAnimation(bottomAnimation)
        userManager = UserManager(this)
        val splashtimeout = 4000

        Handler(Looper.getMainLooper()).postDelayed({
            userManager.ceklogin.asLiveData().observe(this){
                if(it == true){
                    startActivity(Intent(this, FilmActvty::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, LoginActvty::class.java))
                    finish()
                }
            }
            },splashtimeout.toLong())


    }
}