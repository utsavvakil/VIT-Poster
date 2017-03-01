package in.ac.vit.poster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

public class EventPageChapterView extends ActionBarActivity 
{
	Typeface font_regular, font_bold;
	String name = "";

	private PieChart pie;

	private Segment s1;
	private Segment s2;
	Random r = new Random();
	int numb = r.nextInt(35) + 36;
	int going = numb, total = 100, notgoin = total - going;
	static EditText etMessage;
	Button b;
	static String event_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#03b5f5"));
		font_regular = Typeface.createFromAsset(getAssets(),
				"Roboto-Regular.ttf");
		font_bold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");

		FadingActionBarHelper helper = new FadingActionBarHelper()
				.actionBarBackground(colorDrawable)
				.headerLayout(R.layout.header)
				.contentLayout(R.layout.activity_scrollview_chapter_view);
		setContentView(helper.createView(this));
		helper.initActionBar(this);

		Intent i = getIntent();
		name = i.getStringExtra("event_name");
		setTitle(name);
		event_id=i.getStringExtra("event_id");
		etMessage = (EditText)findViewById(R.id.etGCM);
		TextView tv1 = (TextView)findViewById(R.id.tvEventName);
    	tv1.setText(i.getStringExtra("event_name"));
    	TextView tv2 = (TextView)findViewById(R.id.tvOrganizingChapter);
    	tv2.setText("By "+i.getStringExtra("chapter_name"));
    	TextView tv3 = (TextView)findViewById(R.id.tvEventDescription);
    	tv3.setText(i.getStringExtra("description"));
    	//desc=i.getStringExtra("description");
    	TextView tv4 = (TextView)findViewById(R.id.tvEventDate);
    	tv4.setText(i.getStringExtra("date"));
    	//date=i.getStringExtra("date");
    	TextView tv5 = (TextView)findViewById(R.id.tvEventTime);
    	tv5.setText(i.getStringExtra("time"));
    	//time=i.getStringExtra("time");
    	TextView tv6 = (TextView)findViewById(R.id.tvEventVenue);
    	tv6.setText(i.getStringExtra("venue"));
    	TextView tv7 = (TextView)findViewById(R.id.tvEventCoordinator);
    	tv7.setText(i.getStringExtra("cord_name")+" ");
    	TextView tv8 = (TextView)findViewById(R.id.tvEventCoordinatorNumber);
    	tv8.setText(i.getStringExtra("cord_no"));
    	TextView tv9 = (TextView)findViewById(R.id.tvEventFee);
    	tv9.setText(i.getStringExtra("fee"));
        
        b = (Button)findViewById(R.id.fabsendbutton);
        b.setOnClickListener(new OnClickListener()
        {
			
			@Override
			public void onClick(View arg0)
			{
				new SubmitToPHP().execute("");
			}
		});
		/*
		 * i.putExtra("event_id", event_id[position]); i.putExtra("event_name",
		 * event_name[position]); i.putExtra("chapter_name", chapter[position]);
		 * i.putExtra("desciption", description[position]); i.putExtra("date",
		 * date[position]); i.putExtra("time", time[position]);
		 * i.putExtra("venue", venue[position]); i.putExtra("cord_name",
		 * cord_name[position]); i.putExtra("cord_no", cord_no[position]);
		 * TextView tv1 = (TextView)findViewById(R.id.tvEventName);
		 * tv1.setText(i.getStringExtra("event_name")); TextView tv2 =
		 * (TextView)findViewById(R.id.tvOrganizingChapter);
		 * tv2.setText(i.getStringExtra(i.getStringExtra("chapter_name")));
		 * TextView tv3 = (TextView)findViewById(R.id.tvEventDescription);
		 * tv3.setText(i.getStringExtra("description")); TextView tv4 =
		 * (TextView)findViewById(R.id.tvEventDate);
		 * tv4.setText(i.getStringExtra("date")); TextView tv5 =
		 * (TextView)findViewById(R.id.tvEventTime);
		 * tv5.setText(i.getStringExtra("time")); TextView tv6 =
		 * (TextView)findViewById(R.id.tvEventVenue);
		 * tv6.setText(i.getStringExtra("venue")); TextView tv7 =
		 * (TextView)findViewById(R.id.tvEventCoordinator);
		 * tv7.setText(i.getStringExtra("cord_name")); TextView tv8 =
		 * (TextView)findViewById(R.id.tvEventCoordinatorNumber);
		 * tv8.setText(i.getStringExtra("cord_no")); TextView tv9 =
		 * (TextView)findViewById(R.id.tvAboutHeading);
		 * tv9.setTypeface(font_bold); TextView tv10 =
		 * (TextView)findViewById(R.id.tvDateHeading);
		 * tv10.setTypeface(font_bold); TextView tv11 =
		 * (TextView)findViewById(R.id.tvTimeHeading);
		 * tv11.setTypeface(font_bold); TextView tv12 =
		 * (TextView)findViewById(R.id.tvVenueHeading);
		 * tv12.setTypeface(font_bold); TextView tv13 =
		 * (TextView)findViewById(R.id.tvCoordinatorHeading);
		 * tv13.setTypeface(font_bold); TextView tv14 =
		 * (TextView)findViewById(R.id.tvEventFee);
		 * tv14.setTypeface(font_regular); TextView tv15 =
		 * (TextView)findViewById(R.id.tvFeeHeading);
		 * tv15.setTypeface(font_bold);
		 */
		

