package cn.xiaobaihome.xiaobaihelper.di

import cn.xiaobaihome.xiaobaihelper.helper.Constant
import cn.xiaobaihome.xiaobaihelper.helper.network.NetMgr
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api.ApiService
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api.JuHeService
import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.ApiRepository
import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.JuHeRepository
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.login.viewmodel.LoginViewModel
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit

val viewModelModule = module {

    //    viewModel { (id: Int) -> ArticleDetailViewModel(id, get(), get()) }
//    viewModel { (article: Article, nameDate: String) -> CodeDetailViewModel(article, nameDate, get(), get()) }
//    viewModel { MainViewModel(get()) }
//    viewModel { RecentViewModel(get()) }
    viewModel { HomeViewModel(get(),get()) }
    viewModel { LoginViewModel() }
//    viewModel { (category: Int?, keyWord: String?) -> CodeListViewModel(category, keyWord, get()) }
//    viewModel { RecentSearchViewModel(get()) }
//    viewModel { MyArticleViewModel(get()) }
//    viewModel { (type: Int) -> MyCollectViewModel(type, get()) }

}

val remoteModule = module {

    single { NetMgr.getRetrofit(Constant.HOST_API_JUHE) }
    single<JuHeService> { get<Retrofit>().create(JuHeService::class.java) }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }

}

val localModule = module {

    //    single<AppDatabase> { AppDatabase.getInstance(androidApplication()) }
//    single<UserDao> { get<AppDatabase>().userDao() }
//    single<ArticleDao> { get<AppDatabase>().articleDao() }

}

val repoModule = module {

    single { JuHeRepository(get()) }
    single { ApiRepository(get()) }

}


val appModule = listOf(viewModelModule, remoteModule, localModule, repoModule)