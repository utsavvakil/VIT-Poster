package in.ac.vit.poster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DatabaseHandlerChapters extends SQLiteOpenHelper 
{
	Context context;
	private static String makeTable = "CREATE TABLE IF NOT EXISTS chapter"+"" +
			" (id NUMBER, event_id NUMBER, going NUMBER )";
			
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "events";
	
	public DatabaseHandlerChapters(Context context) 
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
	
	public void addEvent(int j)
	{
		/*if(dayEvent.id == 79 || dayEvent.id == 90)
			return;*/
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values_main= new ContentValues();
		values_main.put("event_id", j);
		values_main.put("going", 1);
		db.insert("chapter", null, values_main);
	}
	
	
	public boolean checkForUpdate(String chapter_name)
	{	
		String query = "SELECT * FROM chapter where event_id="+"'"+chapter_name+"'";
		SQLiteDatabase db = this.getWritableDatabase();
		
		 Cursor cursor = db.rawQuery(query, null);
         if(cursor.getCount()<=0){
 return true;
        }
     return false;
	}
	public void updateFavImp(int chap_name) {
		String query = "UPDATE chapter set going=1 where event_id='"+chap_name+"'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public void updateFavNotImp(int chap_name) {
		String query = "UPDATE chapter set going=0 where event_id='"+chap_name+"'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public void removeEvent(int event_id) {
		String query = "DELETE from  chapter  where event_id='"+event_id+"'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public List<Chapters> getAllEvents()
	{
		
		String query = "SELECT distinct event_id FROM chapter "; 
		SQLiteDatabase db = this.getWritableDatabase();
		List<Chapters> data =new ArrayList<Chapters>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Chapters chapter = new Chapters();
				
				//Log.d("id",""+event.id);
				chapter.event_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("event_id")));
				
				data.add(chapter);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}public List<Chapters> getAllGoingEvents()
	{
		
		String query = "SELECT distinct event_id FROM chapter where going=1 "; 
		SQLiteDatabase db = this.getWritableDatabase();
		List<Chapters> data =new ArrayList<Chapters>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Chapters chapter = new Chapters();
				
				//Log.d("id",""+event.id);
				chapter.event_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("event_id")));
				
				data.add(chapter);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public int CheckGoingEvents(int event_id)
	{
		int i=0;
		String query = "SELECT  event_id FROM chapter where event_id='"+event_id+"'"; 
		SQLiteDatabase db = this.getWritableDatabase();
		List<Chapters> data =new ArrayList<Chapters>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Chapters chapter = new Chapters();
				
				//Log.d("id",""+event.id);
				chapter.event_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("event_id")));
				
				data.add(chapter);				
			}
			while(cursor.moveToNext());
			i++;
		}
		cursor.close();
		return i;
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
