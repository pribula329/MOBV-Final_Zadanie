package luky.zadanie.zadaniefinal.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.network.FriendData

class AddDeleteFriendViewModel(private val repository: Repository): ViewModel() {
    private val _statusSuccess = MutableLiveData<String>()
    val statusSucces: LiveData<String>
        get() = _statusSuccess

    private val _statusError = MutableLiveData<String>()
    val statusError: LiveData<String>
        get() = _statusError


    private val _friends = MutableLiveData<List<FriendData>>()
    val friends: LiveData<List<FriendData>>
        get() = _friends

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

    fun deleteFriend(friendName: String){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiDeleteFriendRepository(friendName,
                { _statusSuccess.postValue(it) },
                { _statusError.postValue(it) })
            loading.postValue(false)
        }
    }

    fun  getFriend(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiGetFriendRepository(
                { _statusError.postValue(it) },
                {_friends.postValue(it)},
                {_statusSuccess.postValue(it)})

            loading.postValue(false)
        }
    }


}