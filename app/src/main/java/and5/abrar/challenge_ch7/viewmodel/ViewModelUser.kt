package and5.abrar.challenge_ch7.viewmodel

import and5.abrar.challenge_ch7.api.ApiClient
import and5.abrar.challenge_ch7.model.GetDataUserItem
import and5.abrar.challenge_ch7.model.PostNewUser
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewModelUser: ViewModel() {
    var liveDataLogin : MutableLiveData<List<GetDataUserItem>?> = MutableLiveData()
    var liveDataUpdate : MutableLiveData<PostNewUser?> = MutableLiveData()

    fun getLiveLogin(): MutableLiveData<List<GetDataUserItem>?> {
        return liveDataLogin
    }

    fun loginUserAPI(){
        ApiClient.instance.allUser()
            .enqueue(object : Callback<List<GetDataUserItem>>{
                override fun onResponse(call: Call<List<GetDataUserItem>>, response: Response<List<GetDataUserItem>>) {
                    if (response.isSuccessful){
                        liveDataLogin.postValue(response.body())
                    }else{
                        liveDataLogin.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<GetDataUserItem>>, t: Throwable) {
                    liveDataLogin.postValue(null)
                }

            })
    }

    fun updateUserAPI(id :Int, name : String,pass:String, username: String, address: String, umur : String, image:String){
        ApiClient.instance.updateUser(id.toString(), name,pass, username, address, umur,image)
            .enqueue(object : Callback<PostNewUser>{
                override fun onResponse(call: Call<PostNewUser>, response: Response<PostNewUser>) {
                    if (response.isSuccessful){
                        liveDataUpdate.postValue(response.body())
                    }else{
                        liveDataUpdate.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PostNewUser>, t: Throwable) {
                    liveDataUpdate.postValue(null)
                }

            })
    }
    fun addNewUserApi(
        alamat: String,
        image: String,
        umur: Int,
        username: String,
        password: String,
        name: String
    ) : Boolean{
        var messageResponse = false
        ApiClient.instance.postDataUser(
            PostNewUser(alamat,image,name,password,umur,username)
        ).enqueue(object : Callback<GetDataUserItem>{
            override fun onResponse(call: Call<GetDataUserItem>, response: Response<GetDataUserItem>) {
                messageResponse = response.isSuccessful
            }

            override fun onFailure(call: Call<GetDataUserItem>, t: Throwable) {
                messageResponse = false
            }

        })
        return messageResponse
    }
}