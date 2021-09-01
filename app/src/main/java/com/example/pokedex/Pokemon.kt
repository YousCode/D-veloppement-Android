package com.example.pokedex

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class Pokemon(id: String): Serializable {

    val url = "https://pokeapi.co/api/v2/pokemon/$id";
    var id: String = String();
    var name: String = String();
    var picture: String = String();
    var backPicture: String = String();
    var types: MutableList<String> = ArrayList();
    var color: String = String();

    fun getInfos(context: Context) {
        val queue = Volley.newRequestQueue(context);
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonArray = response.getJSONArray("forms");
                var pokeName = jsonArray.getJSONObject(0).getString("name");
                name = pokeName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                    else it.toString()
                };
                id = response.getString("id");
                val picturesArray = response.getJSONObject("sprites");
                picture = picturesArray.getString("front_default");
                backPicture = picturesArray.getString("back_default");
                val typesArray = response.getJSONArray("types");
                for (i in 0 until typesArray.length()) {
                    val typeObject = typesArray.getJSONObject(i).getJSONObject("type");
                    val typeName = typeObject.getString("name").replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                        else it.toString()
                    };
                    types.add(typeName);
                }
                color = getColor(types[0]);
            }
        ) { error ->
            Log.println(Log.INFO, null, "Error response is: $error")
        }
        queue.add(jsonObjectRequest);
    }

    fun getColor(type: String): String {
        var color = String();
        when(type) {
            "Normal" -> color = "#A8A878"
            "Fighting" -> color = "#C03028"
            "Flying" -> color = "#A890F0"
            "Poison" -> color = "#A040A0"
            "Ground" -> color = "#E0C068"
            "Rock" -> color = "#B8A038"
            "Bug" -> color = "#A8B820"
            "Ghost" -> color = "#705898"
            "Steel" -> color = "#B8B8D0"
            "Fire" -> color = "#F08030"
            "Water" -> color = "#6890F0"
            "Grass" -> color = "#78C850"
            "Electric" -> color = "#F8D030"
            "Psychic" -> color = "#F85888"
            "Ice" -> color = "#98D8D8"
            "Dragon" -> color = "#7038F8"
            "Dark" -> color = "#705848"
            "Fairy" -> color = "#EE99AC"
            else -> color = "#000000"
        }
        return color;
    }
}