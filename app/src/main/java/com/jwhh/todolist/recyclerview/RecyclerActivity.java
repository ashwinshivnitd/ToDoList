package com.jwhh.todolist.recyclerview;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.jwhh.todolist.Helper;
import com.jwhh.todolist.R;
import com.jwhh.todolist.db.ToDoList;
import com.jwhh.todolist.db.ToDoListDatabase;

import java.util.List;

public class RecyclerActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<ToDoList> toDoListList;
    private ToDoListRecyclerAdapter toDoListRecyclerAdapter;
    private EditText selectItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerView = findViewById(R.id.recyclerview);
        this.selectItem = findViewById(R.id.searchitem);

        this.selectItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                filterItems(query);
            }

        });

    }

    public void filterItems(String query) {

        ToDoListDatabase toDoListDatabase = Room.databaseBuilder(this, ToDoListDatabase.class, Helper.TO_DO_LIST_DATABASE_NAME).allowMainThreadQueries().build();
        this.toDoListList.clear();
        this.toDoListList.addAll(toDoListDatabase.toDoListDao().selectItem(query));
        this.toDoListRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.selectallitems) {
            Toast.makeText(this, "All items selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.insertitem) {
            Toast.makeText(this, "Item inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddDeleteListActivity.class);
            startActivityForResult(intent, 1);
            return true;
        } else if (item.getItemId() == R.id.deleteitem) {
            Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Runnable runnable=()->{
            this.toDoListList=ToDoListDatabaseService.getInstance(this).selectAllItems();

            this.runOnUiThread(()->{
                this.toDoListRecyclerAdapter=new ToDoListRecyclerAdapter(toDoListList,this);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                this.recyclerView.setAdapter(toDoListRecyclerAdapter);
            });
        };
        //Background threads start here.
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
