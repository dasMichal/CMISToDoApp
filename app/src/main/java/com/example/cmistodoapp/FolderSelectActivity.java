package com.example.cmistodoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmistodoapp.adapter.FolderAdapter;
import com.example.cmistodoapp.persistency.ToDoFolder_Entity;
import com.example.cmistodoapp.persistency.ToDoViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FolderSelectActivity extends AppCompatActivity
{


	List<Integer> ranNumbers = new ArrayList<>();
	List<String> data2 = new ArrayList<>();
	TableRow tr;
	DrawerLayout menue1;
	Toolbar toolbar1;
	RecyclerView recyclerView;
	FolderAdapter adapter;
	ToDoViewModel viewModel;
	FloatingActionButton fab;
	boolean deleting = false;
	ToDoFolder_Entity newToDOFolder;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.design_folder_select_activity);

		init();

		viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);

		viewModel.getAllFolders().observe(this,folder ->
		{

			Log.d("OnChange Object", "Folder on change");

			adapter = new FolderAdapter(folder, new FolderAdapter.OnItemClickListener()
			{
				@Override
				public void onFolderClick(int id, Context localContext)
				{
					Intent intent = new Intent(localContext, ToDoList.class);
					//intent.putExtra("selectedFolder", folder.get(id).getFolderName());
					intent.putExtra("folderID", id);
					localContext.startActivity(intent);

				}
			}, new FolderAdapter.OnCreateContextMenuListener()
			{
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, int position)
				{

					//onCreateContextMenu(menu, v, menuInfo,position);
					RecyclerView test = (RecyclerView) v.getParent();


					menu.setHeaderTitle("Select The Action");
					menu.add(0, position, 0, "Delete2");//groupId, itemId, order, title


					//MenuInflater inflater = getMenuInflater();
					//inflater.inflate(R.menu.context_menue, menu);

				}
			}, new View.OnCreateContextMenuListener()
			{
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
				{


					RecyclerView tempRecycle = (RecyclerView) v.getParent();
					int position = tempRecycle.getChildAdapterPosition(v);
					LinearLayout parent2 = (LinearLayout) tempRecycle.getChildAt(position);
					int realID =  parent2.getChildAt(0).getId();


					newToDOFolder.setFolderID(folder.get(position).getFolderID());
					newToDOFolder.setFolderName(folder.get(position).getFolderName());

					System.out.println("RealID: "+realID);
					System.out.println("Pos in Recycle: "+position);


					menu.add(realID, realID, 0, "Delete");//groupId, itemId, order, title
				}
			}


			);


			recyclerView.setAdapter(adapter);
			recyclerView.setLayoutManager(new GridLayoutManager(this,2));
			registerForContextMenu(recyclerView);

		});

		logic();



	}




	/*

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("FFFF");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menue, menu);


	}


	 */
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{

		System.out.println("item.getItemId() = " + item.getItemId());
		System.out.println(item.getGroupId());

		switch (item.getTitle().toString())
		{
			case "Delete":

				System.out.println("Delete");

				deleteFolder();


				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	private void deleteFolder()
	{


		new AlertDialog.Builder(this)
				.setTitle("Delete Folder?")
				.setCancelable(true)
				.setPositiveButton(R.string.yes,(dialog, which) ->
				{
					{

						deleting = true;

						viewModel.deleteFolder(newToDOFolder);


					}
				})
				.setNegativeButton(R.string.no,(dialog, which) ->
				{
					{
						dialog.dismiss();
					}
				})

				.show();

	}


	private void logic()
	{

		fab.setOnClickListener(this::createFolder);


		toolbar1.setNavigationOnClickListener(v ->
		{
			menue1.open();
		});







	}





















	private void init()
	{
		newToDOFolder = new ToDoFolder_Entity();
		fab = findViewById(R.id.FAB_createFolder);
		toolbar1 = findViewById(R.id.toolbar1);
		menue1 = findViewById(R.id.drawerLayout);
		recyclerView = findViewById(R.id.folderHolder);
	}


	private void createFolder(View view)
	{

		final EditText input = new EditText(this);
		input.setSingleLine(true);
		new MaterialAlertDialogBuilder(this)
				.setTitle("Name of your Folder")
				//.setMessage("")
				.setCancelable(true)
				.setView(input)
				.setPositiveButton("Create",(dialog, which) ->
				{
					{

						if (!input.getText().toString().isEmpty())
						{

							ToDoFolder_Entity newFolder = new ToDoFolder_Entity();
							newFolder.setFolderName(input.getText().toString());
							viewModel.insertFolder(newFolder);

							adapter.notifyDataSetChanged();
							Snackbar.make(view, "Folder Created", Snackbar.LENGTH_LONG).setAction("Action", null).show();

						} else
						{

							return;

						}

					}
				}).show();




	}







	public float dptopx(float dp)
	{
		return dp * getResources().getDisplayMetrics().density;
	}



	//------------------Graveyard orbit-----------------------


}