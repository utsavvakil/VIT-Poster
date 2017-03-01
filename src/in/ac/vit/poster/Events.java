package in.ac.vit.poster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Events 
{
	int id;
	String event_id;
	String event_name;
	String chapter_name;
	String description;
	String date;
	String time;
	String venue;
	String cord_name;
	String cord_no;
	String fee;
	int going;
	String chap_id;
	String thumb;
	String cover;
	private JSONObject data;
	
	public Events()
	{
		
	}
	public Events(int slno, JSONObject jsonData)
	{	this.data= jsonData;
		
		try 
		{
			//id = jsonData.getString("id");
			id= slno+1;
		//	event_id=jsonData.getInt("event_id");
			event_id = jsonData.getString("id");
			chap_id=jsonData.getString("chapter_id");
			event_name = jsonData.getString("name");
			if(event_name.equals("NULL"))
			{
				event_name = "Not Available";
			}
			chapter_name = "hello";
		//	chapter_name = jsonData.getString("chapter_name");
			if(chapter_name.equals("NULL"))
			{
				chapter_name = "Not Available";
			}
			description = jsonData.getString("about");
			System.out.println("Fetched "+description);
			if(description.equals("NULL"))
			{
				description = "Not Available";
			}
			date = jsonData.getString("date");
			if(date.equals("NULL"))
			{
				date = "Not Available";
			}
			time = jsonData.getString("time");
			if(time.equals("NULL"))
			{
				time = "Not Available";
			}
			venue = jsonData.getString("venue");
			if(venue.equals("NULL"))
			{
				venue = "Not Available";
			}
			cord_name = jsonData.getString("cordName");
			if(cord_name.equals("NULL"))
			{
				cord_name = "Not Available";
			}
			cord_no = jsonData.getString("cordNo");
			if(cord_no.equals("NULL"))
			{
				cord_no = "Not Available";
			}
			fee = jsonData.getString("fee");
			if(fee.equals("NULL"))
			{
				fee = "Not Available";
			}
			thumb = jsonData.getString("thumb");
			thumb = "http://ieeevit.com/poster/app/webroot/img/"+thumb;
			if(thumb.equals("thumb"))
			{
				thumb = "Not Available";
			}
			cover = jsonData.getString("cover");
			cover = "http://ieeevit.com/poster/app/webroot/img/"+cover;
			if(cover.equals("cover"))
			{
				cover = "Not Available";				
			}
			
			
			
			
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
	public String getEventId(){
		return this.event_id;
	}
	public String getEventName(){
		return this.event_name;
	}
	public String getChapterName(){
		return this.chapter_name;
	}
	public String getDescription(){
		return this.description;
	}
	public String getDate(){
		return this.date;
	}
	public String getTime(){
		return this.time;
	}
	public String getVenue(){
		return this.venue;
	}
	
	public String getCord_name(){
		return this.cord_name;
	}
	public String getCord_no(){
		return this.cord_no;
	}
	public String getFee(){
		return this.fee;
	}
	public String getChapterId(){
		return this.chap_id;
	}
	public int getGoing(){
		return this.going;
	}
	public String getThumb(){
		return this.thumb;
	}
	public void setThumb(String thumb){
		this.thumb = thumb;
	}
	public String getCover(){
		return this.cover;
	}
	public void setCover(String cover){
		this.cover = cover;
	}
	
	
}
