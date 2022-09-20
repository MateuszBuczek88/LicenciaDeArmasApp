package com.example.licenciadearmas


import com.example.licenciadearmas.data.*
import com.example.licenciadearmas.data.questions.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single <ILocalDataSource>{
        InMemoryDataSource(
            questionsTema1,
            questionsTema2,
            questionsTema3,
            questionsTema4,
            questionsTema5,
            questionsTema6,
            questionsTema7)
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