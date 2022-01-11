package com.jwhh.todolist.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todolist")
public class ToDoList implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "serial_no")
    public long serialNo;

    @ColumnInfo(name = "task")
    public String task;

    @ColumnInfo(name ="details")
    public String details;

    public ToDoList(){

    }

    public ToDoList(String task, String details) {
        this.task = task;
        this.details = details;
    }

    protected ToDoList(Parcel in) {
        serialNo = in.readLong();
        task = in.readString();
        details = in.readString();
    }

    public static final Creator<ToDoList> CREATOR = new Creator<ToDoList>() {
        @Override
        public ToDoList createFromParcel(Parcel in) {
            return new ToDoList(in);
        }

        @Override
        public ToDoList[] newArray(int size) {
            return new ToDoList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(serialNo);
        parcel.writeString(task);
        parcel.writeString(details);
    }
}
