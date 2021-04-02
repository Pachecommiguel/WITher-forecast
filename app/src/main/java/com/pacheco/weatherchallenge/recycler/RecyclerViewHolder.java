package com.pacheco.weatherchallenge.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacheco.weatherchallenge.databinding.RecyclerviewItemBinding;
import com.pacheco.weatherchallenge.response.Response;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final RecyclerviewItemBinding binding;

    public RecyclerViewHolder(@NonNull View itemView, RecyclerviewItemBinding binding) {
        super(itemView);
        this.binding = binding;
    }

    public void bind(Response response, Clickable listener) {
        itemView.setOnClickListener(v -> listener.onClick(response));
        binding.setResponse(response);
    }
}
