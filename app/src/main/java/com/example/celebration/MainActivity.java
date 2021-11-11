package com.example.celebration;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button downloadButton;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadButton = findViewById(R.id.downloadButton);
        progressBar = findViewById(R.id.progressBar);
        progressTextView = findViewById(R.id.progressTextView);

        downloadButton.setOnClickListener(view -> startDownload());

        path = getFilesDir().getParent();
    }

    private void startDownload() {
        new DownloadAsyncTask(downloadButton,progressBar,progressTextView,path).execute();
    }

    private MediaPlayer mPlayer = new MediaPlayer();;

    public void playSong(View view) {
        try {if(!mPlayer.isPlaying()){
            mPlayer.setDataSource(
                    "/data/user/0/hu.unideb.inf.mobil.celebration/files/song.mp3");
                    mPlayer.prepare();
            // Start playing the Music file
            mPlayer.start();}
        } catch (Exception e) {
            Log.e("IOEX", e.getMessage());
        }

    }
}