package luky.zadanie.zadaniefinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.network.FriendData
import luky.zadanie.zadaniefinal.network.FriendWithPubData

class FriendViewModel(private val repository: Repository): ViewModel() {

    private val _statusSuccess = MutableLiveData<String>()
    val statusSucces: LiveData<String>
        get() = _statusSuccess

    private val _statusError = MutableLiveData<String>()
    val statusError: LiveData<String>
        get() = _statusError


    private val _friendsWithPub = MutableLiveData<List<FriendWithPubData>>()
    val friendsWithPub: LiveData<List<FriendWithPubData>>
        get() = _friendsWithPub

    val loading = MutableLiveData(false)

    fun getFriendWithPub(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiFriendWithPubRepository(
                {_statusError.postValue(it)},
                {_friendsWithPub.postValue(it)},
                {  _statusSuccess.postValue(it) })
            loading.postValue(false)
        }
    }
}