package and5.abrar.challenge_ch7.view


import and5.abrar.challenge_ch7.datastore.UserManager
import and5.abrar.challenge_ch7.model.GetDataUserItem
import and5.abrar.challenge_ch7.viewmodel.ViewModelUser
import and5.abrar.challenge_ch7.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login_actvty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActvty : AppCompatActivity() {
    private lateinit var usermanager : UserManager
    private lateinit var listuserlogin : List<GetDataUserItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_actvty)
        usermanager = UserManager(this)
        btnLogin.setOnClickListener {
                login()
        }
        btnRegLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActvty::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }

    }
    private fun login(){
        val viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.loginUserAPI()
        viewModel.getLiveLogin().observe(this) {
            if (it != null) {
                listuserlogin = it
                loginAuth(listuserlogin)
                startActivity(Intent(this, FilmActvty::class.java))
            } else {
                Toast.makeText(this, "gagal login", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun loginAuth(listlogin : List<GetDataUserItem>){
        usermanager = UserManager(this)
        val nama = login_nama.text.toString()
        val  password = login_pass.text.toString()
        for(i in listlogin.indices){
            if (nama == listlogin[i].username && password == listlogin[i].password) {
               GlobalScope.launch {
                   usermanager.setBoolean(true)
                   usermanager.saveData(
                   listlogin[i].name,
                   listlogin[i].id,
                   listlogin[i].password,
                   listlogin[i].image,
                   listlogin[i].umur.toString(),
                   listlogin[i].username,
                   listlogin[i].address
                   )
               }
                Toast.makeText(this, "Selamat Datang Di indomaret", Toast.LENGTH_SHORT).show()
            }
        }
    }
}