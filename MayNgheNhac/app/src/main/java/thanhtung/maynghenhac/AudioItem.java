package thanhtung.maynghenhac;

/**
 * Created by thanhtung on 7/18/2016.
 */
public class AudioItem {

    private String data;
    private String title;
    private int duration;
    private String artist;
    private boolean isStart = false;

    public AudioItem(String data, String title, int duration, String artist) {
        this.data = data;

        this.title = title;
        this.duration = duration;

        this.artist = artist;
    }

    public String getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }



    public boolean isStart() {
        return isStart;
    }

    public void setSelected(boolean selected) {
        this.isStart = selected;
    }

    @Override
    public String toString() {

        String value=title+"\n"+
                (float)duration/1000
                +"\n"+data
                +"\n"+artist;
        return value;


    }
}
