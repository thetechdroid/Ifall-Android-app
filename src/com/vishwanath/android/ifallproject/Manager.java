package com.vishwanath.android.ifallproject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Manager extends Activity implements Runnable, OnClickListener {
	public ShakeListener mShaker;
	SharedPreferences sp;
	Database myDatabase;
	String message, pass;
	String threshold;
	int delay;
	TextView tv;
	public static ArrayList<String> number_list;
	String incoming_number, incoming_message, password;
	IntentFilter filter;
	Thread thread;
	boolean not_a_fall = true;
	AlertDialog dialog;
	String phone_number;
	public static TelephonyManager teleMgr;
	AudioManager audiomanager;
	LocationManager Lmanager;
	public static String latitude = "unknown", longitude = "unknown";
	boolean use_gps = false;
	LocationUpdate up;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		audiomanager = (AudioManager) Manager.this.getSystemService(Context.AUDIO_SERVICE);
		up = new LocationUpdate(this);

		final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		mShaker = new ShakeListener(this);
		mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
			public void onShake() {
				mShaker.pause();
				not_a_fall = false;
				vibe.vibrate(2000);
				ShowDialog();
				filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
				Manager.this.registerReceiver(mReceiver, filter);
				thread = new Thread(Manager.this);
				thread.start();

			}
		});
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String data = sp.getString("threshold", null);
		if (data == null) {
			Intent it = new Intent(this, Settings.class);
			startActivity(it);
		}

	}

	public void ShowDialog() {
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setMessage("Fall has been observed");
		build.setPositiveButton("Dismiss", this);
		// build.setNegativeButton("Call", this);
		dialog = build.create();
		dialog.show();
	}

	public void run() {
		String number;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (use_gps) {
			message = message + " location " + "Latitude:" + up.latitude + "Logitude:" + up.longitude;
		}

		if (!not_a_fall) {
			SmsManager smanager = SmsManager.getDefault();
			Iterator<String> itr = number_list.iterator();
			while (itr.hasNext()) {
				number = itr.next();
				smanager.sendTextMessage(number, null, message, null, null);
			}
		}

	}

	public void onStart() {
		super.onStart();

		setContentView(R.layout.main);
		tv = (TextView) findViewById(R.id.msg);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		threshold = sp.getString("threshold", "350");
		ShakeListener.FORCE_THRESHOLD = Integer.parseInt(threshold);
		message = sp.getString("message", "Hello World");
		pass = sp.getString("pass", "ifall");
		delay = Integer.parseInt(sp.getString("delay", "15000"));
		use_gps = sp.getBoolean("gps", false);
		message = message + " Please reply for this Message with the keyword " + pass;
		tv.setText("Ifall Message:\n\n" + message);
		number_list = new ArrayList<String>();
		Database dat = new Database(this);
		SQLiteDatabase dt = dat.getReadableDatabase();
		Cursor c = dt.query(Database.TABLE_NAME, new String[] { Database.COLUMN_NUMBER }, null, null, null, null, null);
		while (c.moveToNext()) {
			number_list.add(c.getString(0));
		}

	}

	public class MyThread extends Thread {
		public void run() {
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			audiomanager.setSpeakerphoneOn(true);

		}

	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent) {
			String num;
			String action = intent.getAction();
			Bundle bundle = intent.getExtras();
			android.telephony.gsm.SmsMessage[] msgs = null;
			String str = "";
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new android.telephony.gsm.SmsMessage[pdus.length];
				for (int i = 0; i < msgs.length; i++) {
					msgs[i] = android.telephony.gsm.SmsMessage.createFromPdu((byte[]) pdus[i]);
					incoming_number = msgs[i].getOriginatingAddress();
					incoming_message = msgs[i].getMessageBody().toString();
					Iterator<String> itr = number_list.iterator();
					while (itr.hasNext()) {
						num = itr.next();
						if (incoming_number.contains(num)) {
							if (incoming_message.contains(pass)) {
								new MyThread().start();
								Intent ic = new Intent(Intent.ACTION_CALL);
								ic.setData(Uri.parse("tel:" + incoming_number));
								startActivity(ic);
								break;
							}
						}
					}
				}

				// Toast.makeText(context,incoming_number,
				// Toast.LENGTH_SHORT).show();
			}

		}
	};

	public boolean onCreateOptionsMenu(Menu m) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, m);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem itm) {
		if (itm.getItemId() == R.id.isettings) {
			Intent i = new Intent(this, Settings.class);
			startActivity(i);

		} else if (itm.getItemId() == R.id.iopencontacts) {
			Intent i = new Intent(this, ShowContacts.class);
			startActivity(i);

		}

		return true;
	}

	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if (which == DialogInterface.BUTTON_POSITIVE) {
			not_a_fall = false;

		} else {
			not_a_fall = true;
		}

	}

	LocationListener loc = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

		}
	};

}
