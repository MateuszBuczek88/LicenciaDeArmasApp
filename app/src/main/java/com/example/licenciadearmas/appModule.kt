package com.example.licenciadearmas


import com.example.licenciadearmas.data.*
import com.example.licenciadearmas.data.questions.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single <ILocalDataSource>(named("MemoryData")){
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
            get(named("MemoryData"))
        )
    }

    viewModel { (section: Sections) ->
        LearnViewModel(repository = get(), section = section)
    }
    viewModel {
        TestViewModel(repository = get())
    }
}