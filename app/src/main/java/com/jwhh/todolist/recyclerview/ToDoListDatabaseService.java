package com.jwhh.todolist.recyclerview;


import android.content.Context;

import androidx.room.Room;

import com.jwhh.todolist.Helper;
import com.jwhh.todolist.db.ToDoList;
import com.jwhh.todolist.db.ToDoListDatabase;

import java.util.List;

public class ToDoListDatabaseService {
    private static ToDoListDatabaseService toDoListDatabaseService;
    private ToDoListDatabase toDoListDatabase;

    public static ToDoListDatabaseService getInstance(Context context) {
        if (toDoListDatabaseService == null) {
            toDoListDatabaseService = new ToDoListDatabaseService();
            toDoListDatabaseService.toDoListDatabase = Room.databaseBuilder(context, ToDoListDatabase.class, Helper.TO_DO_LIST_DATABASE_NAME).allowMainThreadQueries().build();
        }
        return toDoListDatabaseService;
    }

    public void insertItem(ToDoList toDoList) {
        this.toDoListDatabase.toDoListDao().insertItem(toDoList);
    }

    public void deleteItem(long serialNo) {
        this.toDoListDatabase.toDoListDao().deleteItem(serialNo);
    }

    public List<ToDoList> selectAllItems() {
        return this.toDoListDatabase.toDoListDao().selectAllItems();
    }

    public List<ToDoList> selectItem(String task) {
        return this.toDoListDatabase.toDoListDao().selectItem(task);
    }

    public void updateItem(ToDoList toDoList) {
        this.toDoListDatabase.toDoListDao().updateItem(toDoList);
    }
}
