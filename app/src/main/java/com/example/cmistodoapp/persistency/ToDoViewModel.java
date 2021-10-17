package com.example.cmistodoapp.persistency;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel
{

	private final ToDo_Repository repositoryToDo;
	private final ToDoFolder_Repository repositoryFolder;
	private final SubTask_Repository repositorySubTask;
	private final LiveData<List<ToDo_Entity>> myToDoListLIVE;
	private final LiveData<List<ToDoFolder_Entity>> myToDoFolderLIVE;
	private  LiveData<List<ToDo_Entity>> workmanagerTestToDo;

	LiveData<List<ToDo_Entity>> myToDoByID ;
	MutableLiveData<String> myToDOTitel ;



	public ToDoViewModel(@NonNull Application application)
	{
		super(application);
		repositoryToDo = new ToDo_Repository(application);
		repositoryFolder = new ToDoFolder_Repository(application);
		repositorySubTask = new SubTask_Repository(application);

		myToDoListLIVE = repositoryToDo.getAllToDos();
		myToDoFolderLIVE = repositoryFolder.getAllFolder();


	}


	public LiveData<List<ToDo_Entity>> getAllToDos()
	{

		return myToDoListLIVE;
	}


	public LiveData<List<ToDo_Entity>> getTODOByID(int id)
	{
		setID(id);
		return repositoryToDo.getByID(id);
	}


	public LiveData<ToDo_Entity> getToDoObject_byID(int id)
	{
		setID(id);
		return repositoryToDo.getToDoObject_byID(id);
	}


	public void setID(int id)
	{
		repositoryToDo.setID(id);
	}


	public void insertToDo(ToDo_Entity toDoEntity)
	{
		repositoryToDo.insertToDo(toDoEntity);
	}


	public void deleteToDo(ToDo_Entity toDoEntity)
	{

		Log.d("ViewModel ToDO","Deleting a ToDO");
		repositoryToDo.deleteToDo(toDoEntity);
	}


	public void deleteToDoWithSubTask(int id)
	{
		repositoryToDo.deleteToDoWithSubTask(id);
	}


	public LiveData<String> getToDoTitle(LiveData<ToDo_Entity> todo)
	{
		this.myToDOTitel.setValue(todo.getValue().getToDoName());
		return myToDOTitel;
	}

	public void updateToDo(ToDo_Entity toDoEntity)
	{
		repositoryToDo.updateToDo(toDoEntity);
	}



	//------------------------Folder-------------------------


	public LiveData<List<ToDoFolder_Entity>> getAllFolders()
	{
		return myToDoFolderLIVE;
	}

	public LiveData<ToDoFolder_Entity> getFolderObjectbyID(int id)
	{
		setID(id);
		return repositoryFolder.getToDoFolderObject_byID(id);
	}

	public LiveData<List<FolderWithToDos>> getsFolderWithToDos(int id)
	{
		//setID(id);
		return repositoryFolder.getsFolderWithToDos(id);
	}

	public LiveData<List<ToDo_Entity>>  getToDosFromFolderTest(int folderid)
	{

		return repositoryFolder.getToDosFromFolderTest(folderid);

	}


	public void insertFolder(ToDoFolder_Entity folder)
	{
		repositoryFolder.insert(folder);
	}



	public void deleteFolder(ToDoFolder_Entity folder)
	{
		Log.d("ViewModel Folder","Deleting a Folder");
		repositoryFolder.deleteFolder(folder);
	}


	//---------------------------SubTasks----------------------


	public LiveData<List<ToDosWithSubTasks>> getSubtasksFromToDO(int id)
	{

		return repositorySubTask.getSubtasksFromToDO(id);

	}

	public LiveData<List<SubTask_Entity>> getSubtasksFromToDOTest(int todoNum)
	{
		return repositorySubTask.getSubtasksFromToDOTest(todoNum);

	}



	public void insertSubTask(SubTask_Entity subTask)
	{
		repositorySubTask.insert(subTask);
	}


	public void deleteSubTask(SubTask_Entity subTask)
	{
		repositorySubTask.deleteSubTask(subTask);

	}


}
