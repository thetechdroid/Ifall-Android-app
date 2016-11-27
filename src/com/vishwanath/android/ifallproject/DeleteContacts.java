package com.vishwanath.android.ifallproject;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteContacts extends Activity implements OnClickListener {
	Button b1;
	ListView lstvw;
	CheckBox cbox;
	ArrayAdapter<String> cadp;
	ArrayList<String> arr;

	public void onCreate(Bundle ic) {
		super.onCreate(ic);
		arr = new ArrayList<String>();
		setContentView(R.layout.systemcontactlist);

		lstvw = (ListView) findViewById(R.id.lstview);
		lstvw.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		b1 = (Button) findViewById(R.id.btn1);
		b1.setOnClickListener(this);

		Database dat = new Database(getBaseContext());
		SQLiteDatabase db1 = dat.getReadableDatabase();
		Cursor c = db1.query(Database.TABLE_NAME, new String[] { Database.COLUMN_NAME, Database.COLUMN_NUMBER }, null,
				null, null, null, null);
		int i = 0;
		while (c.moveToNext()) {
			arr.add(i++, c.getString(0) + "\n" + c.getString(1));

		}

		cadp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arr);
		lstvw.setAdapter(cadp);

	}

	public void onClick(View v) {
		try {
			Database dat = new Database(getBaseContext());
			SQLiteDatabase db = dat.getWritableDatabase();
			int len = lstvw.getCount();
			SparseBooleanArray checked = lstvw.getCheckedItemPositions();
			for (int i = 0; i < len; i++) {
				if (checked.get(i)) {
					StringTokenizer st = new StringTokenizer(arr.get(i), "\n");
					String name = st.nextToken();
					String num = st.nextToken();
					db.delete(Database.TABLE_NAME, "name=? and number=?", new String[] { name, num });

				}
			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), 6000).show();
		}

		Toast.makeText(this, "Selected Contacts Deleted", 6000).show();
	}

}
