package com.bignerdranch.android.simple_music_player_read_songs_from_sd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by PavloByinyk on 17.01.2017.
 */
public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String[] items;
    private ArrayList<File> mySongs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = ( ListView ) findViewById(R.id.lvPlayList);

        mySongs = Worker.fillSongList();
        items = Worker.renameSongs(mySongs);


        initAdapter();
    }

    private void initAdapter(){
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, items);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("songlist",mySongs));
            }
        });
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
}
