package com.example.evalcoerajetpack

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evalcoerajetpack.api.APIClient
import com.example.evalcoerajetpack.api.APIInterface
import com.example.evalcoerajetpack.model.JSONResult
import com.example.evalcoerajetpack.model.Planet
import com.example.evalcoerajetpack.ui.theme.EvalCoeraJetpackTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    lateinit var apiInterface: APIInterface
    val TAG = "MainTAG"

    var planets = mutableStateListOf<Planet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EvalCoeraJetpackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home(planets)
                }
            }
        }

        apiInterface = APIClient.getClient()!!.create(APIInterface::class.java)
        getPlanets()
    }

    private fun getPlanets() {
        val call: Call<JSONResult> = apiInterface.getAllPlanets()

        call.enqueue(object : Callback<JSONResult> {
            override fun onResponse(call: Call<JSONResult>, response: Response<JSONResult?>) {
                Log.i(TAG, "Request to /planets successful")

                val result: JSONResult? = response.body()
                if (result == null) {
                    Log.e(TAG,"body is null")
                } else {
                    val planetsReceived = result.planets
                    if (planetsReceived == null) {
                        Log.e(TAG, "Planets are null")
                    } else {
                        planetsReceived.forEach {
                            Log.i(TAG, it.toString())
                        }
                        planets.addAll(planetsReceived)
                    }
                }
            }

            override fun onFailure(call: Call<JSONResult?>, t: Throwable) {
                Log.w(TAG, "Request to /planets failed")
                call.cancel()
                Log.w(TAG, t.cause)
                t.message?.let { Log.w(TAG, it) }
            }
        })
    }
}


@Composable
fun Home(planets: SnapshotStateList<Planet>) {
//    val planets = listOf(Planet("1"), Planet("2"), Planet("3"))
    val displayedPlanets = remember { mutableStateOf(planets) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(displayedPlanets.value) { planet ->
            PlanetCard(planet, { } )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetCard(planet: Planet, onPlanetClick: (Planet) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        onClick = {onPlanetClick(planet)}
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            Text(planet.name)
            Text("Climate: " + planet.climate)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EvalCoeraJetpackTheme {
       PlanetCard(Planet("climate"), {})
    }
}