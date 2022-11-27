package com.example.sharedandroid.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sharedandroid.R

@Composable
fun ProfileOptionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription : String = ""
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(48.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.light_gray))
    ) {
        Box(modifier = Modifier.fillMaxWidth(1f),
            contentAlignment = Alignment.Center){
            Icon(
                icon, contentDescription = contentDescription,
                modifier = Modifier.align(Alignment.CenterStart))
            Text(
                text = text,
                textAlign = TextAlign.Center)
        }
    }
}