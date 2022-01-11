package com.jwhh.todolist.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.jwhh.todolist.Helper;
import com.jwhh.todolist.R;
import com.jwhh.todolist.db.ToDoList;
import com.jwhh.todolist.db.ToDoListDatabase;

public class AddDeleteListActivity extends AppCompatActivity {
    private ToDoList toDoList;
    private EditText enterTask;
    private EditText enterDetails;
    private Button add;
    private Button delete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delete_item);
        this.toDoList=getIntent().getParcelableExtra(Helper.KEY);
        findViews();
        setData();
        setupListener();
    }
    //Find Views
    private void findViews() {
        this.enterTask=this.findViewById(R.id.taskname);
        this.enterDetails=this.findViewById(R.id.details);
        this.add = this.findViewById(R.id.addtask);
        this.delete = this.findViewById(R.id.cleartask);
    }
    private void setData() {

        if(toDoList==null){
            this.delete.setVisibility(View.GONE);
            return;
        }

        this.enterTask.setText(toDoList.task);
        this.enterDetails.setText(toDoList.details);


        this.add.setText("Item Updated");

        this.delete.setVisibility(View.VISIBLE);
    }


    private void setupListener(){
        this.add.setOnClickListener(view -> {
            String taskName=this.enterTask.getText().toString();
            String detailsStr=this.enterDetails.getText().toString();

            if(taskName.isEmpty()){
                this.enterTask.setError("Invalid Task");
                return;
            }

            if(detailsStr.isEmpty()){
                this.enterDetails.setError("Invalid Details");
                return;
            }

            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();

            ToDoListDatabase toDoListDatabase = Room.databaseBuilder(this, ToDoListDatabase.class, Helper.TO_DO_LIST_DATABASE_NAME).allowMainThreadQueries().build();

            if(this.toDoList==null){
                ToDoList toDoList=new ToDoList(taskName,detailsStr);

                toDoListDatabase.toDoListDao().insertItem(toDoList);
            }else {
                this.toDoList.task=taskName;
                this.toDoList.details=detailsStr;
                toDoListDatabase.toDoListDao().updateItem(this.toDoList);
            }

            finish();

        });

        this.delete.setOnClickListener(view -> {
            ToDoListDatabase toDoListDatabase = Room.databaseBuilder(this, ToDoListDatabase.class, Helper.TO_DO_LIST_DATABASE_NAME).allowMainThreadQueries().build();
            toDoListDatabase.toDoListDao().deleteItem(this.toDoList.serialNo);
            finish();
        });

    }

}
