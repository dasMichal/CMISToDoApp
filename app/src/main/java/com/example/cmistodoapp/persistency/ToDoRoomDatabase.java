package com.example.cmistodoapp.persistency;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ToDo_Entity.class, ToDoFolder_Entity.class}, version = 1, exportSchema = false)
public abstract class ToDoRoomDatabase extends RoomDatabase
{

	public abstract ToDo_DAO DAO();

	// marking the instance as volatile to ensure atomic access to the variable
	private static volatile ToDoRoomDatabase INSTANCE;
	private static final int NUMBER_OF_THREADS = 4;
	static final ExecutorService databaseWriteExecutor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	static ToDoRoomDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (ToDoRoomDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							ToDoRoomDatabase.class, "ToDo_db")
							.addCallback(sRoomDatabaseCallback)
							.fallbackToDestructiveMigration()
							.build();
				}
			}
		}
		return INSTANCE;
	}


	private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
		@Override
		public void onCreate(@NonNull SupportSQLiteDatabase db) {
			super.onCreate(db);

			databaseWriteExecutor.execute(() -> {
				// Populate the database in the background.
				// If you want to start with more words, just add them.
				ToDo_Entity toDoEntity;

				ToDo_DAO dao = INSTANCE.DAO();

				dao.nukeTable();


			});


		}
	};





}
