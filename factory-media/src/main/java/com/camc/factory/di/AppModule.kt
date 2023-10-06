package com.camc.factory.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.camc.factory.common.Constant
import com.camc.factory.data.FactDatabase
import com.camc.factory.data.local.datasource.FileDataSource
import com.camc.factory.data.network.FileUploadApi
import com.camc.factory.data.network.LoginApi
import com.camc.factory.data.repository.CategoryRepository
import com.camc.factory.data.repository.CategoryRepositoryImpl
import com.camc.factory.data.repository.RecordRepository
import com.camc.factory.data.repository.RecordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            // 添加拦截器或其他配置
            .build()
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFileUploadApi(): FileUploadApi {
        val baseUrl = "http://82.157.66.177:8888" // 您的服务器地址和端口
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL) // 根据实际情况设置基本URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FileUploadApi::class.java)
    }
    @Provides
    @Singleton
    fun provideFactDatabase(app: Application): FactDatabase {
        return Room.databaseBuilder(
            app,
            FactDatabase::class.java,
            FactDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: FactDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.recordCategoryDao())
    }

    @Provides
    @Singleton
    fun provideRecordRepository(db: FactDatabase): RecordRepository {
        return RecordRepositoryImpl(db.mediaRecordDao())
    }

    @Provides
    fun provideFileDataSource(): FileDataSource {
        return FileDataSource()
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

}
