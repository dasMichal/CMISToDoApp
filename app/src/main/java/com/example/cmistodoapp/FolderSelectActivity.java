package com.example.cmistodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FolderSelectActivity extends AppCompatActivity
{


	List<Integer> ranNumbers = new ArrayList<>();
	TableRow tr;
	DrawerLayout menue1;
	Toolbar toolbar1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.design_folder_select_activity);

		FloatingActionButton fab = findViewById(R.id.FAB_createFolder);
		fab.setOnClickListener(view -> {

			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
			ranNumbers.add(1);
			ranNumbers.add(2);
			ranNumbers.add(3);
			ranNumbers.add(4);
			createGameField(ranNumbers,4,2.0);

		});

		toolbar1 = findViewById(R.id.toolbar1);
		menue1 = findViewById(R.id.drawerLayout);
		//menue1.close();
		toolbar1.setNavigationOnClickListener(v ->
		{

			menue1.open();

		});


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
			//folderCard.setOnClickListener(v -> compare2(ranNumbers, average, (Integer) folderCard.getTag()));
			tr.addView(folderCard);


		}

	}

	public float dptopx(float dp)
	{
		return dp * getResources().getDisplayMetrics().density;
	}
}