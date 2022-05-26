@file:Suppress("DEPRECATION")

package and5.abrar.challenge_ch7.view


import and5.abrar.challenge_ch7.R
import and5.abrar.challenge_ch7.model.GetDataFilmItem
import and5.abrar.challenge_ch7.room.Favorite
import and5.abrar.challenge_ch7.room.FavoriteDatabase
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_detail_actvty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Suppress("DeferredResultUnused")
class DetailActvty : AppCompatActivity() {
    private var filmDb: FavoriteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_actvty)
        filmDb = FavoriteDatabase.getinstance(this)
        val detailfilm = intent.getParcelableExtra("detailfilm") as GetDataFilmItem?
        val detailfav = intent.getParcelableExtra("detailfav") as Favorite?
        val bottomNavigasi = BottomNavigationView.OnNavigationItemSelectedListener { item->
            when (item.itemId) {
                R.id.favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.Home -> {
                    startActivity(Intent(this, FilmActvty::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.AddButton -> {
                    if (detailfilm != null){
                        addfavfilm()
                        startActivity(Intent(this, FavoriteActivity::class.java))
                        return@OnNavigationItemSelectedListener true
                    }else if(detailfav != null){
                        Toast.makeText(this, "Sudah masuk Favorite", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            false
        }
        val botnav = findViewById<BottomNavigationView>(R.id.bottom_nav_detail)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        if(detailfilm != null){
            tvJudul.text = detailfilm.name
            tvsutradara.text = detailfilm.director
            tvtglFilm.text = detailfilm.date
            tvdesc.text = detailfilm.description
            Glide.with(this).load(detailfilm.image).into(imgdetail)
        }else if(detailfav != null){
            tvJudul.text = detailfav.title
            tvsutradara.text = detailfav.director
            tvtglFilm.text = detailfav.releaseDate
            tvdesc.text = detailfav.synopsis
            Glide.with(this).load(detailfav.image).into(imgdetail)
        }
    }
    private fun addfavfilm(){

        val detailFilm = intent.getParcelableExtra<GetDataFilmItem>("detailfilm")

        GlobalScope.async {
            val director = detailFilm!!.director
            val releasedate = detailFilm.date
            val synopsis = detailFilm.description
            val title = detailFilm.name
            val image = detailFilm.image

            filmDb?.favoriteduo()?.addFavorite(Favorite(null, director, image, releasedate, synopsis, title) )

        }
    }
}