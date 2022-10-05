package com.mmd.cityweather.common.data.di

import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AssetsModule {

    @Provides
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager? {
        return context.assets
    }
}
