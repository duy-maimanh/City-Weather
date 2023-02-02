/*
 * Developed by 2022 Duy Mai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmd.cityweather.data.di

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.CityWeatherDatabase
import com.mmd.cityweather.common.data.database.RoomCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestCacheModule {

    @Provides
    fun provideRoomDatabase(): CityWeatherDatabase {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            CityWeatherDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideCached(database: CityWeatherDatabase): Cache {
        return RoomCache(database.weatherDao(), database.citiesDao())
    }
}
