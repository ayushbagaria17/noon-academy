package com.ayush.noonacademy.ui

import com.ayush.noonacademy.app.RepoComponent
import com.ayush.noonacademy.repo.OmdbRepo
import com.ayush.noonacademy.repo.OmdbRepoModule
import com.ayush.noonacademy.utils.ActivityScope
import com.ayush.noonacademy.utils.rx.AppSchedulerProvider
import com.ayush.noonacademy.utils.rx.SchedulerProvider
import dagger.Component
import dagger.Module
import dagger.Provides

@ActivityScope
@Component(modules = [SearchModule::class, OmdbRepoModule::class],
    dependencies = [RepoComponent::class])
interface SearchComponent{
    @Component.Factory
    interface Factory {
        fun create(
            repoComponent: RepoComponent
        ) : SearchComponent
    }
    fun inject(activity: SearchActivity)
}

@Module
object SearchModule {

    @ActivityScope
    @Provides
    fun schedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @ActivityScope
    @Provides
    fun searchViewModel(omdbRepo: OmdbRepo, schedulerProvider: SchedulerProvider): SearchViewModel = SearchViewModel(omdbRepo, schedulerProvider)
}