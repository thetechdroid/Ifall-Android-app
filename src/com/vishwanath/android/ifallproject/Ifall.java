package com.vishwanath.android.ifallproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Ifall extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.background);

		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Intent intent = new Intent("com.vishwanath.android.ifallproject.IfallListActivity");
				startActivity(intent);
			}
		};
		h.sendEmptyMessageDelayed(0, 3000);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//finish();
	}

}
