package luky.zadanie.zadaniefinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.database.Pub
import luky.zadanie.zadaniefinal.database.PubNear

class NearPubViewModel(private val repository: Repository): ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status


    val loading = MutableLiveData(false)

    val myPub= MutableLiveData<PubNear>(null)

    val allNearPubs: LiveData<List<PubNear>> = repository.getNearPubRepository()


    fun insertNearPubs(myLat: Double, myLon: Double) {
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiPubNearRepository(myLat = myLat, myLon = myLon){
                _status.postValue(it)
            }
            loading.postValue(false)
        }
    }

    fun deleteNearPubs(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.deleteNearPubRepository()
            loading.postValue(false)
        }
    }


}