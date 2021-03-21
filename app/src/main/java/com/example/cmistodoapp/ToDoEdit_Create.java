package com.example.cmistodoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Locale;

import static java.util.TimeZone.getDefault;


public class ToDoEdit_Create extends AppCompatActivity
{






	EditText todoNameTextField;
	TextView todo_reminderText;
	TextView todo_reminderDue;
	Toolbar toolbar;
	CheckBox IsDone;
	LinearLayout subTaskLayout;
	MaterialTimePicker timePicker;
	TimePickerDialog timePickerDialog;
	DatePickerDialog datePickerDialog;
	String ToDoTitle;

	int hour = 0;
	int minute = 0;
	int year;
	int month;
	int dayofmonth;





	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);



		Intent in = getIntent();

		ToDoTitle = in.getStringExtra("toDoTitle");


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
		//subTask1 = findViewById(R.id.subTask1);
		IsDone = findViewById(R.id.todocheck_isdone);
		subTaskLayout = findViewById(R.id.SubTaskLayout);
		todo_reminderText = findViewById(R.id.todo_reminderText);
		todo_reminderDue = findViewById(R.id.todo_reminderDue);
		toolbar = findViewById(R.id.toolbar2);
		toolbar.setTitle(ToDoTitle);
		todoNameTextField.setText(ToDoTitle);


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


		DatePickerDialog.OnDateSetListener DataSetListener = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
			{




			}
		};








		todo_reminderText.setOnClickListener(v ->
		{
			getTime();
			showTimePicker(v,0);

		});


		todo_reminderDue.setOnClickListener(v ->
		{
			getTime();
			showTimePicker(v,1);

		});







		//System.out.println(subTaskLayout.getChildCount());
		//NavHostFragment navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host);
		//navHostFragment.getChildFragmentManager().getFragments().get(0);

		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.setTimeZone(getDefault());


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


		//Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTimeZone(getDefault());
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);

		System.out.println("Stunde "+hour);
		System.out.println("Minute "+ minute);

		System.out.println("tag: "+dayofmonth);
		System.out.println("Monat: "+month);
		System.out.println("Jahr: "+year);


	}


	private void showTimePicker(View textView, int whatTextView)
	{

		TimePickerDialog.OnTimeSetListener TimeSetListener = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				System.out.println("Time Set succesfull");
				//todo_reminderText.setText(" "+hourOfDay+" "+minute);
				showDatePicker(textView,whatTextView,hourOfDay,minute);

			}


		};



		timePickerDialog = new TimePickerDialog(this,TimeSetListener,hour,minute,true);

		timePickerDialog.show();

	}

	private void showDatePicker(View textView, int whatTextView, int hourOfDay, int minute)
	{

		DatePickerDialog.OnDateSetListener DataSetListener = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
			{

				System.out.println("Data Set succesfull");
				//todo_reminderText.setText(" "+hourOfDay+" "+minute);



				Calendar myDate = Calendar.getInstance(Locale.getDefault());
				myDate.setTimeZone(getDefault());
				myDate.set(year,month,dayOfMonth,hourOfDay,minute);
				System.out.println(myDate.getTime());
				TextView text = (TextView) textView;



				switch(whatTextView)
				{
					case 0:
						text.setText(getResources().getString(R.string.reminder_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month));
						break;
					case 1:
						text.setText(getResources().getString(R.string.due_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month));
						break;
					default:
						System.out.println("Should not Happen");
						break;


				}



			}
		};


		datePickerDialog = new DatePickerDialog(this,DataSetListener,year,month,dayofmonth);

		datePickerDialog.show();

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

				if (hasFocus)
				{

					return;

				}

				LinearLayout parent = (LinearLayout) v.getParent().getParent();
				int index = parent.indexOfChild((View) v.getParent());
				System.out.println("Index: "+index);
				int getchildCount = subTaskLayout.getChildCount() ;
				System.out.println("ChildCount "+getchildCount);
				System.out.println(index < getchildCount);




				//if ((!hasFocus) & newSubText.getText().toString().isEmpty() & index != 0 & index+1 < getchildCount ) // code to execute when EditText loses focus and field is empty
				if ((!hasFocus) & newSubText.getText().toString().isEmpty() & index+1 < getchildCount ) // code to execute when EditText loses focus and field is empty
				{

					System.out.println("LOSE FOCUS EMPTY");
					System.out.println(newSubText.getText().toString());
					//System.out.println(newSubText.getText().length());
					System.out.println("Index Empty: "+index);
					subTaskLayout.removeViewAt(index);

				}else if ((!hasFocus) & !newSubText.getText().toString().isEmpty() & index+1 == getchildCount )
				{
					System.out.println("LOSE FOCUS Full");
					System.out.println(newSubText.getText().toString());
					//System.out.println(newSubText.getText().length());

					System.out.println("Index Full: "+index);

					newSubText(subTaskLayout.getChildCount());

				}


			}
		});





		//subRow.addView(addIcon);
		subRow.addView(subTaskCheckbox);
		subRow.addView(newSubText);
		subTaskLayout.addView(subRow);






	}


}