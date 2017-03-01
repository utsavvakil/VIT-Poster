package in.ac.vit.poster;

public class RowItemGrid {
    private String location;
    private String title;
    private int image;
    private int going;
    private int event_id;
    String thumb;
   public RowItemGrid(String title,String location,int image,
		   int going,int event_id, String thumb) {
        this.location = location;
        this.title = title;
        this.image=image;
        this.going=going;
        this.event_id=event_id;
        this.thumb = thumb;
   }
    
    public String getLocation() {
        return location;
    }
    public int getImage() {
        return image;
    }
    public int getGoing() {
        return going;
    }
    public int getEventId() {
        return event_id;
    }
    public void setGoing(int going) {
    	
        this.going=going;
        System.out.println("printing"+this.going);
    }
    public void setLocation(String location) {
        this.location = location;
    }
      public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
     @Override
    public String toString() {
        return title + "\n" + location + "\n" ;
    }

	public String getThumb() {
		return thumb;
	}
}
