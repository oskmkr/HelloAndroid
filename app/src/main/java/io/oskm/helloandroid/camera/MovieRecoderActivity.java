package io.oskm.helloandroid.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import java.io.IOException;

import io.oskm.helloandroid.R;

public class MovieRecoderActivity extends Activity {

    private MediaRecorder videoRecorder;
    private String RECORED_FILE = "/my_recorded_file.mp4";
    private CameraSurfaceView cameraSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_recoder);

        cameraSurfaceView = new CameraSurfaceView(getApplicationContext());
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.movieFrame);
        frameLayout.addView(cameraSurfaceView);

        findViewById(R.id.recodeStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == videoRecorder) {
                    videoRecorder = new MediaRecorder();
                }

                String pathForAppFiles = getFilesDir().getAbsolutePath();

                pathForAppFiles += RECORED_FILE;

                /*
                Camera camera = Camera.open();    // 카메라 객체 할당
                videoRecorder.setCamera(camera); // 레코더의 녹화용 카메라 설정
                */
                videoRecorder.setCamera(cameraSurfaceView.getCamera());

                videoRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                videoRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                videoRecorder.setVideoSize(640, 480);
                videoRecorder.setVideoFrameRate(30);
                videoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

                videoRecorder.setOutputFile(pathForAppFiles);
                videoRecorder.setPreviewDisplay(cameraSurfaceView.getHolder().getSurface());

                try {
                    videoRecorder.prepare();
                    videoRecorder.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("movie", "error", e);
                }
            }
        });

        findViewById(R.id.recodeStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != videoRecorder) {
                    videoRecorder.stop();
                    videoRecorder.release();
                    videoRecorder = null;
                }
    }
});
        }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_recoder, menu);
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

    public void onClick(View v) {

        if (v.getId() == R.id.recodeStart) {


        } else if (v.getId() == R.id.recodeStop) {


        }

    }
}
