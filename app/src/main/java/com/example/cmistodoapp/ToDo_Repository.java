package com.example.cmistodoapp;
import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ToDo_Repository
{

	private ToDo_DAO toDo_dao;
	private LiveData<List<ToDo>> ToDoList;

	ToDo_Repository(Application application) {
		ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(application);
		toDo_dao = db.toDo_dao();
		ToDoList = toDo_dao.getSortedToDos();
	}

	LiveData<List<ToDo>> getAllToDos()
	{
		return ToDoList;
	}

	void insert(ToDo toDo)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() ->
		{
			toDo_dao.insert(toDo);
		});
	}



}
