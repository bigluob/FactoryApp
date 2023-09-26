package com.camc.factory.di

import android.app.Application
import androidx.room.Room
import com.camc.factory.data.FactDatabase
import com.camc.factory.data.repository.CategoryRepository
import com.camc.factory.data.repository.CategoryRepositoryImpl
import com.camc.factory.data.repository.RecordRepository
import com.camc.factory.data.repository.RecordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  /*  @Provides
    @Singleton
    fun provideApiService(loginApi: LoginApi): LoginService {
        return LoginService(loginApi)
    }*/
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
  /*  @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategorys = GetCategorys(repository),
            deleteCategory = DeleteCategory(repository),
            addCategory = AddCategory(repository),
            getCategory = GetCategory(repository)
        )
    }*/
}