package com.example.android_sdcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.widget.EditText;
import android.view.View;

import android.os.Bundle;
import android.os.Environment;

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextName = (EditText)findViewById(R.id.editName);
    }
    public void mOnClick(View v) {
        FileOutputStream fos;
        FileInputStream fis;

        if(!StorageCheck.isExternalStorageWritable())
            return;

        switch(v.getId()) {
            case R.id.btnSave1: // 공동
                try {
                    File file1 = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS);
                    fos = new FileOutputStream(file1.getAbsolutePath() + "/test.txt");
                    fos.write(mEditTextName.getText().toString().getBytes());
                    fos.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnLoad1: // 전용
                try {
                    File file1 = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS);
                    fis = new FileInputStream(file1.getAbsolutePath() + "/test.txt");
                    int nbytes = fis.available();
                    byte[] data = new byte[nbytes];
                    fis.read(data);
                    mEditTextName.setText(new String(data));
                    fis.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnSave2: // 전용
                try {
                    File[] file2 = ContextCompat.getExternalFilesDirs(this,null);
                    fos = new FileOutputStream(file2[0].getAbsolutePath() + "/test.txt");
                    fos.write(mEditTextName.getText().toString().getBytes());
                    fos.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnLoad2: // 공용
                try {
                    File[] file2 = ContextCompat.getExternalFilesDirs(this,null);
                    fis = new FileInputStream(file2[0].getAbsolutePath() + "/test.txt");
                    int nbytes = fis.available();
                    byte[] data = new byte[nbytes];
                    fis.read(data);
                    mEditTextName.setText(new String(data));
                    fis.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

class StorageCheck {
    // 외부 저장소 읽기 / 쓰기 가능한지 검사(sd-card가 있는지 없는지 검사)
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    // 읽기가 가능한지 체크
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED) ||
                state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        }
        return false;
    }
}