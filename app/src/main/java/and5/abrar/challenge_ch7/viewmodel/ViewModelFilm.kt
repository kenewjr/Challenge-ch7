package and5.abrar.challenge_ch7.viewmodel


import and5.abrar.challenge_ch7.api.ApiService
import and5.abrar.challenge_ch7.model.GetDataFilmItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFilm @Inject constructor(apiService: ApiService):ViewModel() {

    private var liveDataFilm = MutableLiveData<List<GetDataFilmItem>>()
    val film : LiveData<List<GetDataFilmItem>> = liveDataFilm

    init {
        viewModelScope.launch {
            val datafilm = apiService.getAllfilm()
            delay(2000)
            liveDataFilm.value = datafilm
        }
    }

}