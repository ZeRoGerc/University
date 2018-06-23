package com.zerogerc.todolist.dao;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.zerogerc.todolist.data.TaskListModel;

@AutoValue
public abstract class TaskList implements TaskListModel {
    public static final Factory<TaskList> FACTORY = new Factory<>(new Creator<TaskList>() {
        @Override
        public TaskList create(long _id, @NonNull String title) {
            return new AutoValue_TaskList(_id, title);
        }
    });

    public static final RowMapper<TaskList> SELECT_ALL_MAPPER = FACTORY.selectAllMapper();
}
