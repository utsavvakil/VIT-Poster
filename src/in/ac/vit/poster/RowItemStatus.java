package in.ac.vit.poster;

public class RowItemStatus {
    private String title,summary;
    public RowItemStatus(String title,String summary) {
        this.title=title;
        this.summary=summary;
        
    }
    public String getSummary() {
        return summary;
    }
    public void setSubDate(String summary) {
        this.summary = summary;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title =title;
    }
    
    
    @Override
    public String toString() {
        return "";
    }
}
