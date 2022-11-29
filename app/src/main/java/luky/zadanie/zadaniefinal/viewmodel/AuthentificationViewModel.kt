package luky.zadanie.zadaniefinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.network.UserResponseData

class AuthentificationViewModel(private val repository: Repository): ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    val user = MutableLiveData<UserResponseData>(null)

    val loading = MutableLiveData(false)

    fun login(name: String, password: String){
        viewModelScope.launch {
            loading.postValue(true)
            repository.loginUserRepository(
                name,password,
                { _status.postValue(it) },
                { user.postValue(it) }
            )
            loading.postValue(false)
        }

    }

    fun register(name: String, password: String){
        viewModelScope.launch {
            loading.postValue(true)
            repository.registerUserRepository(
                name,password,
                { _status.postValue(it) },
                { user.postValue(it) }
            )
            loading.postValue(false)
        }
    }
}