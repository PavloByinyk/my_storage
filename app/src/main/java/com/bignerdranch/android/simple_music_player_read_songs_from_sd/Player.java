package com.bignerdranch.android.simple_music_player_read_songs_from_sd;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by PavloByinyk on 17.01.2017.
 */
public class Player extends AppCompatActivity implements View.OnClickListener {

    private static MediaPlayer mediaPlayer;
    private ArrayList<File> mySongs;
    private SeekBar seekBar;
    private ImageButton btnPouseStart,btnLoadForward,btnLoadBack,btnNext,btnPrevious;
    private TextView etTotalDuration;
    private int possition;
    private Uri u;
    private Thread updateSeekBar;
    int totalDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        if (savedInstanceState != null)
        {
            u= savedInstanceState.getParcelable("uri");
            totalDuration= savedInstanceState.getInt("totalDuration");
        }


        initViews();

        Intent intent=getIntent();
        Bundle b= intent.getExtras();
        mySongs=(ArrayList) b.getParcelableArrayList("songlist");



        seekBarListener(seekBar);





        mySongs=(ArrayList) b.getParcelableArrayList("songlist");
        possition=b.getInt("pos",0);




        if(mediaPlayer!=null){




            if(! u.equals(Uri.parse(mySongs.get(possition).toString()))) {

                mediaPlayer.stop();
                mediaPlayer.release();

                u = Uri.parse(mySongs.get(possition).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                int duration = mediaPlayer.getDuration();
                String textDuration = String.format( "%02d:%02d", duration / 60, duration % 60);
                etTotalDuration.setText(textDuration);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
            }

        }else {
            u = Uri.parse(mySongs.get(possition).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
            int duration = mediaPlayer.getDuration();
            String textDuration = String.format( "%02d:%02d", duration / 60, duration % 60);
            etTotalDuration.setText(textDuration);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
        }




        updateSeekBarThread().start();


//        updateSeekBar =new Thread(){
//            @Override
//            public void run() {
//                int totalDuration = mediaPlayer.getDuration();
//                int currentPosition = 0;
//
//
//                while(currentPosition<totalDuration){
//                    try {
//                        sleep(500);
//                        currentPosition = mediaPlayer.getCurrentPosition();
//                        seekBar.setProgress(currentPosition);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//                super.run();
//            }
//        };

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//
//            }
//        });
//        u = Uri.parse(mySongs.get(possition).toString());
//        mediaPlayer = MediaPlayer.create(getApplicationContext(),u);



//        int duration = mediaPlayer.getDuration();
//        String textDuration = String.format("%d : %d",
//                TimeUnit.MILLISECONDS.toMinutes(duration),
//                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

//        String textDuration = String.format( "%02d:%02d", duration / 60, duration % 60);
//        etTotalDuration.setText(textDuration);




//        mediaPlayer.start();

//        seekBar.setMax(mediaPlayer.getDuration());


    }

//    @Override
//    public void onBackPressed() {
//        this.moveTaskToBack(true);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("uri", u);
        outState.putInt("totalDuration", totalDuration);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        u= savedInstanceState.getParcelable("uri");
        totalDuration= savedInstanceState.getInt("totalDuration");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pause_start :
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPouseStart.setImageResource(R.drawable.ic_action_btn_play);
                }
                else {
                    mediaPlayer.start();
                    btnPouseStart.setImageResource(R.drawable.ic_action_btn_pouse);
                }
                break;
            case R.id.btn_load_forward :
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);

                break;
            case R.id.btn_load_back :
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);

                break;
            case R.id.btn_next :
                mediaPlayer.stop();
                mediaPlayer.release();
                possition= (possition + 1) % mySongs.size();
                u = Uri.parse(mySongs.get(possition).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                break;
            case R.id.btn_previous :
                mediaPlayer.stop();
                mediaPlayer.release();
                possition= possition - 1 < 0 ?  mySongs.size() - 1 : possition - 1;
                u = Uri.parse(mySongs.get(possition).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                break;
        }
    }

    private void initViews(){
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btnPouseStart= (ImageButton) findViewById(R.id.btn_pause_start);
        btnLoadForward=(ImageButton) findViewById(R.id.btn_load_forward);
        btnLoadBack=(ImageButton) findViewById(R.id.btn_load_back);
        btnNext=(ImageButton) findViewById(R.id.btn_next);
        btnPrevious=(ImageButton) findViewById(R.id.btn_previous);

        etTotalDuration = (TextView) findViewById(R.id.et_total_duration);

        btnPouseStart.setOnClickListener(this);
        btnLoadForward.setOnClickListener(this);
        btnLoadBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
    }

    private Thread updateSeekBarThread(){
        updateSeekBar =new Thread(){
            @Override
            public void run() {
                totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;


                while(currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                super.run();
            }
        };
        return updateSeekBar;
    }

    private  void seekBarListener(SeekBar seekBar){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
    }

}
