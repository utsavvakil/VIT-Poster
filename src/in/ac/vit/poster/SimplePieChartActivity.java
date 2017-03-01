/*
 * Copyright 2012 AndroidPlot.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class SimplePieChartActivity extends Activity
{

    
    private PieChart pie;

    private Segment s1;
    private Segment s2;
    Random r = new Random();
    int numb = r.nextInt(35)+36;
    int going=numb,total=100,notgoin=total-going;
    static EditText etMessage;
    Button btSend;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        setContentView(R.layout.chapter_events);

        // initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.mySimplePieChart);
        etMessage=(EditText)findViewById(R.id.editText1);
        btSend=(Button)findViewById(R.id.btSend);
        btSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new SubmitToPHP().execute("new request");
			}
		});
       

        s1 = new Segment("Pending", going);
        s2 = new Segment("Registered", notgoin);
        
        
        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
        sf1.getOuterEdgePaint().setColor(Color.parseColor("#03b5f5"));
        sf1.getInnerEdgePaint().setColor(Color.parseColor("#03b5f5"));
        sf1.getRadialEdgePaint().setColor(Color.parseColor("#03b5f5"));
        
       
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
        pie.getRenderer(PieRenderer.class).setDonutSize(75/100f,
                PieRenderer.DonutMode.PERCENT);
        pie.redraw();
        
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
		String parameters = "message="+etMessage.getText(); 

		try
		{
			url = new URL("http://vitmun2014.com/calender/gcm.php");
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
