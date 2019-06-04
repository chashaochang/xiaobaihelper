package cn.xiaobaihome.xiaobaihelper.di

import cn.xiaobaihome.xiaobaihelper.helper.Constant
import cn.xiaobaihome.xiaobaihelper.helper.network.NetMgr
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api.JuHeService
import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.JuHeRepository
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit

val viewModelModule = module {

    //    viewModel { (id: Int) -> ArticleDetailViewModel(id, get(), get()) }
//    viewModel { (article: Article, nameDate: String) -> CodeDetailViewModel(article, nameDate, get(), get()) }
//    viewModel { MainViewModel(get()) }
//    viewModel { RecentViewModel(get()) }
    viewModel { HomeViewModel(get()) }
//    viewModel { (category: Int?, keyWord: String?) -> CodeListViewModel(category, keyWord, get()) }
//    viewModel { RecentSearchViewModel(get()) }
//    viewModel { MyArticleViewModel(get()) }
//    viewModel { (type: Int) -> MyCollectViewModel(type, get()) }
//    viewModel { LoginViewModel(get()) }

}

val remoteModule = module {

    single { NetMgr.getRetrofit(Constant.HOST_API_JUHE) }
    single<JuHeService> { get<Retrofit>().create(JuHeService::class.java) }
//    single<UserService> { get<Retrofit>().create(UserService::class.java) }

}

val localModule = module {

    //    single<AppDatabase> { AppDatabase.getInstance(androidApplication()) }
//    single<UserDao> { get<AppDatabase>().userDao() }
//    single<ArticleDao> { get<AppDatabase>().articleDao() }

}

val repoModule = module {

    single { JuHeRepository(get()) }
//    single { UserRepository(get(), get()) }

}


val appModule = listOf(viewModelModule, remoteModule, localModule, repoModule)