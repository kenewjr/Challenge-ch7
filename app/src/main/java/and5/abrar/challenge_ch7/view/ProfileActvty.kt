package and5.abrar.challenge_ch7.view


import and5.abrar.challenge_ch7.datastore.UserManager
import and5.abrar.challenge_ch7.viewmodel.ViewModelUser
import and5.abrar.challenge_ch7.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_detail_actvty.*
import kotlinx.android.synthetic.main.activity_film_actvty.*
import kotlinx.android.synthetic.main.activity_profile_actvty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActvty : AppCompatActivity() {
    private lateinit var viewModelUserApi : ViewModelUser
    private lateinit var usermanager : UserManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_actvty)
        usermanager = UserManager(this)
        getDataProfile()
        btnUpdate.setOnClickListener {
            updateData()
        }
        btnLogout.setOnClickListener {
            GlobalScope.launch {
                usermanager.hapusData()
            }
            startActivity(Intent(this, LoginActvty::class.java))
        }
        findViewById<BottomNavigationView>(R.id.bottom_nav_profile).setOnClickListener {
            when (it.id) {
                R.id.favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
                R.id.Home -> startActivity(Intent(this, FilmActvty::class.java))
            }
        }
    }

    private fun getDataProfile(){
        usermanager = UserManager(this)
        usermanager.Image.asLiveData().observe(this){
            Glide.with(this).load(it).into(profileImage)
        }
       usermanager.Address.asLiveData().observe(this){
           up_address.setText(it)
       }
        usermanager.Nama.asLiveData().observe(this){
            up_nama.setText(it)
        }
        usermanager.Umur.asLiveData().observe(this){
            up_umur.setText(it)
        }
        usermanager.userName.asLiveData().observe(this){
            up_username.setText(it)
        }
        usermanager.Pass.asLiveData().observe(this){
            up_pass.setText(it)
        }


    }

    private fun updateData() {
        usermanager = UserManager(this)

        var id = ""
        val nama = up_nama.text.toString()
        val pass = up_pass.text.toString()
        val user = up_username.text.toString()
        val alamat = up_address.text.toString()
        val umur = up_umur.text.toString()
        val image =  usermanager.Image.toString()

        usermanager.Id.asLiveData().observe(this){
            id = it.toString()
        }
        AlertDialog.Builder(this)
            .setTitle("Update data")
            .setMessage("Yakin ingin mengupdate data?")
            .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, i : Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ dialogInterface : DialogInterface, i : Int ->
                viewModelUserApi = ViewModelProvider(this).get(ViewModelUser::class.java)
                viewModelUserApi.updateUserAPI(id.toInt(),nama,pass,user,alamat,umur,image)
                Toast.makeText(this, "Update data berhasil", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    usermanager.saveData(
                       nama,
                        id,
                        pass,
                        image,
                        umur,
                        user,
                        alamat
                    )
                }
                startActivity(Intent(this, FilmActvty::class.java))
            }.show()
    }
}