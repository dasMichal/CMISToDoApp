package com.example.cmistodoapp.WorkManager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.cmistodoapp.R;

import org.jetbrains.annotations.NotNull;

public class NotificationWorker extends Worker
{

	public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params)
	{
		super(context, params);
	}


	@NotNull
	@Override
	public Result doWork() {

		final String CHANNEL_ID = "Seven" ;
		int hourOfDay = getInputData().getInt("hourOfDay",0);
		int minute = getInputData().getInt("minute",0);
		int year =  getInputData().getInt("year",0);
		int month = getInputData().getInt("month",0);
		int dayOfMonth = getInputData().getInt("dayOfMonth",0);

		String notificationText = getApplicationContext().getResources().getString(R.string.reminder_with_time, (int) hourOfDay, (int) minute, (int) dayOfMonth, (int) month);

		System.out.println(notificationText);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_baseline_notifications_24)
				.setContentTitle("Reminder")
				.setContentText(notificationText)
				.setChannelId(CHANNEL_ID)
				.setCategory(NotificationCompat.CATEGORY_MESSAGE)
				.setAllowSystemGeneratedContextualActions(true)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		NotificationManagerCompat.from(getApplicationContext()).notify(420,builder.build());


		// Indicate whether the work finished successfully with the Result
		return Result.success();
	}
}
