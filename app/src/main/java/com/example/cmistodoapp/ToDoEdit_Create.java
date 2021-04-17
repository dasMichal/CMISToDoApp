package com.example.cmistodoapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.time.Duration;
import java.time.ZonedDateTime;


public class ToDoEdit_Create extends AppCompatActivity
{

	private static final String CHANNEL_ID = "Seven" ;
	EditText todoNameTextField;
	TextView todo_reminderText;
	TextView todo_reminderDue;
	Toolbar toolbar;
	CheckBox IsDone;
	LinearLayout subTaskLayout;
	//MaterialTimePicker timePicker;
	TimePickerDialog timePickerDialog;
	DatePickerDialog datePickerDialog;
	String ToDoTitle;
	int toDoID;


	ZonedDateTime currentTime;

	AlarmManager alarmMgr;
	PendingIntent alarmIntent;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		Intent in = getIntent();

		ToDoTitle = in.getStringExtra("toDoTitle");
		toDoID = in.getIntExtra("toDoID",0);
		System.out.println(toDoID);

		init();
		logic();
		newSubText(subTaskLayout.getChildCount());

		/*
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_baseline_location_on_24)
				.setContentTitle("Send Help")
				.setContentText("How to Schedule a notification Josh/Marcus ? ")
				.setChannelId(CHANNEL_ID)
				.setCategory(NotificationCompat.CATEGORY_MESSAGE)
				.setAllowSystemGeneratedContextualActions(true)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		*/


		createNotificationChannel();

		//NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

