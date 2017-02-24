package thanhtung.maynghenhac;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thanhtung on 7/18/2016.
 */
public class MyAdapter extends ArrayAdapter {

    private ArrayList<AudioItem> arrAudio;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, int resource, ArrayList<AudioItem> objects) {
        super(context, resource, objects);
        arrAudio = objects;
        layoutInflater =LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        ViewHolder viewHolder;

        if(view == null){

            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_audio,parent,false);

            ImageView imageSong = (ImageView) view.findViewById(R.id.imageAudio);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            TextView tvCaSy = (TextView) view.findViewById(R.id.tvCaSy);
            TextView tvThoiGian = (TextView) view.findViewById(R.id.tvThoiGian);

            viewHolder.imageAudio = imageSong;
            viewHolder.tvTitle = tvTitle;
            viewHolder.tvCaSy = tvCaSy;
            viewHolder.tvThoiGian = tvThoiGian;

            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        AudioItem item=arrAudio.get(position);
        viewHolder.tvCaSy.setText(item.getArtist());
        viewHolder.imageAudio.setImageResource(R.drawable.play_circle_outline);
        viewHolder.tvTitle.setText(item.getTitle());

        float time = (float)item.getDuration()/1000;


        if(time % 60 < 10){

            viewHolder.tvThoiGian.setText((int)time/60+":0"+(int)time%60);
        } else {
            viewHolder.tvThoiGian.setText((int)time/60+":"+(int)time%60);
        }
        if (item.isStart()){
            viewHolder.imageAudio.setImageResource(R.drawable.pause_circle_outline);
        }else{
            view.setBackgroundColor(Color.WHITE);
        }
        return view;
    }




    class ViewHolder{
        private TextView tvCaSy;
        private TextView tvThoiGian;
        private TextView tvTitle;
        private ImageView imageAudio;
    }

}
