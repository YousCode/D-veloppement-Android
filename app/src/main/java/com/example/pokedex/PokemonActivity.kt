package com.example.pokedex

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide

class PokemonActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        supportActionBar?.hide();
        setContentView(R.layout.pokemon_layout);

        val pokemon = intent.getSerializableExtra("Pokemon") as? Pokemon;
        var bigName = findViewById<TextView>(R.id.big_name);
        bigName.text = pokemon?.name;
        var bigId = findViewById<TextView>(R.id.bigId);
        bigId.text = "#" + pokemon?.id;

        var bigImage = findViewById<ImageView>(R.id.big_picture);
        Glide.with(this)
            .load(pokemon?.picture)
            .into(bigImage);

        var bigBackPicture = findViewById<ImageView>(R.id.big_backpicture);
        Glide.with(this)
            .load(pokemon?.backPicture)
            .into(bigBackPicture);

        var bigType = findViewById<TextView>(R.id.pokType);
        bigType.text = "";
        for (type in pokemon?.types!!) {
            if (bigType.text.isNotEmpty()) {
                val tmp = bigType.text;
                val txt = "$tmp / $type";
                bigType.text = txt;
            } else {
                bigType.text = type;
            }
        }

        var background = findViewById<CardView>(R.id.pokemoninfos);
        background.setBackgroundColor(Color.parseColor(pokemon.color));
    }
}