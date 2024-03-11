import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import com.mshdabiola.mvvn.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
//    private val iNetworkDataSource: INetworkDataSource
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = MutableStateFlow(
        MainActivityUiState.Success(
        UserData(
            useDynamicColor = false,
            themeBrand = ThemeBrand.DEFAULT,
            contrast = Contrast.Normal,
            darkThemeConfig = DarkThemeConfig.LIGHT,
            shouldHideOnboarding = true
        )))

}


sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
