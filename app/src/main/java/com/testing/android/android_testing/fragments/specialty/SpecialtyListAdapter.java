package com.testing.android.android_testing.fragments.specialty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testing.android.android_testing.R;
import com.testing.android.android_testing.db.entity.SpecialtyEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class SpecialtyListAdapter extends RecyclerView.Adapter<SpecialtyListAdapter.SpecialtyViewHolder> {
    private List<SpecialtyEntity> specialties;
    private SpecialtyClickCallback callback;

    public SpecialtyListAdapter(List<SpecialtyEntity> specialties, SpecialtyClickCallback callback) {
        this.specialties = specialties;
        this.callback = callback;
    }

    @NonNull
    @Override
    public SpecialtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specialty, parent, false);
        return new SpecialtyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialtyViewHolder holder, int position) {
        SpecialtyEntity specialty = specialties.get(position);
        if (specialty != null) {
            String name = specialty.getName();
            if (name != null) {
                holder.text_view_specialty.setText(name);
            } else {
                holder.text_view_specialty.setText("");
            }
        }
        holder.itemView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(specialty);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialties.size();
    }

    class SpecialtyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView text_view_specialty;

        SpecialtyViewHolder(View itemView) {
            super(itemView);
            text_view_specialty = itemView.findViewById(R.id.text_view_specialty);
        }
    }
}
