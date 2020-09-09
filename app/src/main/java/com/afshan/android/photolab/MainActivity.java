package com.afshan.android.photolab;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity
{
    VideoView intro;
    static final int REQUEST = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        intro = findViewById(R.id.introVideo);
        setVideo();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setVideo();
        TextView addPhoto;
        TextView insertPhoto;
        addPhoto = findViewById(R.id.addPhoto);
        insertPhoto = findViewById(R.id.insertPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int error = launchLab();
                if (error != 0)
                {
                    Toast.makeText(MainActivity.this, R.string.launchingLab, Toast.LENGTH_SHORT).show();
                }
            }
        });
        insertPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int error = launchLab();
                if (error!= 0)
                {
                    Toast.makeText(MainActivity.this, R.string.launchingLab, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setVideo()
    {
        try
        {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
            intro.setVideoURI(uri);
            intro.start();
            intro.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer)
                {
                    intro.start();
                }
            });
        }
        catch (NullPointerException n)
        {
            Toast.makeText(this, R.string.startingIntroVideo, Toast.LENGTH_SHORT).show();
        }

    }
    private int launchLab()
    {
      try
      {
          Intent intent = new Intent();
          intent.setType("image/*");
          intent.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.selectPic)), REQUEST);

      }
      catch (NullPointerException n)
      {
          return 2;
      }
      return 0;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST)
        {
            Intent intent = new Intent(this, PhotoLab.class);
            assert data != null;
            intent.setData(data.getData());
            startActivity(intent);
        }
    }

}
