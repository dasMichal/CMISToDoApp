package com.example.cmistodoapp.persistency;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.cmistodoapp.converter.Converters;

import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

@Entity(tableName = "ToDo_table",
		foreignKeys = {@ForeignKey(entity = ToDoFolder_Entity.class,
				parentColumns = "Folder_ID",
				childColumns = "FK_FolderID",
				onDelete = ForeignKey.CASCADE)
		})
@TypeConverters({Converters.class})

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


	@ColumnInfo(name= "FK_FolderID", index = true)
	private int FKFolderID;


	@ColumnInfo(name= "ToDo_DueTime")
	private ZonedDateTime dueTime;

	@ColumnInfo(name= "ToDo_RemindeTime")
	private ZonedDateTime remindeTime;


	public ZonedDateTime getDueTime()
	{
		return dueTime;
	}

	public void setDueTime(ZonedDateTime dueTime)
	{
		this.dueTime = dueTime;
	}

	public ZonedDateTime getRemindeTime()
	{
		return remindeTime;
	}

	public void setRemindeTime(ZonedDateTime remindeTime)
	{
		this.remindeTime = remindeTime;
	}

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


