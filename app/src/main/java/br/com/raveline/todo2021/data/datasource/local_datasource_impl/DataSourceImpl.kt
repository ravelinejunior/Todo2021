package br.com.raveline.todo2021.data.datasource.local_datasource_impl

import br.com.raveline.todo2021.data.datasource.local_datasource.LocalDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DataSourceImpl @Inject constructor(localDataSource: LocalDataSource) {
    val mLocalDataSource = localDataSource
}