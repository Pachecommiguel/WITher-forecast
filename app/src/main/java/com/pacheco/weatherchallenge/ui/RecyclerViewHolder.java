package com.pacheco.weatherchallenge.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacheco.weatherchallenge.databinding.RecyclerviewItemBinding;
import com.pacheco.weatherchallenge.retrofit.response.City;
import com.pacheco.weatherchallenge.utils.Constants;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final RecyclerviewItemBinding binding;

    public RecyclerViewHolder(@NonNull View itemView, RecyclerviewItemBinding binding) {
        super(itemView);
        this.binding = binding;
    }

    public void bind(City city, Clickable listener) {
        if (city.getId() == Constants.ADD_ID) {
            binding.constraintLayout.removeView(binding.mainTemp);
            binding.constraintLayout.removeView(binding.temperature);
        }

        itemView.setOnClickListener(v -> listener.onClick(city));
        binding.setCity(city);
    }
}
