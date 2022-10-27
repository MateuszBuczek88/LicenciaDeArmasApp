package com.mbp.licenciadearmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import com.mbp.licenciadearmas.data.Section
import com.mbp.licenciadearmas.ui.theme.LicenciaDeArmasTheme
import com.mbp.licenciadearmas.ui.theme.Shapes

class ChooseSectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {
                    BackgroundBox()
                    ChooseSectionContent { section ->
                        findNavController().navigate(
                            ChooseSectionFragmentDirections.actionChooseSectionFragmentToLearnFragment(
                                section
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChooseSectionContent(sectionClick: (Section) -> Unit) {

    Column(modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp)) {
        SecondaryText(textRes = R.string.choosesection_screen_main_text)
        Spacer(modifier = Modifier.height(15.dp))
        LazyColumn(contentPadding = PaddingValues(8.dp), content = {
            items(1, itemContent = {
                Section.values().forEach { section ->
                    SectionCard(
                        onSectionClick = sectionClick,
                        section = section
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            })
        })
    }
}

@Composable
fun SectionCard(
    onSectionClick: (Section) -> Unit,
    section: Section
) {

    Surface(
        Modifier
            .fillMaxWidth()
            .clickable { onSectionClick(section) },
        elevation = 12.dp,
        shape = Shapes.medium
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = section.nameResId),
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = section.descriptionResId),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal
            )
        }
    }
}