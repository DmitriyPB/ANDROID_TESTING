package com.testing.android.android_testing.fragments.worker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testing.android.android_testing.R;
import com.testing.android.android_testing.db.entity.WorkerEntity;
import com.testing.android.android_testing.fragments.specialty.WorkerClickCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerViewHolder> {
    private List<WorkerEntity> workers;
    private WorkerClickCallback callback;

    public WorkerListAdapter(List<WorkerEntity> workers, WorkerClickCallback callback) {
        this.workers = workers;
        this.callback = callback;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker, parent, false);
        return new WorkerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        WorkerEntity worker = workers.get(position);
        if (worker != null) {
            String firstName = worker.getFirstName();
            firstName = firstName != null ? firstName : "";
            String lastName = worker.getLastName();
            lastName = lastName != null ? lastName : "";
            holder.text_view_name.setText(String.format("%s %s", firstName, lastName));

            String age = worker.getAge();
            holder.text_view_age.setText(age);
        }
        holder.itemView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(worker);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    class WorkerViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView text_view_name;
        AppCompatTextView text_view_age;

        WorkerViewHolder(View itemView) {
            super(itemView);
            text_view_name = itemView.findViewById(R.id.text_view_name);
            text_view_age = itemView.findViewById(R.id.text_view_age);
        }
    }
}
