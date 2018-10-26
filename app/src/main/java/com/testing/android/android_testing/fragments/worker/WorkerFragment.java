package com.testing.android.android_testing.fragments.worker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testing.android.android_testing.R;
import com.testing.android.android_testing.viewmodel.MainViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class WorkerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainViewModel viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(MainViewModel viewModel) {
        AppCompatTextView text_view_first_name = getView().findViewById(R.id.text_view_first_name);
        AppCompatTextView text_view_last_name = getView().findViewById(R.id.text_view_last_name);
        AppCompatTextView text_view_birthday = getView().findViewById(R.id.text_view_birthday);
        AppCompatTextView text_view_age = getView().findViewById(R.id.text_view_age);
        AppCompatTextView text_view_specialty = getView().findViewById(R.id.text_view_specialty);
        viewModel.loadSelectedWorker().observe(this, worker -> {
            if (worker != null) {
                text_view_first_name.setText(worker.getFirstName());
                text_view_last_name.setText(worker.getLastName());
                text_view_birthday.setText(worker.getBirthdayFormatted());
                text_view_age.setText(worker.getAge());
                text_view_specialty.setText(worker.getSpecialtiesFormatted());
            }
        });
    }
}
