package com.testing.android.android_testing.fragments.specialty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testing.android.android_testing.MainActivity;
import com.testing.android.android_testing.R;
import com.testing.android.android_testing.fragments.worker.WorkerListAdapter;
import com.testing.android.android_testing.viewmodel.MainViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import static com.testing.android.android_testing.TestingUtils.dash;

public class WorkerListFragment extends Fragment {
    private MainViewModel viewModel;
    private final WorkerClickCallback workerClickCallback = worker -> {
        viewModel.selectWorker(worker);
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).openWorkerFragment();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(MainViewModel viewModel) {
        RecyclerView specialty_list = getView().findViewById(R.id.recycler_specialty_list);
        AppCompatTextView text_view_specialty_list_caption = getView().findViewById(R.id.text_view_specialty_list_caption);

        viewModel.loadSelectedSpecialty().observe(this, selectedSpec -> {
            if (selectedSpec != null) {
                text_view_specialty_list_caption.setText(getString(R.string.worker_list_caption, selectedSpec.getName()));
            } else {
                text_view_specialty_list_caption.setText(dash);
            }
        });
        viewModel.loadWorkers().observe(this, workers -> {
            if (workers != null && !workers.isEmpty()) {
                specialty_list.setVisibility(View.VISIBLE);
                specialty_list.swapAdapter(new WorkerListAdapter(workers, workerClickCallback), true);
            } else {
                specialty_list.swapAdapter(null, true);
                specialty_list.setVisibility(View.GONE);
            }
        });
    }
}
