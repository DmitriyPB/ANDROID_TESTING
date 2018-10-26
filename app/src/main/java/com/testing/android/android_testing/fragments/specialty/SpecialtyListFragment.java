package com.testing.android.android_testing.fragments.specialty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testing.android.android_testing.MainActivity;
import com.testing.android.android_testing.R;
import com.testing.android.android_testing.viewmodel.MainViewModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class SpecialtyListFragment extends Fragment {

    private MainViewModel viewModel;
    private final SpecialtyClickCallback specialtyClickCallback = specialty -> {
        viewModel.selectSpecialty(specialty);
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).openSpecialtyFragment();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specialty_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(MainViewModel viewModel) {
        RecyclerView specialty_list = getView().findViewById(R.id.recycler_specialty_list);
        viewModel.loadSpecialties().observe(this, specialties -> {
            if (specialties != null && !specialties.isEmpty()) {
                specialty_list.setVisibility(View.VISIBLE);
                specialty_list.swapAdapter(new SpecialtyListAdapter(specialties, specialtyClickCallback), true);
            } else {
                specialty_list.setVisibility(View.GONE);
                specialty_list.swapAdapter(null, true);
            }
        });
    }

}
