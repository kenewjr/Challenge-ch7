package and5.abrar.challenge_ch7.view

import and5.abrar.challenge_ch7.datastore.UserManager

import and5.abrar.challenge_ch7.R
import and5.abrar.challenge_ch7.viewmodel.ViewModelFilm
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_film_actvty.*

@AndroidEntryPoint
class FilmActvty : AppCompatActivity() {
    private lateinit var adapterFilm: AdapterFilm
    private lateinit var userManager: UserManager
    private val bottomNavigasi = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when (item.itemId) {
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.Home -> {
                return@OnNavigationItemSelectedListener false
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_actvty)
        userManager = UserManager(this)
        userManager.userName.asLiveData().observe(this) {
            welcome.text = "halo, $it"
        }
        val botnav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        iniViewmodel()
        avatar.setOnClickListener {
            startActivity(Intent(this, ProfileActvty::class.java))
        }
    }
    private fun iniViewmodel(){
        adapterFilm = AdapterFilm {
            val pindahdata = Intent(applicationContext, DetailActvty::class.java)
            pindahdata.putExtra("detailfilm", it)
            startActivity(pindahdata)
        }
        rvFilm.layoutManager=LinearLayoutManager(this)
        rvFilm.adapter=adapterFilm
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.film.observe(this) {
            if (it != null) {
                adapterFilm.setFilm(it)
                adapterFilm.notifyDataSetChanged()
            }
        }
    }
}