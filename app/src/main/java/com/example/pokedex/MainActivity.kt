package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var gridLayoutManager: GridLayoutManager? = null;
    private var recyclerAdapter: RecyclerAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        supportActionBar?.title = "";
        setContentView(R.layout.activity_main);
        var pokemons: MutableList<Pokemon> = ArrayList();

        for (i in 252..386) {
            var newPokemon = Pokemon(i.toString());
            newPokemon.getInfos(this.applicationContext);
            pokemons.add(newPokemon);
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view);
        gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.layoutManager = gridLayoutManager;

        recyclerAdapter = RecyclerAdapter(pokemons, this);
        recyclerView.adapter = recyclerAdapter;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        var item = menu?.findItem(R.id.action_search);
        var searchView : SearchView = item?.actionView as SearchView ;
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                recyclerAdapter?.filter?.filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}