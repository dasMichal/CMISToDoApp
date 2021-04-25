package com.example.cmistodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmistodoapp.adapter.ToDoListAdapter;
import com.example.cmistodoapp.persistency.ToDoFolder_Entity;
import com.example.cmistodoapp.persistency.ToDoViewModel;
import com.example.cmistodoapp.persistency.ToDo_Entity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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



		viewModel.getAllToDos().observe(this,toDos ->
		{

			/*
			adapter = new ToDoListAdapter(toDos, (id, localContext) ->
			{
				Intent intent = new Intent(localContext, ToDoEdit_Create.class);
				intent.putExtra("toDoID",id);
				localContext.startActivity(intent);
			});

			recyclerView.setAdapter(adapter);
			recyclerView.setLayoutManager(new LinearLayoutManager(this));
			adapter.notifyDataSetChanged();


			 */
		});



		viewModel.getFolderObjectbyID(folderId).observe(this,v ->
		{

			toolbar.setTitle(v.getFolderName());
			newToDoFolderEntity.setFolderName(v.getFolderName());
			toolbar.setTitle(v.getFolderName());
		});


		viewModel.returnScopedFolder(folderId).observe(this,v ->
		{

			Log.d("OnChange Object", "Scoped Return");
			Log.d("FolderID", String.valueOf(folderId));


			scopeToDos = new ArrayList<>(v.get(folderId - 1).ToDo_Entity);


			for (ToDo_Entity tmp: scopeToDos)
			{
				System.out.println(tmp.getToDoName());
				System.out.println(tmp.getFKFolderID());


			}


			/*
			adapter.setData(scopeToDos);
			adapter.notifyDataSetChanged();


			 */


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
								if (input.getText().toString().isEmpty())
								{
									return;
								}else
								{

									ToDo_Entity newToDoEntity = new ToDo_Entity();
									newToDoEntity.setToDoName(input.getText().toString());
									newToDoEntity.setDone(false);
									newToDoEntity.setFKFolderID(folderId);

									viewModel.insertToDo(newToDoEntity);
									//viewModel.getAllToDos();
									viewModel.returnScopedFolder(folderId);
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






			/*
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
			*/


	}
}