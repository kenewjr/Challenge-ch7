@file:Suppress("DEPRECATION")

package and5.abrar.challenge_ch7.view

import and5.abrar.challenge_ch7.R
import and5.abrar.challenge_ch7.room.FavoriteDatabase
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private var filmDb: FavoriteDatabase? = null

    private val bottomNavigasi = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when (item.itemId) {
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
                return@OnNavigationItemSelectedListener true
            }
            R.id.Home -> {
                startActivity(Intent(this, FilmActvty::class.java))
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val botnav = findViewById<BottomNavigationView>(R.id.bottom_nav_fav)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        getFilmFav()
        filmDb = FavoriteDatabase.getinstance(this)
    }

    @SuppressLint("SetTextI18n")
    fun getFilmFav(){
        rv_favfilm.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch {
            val listFavFilm = filmDb?.favoriteduo()?.getFavorite()
            runOnUiThread{
                if (listFavFilm?.size != null) {
                    if(listFavFilm.isEmpty()){
                        tv.text = "data kosong"
                    }else{
                        listFavFilm.let { it ->
                            rv_favfilm.adapter = AdapterFilmFavourite(it){
                                val pindah = Intent(applicationContext,DetailActvty::class.java)
                                pindah.putExtra("detailfav",it)
                                startActivity(pindah)
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getFilmFav()
    }

}