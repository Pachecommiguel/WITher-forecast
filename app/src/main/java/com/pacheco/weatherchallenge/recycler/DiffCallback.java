package com.pacheco.weatherchallenge.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pacheco.weatherchallenge.response.Response;

public class DiffCallback extends DiffUtil.ItemCallback<Response> {

    @Override
    public boolean areItemsTheSame(@NonNull Response oldItem, @NonNull Response newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Response oldItem, @NonNull Response newItem) {
        return false;
    }
}
