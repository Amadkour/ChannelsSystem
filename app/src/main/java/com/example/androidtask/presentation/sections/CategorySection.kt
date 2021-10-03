package com.example.androidtask.presentation.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androidtask.presentation.Component
import javax.inject.Inject

class CategorySection @Inject constructor(private val component: Component){
    @Composable
     fun categoryItemDesign(text: String, modifier: Modifier) {
        Box(
            modifier = modifier
                .padding(all = 10.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(color = Color(58, 61, 70),)
                .height(70.dp)
                .padding(all = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            component.textDesign(
                text = text,
                align= TextAlign.Center,
                modifier = modifier
                    .padding(all = 0.dp)
            )
        }
    }
}