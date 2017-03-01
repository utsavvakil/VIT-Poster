package in.ac.vit.poster;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ChapViewEvents extends Activity implements OnItemClickListener{
	
	ListView llChapEvents;
	HttpClient client;
	JSONObject json;
	DatabaseHandlerCalender db;
	String [] event_name;
	String[] event_id;
	String[] chapter_name;
	String[] description;
	String[] date;
	String[] time;
	String[] venue;
	String[] cord_name;
	String[] cord_no;
	String[] fee;
	int i=0;
	String sharedPrefKey = "chapterdetails";
	SharedPreferences preferences;
	
	
	LinearLayout Events1,Events2,Events3;
	Typeface font;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.chape_events);
		preferences = getSharedPreferences(sharedPrefKey, 0);
		
		String chapter_id = preferences.getString("ChapterId", "");
		
		
		
		db=new DatabaseHandlerCalender(this);
		llChapEvents = (ListView)findViewById(R.id.llChapEvents);
		font = Typeface.createFromAsset(this.getAssets(), "BrandonText-Medium.otf");
		context = getApplicationContext();
		List<Events> event_1 =db.getAllChapterEvents(chapter_id);
		
			System.out.println("fetching data1");
			event_name = new String[event_1.size()];
			event_id = new String[event_1.size()];
			chapter_name = new String[event_1.size()];
			description = new String[event_1.size()];
			date = new String[event_1.size()];
			time = new String[event_1.size()];
			venue = new String[event_1.size()];
			cord_name = new String[event_1.size()];
			cord_no = new String[event_1.size()];
			fee = new String[event_1.size()];
			
			
			for (Events cn : event_1)
			{
				event_name[i]=""+cn.getEventName();
				event_id[i]=""+cn.getEventId();
				chapter_name[i]=""+cn.getChapterName();
				description[i]=""+cn.getDescription();
				date[i]=""+cn.getDate();
				time[i]=""+cn.getTime();
				venue[i]=""+cn.getVenue();
				cord_name[i]=""+cn.getCord_name();
				cord_no[i]=""+cn.getCord_no();
				fee[i]=""+cn.getFee();
				i++;
				System.out.println("fetching data2");
				
			}
			
		
		

		
		List<RowItemChap> rowItems;
		rowItems = new ArrayList<RowItemChap>();
		RowItemChap item;
		
		
		
		for(i=0;i<event_1.size();i++)
		{
			item = new RowItemChap(event_name[i],time[i],venue[i],R.drawable.event_1,date[i]);
			rowItems.add(item);
			System.out.println("fetching data3"+event_name[i]+event_id[i]);
			
		}
			
			
		
		
		
		CustomAdapterChapter adapter = new CustomAdapterChapter(context, rowItems,font);
		llChapEvents.setAdapter(adapter);
		
		llChapEvents.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		Intent i = new Intent(ChapViewEvents.this,EventPageChapterView.class);
		// TODO Auto-generated method stub
		i.putExtra("event_name", event_name[position]);
		i.putExtra("chapter_name", chapter_name[position]);
		System.out.println("lol "+chapter_name[position]);
		i.putExtra("event_id", event_id[position]);
		i.putExtra("description", description[position]);
		i.putExtra("date", date[position]);
		i.putExtra("time", time[position]);
		i.putExtra("venue", venue[position]);
		i.putExtra("cord_name", cord_name[position]);
		i.putExtra("cord_no", cord_no[position]);
		
		startActivity(i);
		
		
	}
	

}
