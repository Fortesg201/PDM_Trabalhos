package com.calculadora

import android.widget.Button
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.calculadora.ui.theme.Blue
import com.calculadora.ui.theme.CalculatorTheme
import com.calculadora.ui.theme.Yellow


@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    label : String,
    isOperation : Boolean = false,
    onNumPressed : (String) -> Unit
){
    Button(
        modifier = modifier
            .size(90.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isOperation) Yellow else Blue
        ),
        onClick = {
            onNumPressed(label)
        }
    ) {
        Text(
            label,
            color = Color.Black,
            style = if (label.count() == 1)
                MaterialTheme.typography.displayMedium else
                MaterialTheme.typography.titleMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorButtonPreview(){
    CalculatorTheme {
        CalculatorButton(
            modifier = Modifier,
            label = "AC",
            onNumPressed = {}
        )
    }
}
