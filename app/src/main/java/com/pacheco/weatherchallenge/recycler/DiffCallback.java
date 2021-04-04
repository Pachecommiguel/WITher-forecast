package com.pacheco.weatherchallenge.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pacheco.weatherchallenge.response.City;

public class DiffCallback extends DiffUtil.ItemCallback<City> {

    @Override
    public boolean areItemsTheSame(@NonNull City oldItem, @NonNull City newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull City oldItem, @NonNull City newItem) {
        return oldItem.getName().equals(newItem.getName()) &&
                (oldItem.getMain().getTemp().intValue() == newItem.getMain().getTemp().intValue());
    }
}
