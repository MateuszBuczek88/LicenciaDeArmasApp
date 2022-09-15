package com.example.licenciadearmas


import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.QuestionRepository
import com.example.licenciadearmas.data.Sections
import com.example.licenciadearmas.data.questions.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<IQuestionRepository> {
        QuestionRepository(
            questionsTema1,
            questionsTema2,
            questionsTema3,
            questionsTema4,
            questionsTema5,
            questionsTema6,
            questionsTema7
        )
    }

    viewModel { (section: Sections) ->
        LearnViewModel(repository = get(), section = section)
    }
    viewModel {
        TestViewModel(get())
    }
}