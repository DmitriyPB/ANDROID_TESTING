package com.testing.android.android_testing.viewmodel;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.testing.android.android_testing.R;
import com.testing.android.android_testing.TestingApp;
import com.testing.android.android_testing.arch.SingleLiveEvent;
import com.testing.android.android_testing.db.entity.SpecialtyEntity;
import com.testing.android.android_testing.db.entity.WorkerEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class MainViewModel extends AndroidViewModel {
    ObservableBoolean dataLoading = new ObservableBoolean(false);//todo bind ui
    private SingleLiveEvent<Void> errorEvent = new SingleLiveEvent<>();
    private final MediatorLiveData<SpecialtyEntity> selectedSpecialty = new MediatorLiveData<>();
    private final MediatorLiveData<List<WorkerEntity>> workersBySpecialty = new MediatorLiveData<>();
    private final MediatorLiveData<WorkerEntity> selectedWorker = new MediatorLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        workersBySpecialty.setValue(null);
        selectedSpecialty.setValue(null);
        selectedWorker.setValue(null);
        workersBySpecialty.addSource(selectedSpecialty, specialty -> {
            ((TestingApp) application).getExecutors().diskIO().execute(() -> {
                List<WorkerEntity> workers = ((TestingApp) application).getRepository().getWorkers(specialty.getId());
                workersBySpecialty.postValue(workers);
            });
        });
    }

    public void selectSpecialty(SpecialtyEntity specialty) {
        workersBySpecialty.postValue(null);
        selectedSpecialty.postValue(specialty);
    }

    public void selectWorker(WorkerEntity worker) {
        selectedWorker.postValue(worker);
    }

    public LiveData<List<SpecialtyEntity>> loadSpecialties() {
        return ((TestingApp) getApplication()).getRepository().loadSpecialties();
    }

    public LiveData<List<WorkerEntity>> loadWorkers() {
        return workersBySpecialty;
    }

    public LiveData<WorkerEntity> loadSelectedWorker() {
        return selectedWorker;
    }

    public LiveData<SpecialtyEntity> loadSelectedSpecialty() {
        return selectedSpecialty;
    }

    private void saveData(String response) {
        TestingApp app = getApplication();
        app.getExecutors().diskIO().execute(() -> {
            if (response != null) {
                try {
                    app.getRepository().saveRaw(response);
                } catch (Throwable e) {
                    errorEvent.call();
                }
            } else {
                errorEvent.call();
            }
        });
    }


    public LiveData<Void> getErrorEvent() {
        return errorEvent;
    }

    public void loadData() {
        TestingApp app = getApplication();
        app.getExecutors().diskIO().execute(() -> {
            app.getRepository().clearData();
            RequestQueue queue = Volley.newRequestQueue(getApplication());
            String url = getApplication().getString(R.string.data_loading_link);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        dataLoading.set(false);
                        saveData(response);
                    }, error -> {
                dataLoading.set(false);
                errorEvent.call();
            });
            dataLoading.set(true);
            queue.add(stringRequest);
        });
    }
}
