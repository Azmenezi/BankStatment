package com.example.bankstatement

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankstatement.ui.theme.BankStatementTheme
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankStatementTheme {
                var accounts by rememberSaveable { mutableStateOf(accountStatementList) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {  Button(onClick = {
                        accounts = accounts + AccountStatement()
                    }) {
                        Text(text = "Add an Account")
                    }
                        LazyColumn (  modifier = Modifier.padding(vertical = 16.dp),verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(accounts) {
                                Log.e("test",it.toString())

                                AccountStatementComp(type = it.transactionType, amount = it.amount, description = it.description)
                            } }

                }
                }
            }
        }
    }
}
val accountStatementList = listOf(AccountStatement(),AccountStatement(),AccountStatement(transactionType = TransactionType.WITHDRAW),AccountStatement(),AccountStatement())

@Composable
fun AccountStatementComp(modefier:Modifier=Modifier,type:TransactionType,amount:BigDecimal,description:String){
    Card(modefier.fillMaxWidth(), colors = CardColors(
        containerColor =   if(type === TransactionType.DEPOSIT) Color.Green else Color.Red,
        contentColor = if(type === TransactionType.DEPOSIT) Color.Black else Color.White,
        disabledContentColor = Color.Gray,
        disabledContainerColor = Color.Black

        ) ) {
        Column(
            modefier.padding(16.dp)
        ){
            Row (modefier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween, ) { Text(text = description.toString())
                Text(text = type.toString()) }

            Text(text = amount.toString())
        }

    }

}

data class AccountStatement(
    val id: String = UUID.randomUUID().toString(),
    val description: String = "Simple Note",
    val amount: BigDecimal = BigDecimal.ONE,
    val transactionType: TransactionType = TransactionType.DEPOSIT
)

enum class TransactionType {
    DEPOSIT, WITHDRAW
}
