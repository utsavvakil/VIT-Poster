package in.ac.vit.poster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.internal.cu;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DatabaseHandlerCalender extends SQLiteOpenHelper 
{
	Context context;
	private static String makeTable = "CREATE TABLE IF NOT EXISTS main"+"" +
			" (id NUMBER, event_id NUMBER, event_name TEXT , chapter_name TEXT, description TEXT, date Date," +
			" time TEXT, venue TEXT, cord_no_1 TEXT, cord_no_2 TEXT,going NUMBER,fee TEXT,chapter_id TEXT," +
			"thumb TEXT, cover TEXT)";
			
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "events";
	
	public DatabaseHandlerCalender(Context context) 
	{

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	public void createTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("Query", makeTable);
		
		db.execSQL(makeTable);
		
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
				
	}
	
	public void addEvent(Events dayEvent)
	{
		/*if(dayEvent.id == 79 || dayEvent.id == 90)
			return;*/
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values_main= new ContentValues();
		Log.d("Here", dayEvent.id + dayEvent.event_name);
		values_main.put("id", dayEvent.id);
		values_main.put("event_id", dayEvent.event_id);
		values_main.put("event_name", dayEvent.event_name);
		values_main.put("chapter_name", dayEvent.chapter_name);
		values_main.put("description", dayEvent.description);
		
		values_main.put("date", dayEvent.date);
		
		
		values_main.put("time", dayEvent.time);
		values_main.put("venue", dayEvent.venue);
		values_main.put("cord_no_1", dayEvent.cord_name);
		values_main.put("cord_no_2", dayEvent.cord_no);
		values_main.put("going", 0);
		values_main.put("fee", dayEvent.fee);
		values_main.put("chapter_id", dayEvent.chap_id);
		values_main.put("thumb",dayEvent.thumb);
		values_main.put("cover",dayEvent.cover);
		System.out.println("chapter_id"+dayEvent.chap_id);
		db.insert("main", null, values_main);
	}
	
	public List<Events> getEvents(int day) throws SQLException
	{
		String query = "SELECT * FROM main WHERE day="+day;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
				
				
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public int count() {
		int i = 0;
		Calendar c = Calendar.getInstance(); 
		int day=c.get(Calendar.DATE);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);
		String query = "SELECT * FROM main "  ;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				//Log.d("id",""+event.id);
				event.event_id = cursor.getString(cursor.getColumnIndex("event_id"));
				event.event_name = cursor.getString(cursor.getColumnIndex("event_name"));
				Log.d("id",""+event.event_name);
				event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
				event.description = cursor.getString(cursor.getColumnIndex("description"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.venue = cursor.getString(cursor.getColumnIndex("venue"));
				event.cord_name=cursor.getString(cursor.getColumnIndex("cord_no_1"));
				event.cord_no=cursor.getString(cursor.getColumnIndex("cord_no_2"));
				event.fee=cursor.getString(cursor.getColumnIndex("fee"));
				event.chap_id=cursor.getString(cursor.getColumnIndex("chapter_id"));
				event.thumb=cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover=cursor.getString(cursor.getColumnIndex("cover"));
				data.add(event);
				i++;
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return i;
	}
	public boolean checkForUpdate(String event_name)
	{	
		String query = "SELECT * FROM main where event_name="+"'"+event_name+"'";
		SQLiteDatabase db = this.getWritableDatabase();
		
		 Cursor cursor = db.rawQuery(query, null);
         if(cursor.getCount()<=0){
 return true;
        }
     return false;
	}
	public boolean checkForGoing()
	{	
		String query = "SELECT * FROM main where going='1'";
		SQLiteDatabase db = this.getWritableDatabase();
		
		 Cursor cursor = db.rawQuery(query, null);
         if(cursor.getCount()<=0){
 return false;
        }
     return true;
	}
	public void updateGoing(int event_id) {
		String query = "UPDATE main set going='1' where event_id='"+event_id+"'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public void updateNotGoing(int event_id) {
		String query = "UPDATE main set going='0' where event_name='"+event_id+"'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	
	public List<Events> getAllDateEvents(String date)
	{
		Calendar c = Calendar.getInstance(); 
		int day=c.get(Calendar.DATE);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);
		String query = "SELECT * FROM main  where date='"+date+"'" ;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				
				//Log.d("id",""+event.id);
				event.event_id = cursor.getString(cursor.getColumnIndex("event_id"));
				event.event_name = cursor.getString(cursor.getColumnIndex("event_name"));
				Log.d("id",""+event.event_name);
				event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
				event.description = cursor.getString(cursor.getColumnIndex("description"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.venue = cursor.getString(cursor.getColumnIndex("venue"));
				event.cord_name=cursor.getString(cursor.getColumnIndex("cord_no_1"));
				event.cord_no=cursor.getString(cursor.getColumnIndex("cord_no_2"));
				event.fee=cursor.getString(cursor.getColumnIndex("fee"));
				event.chap_id=cursor.getString(cursor.getColumnIndex("chapter_id"));
				event.thumb=cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover=cursor.getString(cursor.getColumnIndex("cover"));
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Events> getAllGoingEvents(int chapter_name)
	{
		Calendar c = Calendar.getInstance(); 
		int day=c.get(Calendar.DATE);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);
		String query = "SELECT * FROM main  where event_id='"+chapter_name+"'" ;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				
				//Log.d("id",""+event.id);
				event.event_id = cursor.getString(cursor.getColumnIndex("event_id"));
				event.event_name = cursor.getString(cursor.getColumnIndex("event_name"));
				Log.d("id",""+event.event_name);
				event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
				event.description = cursor.getString(cursor.getColumnIndex("description"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.venue = cursor.getString(cursor.getColumnIndex("venue"));
				event.cord_name=cursor.getString(cursor.getColumnIndex("cord_no_1"));
				event.cord_no=cursor.getString(cursor.getColumnIndex("cord_no_2"));
				event.fee=cursor.getString(cursor.getColumnIndex("fee"));
				event.chap_id=cursor.getString(cursor.getColumnIndex("chapter_id"));
				event.thumb=cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover=cursor.getString(cursor.getColumnIndex("cover"));
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Events> getAllChapterEvents(String chapter_id)
	{
		Calendar c = Calendar.getInstance(); 
		int day=c.get(Calendar.DATE);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);
		String query = "SELECT * FROM main  where chapter_id='"+chapter_id+"'" ;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				
				//Log.d("id",""+event.id);
				event.event_id = cursor.getString(cursor.getColumnIndex("event_id"));
				event.event_name = cursor.getString(cursor.getColumnIndex("event_name"));
				Log.d("id",""+event.event_name);
				event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
				event.description = cursor.getString(cursor.getColumnIndex("description"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.venue = cursor.getString(cursor.getColumnIndex("venue"));
				event.cord_name=cursor.getString(cursor.getColumnIndex("cord_no_1"));
				event.cord_no=cursor.getString(cursor.getColumnIndex("cord_no_2"));
				event.fee=cursor.getString(cursor.getColumnIndex("fee"));
				event.chap_id=cursor.getString(cursor.getColumnIndex("chapter_id"));
				event.thumb=cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover=cursor.getString(cursor.getColumnIndex("cover"));
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Events> getAllEventsGoing()
	{
		Calendar c = Calendar.getInstance(); 
		int day=c.get(Calendar.DATE);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);
		String query = "SELECT * FROM main where going='1' and date >='"+year+"-0"+month+"-0"+day+"'"+" ORDER BY date"  ;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				//Log.d("id",""+event.id);
				event.event_id = cursor.getString(cursor.getColumnIndex("event_id"));
				event.event_name = cursor.getString(cursor.getColumnIndex("event_name"));
				Log.d("id",""+event.event_name);
				event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
				event.description = cursor.getString(cursor.getColumnIndex("description"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.venue = cursor.getString(cursor.getColumnIndex("venue"));
				event.cord_name=cursor.getString(cursor.getColumnIndex("cord_no_1"));
				event.cord_no=cursor.getString(cursor.getColumnIndex("cord_no_2"));
				event.going = Integer.parseInt(cursor.getString(cursor.getColumnIndex("going")));

				event.thumb=cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover=cursor.getString(cursor.getColumnIndex("cover"));
				
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Events> getAllEvents()
	{
		Calendar c = Calendar.getInstance(); 
		int day=c.get(Calendar.DATE);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);
		String query = "SELECT * FROM main "  ;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				//Log.d("id",""+event.id);
				event.event_id = cursor.getString(cursor.getColumnIndex("event_id"));
				event.event_name = cursor.getString(cursor.getColumnIndex("event_name"));
				Log.d("id",""+event.event_name);
				event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
				event.description = cursor.getString(cursor.getColumnIndex("description"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.venue = cursor.getString(cursor.getColumnIndex("venue"));
				event.cord_name=cursor.getString(cursor.getColumnIndex("cord_no_1"));
				event.cord_no=cursor.getString(cursor.getColumnIndex("cord_no_2"));
				event.fee=cursor.getString(cursor.getColumnIndex("fee"));
				event.chap_id=cursor.getString(cursor.getColumnIndex("chapter_id"));

				event.thumb=cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover=cursor.getString(cursor.getColumnIndex("cover"));
				
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	@SuppressLint("DefaultLocale")
	public List<Events> getAllEventsFavChap()
	{
		Calendar c = Calendar.getInstance(); 
		int day=c.get(Calendar.DATE);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);
		String query2="Select * from chapter where chapter_fav=1";
		//String query = "SELECT * FROM main where date >='"+year+"-0"+month+"-0"+day+"'"+" ORDER BY date"  ;
		SQLiteDatabase db = this.getWritableDatabase();
		String[] chapter_name=new String[100];
		int i=0;
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query2, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				
				event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
				
				chapter_name[i]=cursor.getString(cursor.getColumnIndex("chapter_name"));
				
				System.out.println("chapter_name"+chapter_name[i]);
				i++;
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
				 db = this.getWritableDatabase();
				String query;
				List<Events> dataFav =new ArrayList<Events>();
				for(i=0;i<2;i++){
					 query = "SELECT * FROM main where chapter_name='"+chapter_name[i].toUpperCase()+"' and date >='"+year+"-0"+month+"-0"+day+"'"+" ORDER BY date"  ;
					
				 cursor = db.rawQuery(query, null);
				if(cursor.moveToFirst())
				{
					do
					{
						Events event = new Events();
						
						event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
						//Log.d("id",""+event.id);
						event.event_id = cursor.getString(cursor.getColumnIndex("event_id"));
						event.event_name = cursor.getString(cursor.getColumnIndex("event_name"));
						Log.d("id",""+event.event_name);
						event.chapter_name = cursor.getString(cursor.getColumnIndex("chapter_name"));
						event.description = cursor.getString(cursor.getColumnIndex("description"));
						event.date = cursor.getString(cursor.getColumnIndex("date"));
						event.time = cursor.getString(cursor.getColumnIndex("time"));
						event.venue = cursor.getString(cursor.getColumnIndex("venue"));
						event.cord_name=cursor.getString(cursor.getColumnIndex("cord_no_1"));
						event.cord_no=cursor.getString(cursor.getColumnIndex("cord_no_2"));

						event.thumb=cursor.getString(cursor.getColumnIndex("thumb"));
						event.cover=cursor.getString(cursor.getColumnIndex("cover"));
						dataFav.add(event);				
					}
					while(cursor.moveToNext());
				}
				}
				cursor.close();
				
		return dataFav;
	}
	public List<Events> getEventsCate(int cid)
	{
		String query = "SELECT * FROM main WHERE cid="+cid;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	
	public List<Events> getEvent(int day, int id)
	{
		String query = "SELECT * FROM main WHERE day="+day+" AND id="+id;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
				
				
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;

	}
	
	public List<Events> getEvent(int id)
	{
		String query = "SELECT * FROM main WHERE id="+id;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
				
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;

	}
	
	public Cursor listTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor table = db.rawQuery("SELECT name _id FROM sqlite_master WHERE type = 'table' ", null);
		if(table.moveToFirst())
		{
			Log.d("Table List", "Not Null " + table.getCount());
			do
			{
				Log.d("Table Name", table.getString(0));
			}
			while(table.moveToNext());
			
			return table;

			
		}
		table.close();
		return null;
	}
	public boolean exists(String table) {
	    try {
	    	SQLiteDatabase db = this.getWritableDatabase();
	         db.execSQL("SELECT * FROM main");
	         System.out.println("TRUE");
	         return true;
	    } catch (SQLException e) {
	    	System.out.println("FALSE");
	    	return false;
	    }
	}
	
	public void deleteTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String query1 = "DROP TABLE IF EXISTS main";
		
		db.execSQL(query1);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS  main");
		// Create tables again
		onCreate(db);
	}
	
	


	
}
