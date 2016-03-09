package io.oskm.helloandroid.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.Date;

import io.oskm.helloandroid.R;
import io.oskm.helloandroid.util.DirectoryUtils;

/**
 * http://ilililililililililili.blogspot.kr/2013/07/android-database.html
 */
public class VideoRecordActivity extends Activity {

    private Camera camera = null;

    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_ALARM, true);
        ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_DTMF, true);
        ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_MUSIC, true);
        ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_RING, true);
        ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM, true);
        ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_VOICE_CALL, true);


        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        /*
        camera = Camera.open();

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        Button videoRecordStartBtn = (Button) findViewById(R.id.videoRecStartBtn);
        Button videoRecordStopBtn = (Button) findViewById(R.id.videoRecStopBtn);
        Button videoPlayStartBtn = (Button) findViewById(R.id.videoPlayStartBtn);
        Button videoPlayStopBtn = (Button) findViewById(R.id.videoPlayStopBtn);

        videoRecordStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_ALARM, true);
                ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_DTMF, true);
                ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_MUSIC, true);
                ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_RING, true);
                ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM, true);
                ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_VOICE_CALL, true);


                if (null == mediaRecorder) {
                    mediaRecorder = new MediaRecorder();

                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

                    mediaRecorder.setVideoSize(640, 480);
                    mediaRecorder.setVideoFrameRate(30);

                    String outFileName = DirectoryUtils.createDirectory("videoRecord") + "testVideo" + new Date().getTime() + ".mp4";
                    mediaRecorder.setOutputFile(outFileName);

                    mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());


                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mediaRecorder.release();
                        mediaRecorder = null;
                    }

                }


            }
        });

        videoRecordStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == mediaRecorder) {
                    return;
                }

                mediaRecorder.stop();

                mediaRecorder.release();
                mediaRecorder = null;


            }
        });

        videoPlayStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        videoPlayStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.video_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

