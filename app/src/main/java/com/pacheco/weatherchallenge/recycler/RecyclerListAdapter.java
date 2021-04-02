package com.pacheco.weatherchallenge.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.pacheco.weatherchallenge.databinding.RecyclerviewItemBinding;
import com.pacheco.weatherchallenge.response.Response;

public class RecyclerListAdapter extends ListAdapter<Response, RecyclerViewHolder> {

    private final Clickable listener;

    public RecyclerListAdapter(@NonNull DiffUtil.ItemCallback<Response> diffCallback,
                                  Clickable listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewItemBinding binding = RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new RecyclerViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }
}
