package com.example.cs178_act05_querubin_j;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cs178_act05_querubin_j.R;

public class MainActivity extends Activity implements LocationListener{

	private GoogleMap gmap;
	TextView textView;
	Button start, stop;
	String prov, locationPrint;
	
	LocationManager locationManager;
	Criteria criteria = new Criteria();
	Location location;
	
	private int count = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button) findViewById(R.id.btnStart);
		stop = (Button) findViewById(R.id.btnStop);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		prov = locationManager.getBestProvider(criteria, false);
		gmap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		View.OnClickListener listenbuttons = new View.OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(((Button)v).getId() == start.getId()){
					locationManager.requestLocationUpdates(prov, 10000, (float)10.0, MainActivity.this);					
				}
				else if(((Button)v).getId() == stop.getId()){
					locationManager.removeUpdates(MainActivity.this);
					location = locationManager.getLastKnownLocation(prov);
					Toast.makeText(MainActivity.this, "Last Location: " 
							+ location.getLatitude() + ", " + location.getLongitude() + ".",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		start.setOnClickListener(listenbuttons);
		stop.setOnClickListener(listenbuttons);
	}

	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void showUpdate(){
		LatLng tempGrid = new LatLng(location.getLatitude(), location.getLongitude());
		gmap.addMarker(new MarkerOptions().position(tempGrid).title("Location # " + (count+1)));
		count++;
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if(this.location == null || (location.getLatitude() != this.location.getLatitude() && 
				location.getLongitude() != this.location.getLongitude())) {
			this.location = location;
			showUpdate();
			Toast.makeText(this, "You are in: " + this.location.getLatitude() + ", " + 
					this.location.getLongitude(), Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(this, "You are still in the same location.", Toast.LENGTH_SHORT).show();
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