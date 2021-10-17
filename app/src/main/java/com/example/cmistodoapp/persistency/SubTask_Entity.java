package com.example.cmistodoapp.persistency;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "SubTask_table",
		foreignKeys = {@ForeignKey(entity = ToDo_Entity.class,
				parentColumns = "ToDo_ID",
				childColumns = "FK_ToDoID",
				onDelete = ForeignKey.CASCADE)
		})

public class SubTask_Entity
{


	public SubTask_Entity()
	{
		//this.ToDoID = 0;
		this.SubTaskText = "name";
		this.isDone = false;

	}


	@NotNull
	@ColumnInfo(name= "SubTask_Text")
	private String SubTaskText;

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name= "SubTask_ID")
	private int SubTaskID;


	@ColumnInfo(name= "SubTask_IsDone")
	private boolean isDone;

	@ColumnInfo(name= "FK_ToDoID", index = true)
	private int FKToDoID;


	@NotNull
	public String getSubTaskText()
	{
		return SubTaskText;
	}

	public void setSubTaskText(@NotNull String subTaskText)
	{
		SubTaskText = subTaskText;
	}

	public int getSubTaskID()
	{
		return SubTaskID;
	}

	public void setSubTaskID(int subTaskID)
	{
		SubTaskID = subTaskID;
	}

	public boolean isDone()
	{
		return isDone;
	}

	public void setDone(boolean done)
	{
		this.isDone = done;
	}

	public int getFKToDoID()
	{
		return FKToDoID;
	}

	public void setFKToDoID(int FKToDoID)
	{
		this.FKToDoID = FKToDoID;
	}
}
