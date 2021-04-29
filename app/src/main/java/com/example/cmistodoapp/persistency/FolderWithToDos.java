package com.example.cmistodoapp.persistency;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FolderWithToDos
{
	@Embedded public ToDoFolder_Entity folder;
	@Relation(
			parentColumn = "Folder_ID",
			entityColumn = "FK_FolderID"
	)
	public List<ToDo_Entity> ToDo_Entity;

}
