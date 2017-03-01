package in.ac.vit.poster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Fetch extends Activity {
	HttpClient client;
	DatabaseHandlerCalender dbEvents;
	DatabaseHandlerChapters dbGoing;
	JSONObject eventsDay1;
	String responseEvents;
	String responseChapters;
	Events[] events;
	JSONArray jsonArray;
	Intent myintent;
	 ProgressDialog ringProgressDialog;

	 private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	 public static final String EXTRA_MESSAGE = "message";
	 public static final String PROPERTY_REG_ID = "registration_id";
	 private static final String PROPERTY_APP_VERSION = "appVersion";
	 private static final String TAG = "GCMRelated";
	 GoogleCloudMessaging gcm;
	 AtomicInteger msgId = new AtomicInteger();
	 String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbEvents= new DatabaseHandlerCalender(this);
		dbGoing=new DatabaseHandlerChapters(this);
		dbGoing.createTable();
		 ringProgressDialog  = ProgressDialog.show(this, "Please wait ...",  "Fetching data..", true);
	        ringProgressDialog.setCancelable(true);
	        client = new DefaultHttpClient();
    		Toast.makeText(getApplication(), "Refreshing", Toast.LENGTH_SHORT).show();
    		new Read().execute("fetch data");
    		if (checkPlayServices()) {
    		      gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
    		            regid = getRegistrationId(getApplicationContext());
    		            if(!regid.isEmpty()){
    		            
    		            }else{
    		            
    		            }
    		  }
    		if (checkPlayServices()) {
    	        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
    	              regid = getRegistrationId(getApplicationContext());
    	              
    	              if (regid.isEmpty()) {
    	              
    	                  new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute();
    	              }else{
    	               Toast.makeText(getApplicationContext(), "Device already Registered", Toast.LENGTH_SHORT).show();
    	              }
    	       } else {
    	              Log.i(TAG, "No valid Google Play Services APK found.");
    	       }
    	   
    		myintent = new Intent(this,HomeActivity.class);
    		    		
    		dbEvents.createTable();
			
	}
	 private boolean checkPlayServices() {
	     int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	     if (resultCode != ConnectionResult.SUCCESS) {
	         if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	             GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                     PLAY_SERVICES_RESOLUTION_REQUEST).show();
	         } else {
	             Log.i(TAG, "This device is not supported.");
	             finish();
	         }
	         return false;
	     }
	     return true;
	 }
	 private String getRegistrationId(Context context) {
	     final SharedPreferences prefs = getGCMPreferences(context);
	     String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	     if (registrationId.isEmpty()) {
	         Log.i(TAG, "Registration not found.");
	         return "";
	     }
	     // Check if app was updated; if so, it must clear the registration ID
	     // since the existing regID is not guaranteed to work with the new
	     // app version.
	     int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	     int currentVersion = getAppVersion(getApplicationContext());
	     if (registeredVersion != currentVersion) {
	         Log.i(TAG, "App version changed.");
	         return "";
	     }
	     return registrationId;
	 }
	 private SharedPreferences getGCMPreferences(Context context) {
		  // This sample app persists the registration ID in shared preferences, but
		     // how you store the regID in your app is up to you.
		     return getSharedPreferences(Fetch.class.getSimpleName(),
		             Context.MODE_PRIVATE);
		 }
		 
		 /**
		  * @return Application's version code from the {@code PackageManager}.
		  */
		 private static int getAppVersion(Context context) {
		     try {
		         PackageInfo packageInfo = context.getPackageManager()
		                 .getPackageInfo(context.getPackageName(), 0);
		         return packageInfo.versionCode;
		     } catch (NameNotFoundException e) {
		         // should never happen
		         throw new RuntimeException("Could not get package name: " + e);
		     }
		 }
	public class Read extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			try 
			{	 getJSONArray();
				//json = new JSONObject();
			
				 jsonArray=new JSONArray(responseEvents);
				 System.out.println("jsonResponse"+responseEvents);
				 // eventsDay1	=jsonArray.getJSONArray("") ;
			
				
				 
				
			
				if(jsonArray!= null)
				{
					System.out.println("creating table");
					dbEvents.deleteTable();
					dbEvents.createTable();
				}
				
				
				//JSONArray array = json.getJSONArray("comments");

				events = new Events[jsonArray.length()];
				
			} 
			catch (JSONException e) 
			{

				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) 
		{

			//setContentView(R.layout.loading);
			if(jsonArray!= null)
			{
				
				//Toast.makeText(getApplication(), "Refreshed", Toast.LENGTH_SHORT).show();
				for(int i = 0 ; i < jsonArray.length() ; i++)
				{
					
					System.out.print("adding events");
					try {
						events[i] = new Events(i,jsonArray.getJSONObject(i));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
						dbEvents.addEvent(events[i]);
						System.out.println("date in adding"+events[i].date);
					
					

				}
				
				
			ringProgressDialog.dismiss();
			startActivity(myintent);

			
			}
			else
			{
				Toast.makeText(getApplication(), "Refreshing failed. Not connected to internet.", Toast.LENGTH_SHORT).show();
				ringProgressDialog.dismiss();
				//finish();
				//Intent intent = new Intent(Culturals.this,HomeActivity.class);
				//startActivity(intent);
				//fail= true;
			}
		}



	}
	String URL="http://ieeevit.com/poster/users/events";
	public JSONObject getJSONArray() throws ClientProtocolException, IOException, JSONException
	{
		StringBuilder url = new StringBuilder(URL);
		HttpGet get = new HttpGet(url.toString());
		System.out.println("Gonna execute");
		HttpResponse r = client.execute(get);
		
		System.out.println("Done execute");
		int status = r.getStatusLine().getStatusCode();
		if(status == 200)
		{
			HttpEntity e = r.getEntity();
			String JSONstring = EntityUtils.toString(e);

			System.out.println("Status execute 200");
			responseEvents=JSONstring;
			
			JSONObject recieved = null; //= new JSONObject(JSONstring);
			
		//	JSONArray eventsDay1 = recieved.getJSONArray("eventsday"+eventDay);

			return recieved;

		}
		else
		{
			Log.e("API Error", "API Cannot be reached");
			return null;
		}
	}
	
	
	
}
