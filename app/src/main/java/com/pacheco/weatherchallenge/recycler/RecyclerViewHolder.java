package com.pacheco.weatherchallenge.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacheco.weatherchallenge.databinding.RecyclerviewItemBinding;
import com.pacheco.weatherchallenge.response.City;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final RecyclerviewItemBinding binding;

    public RecyclerViewHolder(@NonNull View itemView, RecyclerviewItemBinding binding) {
        super(itemView);
        this.binding = binding;
    }

    public void bind(City city, Clickable listener) {
        itemView.setOnClickListener(v -> listener.onClick(city));
        binding.setCity(city);
    }
}
