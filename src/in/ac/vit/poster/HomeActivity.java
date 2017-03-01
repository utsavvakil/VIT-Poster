package in.ac.vit.poster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

@SuppressLint("ValidFragment")
public class HomeActivity extends FragmentActivity 
{
	Context context;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	DatabaseHandlerCalender db;
	DatabaseHandlerChapters dbGoing;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home_activity);
		db=new DatabaseHandlerCalender(this);
		db.createTable();
		dbGoing= new DatabaseHandlerChapters(this);
		dbGoing.createTable();
		SharedPreferences sf = getSharedPreferences("tutorials_seen", 0);
		String seen = sf.getString("status", "error");
		if(seen.equals("error"))
		{
			SharedPreferences.Editor editor = sf.edit();
			editor.putString("status", "seen");
			editor.commit();
			Intent i = new Intent(HomeActivity.this,Tutorials.class);
			startActivity(i);
		}
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pagerHome);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(1);
		
		new SubmitToPHP().execute("userid");
	}


	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{

		public SectionsPagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		Bundle args;
		@Override
		public Fragment getItem(int position) 
		{
			switch (position)
			{

			case 0:
				Fragment fragment = new DummySectionFragment();
				args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;

			case 1:
				Fragment fragment2 = new DummySectionFragment2();
				args = new Bundle();
				args.putInt(DummySectionFragment2.ARG_SECTION_NUMBER, position + 2);
				fragment2.setArguments(args);
				return fragment2;
			case 2:
				Fragment fragment3 = new DummySectionFragment3();
				args = new Bundle();
				args.putInt(DummySectionFragment3.ARG_SECTION_NUMBER, position + 3);
				fragment3.setArguments(args);
				return fragment3;



			default:
				return null;
			}


		}


		@Override
		public int getCount() 
		{
			// Show 3 total pages.
			return 3;
		}
		
		

	}
	public class SubmitToPHP extends AsyncTask<String, Integer, String>
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
					JSONObject jsonObject=new JSONObject(response);
					 JSONArray json=jsonObject.getJSONArray("events");
					 for(int i=0;i<json.length();i++){
						 int j=json.getInt(i);
						 dbGoing.addEvent(j);
						 System.out.println("now i am adding"+j);
					 }
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
			try 
			{
				JSONObject obj = new JSONObject(response);
				/*JSONArray array = obj.getJSONArray("comments");
				items = new ForumItem[array.length()];
				for(int i = 0 ; i < array.length() ; i++)
				{
					items[i] = new ForumItem(0,array.getJSONObject(i),param);
				}*/
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	 String response;
	String param;

	public  void sendData(String forumName)
	{           
		HttpURLConnection connection;
		OutputStreamWriter request = null;


		URL url = null;   
		response = null;    
		SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", 0);
		 System.out.println("userID"+pref.getString("UserId", ""));
		 
		String parameters = "id="+pref.getString("UserId",""); 

		try
		{
			url = new URL("http://ieeevit.com/poster/users/goingEvents.json");
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
	public class DummySectionFragment2 extends Fragment implements OnItemClickListener, OnRefreshListener<ListView>
	{
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		PullToRefreshListView listView;
		
		View v;
		int i=0;
		public int[] images_going={R.drawable.event_1,R.drawable.event_4,R.drawable.event_6};
		DatabaseHandlerCalender db;
		DatabaseHandlerChapters dbGoing;
		ImageView settings,calendar;
		int [] going;
		public String[] event_id;
		public String[] event_location;
		public String[] time;
		public String[] description;
		public String[] event_name;
		public String[] cord1_name;
		public String[] cord1_contact;
		public String[] date;
		public String[] chapter_name;
		public String[] thumb;
		public String[] cover;
		HttpClient client;
		
		public DummySectionFragment2()
		{
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.dashboard_layout, container, false);
			v= (View)rootView.findViewById(R.id.viewBlue);
			listView=(PullToRefreshListView)rootView.findViewById(R.id.llTodayEvents);
			settings=(ImageView)rootView.findViewById(R.id.ivSettings);
			calendar=(ImageView)rootView.findViewById(R.id.ivCalendar);
			listView.setOnRefreshListener(this);
			client = new DefaultHttpClient();
			calendar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					System.out.println("executing the image view");
					mViewPager.setAdapter(mSectionsPagerAdapter);
					mViewPager.setCurrentItem(2);
					
					
				}
			});
			settings.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("executing the image view");
					mViewPager.setAdapter(mSectionsPagerAdapter);
					mViewPager.setCurrentItem(0);
					
					
				}
			});
			db=new DatabaseHandlerCalender(getActivity());
			dbGoing=new DatabaseHandlerChapters(getActivity());
			initListView();
			return rootView;	
		}
		
		public void initListView()
		{	
			
			Typeface font;
			RowItem item;
			List<RowItem> rowItems;
			font = Typeface.createFromAsset(getActivity().getAssets(), "BrandonText-Medium.otf");
			rowItems = new ArrayList<RowItem>();
			item = new RowItem ("z","sdf","sdf","sdf","#CCCCCC",3,2,"");
			rowItems.add(item);
			
			List<Chapters>chap=dbGoing.getAllEvents();
			System.out.println("size "+chap.size());
			if(chap.size()==0){
				item = new RowItem ("No events","sdf","sdf","sdf","#CCCCCC",1,2,"");
				rowItems.add(item);
				item = new RowItem ("null","sdf","sdf","sdf","#CCCCCC",1,2,"");
				rowItems.add(item);
				
			}
			else{
			int i=0;
			going=new int[chap.size()];
			System.out.println("hello");
			for(Chapters cn1: chap){
				going[i]=cn1.getEventId();
				
				i++;
				
			}
			System.out.println("value of i is"+i);
			if(i==1){
			
			for(int j=0;j<i;j++){
				List<Events> event_1=db.getAllGoingEvents(going[j]);
				System.out.println("event1"+event_1.size());
				
				event_id = new String[event_1.size()];
				event_location = new String[event_1.size()];
				time = new String[event_1.size()];
				description = new String[event_1.size()];
				cord1_name = new String[event_1.size()];
				cord1_contact = new String[event_1.size()];
				date = new String[event_1.size()];
				event_name = new String[event_1.size()];
				chapter_name=new String[event_1.size()];
				thumb=new String[event_1.size()];
				cover=new String[event_1.size()];
				int k=0;
				for (Events cn : event_1)
				{
					
					System.out.println("this is k loop"+k);
					event_id[k]=""+cn.getEventId();
					event_name[k]=""+cn.getEventName();
					
					time[k]=""+cn.getTime();
					event_location[k]=""+cn.getVenue();
					description[k]=""+cn.getDescription();
					cord1_name[k]=""+cn.getCord_name();
					cord1_contact[k]=""+cn.getCord_no();
					date[k] = ""+cn.getDate();
					chapter_name[k]=""+cn.getChapterName();
					thumb[k]=""+cn.getThumb();
					cover[k]=""+cn.getCover();
					k++;
					
					
					
				}
				for ( i = 0; i<event_1.size(); i++) {
					item = new RowItem( event_name[i],time[i],event_location[i],"",
							"#CCCCCC",1,R.drawable.event_1,thumb[i]);
					rowItems.add(item);
					
				}
			}
			
			item = new RowItem ("null","sdf","sdf","sdf",
					"#CCCCCC",1,2,"");
			rowItems.add(item);
			
			}
			
			else{
				event_id = new String[i];
				event_location = new String[i];
				time = new String[i];
				description = new String[i];
				cord1_name = new String[i];
				cord1_contact = new String[i];
				date = new String[i];
				event_name = new String[i];
				chapter_name=new String[i];
				thumb=new String[i];
				cover=new String[i];
				System.out.println("value of i is"+i);
				for(int j=0;j<i;j++){
					List<Events> event_1=db.getAllGoingEvents(going[j]);
					System.out.println("event1"+event_1.size());
					
					
					
					for (Events cn : event_1)
					{
						
						System.out.println("this is k loop"+j);
						event_id[j]=""+cn.getEventId();
						event_name[j]=""+cn.getEventName();
						
						time[j]=""+cn.getTime();
						event_location[j]=""+cn.getVenue();
						description[j]=""+cn.getDescription();
						cord1_name[j]=""+cn.getCord_name();
						cord1_contact[j]=""+cn.getCord_no();
						date[j] = ""+cn.getDate();
						chapter_name[j]=""+cn.getChapterName();
						thumb[j]=""+cn.getThumb();
						cover[j]=""+cn.getCover();
						item = new RowItem( event_name[j],time[j],
								event_location[j],"",
								"#CCCCCC",1,R.drawable.event_1,thumb[j]);
						
						rowItems.add(item);
						
						
						
					}
				}
				item = new RowItem ("null","sdf","sdf","sdf","#CCCCCC",1,2,"");
				rowItems.add(item);
			}
			}
			 item = new RowItem ("z","sdf","sdf","sdf","#CCCCCC",2,2,"");
			rowItems.add(item);
			CustomAdapterMulti adapter = new CustomAdapterMulti(getActivity(), rowItems,font);
			//LayoutInflater inflater = getLayoutInflater();
			listView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
			/*listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					
					
					// Do work to refresh the list here.
				//Paste code here
				}
			});*/
			
		}

		/**
		 * This method builds a simple list of cards
		 */
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			// TODO Auto-generated method stub
			position=position-2;
			Intent i= new Intent(getActivity(),EventPage.class);
			System.out.println("position"+position);
			i.putExtra("event_id", event_id[position]);		
			i.putExtra("event_name", event_name[position]);	
			System.out.println("name==" +event_name[position]);
			i.putExtra("chapter_name", chapter_name[position]);
			i.putExtra("desciption", description[position]);
			i.putExtra("date", date[position]);
			i.putExtra("time", time[position]);
			i.putExtra("venue", event_location[position]);
			i.putExtra("cord_name", cord1_name[position]);
			i.putExtra("cord_no", cord1_contact[position]);
			
			startActivity(i);
			
		}
		
		
		public class CustomAdapterMulti extends BaseAdapter implements OnClickListener, OnItemClickListener {


			public class ViewHolder {

				TextView txtTitle;
				TextView txtLoc;
				TextView txtTime;
				TextView txtCat;
				View	colourBar;
				TextView txtVenue;
				TextView txtCancel;
				Button btnMore;
				CustomAdapterGrid customGridAdapter;
				ExpandableHeightGridView mGridView;
				ArrayList<Item> gridArray = new ArrayList<Item>();
				LinearLayout llGrid;
				NetworkImageView ivIcon;
			}
			public static final int TYPE_ODD = 2;
			public static final int TYPE_EVEN = 1;
			public static final int TYPE_THREE = 3;
			public static final int TYPE_FOUR = 4;
			public static final int TYPE_FIVE = 5;
			Context context;
			ViewHolder holder = null;
			private List<RowItem> objects;
			View v;
			DatabaseHandlerCalender db;
			List<Events> event_1;

			public String[] titles;
			public String[] chapter_name;
			public String[] date;
			public String[] time;
			public String[] venue;
			public String[] fee;
			public String[] description;
			public String[] cord;
			public String[] cord_no;
			public int[] images={R.drawable.event_1,R.drawable.event_2,R.drawable.event_3,R.drawable.event_4,R.drawable.event_5,R.drawable.event_6};
			public int[] going;
			public int[] event_id;
			public String[] thumb;
			public String[] cover;
			@Override
			public int getViewTypeCount() {
				return objects.size();
			}

			@Override
			public int getItemViewType(int position) {
				RowItem object;
				object=objects.get(position);
				return object.getType();
			}

			public CustomAdapterMulti(Context context, List<RowItem> objects,Typeface font) {
				this.context=context;
				this.objects = objects;
				db= new DatabaseHandlerCalender(context);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {


				RowItem object;
				v=convertView;
				object=objects.get(position);
				int listViewItemType = object.getType();
				
				if (convertView == null) {
					if (listViewItemType == TYPE_EVEN) {
						if(object.getTitle()=="No events"){
							convertView = LayoutInflater.from(context).inflate(R.layout.no_events, null);
							convertView.setTag(holder);
						}else if(object.getTitle()=="null"){
							convertView = LayoutInflater.from(context).inflate(R.layout.blank, null);
							convertView.setTag(holder);
						}else{
							
						
						convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
						holder = new ViewHolder();

						holder.txtTitle = (TextView) convertView.findViewById(R.id.textViewtitle);
						holder.txtLoc = (TextView) convertView.findViewById(R.id.textViewLocation);
						holder.txtTime=(TextView) convertView.findViewById(R.id.textViewTime);
						holder.txtVenue = (TextView)convertView.findViewById(R.id.textViewViewVenue);
						holder.txtCancel = (TextView)convertView.findViewById(R.id.textViewCancel);
						holder.ivIcon=(NetworkImageView)convertView.findViewById(R.id.ivIcon);
						holder.txtTitle.setText(object.getTitle());
						holder.txtLoc.setText(object.getLocation());
						holder.txtTime.setText(object.getTime());
						//holder.ivIcon.setImageResource(object.getImage());
						
						RequestQueue mRequestQueue;
						ImageLoader mImageLoader;
						mRequestQueue = Volley.newRequestQueue(context);
						mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
						    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
						    public void putBitmap(String url, Bitmap bitmap) {
						        mCache.put(url, bitmap);
						    }
						    public Bitmap getBitmap(String url) {
						        return mCache.get(url);
						    }
						});
						
						holder.ivIcon.setImageUrl(object.getThumb()
								, mImageLoader);
						holder.txtVenue.setOnClickListener(this);
						holder.txtCancel.setOnClickListener(this);
						convertView.setTag(holder);
						}
					} else if (listViewItemType == TYPE_ODD) {

						convertView = LayoutInflater.from(context).inflate(R.layout.grid_list, null);

						holder = new ViewHolder();
						holder.mGridView = (ExpandableHeightGridView)convertView.findViewById(R.id.gvTodayEvents);
						holder.mGridView.setExpanded(true);
						holder.mGridView.setOnItemClickListener(this);
						int n=db.count();
						RowItemGrid item;
						List<RowItemGrid> rowItems;
						rowItems = new ArrayList<RowItemGrid>();
						db=new DatabaseHandlerCalender(context);
						event_1=db.getAllEvents();
						System.out.println("event_1"+event_1.size());
						titles = new String[event_1.size()];
						chapter_name = new String[event_1.size()];
						date = new String[event_1.size()];
						time = new String[event_1.size()];
						description = new String[event_1.size()];
						going = new int[event_1.size()];
						event_id = new int[event_1.size()];
						fee = new String[event_1.size()];
						venue = new String[event_1.size()];
						cord = new String[event_1.size()];
						cord_no = new String[event_1.size()];
						thumb = new String[event_1.size()];
						cover = new String[event_1.size()];
						System.out.println("sizeeee"+event_1.size());
						int i=0;
						for (Events cn : event_1)
						{	
							titles[i]=""+cn.getEventName();
							chapter_name[i]=""+cn.getChapterName();
							going[i]=cn.getGoing();
							event_id[i]=Integer.parseInt(cn.getEventId());
							date[i]=cn.getDate();
							time[i]=cn.getTime();
							description[i]=cn.getDescription();
							fee[i]=cn.getFee();
							venue[i]=cn.getVenue();
							cord[i]=cn.getVenue();
							cord_no[i]=cn.getCord_no();
							thumb[i]=cn.getThumb();
							cover[i]=cn.getCover();
							i++;
						}
						System.out.println("even1sizw"+event_1.size());
						for ( i = 0; i<event_1.size(); i++) {
							System.out.println("titles"+event_id[i]+"value of i"+i);

							item = new RowItemGrid(titles[i],chapter_name[i],R.drawable.event_1,going[i],event_id[i],thumb[i]);
							rowItems.add(item);
							
						}


						holder.customGridAdapter = new CustomAdapterGrid(this.context, rowItems);
						holder.mGridView.setAdapter(holder.customGridAdapter);

						convertView.setTag(holder);

					}else if (listViewItemType == TYPE_THREE) {
						convertView = LayoutInflater.from(context).inflate(R.layout.attending, null);
						holder = new ViewHolder();

						convertView.setTag(holder);
					} else if (listViewItemType == TYPE_FOUR) {
						convertView = LayoutInflater.from(context).inflate(R.layout.blank, null);
						holder = new ViewHolder();

						convertView.setTag(holder);
					} 
					else if (listViewItemType == TYPE_FIVE) {
						convertView = LayoutInflater.from(context).inflate(R.layout.no_events, null);
						holder = new ViewHolder();

						convertView.setTag(holder);
					} 

				}
				else{
					holder = (ViewHolder) convertView.getTag();
				}

				return convertView;
			}

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public int getCount() {
				return objects.size();
			}

			@Override
			public Object getItem(int position) {
				return objects.get(position);
			}

			@Override
			public long getItemId(int position) {
				return objects.indexOf(getItem(position));
			}

			

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Intent i= new Intent(context,EventPage.class);
				
				i.putExtra("event_id", ""+event_id[position]);
				i.putExtra("event_name", titles[position]);
				i.putExtra("chapter_name", chapter_name[position]);
				System.out.println("lol "+chapter_name[position]);
				i.putExtra("description", description[position]);
				i.putExtra("date", date[position]);
				i.putExtra("time", time[position]);
				i.putExtra("venue", venue[position]);
				i.putExtra("cord_name", cord[position]);
				i.putExtra("cord_no", cord_no[position]);
				i.putExtra("thumb", thumb[position]);
				i.putExtra("cover", cover[position]);
				context.startActivity(i);

			}

		}

		public class CustomAdapterGrid extends BaseAdapter{
			Context context;
			int layoutResourceId;
			int height;
			private List<RowItemGrid> objects;
			DatabaseHandlerCalender db;
			int check;
			DatabaseHandlerChapters dbGoing;
			int  Event_id,id;
			int i=0;
			 String sharedPrefKey = "userid";
			 SharedPreferences preferences;
			 Context send_ctx;
			public int[] images_going={R.drawable.event_1,R.drawable.event_4,R.drawable.event_6};
			int [] going;
			public String[] event_id;
			public String[] event_location;
			public String[] time;
			public String[] description;
			public String[] event_name;
			public String[] cord1_name;
			public String[] cord1_contact;
			public String[] date;
			public String[] chapter_name;
			public String[] thumb;
			public String[] cover;
			public CustomAdapterGrid(Context context, List<RowItemGrid> objects) {
				this.context=context;
				this.objects = objects;
				dbGoing= new DatabaseHandlerChapters(context);
				db=new DatabaseHandlerCalender(context);
				send_ctx=this.context;

			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View row = convertView;
				final RecordHolder holder ;

				if (row == null) {
					LayoutInflater inflater = ((Activity) context).getLayoutInflater();
					row = inflater.inflate(R.layout.grid_item, parent, false);

					holder = new RecordHolder();
					holder.txtTitle = (TextView) row.findViewById(R.id.tvEventName);
					holder.chapName = (TextView)row.findViewById(R.id.tvChapterName);
					holder.imageItem = (NetworkImageView) row.findViewById(R.id.ivAppIcon);
					holder.llGrid=(LinearLayout)row.findViewById(R.id.llGrid);
					holder.ivGoing=(ImageView)row.findViewById(R.id.ivGoing);
					row.setTag(holder);
				} else {
					holder = (RecordHolder) row.getTag();
				}
				//final RowItemGrid rowItem = objects.get(position);
				final RowItemGrid rowItem = (RowItemGrid) getItem(position);
				System.out.println("postio is "+position);

				holder.txtTitle.setText(rowItem.getTitle());
				holder.chapName.setText(rowItem.getLocation());
				//holder.imageItem.setImageResource(rowItem.getImage());
				RequestQueue mRequestQueue;
				ImageLoader mImageLoader;
				mRequestQueue = Volley.newRequestQueue(context);
				mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
				    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
				    public void putBitmap(String url, Bitmap bitmap) {
				        mCache.put(url, bitmap);
				    }
				    public Bitmap getBitmap(String url) {
				        return mCache.get(url);
				    }
				});
				holder.imageItem.setImageUrl(rowItem.getThumb(), mImageLoader);
				
				int id=rowItem.getEventId();
			 check=dbGoing.CheckGoingEvents(id);
				System.out.println("check value"+check);
				if(check==1){
					holder.ivGoing.setImageResource(R.drawable.tick_blue);

				}
				else{
					holder.ivGoing.setImageResource(R.drawable.add_mark);

				}




				holder.ivGoing.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						System.out.println("clicked on postiton"+position);
						Event_id=rowItem.getEventId();
						System.out.println("the value os going"+rowItem.getGoing());
						check=dbGoing.CheckGoingEvents(Event_id);
						
						if(check==0){
							
							holder.ivGoing.setImageResource(R.drawable.tick_blue);
							rowItem.setGoing(1);
							dbGoing.addEvent(rowItem.getEventId());
							check=1;
							System.out.println("going for the venet");
							//db.updateFavImp(rowItem.getEventId());
							new SubmitToPHPGoing().execute("going");
							change_adapter();
						}
						else if(check==1){
							holder.ivGoing.setImageResource(R.drawable.add_mark);
							rowItem.setGoing(0);
							//db.updateFavNotImp(rowItem.getEventId());
							System.out.println(" not going for the venet");
							check=0;
							dbGoing.removeEvent(rowItem.getEventId());
							new SubmitToPHPNotGoing().execute("notgoing");
							change_adapter();
						}
						else{
							
						}
					}
				});
				return row;

			}

			 class RecordHolder {
				TextView txtTitle;
				TextView chapName;
				NetworkImageView imageItem;
				ImageView ivGoing;
				LinearLayout llGrid;
			}
			@Override
			public int getCount() {
				return objects.size();
			}

			@Override
			public Object getItem(int position) {
				return objects.get(position);
			}

			@Override
			public long getItemId(int position) {
				return objects.indexOf(getItem(position));
			}
			public void change_adapter(){
				initListView();
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
					Toast.makeText(context, response, Toast.LENGTH_LONG).show();
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
			public void sendDataNotGoing(String forumName)
			{           
				HttpURLConnection connection;
				OutputStreamWriter request = null;


				URL url = null;   
				response = null;      

				SharedPreferences pref = send_ctx.getSharedPreferences("userid", 0);

				String parameters = "event_id="+Event_id+"&user_id="+pref.getString("UserId",""); 
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
					Toast.makeText(context, response, Toast.LENGTH_LONG).show();
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

			 String response;
			 String param;

			public  void sendData(String forumName)
			{           
				HttpURLConnection connection;
				OutputStreamWriter request = null;


				URL url = null;   
				response = null;         
				preferences = send_ctx.getSharedPreferences(sharedPrefKey, 0);

				String user_id = preferences.getString("UserId", "");

				String parameters = "event_id="+Event_id+"&user_id="+user_id; 
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
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			initListView();
		}

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			new Read().execute("refreshing");
		}
		String responseEvents;
		Events[] events;
		JSONArray jsonArray;
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
						db.deleteTable();
						db.createTable();
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
						
							db.addEvent(events[i]);
							System.out.println("date in adding"+events[i].date);
						
						

					}
					
					initListView();
					listView.onRefreshComplete();
					
				
				
				}
				else
				{
					Toast.makeText(getApplication(), "Refreshing failed. Not connected to internet.", Toast.LENGTH_SHORT).show();
					//finish();
					//Intent intent = new Intent(Culturals.this,HomeActivity.class);
					//startActivity(intent);
					//fail= true;
				}
			}



		}
		public void getJSONArray() throws ClientProtocolException, IOException, JSONException
		{
			String URL="http://ieeevit.com/poster/users/events";
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

				
			}
			else
			{
				Log.e("API Error", "API Cannot be reached");
				
			}
		}
	}
	
}
