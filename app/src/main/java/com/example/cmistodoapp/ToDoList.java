package com.example.cmistodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmistodoapp.adapter.ToDoListAdapter;
import com.example.cmistodoapp.persistency.ToDoFolder_Entity;
import com.example.cmistodoapp.persistency.ToDoViewModel;
import com.example.cmistodoapp.persistency.ToDo_Entity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ToDoList extends AppCompatActivity
{

	public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

	Toolbar toolbar;
	ToDoListAdapter adapter;
	FloatingActionButton fabToDoCreate;
	RecyclerView recyclerView;
	ToDoViewModel viewModel;
	int folderId;
	ToDoFolder_Entity newToDoFolderEntity;
	//List<ToDo_Entity> ScopeToDos = new ArrayList<>();
	List<ToDo_Entity> scopeToDos;



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);

		init();

		Intent in = getIntent();
		folderId = in.getIntExtra("folderID",0);

		//toolbar.setTitle(in.getStringExtra("selectedFolder"));
		viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);

		viewModel.getFolderObjectbyID(folderId).observe(this,v ->
		{
			toolbar.setTitle(v.getFolderName());
			newToDoFolderEntity.setFolderName(v.getFolderName());
			toolbar.setTitle(v.getFolderName());
		});


		/*
		viewModel.getsFolderWithToDos(folderId).observe(this, v ->
		{

			Log.d("OnChange Object", "Scoped Return");
			Log.d("FolderID", String.valueOf(folderId));


			scopeToDos = new ArrayList<>(v.get(folderId - 1).ToDo_Entity);

			for (ToDo_Entity tmp: scopeToDos)
			{
				System.out.println(tmp.getToDoName());
				System.out.println(tmp.getFKFolderID());
			}


			adapter = new ToDoListAdapter(scopeToDos, (id, localContext) ->
			{
				Intent intent = new Intent(localContext, ToDoEdit_Create.class);
				intent.putExtra("toDoID",id);
				localContext.startActivity(intent);
			});

			recyclerView.setAdapter(adapter);
			recyclerView.setLayoutManager(new LinearLayoutManager(this));
			adapter.notifyDataSetChanged();




		});

		*/

		viewModel.getToDosFromFolderTest(folderId).observe(this,todolist ->
		{


			try
			{

				Log.d("OnChange Object Optimized", "Scoped Return " +todolist);

				adapter = new ToDoListAdapter(todolist, (id, localContext) ->
				{
					Intent intent = new Intent(localContext, ToDoEdit_Create.class);
					intent.putExtra("toDoID",id);
					localContext.startActivity(intent);
				});

				recyclerView.setAdapter(adapter);
				recyclerView.setLayoutManager(new LinearLayoutManager(this));
				adapter.notifyDataSetChanged();


			}catch (NullPointerException e)
			{
				Log.e("Observable Scoped","No Object found. Null Point ");
			}


		});




		logic();
	}

	private void init()
	{

		newToDoFolderEntity = new ToDoFolder_Entity();
		toolbar = findViewById(R.id.toolbar);
		recyclerView = findViewById(R.id.RecycleTestID);
		fabToDoCreate = findViewById(R.id.fab_addToDo);

	}


	public void logic()
	{

		fabToDoCreate.setOnClickListener(v ->
		{
			final EditText input = new EditText(this);
			input.setSingleLine(true);
				new MaterialAlertDialogBuilder(this)
						.setTitle("Name of ToDo")
						//.setMessage("")
						.setCancelable(true)
						.setView(input)
						.setPositiveButton("Create",(dialog, which) ->
						{
							{
								if (!input.getText().toString().isEmpty())
								{

									ToDo_Entity newToDoEntity = new ToDo_Entity();
									newToDoEntity.setToDoName(input.getText().toString());
									newToDoEntity.setDone(false);
									newToDoEntity.setFKFolderID(folderId);

									viewModel.insertToDo(newToDoEntity);
									//viewModel.getAllToDos();
									//viewModel.getsFolderWithToDos(folderId);
									adapter.notifyDataSetChanged();

								}


							}
						}).show();


		});



	}




	public float dptopx(float dp)
	{
		return dp * getResources().getDisplayMetrics().density;
	}





	//------------------Graveyard orbit-------------------------------------

}