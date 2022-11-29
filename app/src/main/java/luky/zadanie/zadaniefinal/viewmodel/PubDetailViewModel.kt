package luky.zadanie.zadaniefinal.viewmodel

import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.database.Pub
import luky.zadanie.zadaniefinal.database.PubDetail
import luky.zadanie.zadaniefinal.network.PubDetailData
import luky.zadanie.zadaniefinal.network.UserResponseData

class PubDetailViewModel(private val repository: Repository): ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status


    val loading = MutableLiveData(false)

    val pubDetail: LiveData<PubDetail> = repository.getDetailPubRepository()

    fun getDetailPub(id: String){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiPubDetailRepository(id
            ) { _status.postValue(it) }
            loading.postValue(false)
        }
    }
}