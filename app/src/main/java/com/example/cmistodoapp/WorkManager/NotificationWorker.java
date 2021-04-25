package com.example.cmistodoapp.WorkManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.cmistodoapp.R;
import com.example.cmistodoapp.ToDoEdit_Create;

import org.jetbrains.annotations.NotNull;

public class NotificationWorker extends Worker
{

	public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params)
	{
		super(context, params);
	}


	@NotNull
	@Override
	public Result doWork()
	{

		final String CHANNEL_ID = "Seven" ;
		int hourOfDay = getInputData().getInt("hourOfDay",0);
		int minute = getInputData().getInt("minute",0);
		int year =  getInputData().getInt("year",0);
		int month = getInputData().getInt("month",0);
		int dayOfMonth = getInputData().getInt("dayOfMonth",0);
		int toDoID = getInputData().getInt("toDoID",0);
		String notificationText2 = getInputData().getString("notificationText");
		String notificationType = getInputData().getString("notificationType");






		// Create an Intent for the activity you want to start
		Intent resultIntent = new Intent(getApplicationContext(), ToDoEdit_Create.class);
		resultIntent.putExtra("toDoID",toDoID);


		// Create the TaskStackBuilder and add the intent, which inflates the back stack
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
		stackBuilder.addNextIntentWithParentStack(resultIntent);
		// Get the PendingIntent containing the entire back stack
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


		//String notificationText = getApplicationContext().getResources().getString(R.string.reminder_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month);
		//System.out.println(notificationText);



		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_baseline_notifications_24)
				.setContentTitle(notificationType)
				.setContentText(notificationText2)
				.setChannelId(CHANNEL_ID)
				.setCategory(NotificationCompat.CATEGORY_REMINDER)
				.setAllowSystemGeneratedContextualActions(true)
				.setContentIntent(resultPendingIntent)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		NotificationManagerCompat.from(getApplicationContext()).notify(420,builder.build());


		// Indicate whether the work finished successfully with the Result
		return Result.success();
	}
}
