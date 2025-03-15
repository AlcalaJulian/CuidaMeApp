package es.usj.mastertsa.cuidameapp.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import es.usj.mastertsa.cuidameapp.R


@Composable
fun AuthenticationScreen(
    authViewModel: AuthViewModel,
    navigateToLogin:()-> Unit = {},
    navigateToRegister: () -> Unit = {}){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color.Gray, Color.Black), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.cuidamelogo),
            contentDescription = "",
            modifier = Modifier.clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navigateToLogin()},
            modifier = Modifier.fillMaxWidth().padding(32.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
//        CustomeButton("Login with gmail", R.drawable.gmaillogo)
//        Spacer(modifier = Modifier.height(8.dp))
//        CustomeButton("Login with facebook", R.drawable.facebooklogo)
//        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CustomeButton(text: String, drawableId: Int){
    Box(modifier = Modifier.fillMaxWidth()
        .height(48.dp)
        .padding(horizontal =  32.dp)
        .border(2.dp, Color.White, CircleShape),
        contentAlignment = Alignment.CenterStart
        ){
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 12.dp)
                .size(19.dp)
        )
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}