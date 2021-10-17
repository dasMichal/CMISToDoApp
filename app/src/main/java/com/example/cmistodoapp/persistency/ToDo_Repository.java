package com.example.cmistodoapp.persistency;
import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDo_Repository
{

	private final ToDo_DAO toDo_dao;
	private final LiveData<List<ToDo_Entity>> toDoList_Repo;
	private LiveData<List<ToDo_Entity>> toDoByID_Repo;
	LiveData<ToDo_Entity> toDoByIDObject_Repo;
	int id;


	public ToDo_Repository(Application application)
	{
		ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(application);
		toDo_dao = db.DAO();
		toDoList_Repo = toDo_dao.getSortedToDos();

	}

	 LiveData<List<ToDo_Entity>> getAllToDos()
	{
		return toDoList_Repo;
	}


	void deleteToDo(ToDo_Entity toDoEntity)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() -> toDo_dao.deleteToDo(toDoEntity));
	}


	void deleteToDoWithSubTask(int  id)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() -> toDo_dao.deleteToDoWithSubTask(id));
	}


	void insertToDo(ToDo_Entity toDoEntity)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() -> toDo_dao.insertToDo(toDoEntity));
	}

	void updateToDo(ToDo_Entity toDoEntity)
	{

		ToDoRoomDatabase.databaseWriteExecutor.execute(() -> toDo_dao.updateToDo(toDoEntity));

	}


	LiveData<List<ToDo_Entity>> getByID(int id)
	{
		toDoByID_Repo = toDo_dao.getToDo_ByID(id);
		return toDoByID_Repo;
	}


	LiveData<ToDo_Entity> getToDoObject_byID(int id)
	{
		toDoByIDObject_Repo = toDo_dao.getToDoObject_byID(id);
		return toDoByIDObject_Repo;
	}

	void setID(int id)
	{
		this.id= id;
	}


}
