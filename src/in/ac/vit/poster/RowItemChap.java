package in.ac.vit.poster;

public class RowItemChap {
    private String location;
    private String title;
    private String time;
    
    private String date;
    private int image;
    public RowItemChap(String title, String time,String location,int image,String date) {
        this.location = location;
        this.title = title;
        this.time= time;
        
        this.date=date;
        this.image=image;
    }
   
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate(){
    	return date;
    }
    public void setDate(String date)
    {
    	this.date=date;
    }
    public int getImage()
    {
    	return image;
    }
    public void setImage()
    {
    	this.image = image;
    }
  
    @Override
    public String toString() {
        return title + "\n" + location + "\n" +time+"\n";
    }
}
