package com.abdallahyasser.maslahty.presentation.shared_composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.abdallahyasser.maslahty.theme.GoldenYellow

@Composable
fun CustomEditText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    Box(modifier = modifier) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = GoldenYellow,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
    }

}