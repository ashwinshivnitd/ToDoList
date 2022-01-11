package com.jwhh.todolist.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ToDoList.class},version=2)
public abstract  class ToDoListDatabase extends RoomDatabase {
    public abstract ToDoListDao toDoListDao();
}
