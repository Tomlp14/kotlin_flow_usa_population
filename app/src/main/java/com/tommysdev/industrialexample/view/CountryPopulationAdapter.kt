package com.tommysdev.industrialexample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tommysdev.industrialexample.R
import com.tommysdev.industrialexample.repository.CountryPopulation


class CountryPopulationAdapter(var countryPopulations: ArrayList<CountryPopulation>): RecyclerView.Adapter<CountryPopulationAdapter.CountryViewHolder>() {

    fun updateCountries(newCountryPopulations: List<CountryPopulation>) {
        countryPopulations.clear()
        countryPopulations.addAll(newCountryPopulations)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )

    override fun getItemCount() = countryPopulations.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countryPopulations[position])
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val population = view.findViewById<TextView>(R.id.population)
        private val countryName = view.findViewById<TextView>(R.id.nation)
        private val yearPopulation = view.findViewById<TextView>(R.id.year)

        fun bind(countryPopulation: CountryPopulation) {
            countryName.text = countryPopulation.nation
            yearPopulation.text = countryPopulation.year
            population.text = countryPopulation.population
        }
    }
}