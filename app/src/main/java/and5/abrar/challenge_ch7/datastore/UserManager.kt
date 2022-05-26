package and5.abrar.challenge_ch7.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_pref")

    companion object{
        val NAMA = preferencesKey<String>("nama")
        val PASS = preferencesKey<String>("pass")
        val IMAGE = preferencesKey<String>("IMAGE")
        val ID = preferencesKey<String>("id")
        val UMUR = preferencesKey<String>("umur")
        val USERNAME = preferencesKey<String>("username")
        val ADDRESS = preferencesKey<String>("ADDRESS")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveData(nama: String, id:String,pass: String, image: String, umur:String,username:String,adress:String){
        dataStore.edit {
            it[NAMA]= nama
            it[ID]= id
            it[PASS]= pass
            it[IMAGE] = image
            it[UMUR]= umur
            it[USERNAME]= username
            it[ADDRESS]= adress
        }
    }

    val nama : Flow<String> = dataStore.data.map {
        it[NAMA] ?:""
    }
    val id : Flow<String> = dataStore.data.map {
        it[ID] ?:""
    }
    val pass : Flow<String> = dataStore.data.map {
        it[PASS] ?:""
    }
    val umur : Flow<String> = dataStore.data.map {
        it[UMUR] ?:""
    }
    val userName : Flow<String> = dataStore.data.map {
        it[USERNAME] ?:""
    }
    val gambar : Flow<String> = dataStore.data.map {
        it[IMAGE] ?:""
    }
    val a : Flow<String> = dataStore.data.map {
        it[ADDRESS] ?:""
    }
    val ceklogin : Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }
    suspend fun hapusData(){
        dataStore.edit {
            it.clear()
        }

    }

    suspend fun setBoolean(boolean: Boolean){
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

}