package com.example.licenciadearmas

import androidx.lifecycle.ViewModel
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Sections

class LearnViewModel(val repository: IQuestionRepository,val section :Sections): ViewModel() {
}