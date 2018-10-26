package com.testing.android.android_testing.db.entity;

import com.testing.android.android_testing.TestingUtils;

import java.util.Date;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "worker")
public class WorkerEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private List<Long> specialtyIds;
    @Ignore
    private List<SpecialtyEntity> specialties;

    public WorkerEntity() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Long> getSpecialtyIds() {
        return specialtyIds;
    }

    public void setSpecialtyIds(List<Long> specialtyIds) {
        this.specialtyIds = specialtyIds;
    }

    public List<SpecialtyEntity> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<SpecialtyEntity> specialties) {
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        return "WorkerEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", specialtyIds=" + specialtyIds +
                '}';
    }

    public String getAge() {
        return TestingUtils.getAgeFormatted(birthday);
    }

    public String getBirthdayFormatted() {
        return TestingUtils.formatBirthday(birthday);
    }

    public String getSpecialtiesFormatted() {
        return TestingUtils.specialtiesToString(specialties);
    }
}
