package com.example.cmistodoapp.persistency;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SubTask_Repository
{


	private ToDo_DAO toDo_dao;
	LiveData<List<ToDosWithSubTasks>> SubTasks_Scoped;

	public SubTask_Repository(Application application)
	{
		ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(application);
		toDo_dao = db.DAO();
	}

	void insert(SubTask_Entity subTask)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() ->
		{
			toDo_dao.insertSubTask(subTask);
		});
	}

	void deleteSubTask(SubTask_Entity subTask)
	{
		ToDoRoomDatabase.databaseWriteExecutor.execute(() ->
		{
			toDo_dao.deleteSubTask(subTask);
		});
	};

	LiveData<List<ToDosWithSubTasks>> getSubtasksFromToDO(int id)
	{
		SubTasks_Scoped = toDo_dao.getSubtasksFromToDO(id);
		return SubTasks_Scoped;
	}




	LiveData<List<SubTask_Entity>> getSubtasksFromToDOTest(int todoNum)
	{
		return toDo_dao.getSubtasksFromToDOTest(todoNum);

	}






















}
