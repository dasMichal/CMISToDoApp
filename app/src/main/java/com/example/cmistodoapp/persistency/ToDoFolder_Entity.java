package com.example.cmistodoapp.persistency;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Folder_Table")
public class ToDoFolder_Entity
{

	@NotNull
	@ColumnInfo(name= "Folder_name")
	private String FolderName;

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name= "Folder_ID")
	private int FolderID;


	@NotNull
	public String getFolderName()
	{
		return FolderName;
	}

	public void setFolderName(@NotNull String folderName)
	{
		FolderName = folderName;
	}

	public int getFolderID()
	{
		return FolderID;
	}

	public void setFolderID(int folderID)
	{
		FolderID = folderID;
	}
}





