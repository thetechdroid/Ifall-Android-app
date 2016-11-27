package com.vishwanath.android.ifallproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IfallListActivity extends ListActivity {

	String Classes[] = { "AddContact", "DeleteContacts", "Manager", "Settings", "ShowContacts" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Ifall");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Classes));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		final String class1 = Classes[position];
		try {
			Class myclass = Class.forName("com.vishwanath.android.ifallproject." + class1);
			Intent intent = new Intent(IfallListActivity.this, myclass);
			startActivity(intent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
