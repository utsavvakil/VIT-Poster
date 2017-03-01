package in.ac.vit.poster;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

public class EventPage extends ActionBarActivity implements OnClickListener 
{
	Typeface font_regular,font_bold;
	String name="";
	String time="",date="";
	String desc="";
	Button iv;
	Button ivGoing;
	DatabaseHandlerChapters db;
	static int event_id;
	int check;
	static SharedPreferences pref;
	static SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03b5f5"));
        font_regular = Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");
        font_bold = Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf");
         pref = getSharedPreferences("userid", 0);
         preferences = getSharedPreferences("userid", 0);

        FadingActionBarHelper helper = new FadingActionBarHelper()
        .actionBarBackground(colorDrawable)
        .headerLayout(R.layout.header)
        .contentLayout(R.layout.activity_scrollview);
    setContentView(helper.createView(this));
    helper.initActionBar(this);
    db= new DatabaseHandlerChapters(this);
	
    iv = (Button)findViewById(R.id.fabbutton);
    iv.setOnClickListener(this);

    Intent  i = getIntent();
    name=i.getStringExtra("event_name");
    System.out.println("hellow world "+name);
    setTitle(name);
        /*
		i.putExtra("event_id", event_id[position]);
		i.putExtra("event_name", event_name[position]);
		i.putExtra("chapter_name", chapter[position]);
		i.putExtra("desciption", description[position]);
		i.putExtra("date", date[position]);
		i.putExtra("time", time[position]);
		i.putExtra("venue", venue[position]);
		i.putExtra("cord_name", cord_name[position]);
		i.putExtra("cord_no", cord_no[position]);*/ 
        TextView tv1 = (TextView)findViewById(R.id.tvEventName);
    	tv1.setText(i.getStringExtra("event_name"));
    	TextView tv2 = (TextView)findViewById(R.id.tvOrganizingChapter);
    	tv2.setText("By "+i.getStringExtra("chapter_name"));
    	TextView tv3 = (TextView)findViewById(R.id.tvEventDescription);
    	tv3.setText(i.getStringExtra("description"));
    	desc=i.getStringExtra("description");
    	TextView tv4 = (TextView)findViewById(R.id.tvEventDate);
    	tv4.setText(i.getStringExtra("date"));
    	date=i.getStringExtra("date");
    	TextView tv5 = (TextView)findViewById(R.id.tvEventTime);
    	tv5.setText(i.getStringExtra("time"));
    	time=i.getStringExtra("time");
    	TextView tv6 = (TextView)findViewById(R.id.tvEventVenue);
    	tv6.setText(i.getStringExtra("venue"));
    	TextView tv7 = (TextView)findViewById(R.id.tvEventCoordinator);
    	tv7.setText(i.getStringExtra("cord_name")+" ");
    	TextView tv8 = (TextView)findViewById(R.id.tvEventCoordinatorNumber);
    	tv8.setText(i.getStringExtra("cord_no"));
    	TextView tv9 = (TextView)findViewById(R.id.tvEventFee);
    	tv9.setText(i.getStringExtra("fee"));
    	event_id=Integer.parseInt(i.getStringExtra("event_id"));
    	System.out.println("event_id "+event_id);
    	check=db.CheckGoingEvents(event_id);
		System.out.println("check value"+check);
		ivGoing = (Button)findViewById(R.id.fabbutton);
    	
		if(check==1){
			ivGoing.setBackgroundResource(R.drawable.blue_tick);

		}
		else{
			ivGoing.setBackgroundResource(R.drawable.blue_add);

		}
        
