package thanhtung.maynghenhac;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by thanhtung on 7/18/2016.
 */
public class MediaManager implements MediaPlayer.OnCompletionListener{

    private MediaPlayer mediaPlayer;

    private Context context;

    private ArrayList<AudioItem> arrAudio;

    private int index;

    public MediaManager(Context context, ArrayList<AudioItem> arrAudio) {
        this.context = context;
        this.arrAudio = arrAudio;
    }

    public void create(int position) {

        index = position;
        String data = arrAudio.get(position).getData();
        Uri uri = Uri.parse(data);

        if (mediaPlayer != null ) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context,uri);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(this);
        }
        else {
            changeIndex(1);
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void seek(int newPosition) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(newPosition);
        }
    }

    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        else  return 0;
    }

    public int getPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        else return 0;

    }


    public void loop(boolean isLoop) {
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(isLoop);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        changeIndex(1);
    }

    public void changeIndex(int value) {
        index += value;
        if(index < 0) {
            index = arrAudio.size() - 1;
        }
        else if (index > arrAudio.size()-1) {
            index = 0;
        }
        create(index);
        start();
    }

    public void backIndex(int value){
        index -= value;
        if(index < 0) {
            index = arrAudio.size() - 1;
        }
        else if (index > arrAudio.size()-1) {
            index = 0;
        }
        create(index);
        start();
    }






}
