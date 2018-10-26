package com.testing.android.android_testing;


import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.testing.android.android_testing.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this, getInstance(this.getApplication())).get(MainViewModel.class);
        model.getErrorEvent().observe(this, aVoid -> showDataLoadingErrorSnack());
        model.loadData();
    }

    private void showDataLoadingErrorSnack() {
        View content = findViewById(android.R.id.content);
        Snackbar.make(content, getString(R.string.data_loading_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.data_loading_retry_action), v -> model.loadData())
                .show();
    }

    public void openSpecialtyFragment() {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.workerListFragment);
    }

    public void openWorkerFragment() {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.workerFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
