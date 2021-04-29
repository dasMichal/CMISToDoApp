package com.example.cmistodoapp.WorkManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.LiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.cmistodoapp.R;
import com.example.cmistodoapp.ToDoEdit_Create;
import com.example.cmistodoapp.persistency.ToDoRoomDatabase;
import com.example.cmistodoapp.persistency.ToDo_DAO;
import com.example.cmistodoapp.persistency.ToDo_Entity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

		int toDoID = getInputData().getInt("toDoID",0);
		int folderID = getInputData().getInt("FolderID",0);
		String notificationText2 = getInputData().getString("NotificationText");
		String NotificationType = getInputData().getString("NotificationType");
		ToDo_Entity tempToDo = new ToDo_Entity();
		LiveData<ToDo_Entity> toDoByIDObject_Repo;
		//getInstance(getApplicationContext());

		Context context = getApplicationContext();
		ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(context);
		ToDo_DAO todoDAO = db.DAO();

		tempToDo.setToDoName(todoDAO.getPLAINToDoObject_byID(toDoID).getToDoName());


		// Create an Intent for the activity you want to start
		Intent resultIntent = new Intent(getApplicationContext(), ToDoEdit_Create.class);
		resultIntent.putExtra("toDoID",toDoID);

		// Create the TaskStackBuilder and add the intent, which inflates the back stack
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
		stackBuilder.addNextIntentWithParentStack(resultIntent);
		Objects.requireNonNull(stackBuilder.editIntentAt(1)).putExtra("folderID",folderID);
		// Get the PendingIntent containing the entire back stack
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);



		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_baseline_notifications_24)
				.setContentTitle(NotificationType)
				.setContentText(tempToDo.getToDoName())
				.setChannelId(CHANNEL_ID)
				.setCategory(NotificationCompat.CATEGORY_REMINDER)
				.setAllowSystemGeneratedContextualActions(true)
				.setContentIntent(resultPendingIntent)
				.setAutoCancel(true)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		NotificationManagerCompat.from(getApplicationContext()).notify(420,builder.build());


		// Indicate whether the work finished successfully with the Result
		return Result.success();
	}
}
