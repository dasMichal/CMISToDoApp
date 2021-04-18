package com.example.cmistodoapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "ToDo_table")
public class ToDo
{

	@NotNull
	@ColumnInfo(name= "ToDo_Name")
	private String ToDoName;

	@PrimaryKey
	@ColumnInfo(name= "ToDo_ID")
	private int ToDoID;


	@ColumnInfo(name= "ToDo_IsDone")
	private boolean isDone;


	public ToDo(int toDoID, @NotNull String toDoName,  boolean isDone)
	{
		this.ToDoID = toDoID;
		this.ToDoName = toDoName;
		this.isDone = isDone;
	}


	@NotNull
	public String getToDoName()
	{
		return ToDoName;
	}

	public void setToDoName(@NotNull String toDoName)
	{
		ToDoName = toDoName;
	}

	public int getToDoID()
	{
		return ToDoID;
	}

	public void setToDoID(int toDoID)
	{
		ToDoID = toDoID;
	}

	public boolean isDone()
	{
		return isDone;
	}

	public void setDone(boolean done)
	{
		isDone = done;
	}
}
