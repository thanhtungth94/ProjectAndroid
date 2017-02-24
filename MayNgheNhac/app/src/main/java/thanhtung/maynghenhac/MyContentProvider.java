package thanhtung.maynghenhac;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by thanhtung on 7/18/2016.
 */
public class MyContentProvider {

    private ContentResolver resolver;

    public static final String[] COLUMN_NAME = {
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST
    };

    public MyContentProvider(Context context) {
        resolver = context.getContentResolver();
    }


    public ArrayList<AudioItem> readData() {

        ArrayList<AudioItem> arrAudio = new ArrayList<>();

        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,COLUMN_NAME,null,null,null);

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false ) {

            String data = cursor.getString(0);
            String title = cursor.getString(1);

            int duration = cursor.getInt(2);
            String artist = cursor.getString(3);

            AudioItem mediaItem = new AudioItem(data,title,duration,artist);

            arrAudio.add(mediaItem);

            cursor.moveToNext();
        }
        return arrAudio;
    }

}
