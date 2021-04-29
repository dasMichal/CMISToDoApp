package com.example.cmistodoapp.persistency;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ToDosWithSubTasks
{

	@Embedded
	public ToDo_Entity toDo_entity;
	@Relation(
			parentColumn = "ToDo_ID",
			entityColumn = "FK_ToDoID"
	)
	public List<SubTask_Entity> SubTask_Entity;





}
