package com.example.cmistodoapp;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.cmistodoapp.WorkManager.NotificationWorker;
import com.example.cmistodoapp.databinding.ActivityMain2Binding;
import com.example.cmistodoapp.persistency.SubTask_Entity;
import com.example.cmistodoapp.persistency.ToDoViewModel;
import com.example.cmistodoapp.persistency.ToDo_Entity;

import java.time.Duration;
import java.time.ZonedDateTime;


public class ToDoEdit_Create extends AppCompatActivity implements LifecycleOwner
{

	private static final String CHANNEL_ID_Due = "Due" ;
	private static final String CHANNEL_ID_Reminder = "Reminder" ;

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
	ToDoViewModel viewModel;
	MutableLiveData<String> ToDoTitleLive;
	ZonedDateTime currentTime;
	ToDo_Entity newtoDoEntityObject;
	SubTask_Entity newSubTaskEntityObject;
	ImageButton deleteButton;
	boolean deleting = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		ActivityMain2Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main2);
		binding.setLifecycleOwner(this);
		viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);

		Intent in = getIntent();
		toDoID = in.getIntExtra("toDoID",0);


		init();


		viewModel.getToDoObject_byID(toDoID).observe(this, v ->
		{

			try
			{
				System.out.println(v.getToDoName());
				toolbar.setTitle(v.getToDoName());
				todoNameTextField.setText(v.getToDoName());
				IsDone.setChecked(v.isDone());

				newtoDoEntityObject.setToDoID(v.getToDoID());
				newtoDoEntityObject.setToDoName(v.getToDoName());
				newtoDoEntityObject.setDone(v.isDone());
				newtoDoEntityObject.setFKFolderID(v.getFKFolderID());


				if(v.getDueTime() != null)
				{
					newtoDoEntityObject.setDueTime(v.getDueTime());
					setText(1, newtoDoEntityObject);
				}
				if(v.getRemindeTime() != null)
				{
					newtoDoEntityObject.setRemindeTime(v.getRemindeTime());
					setText(0, newtoDoEntityObject);
				}





			}catch (NullPointerException e)
			{

				Log.e("Observable","No Object found ");
			}


		});


		/*

		viewModel.getSubtasksFromToDO(toDoID).observe(this, v ->
		{

			if(deleting)
			{
				return;
			}

			try
			{
				ArrayList<SubTask_Entity> scopeSubTasks = new ArrayList<>(v.get(0).SubTask_Entity);
				subTaskLayout.removeAllViews();

				for (int i = 0; i < scopeSubTasks.size(); i++)
				{
					SubTask_Entity subTask = scopeSubTasks.get(i);
					System.out.println(subTask.getSubTaskText());

					newSubTextDatabase(subTask.getSubTaskText(), subTask.isDone(), subTask.getSubTaskID(),i);
				}
				newSubText(subTaskLayout.getChildCount());


			}catch (NullPointerException e)
			{
				Log.e("Observable Scoped","No Object found ");

			}


		});



		 */



		viewModel.getSubtasksFromToDOTest(toDoID).observe(this, subtasks ->
		{


			if(deleting)
			{
				return;
			}

			subTaskLayout.removeAllViews();

			for (int i = 0; i < subtasks.size(); i++)
			{
				SubTask_Entity subTask = subtasks.get(i);
				System.out.println(subTask.getSubTaskText());

				newSubTextDatabase(subTask.getSubTaskText(), subTask.isDone(), subTask.getSubTaskID(),i);
			}
			newSubText(subTaskLayout.getChildCount());


		});


		viewModel.getToDoObject_byID(toDoID);


		logic();

		//newSubText(subTaskLayout.getChildCount());

		createNotificationChannel();

	}


	@Override
	protected void onPause()
	{
		super.onPause();
		newtoDoEntityObject.setToDoName(todoNameTextField.getText().toString());
		newtoDoEntityObject.setDone(IsDone.isChecked());
		viewModel.updateToDo(newtoDoEntityObject);

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		//newtoDoEntityObject.setToDoName(todoNameTextField.getText().toString());
		//newtoDoEntityObject.setDone(IsDone.isChecked());
		//viewModel.updateDataset(newtoDoEntityObject);
	}

	/**
	 * Init function to assign all fields
	 */
	private void init()         //Assign all the Objects in one Place
	{

		newtoDoEntityObject = new ToDo_Entity();

		newSubTaskEntityObject = new SubTask_Entity();
		todoNameTextField = findViewById(R.id.todoNameTextField);
		IsDone = findViewById(R.id.todocheck_isdone);
		subTaskLayout = findViewById(R.id.SubTaskLayout);
		todo_reminderText = findViewById(R.id.todo_reminderText);
		todo_reminderDue = findViewById(R.id.todo_reminderDue);
		deleteButton = findViewById(R.id.deleteButton);
		toolbar = findViewById(R.id.toolbar2);
		toolbar.setTitle(ToDoTitle);
		todoNameTextField.setText(ToDoTitle);
	}


	private void createNotificationChannel()
	{
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			CharSequence name = getString(R.string.notification_channel_name_Reminder);
			String description = getString(R.string.channel_description);
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channelReminder = new NotificationChannel(CHANNEL_ID_Reminder, name, importance);
			channelReminder.setDescription(description);


			 name = getString(R.string.notification_channel_name_Due);
			 description = getString(R.string.channel_description);
			 importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channelDue = new NotificationChannel(CHANNEL_ID_Due, name, importance);
			channelDue.setDescription(description);




			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channelReminder);
			notificationManager.createNotificationChannel(channelDue);
		}


	}



	public void logic()
	{

		todoNameTextField.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{

				if (actionId == KeyEvent.KEYCODE_CALL)
				{
					Log.d("KeyEvent","Enter Pressed");
					newtoDoEntityObject.setToDoName(todoNameTextField.getText().toString());
					viewModel.updateToDo(newtoDoEntityObject);
				}
				return false;
			}

		});





		deleteButton.setOnClickListener(v ->
		{

				new AlertDialog.Builder(this)
						.setTitle("Delete ToDo?")
						.setCancelable(true)
						.setPositiveButton(R.string.yes,(dialog, which) ->
						{
							{

								deleting = true;
								viewModel.getSubtasksFromToDO(newtoDoEntityObject.getToDoID()).removeObservers(this);
								viewModel.deleteToDo(newtoDoEntityObject);
								//Intent intent = new Intent(ToDoEdit_Create.this, ToDoList.class);
								finish();
								//startActivityIfNeeded(intent, 0);
							}
						})
						.setNegativeButton(R.string.no,(dialog, which) ->
						{
							{
								dialog.dismiss();
							}
						})

						.show();


		});



		//Setting On Click Listener
		/*
		toolbar.setNavigationOnClickListener(v ->
		{
			Intent intent = new Intent(ToDoEdit_Create.this, ToDoList.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			finish();
			startActivityIfNeeded(intent, 0);
		});

		 */

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


	private void setText(int whatTextView,ToDo_Entity toDo_entity)
	{

		switch(whatTextView)
		{
			case 0:

				todo_reminderText.setText(getResources().getString(R.string.reminder_with_time, toDo_entity.getRemindeTime().getHour(), toDo_entity.getRemindeTime().getMinute(), toDo_entity.getRemindeTime().getDayOfMonth(), toDo_entity.getRemindeTime().getMonthValue()));
				break;
			case 1:
				todo_reminderDue.setText(getResources().getString(R.string.due_with_time, toDo_entity.getDueTime().getHour(), toDo_entity.getDueTime().getMinute(), toDo_entity.getDueTime().getDayOfMonth(), toDo_entity.getDueTime().getMonthValue()));
				break;
			default:
				Log.wtf("Ballistic descent mode", "This Should definitely not happen (Error in whatTextView switch Case");
				break;
		}



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
				String NotificationType;

				/*
				Switch Case to change the respective TextView that was selected and called the  Time and DatePicker.
				Indicator is the whatTextView int
				*/
				switch(whatTextView)
				{
					case 0:
						text.setText(getResources().getString(R.string.reminder_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month));
						NotificationType = "Reminder";
						newtoDoEntityObject.setRemindeTime(ZonedDateTime.of(year,month,dayOfMonth,hourOfDay,minute,0,0,currentTime.getZone()) );

						break;
					case 1:
						text.setText(getResources().getString(R.string.due_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month));
						newtoDoEntityObject.setDueTime(ZonedDateTime.of(year,month,dayOfMonth,hourOfDay,minute,0,0,currentTime.getZone()) );
						NotificationType = "ToDo Due";

						break;
					default:
						Log.wtf("Ballistic descent mode", "This Should definitely not happen (Error in whatTextView switch Case");
						NotificationType = "Because Java insists on this being here";
						break;
				}


				//Creates a Data Object to pass Data to the WorkManager
				Data datePackData = new Data.Builder()           //Very Smart name, I know.  Don't @ me

						.putInt("toDoID",toDoID)
						.putInt("FolderID",newtoDoEntityObject.getFKFolderID())
						.putString("NotificationText",text.getText().toString())
						.putString("NotificationType",NotificationType)
						.putString("ToDoTitle",newtoDoEntityObject.getToDoName())
						.build();




				notificationPlaner(datePackData,year,  month,  dayOfMonth, hourOfDay,  minute);




			}
		};


		//First
		datePickerDialog = new DatePickerDialog(this,DataSetListener, currentTime.getYear(), currentTime.getMonthValue(), currentTime.getDayOfMonth()); //Creates the DatePicker Dialog
		datePickerDialog.show(); //Shows the DatePicker Dialog
	}


	private void notificationPlaner( Data DatePackData,int year, int month, int dayOfMonth, int hourOfDay, int minute)
	{


		//saving the Time the User has selected in a ZonedDateTime variable
		ZonedDateTime selectedTime = ZonedDateTime.of(year,month,dayOfMonth,hourOfDay,minute,0,0,currentTime.getZone());
		getTime();//Grabbing the current Time

		//Calculate the Duration between the current Time and the User selected Time
		Duration delayToNotification = Duration.between(currentTime,selectedTime); //calculating the "Distance" between the two Dates

		Log.d("Time Duration", String.valueOf(delayToNotification));


		//Creating a WorkRequest
		WorkRequest notificationScheduleRequest =
				new OneTimeWorkRequest.Builder(NotificationWorker.class)
						.setInitialDelay(delayToNotification)  //Setting the delay to the Value that was calculated before
						.setInputData(DatePackData)             //Passing Data to the WorkManager
						.addTag(String.valueOf(newtoDoEntityObject.getToDoID()))  //Adding a tag to keep track of this WorkRequest
						.build();                               //Build the request

		//Submitting the WorkRequest to the system
		WorkManager
				.getInstance(ToDoEdit_Create.this)
				.enqueue(notificationScheduleRequest);

		Log.i("WorkManager","Notification scheduled in "+delayToNotification );

	}


	/**
	 * Creates a new Sub-task TextField under the {@link EditText todoNameTextField}
	 * @param childCount The current amount of Sub-Task TextFields
	 */
	private void newSubText(int childCount)
	{


		LinearLayout.LayoutParams LinParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

		//Creating and Constructing a new Sub-Task Layout
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
		newSubText.setHint("SubTask");
		newSubText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
		newSubText.setSingleLine(true);


		subTaskCheckbox.setOnClickListener(v ->
		{
			//Checks if the Sub-Task Checkbox is toggled and disables/enables the corresponding EditText Fields
			newSubText.setEnabled(!subTaskCheckbox.isChecked());
			Log.d("subTaskCheckbox", String.valueOf(subTaskCheckbox.isChecked()));

		});



		//Listener which Triggers when the Enter Key is being pressed while in one of the newSubText  Fields
		newSubText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{

				if (actionId == KeyEvent.KEYCODE_ENDCALL)
				{
					Log.d("KeyEvent","Enter Pressed");
					newSubText.clearFocus();    //Removes the Focus of the current Sub-Task TextField
				}
				return false;
			}


		});


		//Listener which Triggers if one of the newSubText Fields changes focus
		newSubText.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{
					//If the EditText Field still has Focus the abort
					return;
				}

				//Reference the Parent LinearLayout to the index of the Full newSubText Fields
				LinearLayout parent = (LinearLayout) v.getParent().getParent();
				int index = parent.indexOfChild((View) v.getParent());
				Log.d("SubTask Index", String.valueOf(index));

				//Get the number of current newSubText EditText Fields
				int getchildCount = subTaskLayout.getChildCount();
				Log.d("SubTask ChildCount", String.valueOf(getchildCount));

				System.out.println(index < getchildCount);

				SubTask_Entity tmpSubTaskObject = new SubTask_Entity();


				//if ((!hasFocus) & newSubText.getText().toString().isEmpty() & index != 0 & index+1 < getchildCount ) // code to execute when EditText loses focus and field is empty

				if ((!hasFocus) & newSubText.getText().toString().isEmpty() & index+1 < getchildCount ) // code to execute when EditText loses focus and field is empty
				{
					//If newSubText Field is empty and not the only Field then delete itself
					Log.d("newSubText", "Focus Lost. TextField Empty");
					Log.d("newSubText","Index Empty: "+index);

					subTaskLayout.removeViewAt(index);

				}else if ((!hasFocus) & !newSubText.getText().toString().isEmpty() & index+1 == getchildCount )
				{
					//If newSubText Field is full and the only Field then create a new newSubText Field
					Log.d("newSubText", "Focus Lost. TextField Full");
					Log.d("newSubText","Index Full: "+index);


					tmpSubTaskObject.setSubTaskText(newSubText.getText().toString());
					tmpSubTaskObject.setDone(false);
					tmpSubTaskObject.setFKToDoID(toDoID);
					viewModel.insertSubTask(tmpSubTaskObject);

					//viewModel.returnScopedSubTasks(toDoID);

					//newSubText(subTaskLayout.getChildCount());

				}


			}
		});


		//subRow.addView(addIcon);
		subRow.addView(subTaskCheckbox);
		subRow.addView(newSubText);
		subTaskLayout.addView(subRow);

	}



	/**
	 * Creates a new Sub-task TextField under the {@link EditText todoNameTextField}
	 * @param childCount The current amount of Sub-Task TextFields
	 */
	private void newSubTextDatabase(String subTaskText, boolean done, int subTaskID,int count)
	{

		int childCount = count ;



		LinearLayout.LayoutParams LinParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		//Creating and Constructing a new Sub-Task Layout
		LinearLayout subRow = new LinearLayout(this);
		subRow.setOrientation(LinearLayout.HORIZONTAL);
		subRow.setLayoutParams(LinParam);
		subRow.setId(subTaskID);

		ImageButton addIcon = new ImageButton(this);
		addIcon.setImageResource(R.drawable.ic_baseline_add_24);
		addIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));

		CheckBox subTaskCheckbox = new CheckBox(this);
		subTaskCheckbox.setLayoutParams(new LinearLayout.LayoutParams((int) dptopx(33), LinearLayout.LayoutParams.MATCH_PARENT, 0));
		subTaskCheckbox.setChecked(done);

		EditText newSubText = new EditText(this);
		newSubText.setHint("SubTask");
		newSubText.setText(subTaskText);
		newSubText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
		newSubText.setSingleLine(true);
		newSubText.setId(subTaskID);


		subTaskCheckbox.setOnClickListener(v ->
		{
			//Checks if the Sub-Task Checkbox is toggled and disables/enables the corresponding EditText Fields
			newSubText.setEnabled(!subTaskCheckbox.isChecked());
			Log.d("subTaskCheckbox", String.valueOf(subTaskCheckbox.isChecked()));

		});



		//Listener which Triggers when the Enter Key is being pressed while in one of the newSubText  Fields
		newSubText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{

				if (actionId == KeyEvent.KEYCODE_ENDCALL)
				{
					Log.d("KeyEvent","Enter Pressed");
					newSubText.clearFocus();    //Removes the Focus of the current Sub-Task TextField
				}
				return false;
			}

		});


		//Listener which Triggers if one of the newSubText Fields changes focus
		newSubText.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{
					//If the EditText Field still has Focus the abort
					return;
				}

				//Reference the Parent LinearLayout to the index of the Full newSubText Fields
				LinearLayout parent = (LinearLayout) v.getParent().getParent();
				int index = parent.indexOfChild((View) v.getParent());
				Log.d("SubTask Index", String.valueOf(index));

				//Get the number of current newSubText EditText Fields
				int getchildCount = subTaskLayout.getChildCount();
				Log.d("SubTask ChildCount", String.valueOf(getchildCount));

				System.out.println(index < getchildCount);

				SubTask_Entity tmpSubTaskObject = new SubTask_Entity();


				//if ((!hasFocus) & newSubText.getText().toString().isEmpty() & index != 0 & index+1 < getchildCount ) // code to execute when EditText loses focus and field is empty

				if ((!hasFocus) & newSubText.getText().toString().isEmpty() & index+1 < getchildCount ) // code to execute when EditText loses focus and field is empty
				{
					//If newSubText Field is empty and not the only Field then delete itself
					Log.d("newSubText", "Focus Lost. TextField Empty");
					Log.d("newSubText","Index Empty: "+index);

					tmpSubTaskObject.setSubTaskText(subTaskText);
					tmpSubTaskObject.setSubTaskID(subTaskID);
					tmpSubTaskObject.setFKToDoID(toDoID);
					System.out.println(toDoID);
					viewModel.deleteSubTask(newSubTaskEntityObject);
					viewModel.getSubtasksFromToDO(toDoID);

					subTaskLayout.removeViewAt(index);

				}else if ((!hasFocus) & !newSubText.getText().toString().isEmpty() & index+1 == getchildCount )
				{
					//If newSubText Field is full and the only Field then create a new newSubText Field
					Log.d("newSubText", "Focus Lost. TextField Full");
					Log.d("newSubText","Index Full: "+index);


					tmpSubTaskObject.setSubTaskText(newSubText.getText().toString());
					tmpSubTaskObject.setDone(false);
					tmpSubTaskObject.setFKToDoID(toDoID);
					viewModel.insertSubTask(tmpSubTaskObject);

					//viewModel.returnScopedSubTasks(toDoID);

					//newSubText(subTaskLayout.getChildCount());

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