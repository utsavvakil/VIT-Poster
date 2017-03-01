package in.ac.vit.poster;

public class RowItem {
    private String location;
    private String title;
    private String time;
    private String cat;
    private String colour;
    private int type;
    private int image;
    String thumb;
    public RowItem(String title, String time,String location,
    		String cat,String colour,int type,int image, String thumb) {
        this.location = location;
        this.title = title;
        this.time= time;
        this.cat=cat;
        this.colour=colour;
        this.type = type;
        this.image=image;
        this.thumb=thumb;
    }
    public String getColour() {
        return colour;
    }public int getImage() {
        return image;
    }
    public void setcolour(String colour) {
        this.colour = colour;
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
    public void setCat(String cat) {
        this.cat = cat;
    }
    public String getCat() {
        return cat;
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
    public void setType(int type)
    {
    	this.type = type;
    }
    public int getType()
    {
    	return type;
    }
    public String getThumb()
    {
    	return thumb;
    }
    @Override
    public String toString() {
        return title + "\n" + location + "\n" +time+"\n"+cat;
    }
}
