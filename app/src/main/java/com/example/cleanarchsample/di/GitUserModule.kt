package com.example.cleanarchsample.di

import com.example.cleanarchsample.data.datasource.GitRepoRemoteDataSource
import com.example.cleanarchsample.data.datasource.GitRepoRemoteDataSourceImpl
import com.example.cleanarchsample.data.datasource.GitUserRemoteDataSource
import com.example.cleanarchsample.data.datasource.GitUserRemoteDataSourceImpl
import com.example.cleanarchsample.data.repository.GitRepoRepositoryImpl
import com.example.cleanarchsample.data.service.GitRepoService
import com.example.cleanarchsample.data.service.GitUserService
import com.example.cleanarchsample.domain.repository.GitRepoRepository
import com.example.cleanarchsample.domain.repository.GitUserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
interface UserModule {

    @Binds
    fun bindGitUserRepository(gitUserRepository: GitUserRepository): GitUserRepository

    @Binds
    fun bindGitUserRemoteDataSource(gitUserRemoteDataSource: GitUserRemoteDataSourceImpl): GitUserRemoteDataSource

    @Binds
    fun bindGitRepositoriesRemoteDataSource(gitRepoRepository: GitRepoRepositoryImpl): GitRepoRepository

    @Binds
    fun bindGitRepoRemoteDataSource(gitRepoRemoteDataSource: GitRepoRemoteDataSourceImpl): GitRepoRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object UserNetworkingModule {

    @Provides
    fun providesGitUserService(retrofit: Retrofit): GitUserService {
        return retrofit.create()
    }

    @Provides
    fun providesGitRepoService(retrofit: Retrofit): GitRepoService {
        return retrofit.create()
    }
}