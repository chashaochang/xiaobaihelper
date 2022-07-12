package cn.xiaobaihome.xiaobaihelper.di

import cn.xiaobaihome.xiaobaihelper.api.ApiService
import cn.xiaobaihome.xiaobaihelper.api.MinerApiService
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
    fun provideApiService(): ApiService {
        return ApiService.create()
    }

    @Singleton
    @Provides
    fun provideMinerApiService(): MinerApiService {
        return MinerApiService.create()
    }
}