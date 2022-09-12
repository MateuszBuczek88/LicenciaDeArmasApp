package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        ChooseSectionFragmentDirections.actionChooseSectionFragmentToLearnFragment(it)
                    )
                }
            }
        }
    }

}

@Composable
fun ChooseSectionContent(sectionClick: (Sections) -> Unit) {

    Column() {
        Sections.values().forEach {
            SectionCard(
                onSectionClick = sectionClick,
                section_name = stringResource(id = it._name),
                description = stringResource(id = it.description),
                section = it
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
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

        Surface(Modifier.fillMaxWidth().clickable { onSectionClick(section) }) {
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
