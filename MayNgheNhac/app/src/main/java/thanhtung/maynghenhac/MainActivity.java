package thanhtung.maynghenhac;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements
        SeekBar.OnSeekBarChangeListener ,View.OnClickListener,AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{


    private MyAdapter adapter;
    private ArrayList<AudioItem> arrAudio;
    private ListView listView;
    private MyContentProvider myContentProvider;
    private MediaManager mediaManager;
    private boolean isPlaying=true;
    private int temp=0;
    private int pre=0;
    private boolean isRunning = true;


    private TextView tvTitle, tvArtist, tvDuration;
    private TextView textViewTitle, textViewCaSi, textViewPosition;

    private SeekBar seekBar;
    private ImageView imageViewBack,imageViewPlay,imageViewNext,imageViewStop;

    private float time;
    private  Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        arrAudio = new ArrayList<AudioItem>();
        myContentProvider = new MyContentProvider(this);
        arrAudio = myContentProvider.readData();
        mediaManager=new MediaManager(this,arrAudio);
        MyAsync myAsync=new MyAsync();
        myAsync.execute();
        initView();
    }

    private void initView() {

        tvArtist = (TextView) findViewById(R.id.tvCaSy);
        tvDuration = (TextView) findViewById(R.id.tvThoiGian);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        textViewTitle = (TextView) findViewById(R.id.tvTitle);
        textViewCaSi = (TextView) findViewById(R.id.tvCasi);
        textViewPosition = (TextView) findViewById(R.id.tvTime);

        imageViewPlay = (ImageView) findViewById(R.id.imvPlay);
        imageViewNext = (ImageView) findViewById(R.id.imvNext);
        imageViewBack = (ImageView) findViewById(R.id.imvPrevious);
        imageViewStop= (ImageView) findViewById(R.id.imageStop);


        seekBar = (SeekBar) findViewById(R.id.seekbar);

        listView = (ListView) findViewById(R.id.lvAudio);
        adapter = new MyAdapter(this,android.R.layout.simple_list_item_1,arrAudio);
        listView.setAdapter(adapter);

        seekBar.setOnSeekBarChangeListener(this);
        imageViewPlay.setOnClickListener(this);
        imageViewNext.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        mediaManager.create(position);
        mediaManager.start();

        temp=position;
        pre=position;

        textViewTitle.setText(arrAudio.get(position).getTitle());
        textViewCaSi.setText(arrAudio.get(position).getArtist());
        textViewPosition.setText((position + 1) + "/" + arrAudio.size() + "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imvPlay:

                if (isPlaying==true){
                    imageViewPlay.setImageResource(R.drawable.play_circle_outline);
                    mediaManager.pause();
                    isPlaying=false;
                }else if (isPlaying==false){
                    imageViewPlay.setImageResource(R.drawable.pause_circle_outline);
                    mediaManager.start();
                    isPlaying=true;
                }
                break;

            case R.id.imvNext:
               mediaManager.changeIndex(1);
                break;
            case R.id.imvPrevious:
               mediaManager.backIndex(1);
                break;
            case R.id.imageStop:
                mediaManager.stop();

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setTitle("Thong Tin ");


        TextView textViewTitle,textViewCaSi,textViewTime;

        textViewTitle=(TextView) dialog.findViewById(R.id.textviewTitle);
        textViewCaSi=(TextView) dialog.findViewById(R.id.textviewCasi);
        textViewTime=(TextView) dialog.findViewById(R.id.textviewTime);

        textViewTitle.setText(arrAudio.get(position).getTitle());
        textViewCaSi.setText(arrAudio.get(position).getArtist());

        time = (float)arrAudio.get(position).getDuration()/1000;

        if(time % 60 < 10){
            textViewTime.setText((int)time/60+":0"+(int)time%60);
        } else {
            textViewTime.setText((int)time/60+":"+(int)time%60);
        }



        dialog.show();
        return true;
    }




    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){

            mediaManager.seek(progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    class MyAsync extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (isRunning){
                publishProgress();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
                int duration=mediaManager.getDuration();
                int position=mediaManager.getPosition();
                seekBar.setMax(duration);
                seekBar.setProgress(position);
        }
    }
}
