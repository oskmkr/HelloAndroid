package io.oskm.helloandroid.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by oskm on 2014-09-26.
 */
public class DirectoryUtils {

    /*
    // 외장메모리가 있는지 확인한다.
    // Environment.getExternalStorageState() 를 통해서 현재 외장메모리를 상태를 알수있다.
    String state = Environment.getExternalStorageState();
    // Environment.MEDIA_MOUNTED 외장메모리가 마운트 flog
    if (!state.equals(Environment.MEDIA_MOUNTED)) {
        Toast.makeText(getApplicationContext(), "외장 메모리가 마운트 되지않았습니다.", Toast.LENGTH_LONG).show();
    } else {
        EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    String newFilename = "";
    if (EXTERNAL_STORAGE_PATH == null || EXTERNAL_STORAGE_PATH.equals("")) {
        // 내장 메모리를 사용합니다.
        newFilename = RECORDED_FILE + fileIndex + ".mp4";
    } else {
        // 외장 메모리를 사용합니다.
        newFilename = EXTERNAL_STORAGE_PATH + "/" + RECORDED_FILE + fileIndex + ".mp4";
    }
    */
    public static String createDirectory(String dirName) {
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
