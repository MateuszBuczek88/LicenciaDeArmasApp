package com.example.licenciadearmas.data

import androidx.annotation.StringRes
import com.example.licenciadearmas.R

enum class Sections(@StringRes val description:Int, @StringRes val _name:Int) {
    Tema1(description = R.string.section_1_description, _name = R.string.section_1_name),
    Tema2(description = R.string.section_2_description, _name = R.string.section_2_name),
    Tema3(description = R.string.section_3_description, _name = R.string.section_3_name),
    Tema4(description = R.string.section_4_description, _name = R.string.section_4_name),
    Tema5(description = R.string.section_5_description, _name = R.string.section_5_name),
    Tema6(description = R.string.section_6_description, _name = R.string.section_6_name),
    Tema7(description = R.string.section_7_description, _name = R.string.section_7_name)
}