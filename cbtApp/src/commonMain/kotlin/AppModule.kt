import com.mshdabiola.analytics.di.analyticsModule
import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val appModule = module {
    includes(analyticsModule)
    commonViewModel { MainViewModel() }
}
