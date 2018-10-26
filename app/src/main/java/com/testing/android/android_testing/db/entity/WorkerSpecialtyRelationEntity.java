package com.testing.android.android_testing.db.entity;

import androidx.room.Entity;

@Entity(tableName = "worker_spec_relation",
        primaryKeys = {"worker_id", "spec_id"})
public class WorkerSpecialtyRelationEntity {
    public long worker_id;
    public long spec_id;

    public WorkerSpecialtyRelationEntity(long worker_id, long spec_id) {
        this.worker_id = worker_id;
        this.spec_id = spec_id;
    }

    @Override
    public String toString() {
        return "WorkerSpecialtyRelationEntity{" +
                "worker_id=" + worker_id +
                ", spec_id=" + spec_id +
                '}';
    }
}
