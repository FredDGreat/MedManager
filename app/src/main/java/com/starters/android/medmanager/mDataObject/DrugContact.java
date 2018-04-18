package com.starters.android.medmanager.mDataObject;

/**
 * Created by Fred
 */
public class DrugContact {

    String mDrugName;
    String mDesc;
    String mInterval;
    String mStartNEndDate;
    int id;

    public DrugContact() {
    }


    public void setMedName(String name) {
        this.mDrugName = name;
    }
    public String getMedName() {
        return mDrugName;
    }

    public void setInterval(String name) {
        this.mInterval = name;
    }
    public String getInterval() {
        return mInterval;
    }

    public void setDesc(String name) {
        this.mDesc = name;
    }
    public String getDesc() {
        return mDesc;
    }

    public void setStartNEndDate(String name) {
        this.mStartNEndDate = name;
    }
    public String getStartNEndDate() {
        return mStartNEndDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return mDrugName+"\n"+mDesc+"\n"+mInterval+"\n"+mStartNEndDate;
    }
}