        changeTheFonts(); 
       
    }
    
    void changeTheFonts()
    {
    	TextView tv1 = (TextView)findViewById(R.id.tvEventName);
    	tv1.setTypeface(font_bold);
    	tv1.setText(name+"                                     ");
    	TextView tv2 = (TextView)findViewById(R.id.tvOrganizingChapter);
    	tv2.setTypeface(font_regular);
    	TextView tv3 = (TextView)findViewById(R.id.tvEventDescription);
    	tv3.setTypeface(font_regular);
    	TextView tv4 = (TextView)findViewById(R.id.tvEventDate);
    	tv4.setTypeface(font_regular);
    	TextView tv5 = (TextView)findViewById(R.id.tvEventTime);
    	tv5.setTypeface(font_regular);
    	TextView tv6 = (TextView)findViewById(R.id.tvEventVenue);
    	tv6.setTypeface(font_regular);
    	TextView tv7 = (TextView)findViewById(R.id.tvEventCoordinator);
    	tv7.setTypeface(font_regular);
    	TextView tv8 = (TextView)findViewById(R.id.tvEventCoordinatorNumber);
    	tv8.setTypeface(font_regular);
    	TextView tv9 = (TextView)findViewById(R.id.tvAboutHeading);
    	tv9.setTypeface(font_bold);
    	TextView tv10 = (TextView)findViewById(R.id.tvDateHeading);
    	tv10.setTypeface(font_bold);
    	TextView tv11 = (TextView)findViewById(R.id.tvTimeHeading);
    	tv11.setTypeface(font_bold);
    	TextView tv12 = (TextView)findViewById(R.id.tvVenueHeading);
    	tv12.setTypeface(font_bold);
    	TextView tv13 = (TextView)findViewById(R.id.tvCoordinatorHeading);
    	tv13.setTypeface(font_bold);
    	TextView tv14 = (TextView)findViewById(R.id.tvEventFee);
    	tv14.setTypeface(font_regular);
    	TextView tv15 = (TextView)findViewById(R.id.tvFeeHeading);
    	tv15.setTypeface(font_bold);
    	
    }
    

	@SuppressLint("NewApi")
	public void saveEvent()
	{
		/*
		Intent calIntent = new Intent(Intent.ACTION_INSERT); 
		calIntent.setType("vnd.android.cursor.item/event");  
		//add extra event ID
		calIntent.putExtra(Events.TITLE, "My House Party"); 
		calIntent.putExtra(Events.EVENT_LOCATION, "My Beach House"); 
		calIntent.putExtra(Events.DESCRIPTION, "A Pig Roast on the Beach"); 
		 
		GregorianCalendar calDate = new GregorianCalendar(2014, 9, 9);
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true); 
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 
		     calDate.getTimeInMillis()); 
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 
		     calDate.getTimeInMillis()); 
		 
		startActivity(calIntent); 
		
		*/
		
		// Projection array. Creating indices for this array instead of doing
		// dynamic lookups improves performance.
		final String[] EVENT_PROJECTION = new String[] {
		    Calendars._ID,                           // 0
		    Calendars.ACCOUNT_NAME,                  // 1
		    Calendars.CALENDAR_DISPLAY_NAME,         // 2
		    Calendars.OWNER_ACCOUNT                  // 3
		};
		  
		// The indices for the projection array above.
		final int PROJECTION_ID_INDEX = 0;
		final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
		final int PROJECTION_DISPLAY_NAME_INDEX = 2;
		final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
		
		Cursor cur = null;
		ContentResolver cr1 = getContentResolver();
		Uri uri1 = Calendars.CONTENT_URI;   
		String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" 
		                        + Calendars.ACCOUNT_TYPE + " = ?) AND ("
		                        + Calendars.OWNER_ACCOUNT + " = ?))";
		String[] selectionArgs = new String[] {"sampleuser@gmail.com", "com.google",
		        "sampleuser@gmail.com"}; 
		// Submit the query and get a Cursor object back. 
		cur = cr1.query(uri1, EVENT_PROJECTION, selection, selectionArgs, null);
		
		Toast.makeText(this, "Count: "+cur.getCount(), Toast.LENGTH_LONG).show();
		
		while (cur.moveToNext()) {
		    long calID = 0;
		    String displayName = null;
		    String accountName = null;
		    String ownerName = null;
		    
		    Toast.makeText(this, "Yo!", Toast.LENGTH_LONG).show();
		      
		    // Get the field values
		    calID = cur.getLong(PROJECTION_ID_INDEX);
		    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
		    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
		    ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
		    
		    Toast.makeText(this, "Yo!"+calID+" "+" "+displayName+
		    		" "+accountName+" "+ownerName, Toast.LENGTH_LONG).show();
		              
		    // Do something with the values...

		}
		
		
		String[] dateSplit = date.split("-");
		String[] timeSplit = time.split(":");
		long calID = 1;
		long startMillis = 0; 
		long endMillis = 0;     
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(Integer.parseInt(dateSplit[0]),
				Integer.parseInt(dateSplit[1])-1,
				Integer.parseInt(dateSplit[2]), 
				Integer.parseInt(timeSplit[0]),  
				Integer.parseInt(timeSplit[1]));
		startMillis = beginTime.getTimeInMillis();
		Calendar endTime = Calendar.getInstance();
		//endTime.set(2014, 8, 6, 22, 50);
		beginTime.set(Integer.parseInt(dateSplit[0]),
				Integer.parseInt(dateSplit[1])-1,
				Integer.parseInt(dateSplit[2]), 
				Integer.parseInt(timeSplit[0]+2),  
				Integer.parseInt(timeSplit[1]));
		endMillis = endTime.getTimeInMillis();
		
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Events.DTSTART, startMillis);
		values.put(Events.DTEND, endMillis);
		values.put(Events.TITLE, name);
		values.put(Events.DESCRIPTION, desc);
		values.put(Events.CALENDAR_ID, calID);
		TimeZone tz = TimeZone.getDefault();
		values.put(Events.EVENT_TIMEZONE, tz.getID());
		Uri uri = cr.insert(Events.CONTENT_URI, values);
		
		long eventID = Long.parseLong(uri.getLastPathSegment());
		
		Toast.makeText(getApplicationContext(), "Event ID: "+eventID, Toast.LENGTH_LONG).show();
		
		
		/*ContentResolver cr2 = getContentResolver();
		ContentValues values2 = new ContentValues();
		values2.put(Reminders.MINUTES, 15);
		values2.put(Reminders.EVENT_ID, eventID);
		values2.put(Reminders.METHOD, Reminders.METHOD_ALERT);
		Uri uri3 = cr2.insert(Reminders.CONTENT_URI, values2);*/
		
		Toast.makeText(this, "Reminder set", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onClick(View arg0) 
	{
		
		
		if(check==0){
			saveEvent();
			ivGoing.setBackgroundResource(R.drawable.blue_tick);
			check=1;
			db.addEvent(event_id);
			System.out.println("going for the venet");
			//db.updateFavImp(rowItem.getEventId());
			new SubmitToPHPGoing().execute("going");
		}
		else{
			ivGoing.setBackgroundResource(R.drawable.blue_add);
			check=0;
			//db.updateFavNotImp(rowItem.getEventId());
			System.out.println(" not going for the venet");
			db.removeEvent(event_id);
			new SubmitToPHPNotGoing().execute("notgoing");
		}
	}
	public class SubmitToPHPNotGoing extends AsyncTask<String, Integer, String>
	{


		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground(String... strings) {

			sendDataNotGoing(param);
			return null;
		}


		@Override
		protected void onProgressUpdate(Integer... values)
		{

		}

		@Override
		protected void onPostExecute(String s)
		{
			System.out.println("Here");
			if(response.equals("failure") )
			{
				System.out.println("Here in");
				response = "Invalid credentials, please try again";
			}
			else
			{
				System.out.println("Here abc "+response+" "+response.length());
				try 
				{

					JSONObject json = new JSONObject(response);
					//String username = json.getString("username");
					//String userType = json.getString("user_type");
					//String sharedPrefKey = "userdetails";
					//SharedPreferences preferences;
					//preferences = getSharedPreferences(sharedPrefKey, 0);
					//SharedPreferences.Editor editor = preferences.edit();
					//editor.putString("username", username);
					//editor.putString("user_type", userType);
					//Toast.makeText(getActi, response, Toast.LENGTH_LONG).show();
				} 
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
			/*try 
			{
				JSONObject obj = new JSONObject(response);
				JSONArray array = obj.getJSONArray("comments");
				items = new ForumItem[array.length()];
				for(int i = 0 ; i < array.length() ; i++)
				{
					items[i] = new ForumItem(0,array.getJSONObject(i),param);
				}
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}


	}
	public static void sendDataNotGoing(String forumName)
	{           
		HttpURLConnection connection;
		OutputStreamWriter request = null;


		URL url = null;   
		response = null;      

		

		String parameters = "event_id="+event_id+"&user_id="+pref.getString("UserId",""); 
		System.out.println("parameters"+parameters);
		try
		{
			url = new URL("http://ieeevit.com/poster/users/notgoing.json");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");    

			request = new OutputStreamWriter(connection.getOutputStream());
			request.write(parameters);
			request.flush();
			request.close();            
			String line = "";               
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			// Response from server after login process will be stored in response variable.                
			response = sb.toString();
			// You can perform UI operations here          
			isr.close();
			reader.close();

		}
		catch(IOException e)
		{
			// Error
		}
	}
	public class SubmitToPHPGoing extends AsyncTask<String, Integer, String>
	{


		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground(String... strings) {

			sendData(param);
			return null;
		}


		@Override
		protected void onProgressUpdate(Integer... values)
		{

		}

		@Override
		protected void onPostExecute(String s)
		{
			System.out.println("Here");
			if(response.equals("failure") )
			{
				System.out.println("Here in");
				response = "Invalid credentials, please try again";
			}
			else
			{
				System.out.println("Here abc "+response+" "+response.length());
				try 
				{

					JSONObject json = new JSONObject(response);
					//String username = json.getString("username");
					//String userType = json.getString("user_type");
					//String sharedPrefKey = "userdetails";
					//SharedPreferences preferences;
					//preferences = getSharedPreferences(sharedPrefKey, 0);
					//SharedPreferences.Editor editor = preferences.edit();
					//editor.putString("username", username);
					//editor.putString("user_type", userType);
					//Toast.makeText(getActi, response, Toast.LENGTH_LONG).show();
				} 
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
			/*try 
			{
				JSONObject obj = new JSONObject(response);
				JSONArray array = obj.getJSONArray("comments");
				items = new ForumItem[array.length()];
				for(int i = 0 ; i < array.length() ; i++)
				{
					items[i] = new ForumItem(0,array.getJSONObject(i),param);
				}
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}


	}

	static String response;
	static String param;
	
	public static void sendData(String forumName)
	{           
		HttpURLConnection connection;
		OutputStreamWriter request = null;


		URL url = null;   
		response = null;         
		
		String user_id = preferences.getString("UserId", "");

		String parameters = "event_id="+event_id+"&user_id="+user_id; 
		System.out.println("parameter for sending data"+parameters);
		try
		{
			url = new URL("http://ieeevit.com/poster/users/going.json");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");    

			request = new OutputStreamWriter(connection.getOutputStream());
			request.write(parameters);
			request.flush();
			request.close();            
			String line = "";               
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			// Response from server after login process will be stored in response variable.                
			response = sb.toString();
			// You can perform UI operations here          
			isr.close();
			reader.close();

		}
		catch(IOException e)
		{
			// Error
		}
	}


}