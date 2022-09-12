package com.example.licenciadearmas.data

import androidx.annotation.StringRes
import com.example.licenciadearmas.R

enum class Sections(@StringRes val descriptionResId:Int, @StringRes val nameResId:Int) {
    Tema1(descriptionResId = R.string.section_1_description, nameResId = R.string.section_1_name),
    Tema2(descriptionResId = R.string.section_2_description, nameResId = R.string.section_2_name),
    Tema3(descriptionResId = R.string.section_3_description, nameResId = R.string.section_3_name),
    Tema4(descriptionResId = R.string.section_4_description, nameResId = R.string.section_4_name),
    Tema5(descriptionResId = R.string.section_5_description, nameResId = R.string.section_5_name),
    Tema6(descriptionResId = R.string.section_6_description, nameResId = R.string.section_6_name),
    Tema7(descriptionResId = R.string.section_7_description, nameResId = R.string.section_7_name)
}