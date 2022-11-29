package luky.zadanie.zadaniefinal.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.database.Pub

class PubViewModel(private val repository: Repository): ViewModel()  {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status


    val loading = MutableLiveData(false)

    val allPubs: LiveData<List<Pub>> = repository.getPubsRepository()


    private fun insertPubs() {
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiPubListRepository {
                _status.postValue(it)
            }
            loading.postValue(false)
        }
    }

    fun addPubsToDatabase() {
        insertPubs()
    }


}