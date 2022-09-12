package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.findNavController
import com.example.licenciadearmas.data.Sections


class ChooseSectionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {

                ChooseSectionContent {
                    findNavController().navigate(
                        ChooseSectionFragmentDirections.actionChooseSectionFragmentToLearnFragment(
                            when (it) {
                                Sections.Tema1 -> it
                                Sections.Tema2 -> it
                                Sections.Tema3 -> it
                                Sections.Tema4 -> it
                                Sections.Tema5 -> it
                                Sections.Tema6 -> it
                                Sections.Tema7 -> it
                            }

                        )
                    )
                }
            }
        }
    }

}

@Composable
fun ChooseSectionContent(sectionClick: (Sections) -> Unit) {

    Column() {
        SectionCard(
            onSectionClick = sectionClick,
            section_name = stringResource(id = R.string.section_1_name),
            description = stringResource(id = R.string.section_1_description),
            section = Sections.Tema1
        )
        SectionCard(
            onSectionClick = sectionClick,
            section_name = stringResource(id = R.string.section_2_name),
            description = stringResource(id = R.string.section_2_description),
            section = Sections.Tema2
        )

        SectionCard(
            onSectionClick = sectionClick,
            section_name = stringResource(id = R.string.section_3_name),
            description = stringResource(id = R.string.section_3_description),
            section = Sections.Tema3
        )
        SectionCard(
            onSectionClick = sectionClick,
            section_name = stringResource(id = R.string.section_4_name),
            description = stringResource(id = R.string.section_4_description),
            section = Sections.Tema4
        )
        SectionCard(
            onSectionClick = sectionClick,
            section_name = stringResource(id = R.string.section_5_name),
            description = stringResource(id = R.string.section_5_description),
            section = Sections.Tema5
        )
        SectionCard(
            onSectionClick = sectionClick,
            section_name = stringResource(id = R.string.section_6_name),
            description = stringResource(id = R.string.section_6_description),
            section = Sections.Tema6
        )
        SectionCard(
            onSectionClick = sectionClick,
            section_name = stringResource(id = R.string.section_7_name),
            description = stringResource(id = R.string.section_7_description),
            section = Sections.Tema7
        )


    }
}

@Preview
@Composable
fun ChooseSectionContentPreview() {
    ChooseSectionContent({})
}

@Composable
fun SectionCard(
    onSectionClick: (Sections) -> Unit,
    section_name: String,
    description: String,
    section: Sections
) {
    Row() {

        Surface(Modifier.clickable { onSectionClick(section) }) {
            Column() {
                Text(
                    text = section_name,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
