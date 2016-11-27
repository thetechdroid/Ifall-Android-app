package com.vishwanath.android.ifallproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ifallcontacts.db";
	public static final String TABLE_NAME = "mysocialcontacts";
	public static final String COLUMN_NAME = "name";
	public static final int DATABASE_VERSION = 1;
	public static final String COLUMN_NUMBER = "number";
	public static final String TABLE_QUERY = "create table " + TABLE_NAME + "(" + COLUMN_NAME + " TEXT," + COLUMN_NUMBER
			+ " TEXT)";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_QUERY);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
