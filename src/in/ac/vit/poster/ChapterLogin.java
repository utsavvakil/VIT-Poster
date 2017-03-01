package in.ac.vit.poster;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChapterLogin extends ActionBarActivity implements OnClickListener{
	
	EditText etUser,etPass;
	
	Button btnLogin;
	String response = "";
	String user = "";
	String pass = "";
	ProgressDialog ringProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.chap_login);
		
		etUser = (EditText)findViewById(R.id.etUser);
		etPass = (EditText)findViewById(R.id.etPass);
		btnLogin = (Button)findViewById(R.id.btnSubmit);
		
		btnLogin.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	
		
		try
		{
			user = etUser.getText().toString();
			if(user.length()==0)
			{
				user="";
			}
			pass = etPass.getText().toString();
			if(pass.length()==0)
			{
				pass="";
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		 ringProgressDialog  = ProgressDialog.show(this, "Please wait ...",  "Fetching data..", true);
	        ringProgressDialog.setCancelable(true);
	    
		String blah = "xyz";
		new SubmitToPHP().execute(blah);
		
	
		
		
	}
	protected void tryLogin(String mUsername, String mPassword)
	{           
		HttpURLConnection connection;
		OutputStreamWriter request = null;

		URL url = null;   
		response = null;         
		String parameters = "name="+user+"&password="+pass;   

		try
		{
			url = new URL("http://ieeevit.com/poster/users/chapterLogin.json");
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

	
	public class SubmitToPHP extends AsyncTask<String, Integer, String>
	{


		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground(String... strings) {

			System.out.print("I'm here");
			if(user.length() != 0 && pass.length() != 0)
			{
				tryLogin(user, pass);
			}
			else
			{
				response = "Please ensure all fields are entered";
			}
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
					String chapterid = json.getString("message");
					
					String sharedPrefKey = "chapterdetails";
					SharedPreferences preferences;
					preferences = getSharedPreferences(sharedPrefKey, 0);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("ChapterId", chapterid);
					
					editor.commit();
					
					Intent intent = new Intent(ChapterLogin.this,ChapViewEvents.class);
					startActivity(intent);
					Toast.makeText(getApplicationContext(), chapterid, Toast.LENGTH_LONG).show();
					ringProgressDialog.dismiss();
				} 
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
		}


	}

	
	

}
