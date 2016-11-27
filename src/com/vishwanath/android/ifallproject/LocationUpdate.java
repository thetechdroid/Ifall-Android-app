package com.vishwanath.android.ifallproject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUpdate implements LocationListener {
	Context cntxt;
	LocationManager Lmanager;
	String latitude = "unknown";
	String longitude = "unknown";

	public LocationUpdate(Context cntxt) {
		this.cntxt = cntxt;
		Lmanager = (LocationManager) cntxt.getSystemService(Context.LOCATION_SERVICE);
		Lmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = "" + location.getLatitude();
		longitude = "" + location.getLongitude();

	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
