package com.example.cmistodoapp.persistency;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "ToDo_table")
public class ToDo_Entity
{

	public ToDo_Entity()
	{
		//this.ToDoID = 0;
		this.ToDoName = "name";
		this.isDone = false;

	}

	@NotNull
	@ColumnInfo(name= "ToDo_Name")
	private String ToDoName;

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name= "ToDo_ID")
	private int ToDoID;


	@ColumnInfo(name= "ToDo_IsDone")
	private boolean isDone;

	@ColumnInfo(name= "FK_FolderID")
	private int FKFolderID;




	public int getFKFolderID()
	{
		return FKFolderID;
	}

	public void setFKFolderID(int FKFolderID)
	{
		this.FKFolderID = FKFolderID;
	}

	@NotNull
	public String getToDoName()
	{
		return this.ToDoName;
	}

	public void setToDoName(@NotNull String toDoName)
	{
		ToDoName = toDoName;
	}

	public int getToDoID()
	{
		return this.ToDoID;
	}

	public void setToDoID(int toDoID)
	{
		ToDoID = toDoID;
	}

	public boolean isDone()
	{
		return this.isDone;
	}

	public void setDone(boolean done)
	{
		isDone = done;
	}


}


