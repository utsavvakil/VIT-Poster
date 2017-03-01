package in.ac.vit.poster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Chapters 
{
	int id;
	int event_id;
	int user_id;
	int going;
	
	private JSONObject data;
	
	public Chapters()
	{
		
	}
	public Chapters(int slno, JSONObject jsonData)
	{	this.data= jsonData;
		
		//id = jsonData.getString("id");
		id= slno+1;
//	chapter_id=jsonData.getInt("event_id");
		
		try {
			event_id = jsonData.getInt("event_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public int getEventId(){
		return this.event_id;
	}
	
	
	public int getGoing(){
		return this.going;
	}
	
	
}
