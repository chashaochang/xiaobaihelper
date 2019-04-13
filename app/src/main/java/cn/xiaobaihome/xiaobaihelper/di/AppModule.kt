package cn.xiaobaihome.xiaobaihelper.di

import org.koin.dsl.module.module

val viewModelModule = module {

//    viewModel { (id: Int) -> ArticleDetailViewModel(id, get(), get()) }
//    viewModel { (article: Article, nameDate: String) -> CodeDetailViewModel(article, nameDate, get(), get()) }
//    viewModel { MainViewModel(get()) }
//    viewModel { RecentViewModel(get()) }
//    viewModel { (tid: Int, keyWord: String?) -> ArticleListViewModel(tid, keyWord, get()) }
//    viewModel { (category: Int?, keyWord: String?) -> CodeListViewModel(category, keyWord, get()) }
//    viewModel { RecentSearchViewModel(get()) }
//    viewModel { MyArticleViewModel(get()) }
//    viewModel { (type: Int) -> MyCollectViewModel(type, get()) }
//    viewModel { LoginViewModel(get()) }

}

val remoteModule = module {

//    single<Retrofit> { NetMgr.getRetrofit(Constants.HOST_API) }
//    single<PaoService> { get<Retrofit>().create(PaoService::class.java) }
//    single<UserService> { get<Retrofit>().create(UserService::class.java) }

}

val localModule = module {

//    single<AppDatabase> { AppDatabase.getInstance(androidApplication()) }
//    single<UserDao> { get<AppDatabase>().userDao() }
//    single<ArticleDao> { get<AppDatabase>().articleDao() }

}

val repoModule = module {

//    single { PaoRepository(get(), get()) }
//    single { UserRepository(get(), get()) }

}


val appModule = listOf(viewModelModule, remoteModule, localModule, repoModule)