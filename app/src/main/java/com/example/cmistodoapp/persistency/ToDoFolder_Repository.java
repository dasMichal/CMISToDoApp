package com.example.cmistodoapp.persistency;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDoFolder_Repository
{


	private ToDo_DAO toDo_dao;
	private LiveData<List<ToDoFolder_Entity>> ToDoFolderList_Repo;
	LiveData<ToDoFolder_Entity> ToDoFolderByIdObject_Repo;
	LiveData<List<FolderWithToDos>> ToDo_Scoped;



	public ToDoFolder_Repository(Application application) {
		ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(application);
		toDo_dao = db.DAO();
		ToDoFolderList_Repo = toDo_dao.getSortedFolderList();

	}


	LiveData<List<ToDoFolder_Entity>> getAllFolder()
	{
		return ToDoFolderList_Repo;
	}

	void insert(ToDoFolder_Entity folder)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() ->
		{
			toDo_dao.insertFolder(folder);
		});
	}


	LiveData<ToDoFolder_Entity> returnFolderObject(int id)
	{
		ToDoFolderByIdObject_Repo = toDo_dao.returnToDoFolderByIDObject(id);
		return ToDoFolderByIdObject_Repo;
	}


	LiveData<List<FolderWithToDos>> returnScopedFolder(int id)
	{
		ToDo_Scoped = toDo_dao.getsFolderToDos();
		return ToDo_Scoped;
	}



}
