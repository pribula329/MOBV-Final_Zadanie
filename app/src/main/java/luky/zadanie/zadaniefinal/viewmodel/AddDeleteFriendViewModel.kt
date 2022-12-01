package luky.zadanie.zadaniefinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import luky.zadanie.zadaniefinal.Repository

class AddDeleteFriendViewModel(private val repository: Repository): ViewModel() {
    private val _statusSuccess = MutableLiveData<String>()
    val statusSucces: LiveData<String>
        get() = _statusSuccess

    private val _statusError = MutableLiveData<String>()
    val statusError: LiveData<String>
        get() = _statusError

    val loading = MutableLiveData(false)


    fun addFriend(friendName: String){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiAddFriendRepository(friendName,
                { _statusSuccess.postValue(it) },
                { _statusError.postValue(it) })
            loading.postValue(false)
        }
    }

    fun  getFriend(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiGetFriendRepository { _statusError.postValue(it) }
            loading.postValue(false)
        }
    }


}