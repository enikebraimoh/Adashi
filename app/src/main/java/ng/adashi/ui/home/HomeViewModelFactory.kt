package ng.adashi.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.adashi.repository.HomeRepository

class HomeViewModelFactory(
    val app : Application,
    val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(app, homeRepository) as T
    }
}