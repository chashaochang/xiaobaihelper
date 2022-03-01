package cn.xiaobaihome.xiaobaihelper.di

import cn.xiaobaihome.xiaobaihelper.helper.Constant
import cn.xiaobaihome.xiaobaihelper.api.NetMgr
import cn.xiaobaihome.xiaobaihelper.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideJuHeService(): ApiService {
        return NetMgr.getRetrofit(Constant.HOST_API).create(ApiService::class.java)
    }
}
//val viewModelModule = module {
//
//    //    viewModel { (id: Int) -> ArticleDetailViewModel(id, get(), get()) }
////    viewModel { (article: Article, nameDate: String) -> CodeDetailViewModel(article, nameDate, get(), get()) }
////    viewModel { MainViewModel(get()) }
////    viewModel { RecentViewModel(get()) }
//    viewModel { HomeViewModel(get(),get()) }
//    viewModel { LoginViewModel() }
////    viewModel { (category: Int?, keyWord: String?) -> CodeListViewModel(category, keyWord, get()) }
////    viewModel { RecentSearchViewModel(get()) }
////    viewModel { MyArticleViewModel(get()) }
////    viewModel { (type: Int) -> MyCollectViewModel(type, get()) }
//
//}
//
//val remoteModule = module {
//
//    single { NetMgr.getRetrofit(Constant.HOST_API_JUHE) }
//    single<JuHeService> { get<Retrofit>().create(JuHeService::class.java) }
//    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
//
//}
//
//val localModule = module {
//
//    //    single<AppDatabase> { AppDatabase.getInstance(androidApplication()) }
////    single<UserDao> { get<AppDatabase>().userDao() }
////    single<ArticleDao> { get<AppDatabase>().articleDao() }
//
//}
//
//val repoModule = module {
//
//    single { JuHeRepository(get()) }
//    single { ApiRepository(get()) }
//
//}
//
//
//val appModule = listOf(viewModelModule, remoteModule, localModule, repoModule)