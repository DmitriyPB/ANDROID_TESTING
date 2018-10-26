package com.testing.android.android_testing;

import com.testing.android.android_testing.db.AppDatabase;
import com.testing.android.android_testing.db.entity.SpecialtyEntity;
import com.testing.android.android_testing.db.entity.WorkerEntity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import androidx.lifecycle.LiveData;

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<SpecialtyEntity>> loadSpecialties() {
        return mDatabase.appDao().loadAllSpecialty();
    }

    public List<WorkerEntity> getWorkers(final long specialtyId) {
        List<WorkerEntity> workers = mDatabase.appDao().getWorkersBySpecialty(specialtyId);
        for (WorkerEntity worker : workers) {
            if (worker != null) {
                if (worker.getSpecialtyIds() != null) {
                    worker.setSpecialties(mDatabase.appDao().getSpecialtiesByIds(worker.getSpecialtyIds()));
                }
            }
        }
        return workers;
    }

    public void clearData() {
        mDatabase.clearAllTables();
    }

    public void saveRaw(String response) throws ParseException {
        Set<SpecialtyEntity> specList = new HashSet<>();
        List<WorkerEntity> workersList = new LinkedList<>();

        JSONObject full = (JSONObject) new JSONParser().parse(response);
        parseData(full, workersList::add, specList::add);

        mDatabase.appDao().saveSpecialtiesAndWorkers(new LinkedList<>(specList), workersList);
    }

    private interface Consumer<T> {
        void accept(T t);
    }

    private void parseData(JSONObject full, Consumer<WorkerEntity> workerConsumer, Consumer<SpecialtyEntity> specialtyConsumer) {
        if (full != null) {
            JSONArray respArray = (JSONArray) full.get("response");
            if (respArray != null) {
                for (Object el : respArray) {
                    JSONObject json = ((JSONObject) el);
                    List<Long> specIds = new LinkedList<>();
                    JSONArray specialties = (JSONArray) json.get("specialty");
                    if (specialties != null) {
                        for (Object specialty : specialties) {
                            SpecialtyEntity entity = buildSpecialty((JSONObject) specialty);
                            if (entity != null) {
                                specIds.add(entity.getId());
                                specialtyConsumer.accept(entity);
                            }
                        }
                    }
                    WorkerEntity worker = buildWorker(json);
                    if (worker != null) {
                        worker.setSpecialtyIds(specIds);
                        workerConsumer.accept(worker);
                    }
                }
            }
        }
    }

    private WorkerEntity buildWorker(JSONObject json) {
        WorkerEntity worker = new WorkerEntity();
        String f_name = (String) json.get("f_name");
        String l_name = (String) json.get("l_name");
        String birthday = (String) json.get("birthday");
        worker.setFirstName(TestingUtils.capitalizeFirstLetter(f_name));
        worker.setLastName(TestingUtils.capitalizeFirstLetter(l_name));
        worker.setBirthday(TestingUtils.extractDate(birthday));
        return worker;
    }

    private SpecialtyEntity buildSpecialty(JSONObject specJson) {
        SpecialtyEntity entity = new SpecialtyEntity();
        Number specialty_id = (Number) specJson.get("specialty_id");
        String name = (String) specJson.get("name");
        if (specialty_id != null && name != null) {
            entity.setId(specialty_id.intValue());
            entity.setName(name);
        }
        return entity;
    }
}

