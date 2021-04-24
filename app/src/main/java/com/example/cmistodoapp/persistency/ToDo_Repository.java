package com.example.cmistodoapp.persistency;
import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDo_Repository
{

	private ToDo_DAO toDo_dao;
	private LiveData<List<ToDo_Entity>> toDoList_Repo;
	private LiveData<List<ToDo_Entity>> toDoByID_Repo;
	LiveData<ToDo_Entity> toDoByIDObject_Repo;
	int id;


	public ToDo_Repository(Application application) {
		ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(application);
		toDo_dao = db.DAO();
		toDoList_Repo = toDo_dao.getSortedToDos();

	}

	 LiveData<List<ToDo_Entity>> getAllToDos()
	{
		return toDoList_Repo;
	}


	void insert(ToDo_Entity toDoEntity)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() ->
		{
			toDo_dao.insertToDo(toDoEntity);
		});
	}

	void updateDatasetREPO(ToDo_Entity toDoEntity)
	{
		/*
		int id  = toDo_dao.getItemId(toDoEntity.getToDoID());

		if(id == null)
			insert(toDoEntity);
		else

		 */

		ToDoRoomDatabase.databaseWriteExecutor.execute(() ->
		{
			toDo_dao.updateDataset(toDoEntity);
		});

	}



	LiveData<List<ToDo_Entity>> getByID(int id)
	{

		toDoByID_Repo = toDo_dao.getToDoByID(id);
		return toDoByID_Repo;

	}



	LiveData<ToDo_Entity> returnToDoObject(int id)
	{
		toDoByIDObject_Repo = toDo_dao.returnToDoByIDObject(id);
		return toDoByIDObject_Repo;


	}

	void setID(int id)
	{
		this.id= id;
	}


}
