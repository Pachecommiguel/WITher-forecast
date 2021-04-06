package com.pacheco.weatherchallenge.ui.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pacheco.weatherchallenge.network.models.City;

public class DiffCallback extends DiffUtil.ItemCallback<City> {

    @Override
    public boolean areItemsTheSame(@NonNull City oldItem, @NonNull City newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull City oldItem, @NonNull City newItem) {
        return oldItem.getName().equals(newItem.getName()) &&
                Math.round(oldItem.getMain().getTemp()) == Math.round(newItem.getMain().getTemp());
    }
}
