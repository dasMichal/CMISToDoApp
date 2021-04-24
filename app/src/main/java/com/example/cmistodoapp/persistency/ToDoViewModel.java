package com.example.cmistodoapp.persistency;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel
{

	private final ToDo_Repository repositoryToDo;
	private final ToDoFolder_Repository repositoryFolder;
	private final LiveData<List<ToDo_Entity>> myToDoListLIVE;
	private final LiveData<List<ToDoFolder_Entity>> myToDoFolderLIVE;

	LiveData<List<ToDo_Entity>> myToDoByID ;
	MutableLiveData<String> myToDOTitel ;


	private LiveData<List<ToDo_Entity>> mAllWords;
	private LiveData<List<ToDo_Entity>> searchByLiveData;
	private MutableLiveData<Integer> filterLiveData = new MutableLiveData<>();


	public ToDoViewModel(@NonNull Application application)
	{
		super(application);
		repositoryToDo = new ToDo_Repository(application);
		repositoryFolder = new ToDoFolder_Repository(application);

		myToDoListLIVE = repositoryToDo.getAllToDos();
		myToDoFolderLIVE = repositoryFolder.getAllFolder();

		searchByLiveData = Transformations.switchMap(filterLiveData, repositoryToDo::getByID);

		//myToDoByID = new MutableLiveData<>();
		//myToDOTitel = new MutableLiveData<>();

	}


	public LiveData<List<ToDo_Entity>> getAllToDos()
	{

		return myToDoListLIVE;
	};


	public LiveData<List<ToDo_Entity>> getTODOByID(int id)
	{

		setID(id);
		return repositoryToDo.getByID(id);

	}


	public LiveData<ToDo_Entity> getTODOObjectByID(int id)
	{

		setID(id);
		return repositoryToDo.returnToDoObject(id);

	}


	public void setID(int id)
	{
		repositoryToDo.setID(id);

	}



	public void setFilter(int id)
	{

		System.out.println("FilterSet to " +id);
		filterLiveData.setValue(id);

	}



	public LiveData<List<ToDo_Entity>> getSearchBy()
	{
		return searchByLiveData;
	}


	public void insertToDo(ToDo_Entity toDoEntity)
	{
		repositoryToDo.insert(toDoEntity);
	}

	public LiveData<String> getToDoTitle(LiveData<ToDo_Entity> todo)
	{

		this.myToDOTitel.setValue(todo.getValue().getToDoName());

		return myToDOTitel;
	}


	public void  updateDataset(ToDo_Entity toDoEntity)
	{

		repositoryToDo.updateDatasetREPO(toDoEntity);

	}




	public LiveData<List<ToDoFolder_Entity>> getAllFolders()
	{

		return myToDoFolderLIVE;
	};


	public LiveData<ToDoFolder_Entity> getFolderObjectbyID(int id)
	{

		setID(id);
		return repositoryFolder.returnFolderObject(id);

	}




	public LiveData<List<FolderWithToDos>> returnScopedFolder(int id)
	{

		//setID(id);
		return repositoryFolder.returnScopedFolder(id);

	}

	public void insertFolder(ToDoFolder_Entity folder)
	{
		repositoryFolder.insert(folder);
	}


}
