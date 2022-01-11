package com.jwhh.todolist.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.todolist.Helper;
import com.jwhh.todolist.R;
import com.jwhh.todolist.db.ToDoList;

import java.util.List;

public class ToDoListRecyclerAdapter extends RecyclerView.Adapter<ToDoListRecyclerAdapter.MyViewHolder> {
    public List<ToDoList> toDoListList;
    public Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView taskLabel, detailLabel;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.taskLabel = itemView.findViewById(R.id.todolistitems);
            this.detailLabel = itemView.findViewById(R.id.todolistdetails);
        }
    }

    public ToDoListRecyclerAdapter(List<ToDoList> toDoListList, Context context) {
        this.toDoListList = toDoListList;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListRecyclerAdapter.MyViewHolder holder, int position) {
        ToDoList toDoList = toDoListList.get(position);
        holder.taskLabel.setText(toDoList.task);
        holder.detailLabel.setText(toDoList.details);
        holder.taskLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddDeleteListActivity.class);
                intent.putExtra(Helper.KEY, toDoList);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return toDoListList.size();
    }


}
