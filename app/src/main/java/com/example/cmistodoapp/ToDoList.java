package com.example.cmistodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoList extends AppCompatActivity
{

	List<String> data2 = new ArrayList<>();

	Toolbar toolbar;
	TestRecycle adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);
		toolbar = findViewById(R.id.toolbar);
		//createToDoList();

		RecyclerView recyclerView = findViewById(R.id.RecycleTestID);

		 adapter = new TestRecycle(generateData(), new TestRecycle.OnItemClickListener()
		{
			@Override
			public void onItemClick(Integer item)
			{
				System.out.println("Hi");
				System.out.println(item);
				//recyclerView.removeViewAt(item);
				//adapter.notifyItemRemoved(item);


			}

		});


		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		//recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));




		/**
		RecyclerView recyclerViewDone = findViewById(R.id.RecycleDone);
		CustomAdapter custadapter = new CustomAdapter(generateData());
		recyclerViewDone.setAdapter(custadapter);
		recyclerViewDone.setLayoutManager(new LinearLayoutManager(this));
		**/


		Intent in = getIntent();
		toolbar.setTitle(in.getStringExtra("selectedFolder"));


		logic();
	}



	public void logic()
	{


		System.out.println("LOL");


	}

	private List<Integer> generateData()
	{
		List<Integer> data = new ArrayList<>();

		for (int i = 0; i < 6; i++)
		{

			//data.add("ToDo Nr "+ i);
			data.add(i);
		}
		return data;


	}

	private void createToDoList()
	{

		TableLayout.LayoutParams TableLayout = new TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.MATCH_PARENT);
		TableLayout.weight = 1;

		TableRow.LayoutParams RowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
		RowLayout.weight = 1;
		RowLayout.gravity= Gravity.CENTER;
		RowLayout.setMargins(10,20 ,10,20);





		LinearLayout.LayoutParams ListHolderParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


		LinearLayout GlobalList = findViewById(R.id.GlobaleList);
		LinearLayout ListHolder = new LinearLayout(this);
		ListHolder.setLayoutParams(ListHolderParams);
		GlobalList.addView(ListHolder);



		LinearLayout TimeandLocationHolder = new LinearLayout(this);
		LinearLayout ToDOCardContendList = new LinearLayout(this );
		TimeandLocationHolder.setOrientation(LinearLayout.VERTICAL);
		ListHolder.setOrientation(LinearLayout.VERTICAL);
		ToDOCardContendList.setOrientation(LinearLayout.HORIZONTAL);



		CardView ToDoCard = new CardView(this);
		TextView ToDoText = new TextView(this);
		TextView ToDoTime = new TextView(this);
		TextView ToDoLocation = new TextView(this);
		View viewDivider = new View(this);
		viewDivider.setLayoutParams(new RelativeLayout.LayoutParams((int) dptopx(2),ViewGroup.LayoutParams.MATCH_PARENT));
		RadioButton ToDoRadioButton = new RadioButton(this);



		TimeandLocationHolder.addView(ToDoTime,0);
		TimeandLocationHolder.addView(ToDoLocation,1);

		ToDOCardContendList.addView(ToDoRadioButton,0);
		ToDOCardContendList.addView(ToDoText,1);
		ToDOCardContendList.addView(viewDivider,2);
		ToDOCardContendList.addView(TimeandLocationHolder,3);

		ToDoCard.addView(ToDOCardContendList);
		ListHolder.addView(ToDoCard);






			/**
			CardView folderCard = new CardView(this);

			folderCard.setMinimumWidth((int) dptopx(100));
			folderCard.setMinimumHeight((int) dptopx(100));

			TextView playCardText = new TextView(this);
			playCardText.setText(" "+ranNumbers.get(i));
			playCardText.setGravity(Gravity.CENTER);
			playCardText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


			folderCard.setContentPadding((int) dptopx(20),(int) dptopx(20),(int) dptopx(20),(int) dptopx(20));
			folderCard.setId(i);
			folderCard.setTag(ranNumbers.get(i));
			folderCard.setElevation(10);
			folderCard.addView(playCardText);
			folderCard.setLayoutParams(RowLayout);
			folderCard.setOnClickListener(v -> changeActivity());
			**/


	}






	public float dptopx(float dp)
	{
		return dp * getResources().getDisplayMetrics().density;
	}



}