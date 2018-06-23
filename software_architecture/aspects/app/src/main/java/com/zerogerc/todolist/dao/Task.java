package com.zerogerc.todolist.dao;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.zerogerc.todolist.data.TaskModel;

@AutoValue
public abstract class Task implements TaskModel {

    public static final Factory<Task> FACTORY = new Factory<>(new Creator<Task>() {
        @Override
        public Task create(long _id, @NonNull String text, long done, long list_id) {
            return new AutoValue_Task(_id, text, done, list_id);
        }
    });

    public static final RowMapper<Task> SELECT_ALL_MAPPER = FACTORY.selectByListMapper();
}
