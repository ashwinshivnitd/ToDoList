package com.jwhh.todolist.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ToDoList toDoList);

    @Query("Delete from todolist where serial_no=:serialNo")
    void deleteItem(long serialNo);

    @Query("Select * from todolist")
    List<ToDoList> selectAllItems();

    @Query("Select * from todolist where task LIKE '%' || :task || '%'")
    List<ToDoList> selectItem(String task);

    @Update
    void updateItem(ToDoList toDoList);
}
