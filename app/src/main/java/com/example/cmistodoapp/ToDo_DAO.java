package com.example.cmistodoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface ToDo_DAO
{

	@Query("SELECT * FROM ToDo_table ORDER BY ToDo_ID ASC")
	LiveData<List<ToDo>> getSortedToDos();

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insert(ToDo toDo);

	@Query("DELETE FROM ToDo_table")
	void nukeTable();

}
