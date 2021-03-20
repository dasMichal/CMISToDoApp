package com.example.cmistodoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity2 extends AppCompatActivity
{





	TextView randomNumberBoundText;
	EditText todoNameTextField;
	EditText subTask1;
	TextView todo_reminderText;
	RadioButton IsDone;
	LinearLayout subTaskLayout;
	MaterialTimePicker timePicker;
	FragmentManager FragManager;
	FragmentManager manager = getSupportFragmentManager();
	Calendar calendar;
	TimePickerDialog timePickerDialog;
	DatePickerDialog datePickerDialog;
	String usrName;

	int numSubtask = 0;
	int hour = 0;
	int minute = 0;





	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);



		Intent in = getIntent();

		usrName = in.getStringExtra("toDoTitle");
		/*
		scoreArray = in.getIntArrayExtra("scoreArray");
		totalScore = in.getIntExtra("totalScore",0);
		totalTime = in.getLongExtra("totalTime",0);
		*/



		init();
		logic();
		newSubText(subTaskLayout.getChildCount());

	}



	/**
	 * Init function to assign all fields
	 */
	private void init()         //Assign all the Objects in one Place
	{


		todoNameTextField = findViewById(R.id.todoNameTextField);
		subTask1 = findViewById(R.id.subTask1);
		IsDone = findViewById(R.id.todoradio_isdone);
		subTaskLayout = findViewById(R.id.SubTaskLayout);
		todo_reminderText = findViewById(R.id.todo_reminderText);

		todoNameTextField.setText(usrName);

		//endGame.setOnClickListener(v -> endGame(usrName,scoreArray,score));


	}







	public void logic()
	{


		todoNameTextField.addTextChangedListener(new TextWatcher()
		{


			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				if (todoNameTextField.getText().length() != 0)
				{
					System.out.println(todoNameTextField.getText().toString());
				}else System.out.println("Empty TextField");

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});


		subTask1.addTextChangedListener(new TextWatcher()
		{


			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				System.out.println("After change");
				//newSubText(subTaskLayout.getChildCount());
			}
		});


		TimePickerDialog.OnTimeSetListener TimeSetListener = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				System.out.println("Time Set succesfull");
				todo_reminderText.setText(" "+hourOfDay+" "+minute);
				System.out.println(view.getParent());


			}


		};

		todo_reminderText.setOnClickListener(v ->
		{

			getTime();

			timePickerDialog = new TimePickerDialog(this,TimeSetListener,hour,minute,true);

			timePickerDialog.show();


		});







		//System.out.println(subTaskLayout.getChildCount());
		//NavHostFragment navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host);
		//navHostFragment.getChildFragmentManager().getFragments().get(0);

		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.setTimeZone(TimeZone.getDefault());


		//calendar.setTime(new SimpleDateFormat().parse("dd-MM-yyyy"));
		//calendar.setTime(new SimpleDateFormat( Locale.GERMAN);
		//Calendar.getInstance(Locale.GERMANY);
		//System.out.println();


		MaterialTimePicker.Builder MatTimePicker = new MaterialTimePicker.Builder();

		MatTimePicker.setTitleText("Hallo");
		MatTimePicker.setTimeFormat(TimeFormat.CLOCK_24H);
		MatTimePicker.setHour(Calendar.HOUR_OF_DAY);
		MatTimePicker.setMinute(Calendar.MINUTE);
		//MatTimePicker.build().show(manager,"MatTimePicker");







	}

	private void getTime()
	{


		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.setTimeZone(TimeZone.getDefault());
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		System.out.println("Stunde "+hour);
		System.out.println("Minute "+ minute);


	}

	private void newSubText(int childCount)         //TODO Fix Not appearing SubTask field
	{

		LinearLayout.LayoutParams LinParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

		LinearLayout subRow = new LinearLayout(this);
		subRow.setOrientation(LinearLayout.HORIZONTAL);
		subRow.setLayoutParams(LinParam);
		subRow.setId((childCount+1));

		ImageButton addIcon = new ImageButton(this);
		addIcon.setImageResource(R.drawable.ic_baseline_add_24);
		addIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));



		EditText newSubText = new EditText(this);
		//newSubText.setText(""+(childCount+1));
		newSubText.setHint("Subtask");
		newSubText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
		newSubText.setSingleLine(true);


		newSubText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{

				System.out.println("Editor Action");
				System.out.println(actionId);
				System.out.println(event);
				System.out.println(KeyEvent.keyCodeToString(actionId));
				if (actionId == KeyEvent.KEYCODE_ENDCALL)
				{
					System.out.println("Enter pressed");
					newSubText.clearFocus();
				}
				return false;
			}


		});



		newSubText.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if ((!hasFocus) & newSubText.getText().toString().isEmpty()) // code to execute when EditText loses focus and field is empty
				{

					System.out.println("LOSE FOCUS EMPTY");
					System.out.println(newSubText.getText().toString());
					System.out.println(newSubText.getText().length());
					subTaskLayout.removeViewAt(childCount);       //TODO Fix bug with childCound
				}

				if ((!hasFocus) & !newSubText.getText().toString().isEmpty())
				{
					System.out.println("LOSE FOCUS Full");
					System.out.println(newSubText.getText().toString());
					System.out.println(newSubText.getText().length());
					newSubText(subTaskLayout.getChildCount());

				}


			}
		});



		subRow.addView(addIcon);
		subRow.addView(newSubText);
		subTaskLayout.addView(subRow);






	}


}