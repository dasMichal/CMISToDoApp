package com.example.cmistodoapp.persistency;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDo_DAO
{

	//----------------------ToDo_Entity---------------------------------//

	@Query("SELECT * FROM ToDo_table ORDER BY ToDo_ID ASC")
	LiveData<List<ToDo_Entity>> getSortedToDos();

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertToDo(ToDo_Entity toDo);

	@Update(onConflict = OnConflictStrategy.REPLACE)
	void updateDataset(ToDo_Entity toDo);


	@Query("DELETE FROM ToDo_table")
	void nukeTable();

	@Query("SELECT * FROM ToDo_table WHERE ToDo_ID = :todoNum ")
	LiveData<List<ToDo_Entity>> getToDoByID(int todoNum);

	@Query("SELECT * FROM ToDo_table WHERE ToDo_ID = :todoNum ")
	LiveData<ToDo_Entity> returnToDoByIDObject(int todoNum);

	@Query("SELECT ToDo_ID FROM ToDo_table WHERE ToDo_ID = :todoNum LIMIT 1")
	int getItemId(int todoNum);


	@Delete
	void deleteToDo(ToDo_Entity toDo);




	//----------------------ToDoFolder_Entity---------------------------------//


	@Query("SELECT * FROM Folder_Table ORDER BY Folder_ID ASC")
	LiveData<List<ToDoFolder_Entity>> getSortedFolderList();

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertFolder(ToDoFolder_Entity folder);

	@Query("SELECT * FROM Folder_Table WHERE Folder_ID = :folderNum ")
	LiveData<ToDoFolder_Entity> returnToDoFolderByIDObject(int folderNum);






	@Transaction
	@Query("SELECT * FROM Folder_Table")
	public LiveData<List<FolderWithToDos>> getsFolderToDos();



}
