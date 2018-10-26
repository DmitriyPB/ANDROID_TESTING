package com.testing.android.android_testing.db.dao;

import com.testing.android.android_testing.db.entity.SpecialtyEntity;
import com.testing.android.android_testing.db.entity.WorkerEntity;
import com.testing.android.android_testing.db.entity.WorkerSpecialtyRelationEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveSpecialties(List<SpecialtyEntity> specialties);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertWorkerSpecRelation(WorkerSpecialtyRelationEntity relation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertWorker(WorkerEntity worker);

    @Query("SELECT * FROM specialty")
    public abstract LiveData<List<SpecialtyEntity>> loadAllSpecialty();

    @Query("SELECT * FROM worker where id in(select worker_id from worker_spec_relation where spec_id = :specialtyId)")
    public abstract List<WorkerEntity> getWorkersBySpecialty(long specialtyId);


    @Query("SELECT * FROM specialty where id in (:specialtyIds)")
    public abstract List<SpecialtyEntity> getSpecialtiesByIds(List<Long> specialtyIds);

    @Transaction
    public void saveSpecialtiesAndWorkers(List<SpecialtyEntity> specialties, List<WorkerEntity> workers) {
        saveSpecialties(specialties);
        for (WorkerEntity worker : workers) {
            long id = insertWorker(worker);
            for (Long specId : worker.getSpecialtyIds()) {
                insertWorkerSpecRelation(new WorkerSpecialtyRelationEntity(id, specId));
            }
        }
    }
}
