package com.vishwanath.android.ifallproject;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.StringTokenizer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AddContact extends Activity implements OnClickListener {
	Button b1;
	EditText txt1, txt2;

	public void onCreate(Bundle ic) {
		super.onCreate(ic);
		setContentView(R.layout.addcontacts);

		txt1 = (EditText) findViewById(R.id.txt_name);
		txt2 = (EditText) findViewById(R.id.txt_num);
		b1 = (Button) findViewById(R.id.btn_submit);
		b1.setOnClickListener(this);

	}

	public void onClick(View v) {

		if (!txt1.getText().toString().equals("") && !txt2.getText().toString().equals("")) {
			char[] c = txt2.getText().toString().toCharArray();
			if (c.length == 10) {
				Database dat = new Database(getBaseContext());
				SQLiteDatabase db = dat.getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put(Database.COLUMN_NAME, txt1.getText().toString());
				cv.put(Database.COLUMN_NUMBER, txt2.getText().toString());
				db.insert(Database.TABLE_NAME, null, cv);
				Toast.makeText(this, "Contacts Added Successfully", 6000).show();
			} else {
				Toast.makeText(this, "Number Must Be 10 Charaters", 6000).show();
			}
		} else {
			Toast.makeText(this, "Fields Are Empty", 6000).show();
		}

	}

}
