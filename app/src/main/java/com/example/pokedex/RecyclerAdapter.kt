package com.example.pokedex

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList

class RecyclerAdapter(pokemonsList: MutableList<Pokemon>,
                      val context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable {

    val pokemons : MutableList<Pokemon> = ArrayList();
    var filteredPokemons : MutableList<Pokemon> = ArrayList();

    init {
        filteredPokemons = pokemonsList;
        pokemons.addAll(pokemonsList);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false);
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return filteredPokemons.size;
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.pokName.text = filteredPokemons[position].name;
        holder.pokNumber.text = "#" + filteredPokemons[position].id;
        Glide.with(context)
            .load(filteredPokemons[position].picture)
            .into(holder.pokImage);
        holder.itemView.setBackgroundColor(Color.parseColor(filteredPokemons[position].color));
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var pokImage: ImageView;
        var pokName: TextView;
        var pokNumber: TextView;

        init {
            pokImage = itemView.findViewById(R.id.pok_image);
            pokName = itemView.findViewById(R.id.pok_name);
            pokNumber = itemView.findViewById(R.id.pok_number);

            itemView.setOnClickListener {

                val intent = Intent(context, PokemonActivity::class.java);
                intent.putExtra("Pokemon", filteredPokemons[layoutPosition]);
                context.startActivity(intent);
            }
        }
    }

    override fun getFilter(): Filter {
        return myFilter;
    }

    private var myFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {

            var filteredList : MutableList<Pokemon> = ArrayList();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(pokemons);
            } else {
                for (pokemon in pokemons) {
                    if (pokemon.name.contains(charSequence.toString(), ignoreCase = true)
                        || pokemon.types.any { it.lowercase() == charSequence.toString().lowercase() }
                        || pokemon.id == charSequence.toString().lowercase()) {
                            filteredList.add(pokemon);
                    }
                }
            }

            var filterResults = FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            filteredPokemons.clear();
            filteredPokemons.addAll(filterResults.values as Collection<Pokemon>);
            notifyDataSetChanged();
        }
    }
}
