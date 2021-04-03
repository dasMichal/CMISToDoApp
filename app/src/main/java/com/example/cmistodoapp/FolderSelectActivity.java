package com.example.cmistodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.design_folder_select_activity);

		recyclerView = findViewById(R.id.folderHolder);

		adapter = new FolderAdapter(data2, new FolderAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(Integer item)
			{ }

			public void test(View v)
			{ }

		});

		recyclerView.setAdapter(adapter);
		//recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setLayoutManager(new GridLayoutManager(this,2));



		FloatingActionButton fab = findViewById(R.id.FAB_createFolder);
		fab.setOnClickListener(view -> {

			createFolder(view);

		});

		toolbar1 = findViewById(R.id.toolbar1);
		menue1 = findViewById(R.id.drawerLayout);



		toolbar1.setNavigationOnClickListener(v ->
		{
			menue1.open();
		});


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

						if (input.getText().toString().isEmpty())
						{

							return;

						}else
						{

							data2.add(input.getText().toString());
							System.out.println(data2);
							adapter.notifyDataSetChanged();
							Snackbar.make(view, "Folder Created", Snackbar.LENGTH_LONG).setAction("Action", null).show();

						}

					}
				}).show();




	}





	private void createGameField(List<Integer> ranNumbers, int numberFields, double average)
	{


		TableLayout TableGameField = findViewById(R.id.FolderTable);

		TableGameField.removeAllViews();
		TableGameField.setPadding(0,10,0,10);

		TableLayout.LayoutParams TableLayout = new TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.MATCH_PARENT);
		TableLayout.weight = 1;

		TableRow.LayoutParams RowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
		RowLayout.weight = 1;
		RowLayout.gravity= Gravity.CENTER;
		RowLayout.setMargins(10,20 ,10,20);




		List<String> namesofFolders = new ArrayList<>();

		namesofFolders.add("School");
		namesofFolders.add("Exercise");
		namesofFolders.add("Work");
		namesofFolders.add("Vacation");




		for (int i = 0; i < numberFields; i++)
		{

			//System.out.println(i%2);
			if (i % 2 == 0)
			{
				//System.out.println("modulo if");
				tr = new TableRow(this);
				tr.setLayoutParams(TableLayout);
				TableGameField.addView(tr);

			}


			CardView folderCard = new CardView(this);

			folderCard.setMinimumWidth((int) dptopx(100));
			folderCard.setMinimumHeight((int) dptopx(100));

			TextView playCardText = new TextView(this);
			//playCardText.setText(" "+ranNumbers.get(i));
			playCardText.setText(" "+namesofFolders.get(i));
			playCardText.setGravity(Gravity.CENTER);
			playCardText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


			folderCard.setContentPadding((int) dptopx(20),(int) dptopx(20),(int) dptopx(20),(int) dptopx(20));
			folderCard.setId(i);
			folderCard.setTag(ranNumbers.get(i));
			folderCard.setElevation(10);
			folderCard.addView(playCardText);
			folderCard.setLayoutParams(RowLayout);
			folderCard.setOnClickListener(v -> changeActivity(folderCard.getId(),playCardText.getText().toString()));
			tr.addView(folderCard);


		}

	}







	public void changeActivity(int id, String s)
	{
		Intent intent = new Intent(FolderSelectActivity.this, ToDoList.class);

		intent.putExtra("selectedFolder", s);
		startActivity(intent);      //Start New activity


	}



	public float dptopx(float dp)
	{
		return dp * getResources().getDisplayMetrics().density;
	}
}