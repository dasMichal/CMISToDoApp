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


	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertToDo(ToDo_Entity toDo);

	//TODO RENAME IT LATER
	@Update(onConflict = OnConflictStrategy.REPLACE)
	void updateToDo(ToDo_Entity toDo);

	@Delete
	void deleteToDo(ToDo_Entity toDo);

	//@Transaction
	@Query("DELETE FROM SubTask_table WHERE FK_ToDoID = :toDoNum ")
	void deleteToDoWithSubTask(int toDoNum);

	@Query("DELETE FROM ToDo_table")
	void nukeTable();

	@Query("SELECT * FROM ToDo_table ORDER BY ToDo_ID ASC")
	LiveData<List<ToDo_Entity>> getSortedToDos();

	@Query("SELECT * FROM ToDo_table WHERE ToDo_ID = :todoNum ")
	LiveData<List<ToDo_Entity>> getToDo_ByID(int todoNum);

	@Query("SELECT * FROM ToDo_table WHERE ToDo_ID = :todoNum ")
	LiveData<ToDo_Entity> getToDoObject_byID(int todoNum);


	@Query("SELECT * FROM ToDo_table WHERE ToDo_ID = :todoNum ")
	ToDo_Entity getPLAINToDoObject_byID(int todoNum);

	@Query("SELECT ToDo_ID FROM ToDo_table WHERE ToDo_ID = :todoNum LIMIT 1")
	int getItemId(int todoNum);





	//----------------------ToDoFolder_Entity---------------------------------//


	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertFolder(ToDoFolder_Entity folder);

	@Delete
	void deleteFolder(ToDoFolder_Entity folder);

	@Query("SELECT * FROM Folder_Table ORDER BY Folder_ID ASC")
	LiveData<List<ToDoFolder_Entity>> getSortedFolderList();


	@Query("SELECT * FROM Folder_Table WHERE Folder_ID = :folderNum ")
	LiveData<ToDoFolder_Entity> getToDoFolderObject_byID(int folderNum);

	@Transaction
	@Query("SELECT * FROM Folder_Table")
	LiveData<List<FolderWithToDos>> getsFolderWithToDos();


	@Transaction
	@Query("SELECT * FROM ToDo_table WHERE FK_FolderID = :folderNum")
	LiveData<List<ToDo_Entity>> getToDosFromFolderTest(int folderNum);


	@Query("DELETE FROM ToDo_table WHERE FK_FolderID = :folderNum ")
	void deleteFolderWithToDos(int folderNum);



	//----------------------SubTask_Entity---------------------------------//


	@Query("SELECT * FROM SubTask_table ORDER BY SubTask_ID ASC")
	LiveData<List<SubTask_Entity>> getSortedSubTasks();

	@Insert(onConflict  = OnConflictStrategy.REPLACE)
	void insertSubTask(SubTask_Entity subTask);

	@Update(onConflict = OnConflictStrategy.REPLACE)
	void updateSubTask(SubTask_Entity subTask);

	@Transaction
	@Query("SELECT * FROM ToDo_table WHERE ToDo_ID = :todoNum ")
	LiveData<List<ToDosWithSubTasks>> getSubtasksFromToDO(int todoNum);

	@Transaction
	@Query("SELECT * FROM SubTask_table WHERE FK_ToDoID = :todoNum")
	LiveData<List<SubTask_Entity>> getSubtasksFromToDOTest(int todoNum);

	@Delete
	void deleteSubTask(SubTask_Entity subTask);




}
