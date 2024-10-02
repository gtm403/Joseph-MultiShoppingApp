package com.example.multipaneshoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.multipaneshoppingapp.ui.theme.MultipaneShoppingAppTheme
import kotlinx.coroutines.selects.select


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultipaneShoppingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                    ShoppingApp()
                }
            }
        }
    }
}


data class Product(val name: String, val price: String, val description: String)

@Composable
fun ShoppingApp() {

    val products = listOf(
        Product("Nike Air Forces", "$100", "All white shoes for casual wear"),
        Product("Calculator", "$100", "TI-84 scientific calculator"),
        Product("Water Bottle", "$50", "Stainless steel water bottle to maintain water temperatures"),
        Product("Earbuds", "$100", "Bluetooth earbuds for easy connectivity; bass boosted"),
        Product("iPhone16", "$1000", "Latest Apple iPhone with the same features as the 15"),
        Product("Electric Toothbrush", "$50", "Electric toothbrush for more effective cleaning"),
        Product("Down Jacket", "$200", "Winter down jacket for cold weather"),
        Product("Macbook Air 2020", "$800", "M2 Macbook Air refurbished"),
    )

    var selectedProduct by remember { mutableStateOf<Product?>(null) }


    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT


    if (isPortrait) {

        if (selectedProduct == null) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ){
                Text("Select a product to view details", style = MaterialTheme.typography.labelLarge)
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(products) { product: Product ->
                    Column(
                        modifier = Modifier

                            .clickable {
                                selectedProduct = product // Update the selected product
                            } ,

                    ) {
                        Text(text = product.name)
                    }
                }

            }
        } else {
                ProductDetailsScreen(selectedProduct!!) {
                    selectedProduct = null
            }
        }
    } else {
        androidx.compose.foundation.layout.Row (modifier = Modifier.fillMaxSize()){
            LazyColumn (
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ){
                items(products) {product: Product ->
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedProduct = product
                            }
                    ){
                        Text(text = product.name)
                    }
                }
            }

            Box (
                modifier = Modifier
                    .padding(16.dp)
            ){
                if (selectedProduct!=null) {
                    ProductDetails(selectedProduct!!)
                } else {
                    Text(
                        "Select a product to view details",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(product: Product, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = onBackClick) {
            Text("Back")
        }

        Box (
            modifier = Modifier.fillMaxSize(), // Fill the screen
            contentAlignment = Alignment.Center
        ){
            ProductDetails(product)
        }
    }
}

@Composable
fun ProductDetails(product: Product) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = product.name)
        Text(text = "Price: ${product.price}")
        Text(text = product.description)
    }
}



