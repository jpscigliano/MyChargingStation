package com.find.domain.di

import com.find.domain.AppDispatchers
import com.find.domain.AppDispatchersImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BaseDomainModule {
    @Binds
    abstract fun bindDispatchers(appDispatchersImp: AppDispatchersImp):AppDispatchers
}