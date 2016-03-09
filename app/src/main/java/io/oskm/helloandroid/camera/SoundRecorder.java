package io.oskm.helloandroid.camera;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import io.oskm.helloandroid.R;

public class SoundRecorder extends Activity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private RecordState recordState = RecordState.NONE;
    private PlayState playState = PlayState.NONE;
    private Handler recordProgressHandler;
    private float progressTime = 00.00f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_recorder);

        this.mediaRecorder = new MediaRecorder();
        this.mediaPlayer = new MediaPlayer();

        recordProgressHandler = new Handler() {
            public void handleMessage(Message msg) {

                TextView textView = (TextView) findViewById(R.id.recordStatus);

                if(recordState == RecordState.NONE) {
                    textView.setText(String.valueOf(progressTime));
                    return;
                }

                textView.setText(String.format("%.2f", (progressTime += 0.01)));

                recordProgressHandler.sendEmptyMessageDelayed(0, 10);
            }
        };

        final Button recordBtn = (Button) findViewById(R.id.recordBtn);

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RecordState.NONE == recordState) {
                    startRecord();
                    recordBtn.setText(RecordState.RECORDING.getLabel());
                } else {
                    stopRecord();
                    recordBtn.setText(RecordState.NONE.getLabel());
                }


            }
        });

        findViewById(R.id.playBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PlayState.NONE == playState) {
                    startPlay();
                } else {
                    stopPlay();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sound_recorder, menu);
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

    private void startRecord() {
        try {
            mediaRecorder.reset();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            String outFileName = createDirectory("audioRecorder") + "testAudio" + new Date().getTime() + ".amr";
            mediaRecorder.setOutputFile(outFileName);
            mediaRecorder.prepare();
            mediaRecorder.start();

            recordProgressHandler.sendEmptyMessageDelayed(0, 10);

            recordState = RecordState.RECORDING;

        } catch (IOException e) {
            e.printStackTrace();
            recordState = RecordState.NONE;
        }


    }

    private void stopRecord() {
        mediaRecorder.stop();
        recordState = RecordState.NONE;
        progressTime = 0;
        recordProgressHandler.sendEmptyMessage(0);
    }

    private void startPlay() {

        playState = PlayState.PLAYING;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playState = PlayState.NONE;
            }
        });

        try {
            mediaPlayer.setDataSource("/testAudio");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void stopPlay() {
        mediaPlayer.stop();
        mediaPlayer.release();
        playState = PlayState.NONE;

    }

    private String createDirectory(String dirName) {
        String mRootPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + dirName;

        try {
            File fRoot = new File(mRootPath);
            if (fRoot.exists() == false) {
                if (fRoot.mkdirs() == false) {
                    throw new Exception("");
                }
            }
        } catch (Exception e) {
            mRootPath = "-1";
        }

        return mRootPath + "/";
    }
}

enum RecordState {
    RECORDING("Recording"),
    NONE("Rec");

    RecordState(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return this.label;
    }

}

enum PlayState {
    PLAYING,
    NONE;
}