        pie = (PieChart) findViewById(R.id.mySimplePieChart);

		s1 = new Segment("", going);
		s2 = new Segment("", notgoin);

		SegmentFormatter sf1 = new SegmentFormatter();
		sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
		sf1.getOuterEdgePaint().setColor(Color.parseColor("#FF0000"));
		sf1.getInnerEdgePaint().setColor(Color.parseColor("#FF0000"));
		sf1.getRadialEdgePaint().setColor(Color.parseColor("#FF0000"));

		SegmentFormatter sf2 = new SegmentFormatter();
		sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);
		sf2.getOuterEdgePaint().setColor(Color.parseColor("#03b5f5"));
		sf2.getInnerEdgePaint().setColor(Color.parseColor("#03b5f5"));
		sf2.getRadialEdgePaint().setColor(Color.parseColor("#03b5f5"));

		pie.addSeries(s1, sf1);
		pie.addSeries(s2, sf2);
		pie.setBorderPaint(null);

		// pie.getBorderPaint().setColor(Color.RED);
		pie.getBackgroundPaint().setColor(Color.WHITE);
		pie.getRenderer(PieRenderer.class).setDonutSize(75 / 100f,
				PieRenderer.DonutMode.PERCENT);
		pie.redraw();

		changeTheFonts();

	}

	void changeTheFonts() {
		TextView tv1 = (TextView) findViewById(R.id.tvEventName);
		tv1.setTypeface(font_bold);
		tv1.setText(name + "                                     ");
		TextView tv2 = (TextView) findViewById(R.id.tvOrganizingChapter);
		tv2.setTypeface(font_regular);
		TextView tv3 = (TextView) findViewById(R.id.tvEventDescription);
		tv3.setTypeface(font_regular);
		TextView tv4 = (TextView) findViewById(R.id.tvEventDate);
		tv4.setTypeface(font_regular);
		TextView tv5 = (TextView) findViewById(R.id.tvEventTime);
		tv5.setTypeface(font_regular);
		TextView tv6 = (TextView) findViewById(R.id.tvEventVenue);
		tv6.setTypeface(font_regular);
		TextView tv7 = (TextView) findViewById(R.id.tvEventCoordinator);
		tv7.setTypeface(font_regular);
		TextView tv8 = (TextView) findViewById(R.id.tvEventCoordinatorNumber);
		tv8.setTypeface(font_regular);
		TextView tv9 = (TextView) findViewById(R.id.tvAboutHeading);
		tv9.setTypeface(font_bold);
		TextView tv10 = (TextView) findViewById(R.id.tvDateHeading);
		tv10.setTypeface(font_bold);
		TextView tv11 = (TextView) findViewById(R.id.tvTimeHeading);
		tv11.setTypeface(font_bold);
		TextView tv12 = (TextView) findViewById(R.id.tvVenueHeading);
		tv12.setTypeface(font_bold);
		TextView tv13 = (TextView) findViewById(R.id.tvCoordinatorHeading);
		tv13.setTypeface(font_bold);
		TextView tv14 = (TextView) findViewById(R.id.tvEventFee);
		tv14.setTypeface(font_regular);
		TextView tv15 = (TextView) findViewById(R.id.tvFeeHeading);
		tv15.setTypeface(font_bold);
		TextView tv16 = (TextView) findViewById(R.id.tvSend);
		tv16.setTypeface(font_bold);
		TextView tv17 = (TextView) findViewById(R.id.tvStats);
		tv17.setTypeface(font_bold);
		TextView tv18 = (TextView) findViewById(R.id.tvReg);
		tv18.setTypeface(font_regular);
		TextView tv19 = (TextView) findViewById(R.id.tvRegPending);
		tv19.setTypeface(font_regular);
		EditText et = (EditText) findViewById(R.id.etGCM);
		et.setTypeface(font_bold);
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
		
		String parameters = "message="+etMessage.getText()+"&id="+event_id; 
		System.out.println("para"+parameters);
		try
		{
			url = new URL("http://ieeevit.com/poster/users/sendGcm.json");
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