		// notificationId is a unique int for each notification that you must define
		//notificationManager.notify(420, builder.build());



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

	}



	private void createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = getString(R.string.channel_name);
			String description = getString(R.string.channel_description);
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
			channel.setDescription(description);

			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}






	public void logic()
	{

		//Setting On Click Listener
		toolbar.setNavigationOnClickListener(v ->
		{


			Intent intent = new Intent(ToDoEdit_Create.this, ToDoList.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			//intent.putExtra("toDoName",todoNameTextField.getText().toString());
			//intent.putExtra("toDoID",toDoID);
			//startActivity(intent);
			finish();
			startActivityIfNeeded(intent, 0);

		});



		todo_reminderText.setOnClickListener(v ->
		{
			getTime();
			showTimePicker(v,0);        //Starts The Time and Date Picker

		});


		todo_reminderDue.setOnClickListener(v ->
		{
			getTime();
			showTimePicker(v,1); //Starts The Time and Date Picker
		});


		/*
		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.setTimeZone(getDefault());

		calendar.setTime(new SimpleDateFormat().parse("dd-MM-yyyy"));
		calendar.setTime(new SimpleDateFormat( Locale.GERMAN);
		Calendar.getInstance(Locale.GERMANY);
		System.out.println();
		 */

		/*
		MaterialTimePicker.Builder MatTimePicker = new MaterialTimePicker.Builder();

		MatTimePicker.setTitleText("Hallo");
		MatTimePicker.setTimeFormat(TimeFormat.CLOCK_24H);
		MatTimePicker.setHour(Calendar.HOUR_OF_DAY);
		MatTimePicker.setMinute(Calendar.MINUTE);
		//MatTimePicker.build().show(manager,"MatTimePicker");

		*/



	}

	/**
	 * Gets the Current Time
	 */
	private void getTime()
	{
		//Getting the Current Time using the Java 8 Date Time API
		currentTime = ZonedDateTime.now();

		System.out.println("Stunde "+currentTime.getHour());
		System.out.println("Minute "+ currentTime.getMinute());
		System.out.println("tag: "+ currentTime.getDayOfMonth());
		System.out.println("Monat: "+ currentTime.getMonth());
		System.out.println("Jahr: "+ currentTime.getYear());

	}




	private void showTimePicker(View textView, int whatTextView)
	{
		//Second
		TimePickerDialog.OnTimeSetListener TimeSetListener = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				Log.d("TimePicker","Time set successful");
				showDatePicker(textView,whatTextView,hourOfDay,minute); //Starts the DatePicker Dialog right after
			}
		};

		//First
		timePickerDialog = new TimePickerDialog(this,TimeSetListener,currentTime.getHour(), currentTime.getMinute(),true); //Creates the TimePicker Dialog
		timePickerDialog.show();        //Shows the TimePicker Dialog
	}



	private void showDatePicker(View textView, int whatTextView, int hourOfDay, int minute)
	{
		//Second
		DatePickerDialog.OnDateSetListener DataSetListener = new DatePickerDialog.OnDateSetListener()
		{

			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
			{


				Log.d("DatePicker","Data Set successful");

				TextView text = (TextView) textView;

				//Creates a Data Object to pass Data to the WorkManager
				Data datePackData = new Data.Builder()           //Very Smart name, I know.  Don't @ me
						.putInt("minute", minute)
						.putInt("hourOfDay", hourOfDay)
						.putInt("dayOfMonth", dayOfMonth)
						.putInt("month", month)
						.putInt("year", year)
						.build();




				/*
				Switch Case to change the respective TextView that was selected and called the  Time and DatePicker.
				Indicator is the whatTextView int
				*/
				switch(whatTextView)
				{
					case 0:
						text.setText(getResources().getString(R.string.reminder_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month));
						notificationPlaner(datePackData,year,  month,  dayOfMonth, hourOfDay,  minute);
						break;
					case 1:
						text.setText(getResources().getString(R.string.due_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month));
						notificationPlaner(datePackData,year,  month,  dayOfMonth, hourOfDay,  minute);
						break;
					default:
						System.out.println("Should not Happen");
						break;


				}



			}
		};


		//First
		datePickerDialog = new DatePickerDialog(this,DataSetListener, currentTime.getYear(), currentTime.getMonthValue(), currentTime.getDayOfMonth()); //Creates the DatePicker Dialog
		datePickerDialog.show(); //Shows the DatePicker Dialog
	}




	private void notificationPlaner( Data DatePackData,int year, int month, int dayOfMonth, int hourOfDay, int minute)
	{
		//saving the Time the USer has selected in a ZonedDateTime variable
		ZonedDateTime selectedTime = ZonedDateTime.of(year,month,dayOfMonth,hourOfDay,minute,0,0,currentTime.getZone());

		getTime(); //Grabbing the current Time
		//Calculate the Duration between the current Time and the User selected Time
		Duration delayToNotification = Duration.between(currentTime,selectedTime); //calculating the "Distance" between the two Dates

		System.out.println("Duration "+delayToNotification);
		Log.d("Time Duration", String.valueOf(delayToNotification));


		//Creating a WorkRequest
		WorkRequest notificationScheduleRequest =
				new OneTimeWorkRequest.Builder(NotificationWorker.class)
						.setInitialDelay(delayToNotification)  //Setting the delay to the Value that was calculated before
						.setInputData(DatePackData)             //Passing Data to the WorkManager
						.addTag("TestRequest")                  //Adding a tag to keep track of this WorkRequest
						.build();                               //Build the request


		//Submitting the WorkRequest to the system
		WorkManager
				.getInstance(ToDoEdit_Create.this)
				.enqueue(notificationScheduleRequest);


	}


	private void newSubText(int childCount)
	{

		//System.out.println("Child Count: "+childCount);
		LinearLayout.LayoutParams LinParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

		LinearLayout subRow = new LinearLayout(this);
		subRow.setOrientation(LinearLayout.HORIZONTAL);
		subRow.setLayoutParams(LinParam);
		subRow.setId((childCount+1));

		ImageButton addIcon = new ImageButton(this);
		addIcon.setImageResource(R.drawable.ic_baseline_add_24);
		addIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));

		CheckBox subTaskCheckbox = new CheckBox(this);
		subTaskCheckbox.setLayoutParams(new LinearLayout.LayoutParams((int) dptopx(33), LinearLayout.LayoutParams.MATCH_PARENT, 0));



		EditText newSubText = new EditText(this);
		//newSubText.setText(""+(childCount+1));
		newSubText.setHint("Subtask");
		newSubText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
		newSubText.setSingleLine(true);


		subTaskCheckbox.setOnClickListener(v ->
		{

			/*
			LinearLayout parent = (LinearLayout) v.getParent().getParent();
			int index = parent.indexOfChild((View) v.getParent());
			System.out.println("Index: "+index);
			LinearLayout subparent = (LinearLayout) v.getParent();
			 */


			if (subTaskCheckbox.isChecked())
			{

				System.out.println(subTaskCheckbox.isChecked());
				newSubText.setEnabled(false);
			} else
			{
				newSubText.setEnabled(true);
				System.out.println(subTaskCheckbox.isChecked());

			}

		});




		newSubText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{

				//System.out.println("Editor Action");
				//System.out.println(actionId);
				//System.out.println(event);
				//System.out.println(KeyEvent.keyCodeToString(actionId));
				if (actionId == KeyEvent.KEYCODE_ENDCALL)
				{
					//System.out.println("Enter pressed");
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



	public float dptopx(float dp)
	{
		return dp * getResources().getDisplayMetrics().density;
	}



}