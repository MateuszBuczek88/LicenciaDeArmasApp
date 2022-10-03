package com.example.licenciadearmas.di

import com.example.licenciadearmas.LearnViewModel
import com.example.licenciadearmas.TestViewModel
import com.example.licenciadearmas.data.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ILocalDataSource> {
        FromFileDataSource(
            mapOf(
                Section.Tema1 to "questionsTema1.json",
                Section.Tema2 to "questionsTema2.json",
                Section.Tema3 to "questionsTema3.json",
                Section.Tema4 to "questionsTema4.json",
                Section.Tema5 to "questionsTema5.json",
                Section.Tema6 to "questionsTema6.json",
                Section.Tema7 to "questionsTema7.json"
            ), androidContext().resources.assets
        )
    }

    single<IQuestionRepository> {
        QuestionRepository(
            get()
        )
    }

    viewModel { (section: Section) ->
        LearnViewModel(repository = get(), section = section)
    }
    viewModel {
        TestViewModel(repository = get())
    }
}