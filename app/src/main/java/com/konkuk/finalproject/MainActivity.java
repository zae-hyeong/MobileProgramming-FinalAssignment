package com.konkuk.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    String title;
    FrameLayout makeNewCanvus;
    FrameLayout viewCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.main_logo);
        actionBar.setTitle("");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();
    }

    private void initView() {
        makeNewCanvus = (FrameLayout)findViewById(R.id.main_newCanvus_layout);
        makeNewCanvus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CanvusActivity.class);
                startActivity(intent);
            }
        });

        viewCollection = (FrameLayout)findViewById(R.id.main_viewCollection_layout);
        viewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });
    }

    //캔버스 저장 함수
    private void saveView(View view ) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        Bitmap  b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);

        if(b!=null){
            try {
                File f = new File(path+"/notes");
                f.mkdir();
                File f2 = new File(path + "/notes/"+title+".png"); //타이틀 받아오기

                Canvas c = new Canvas( b );

                view.draw(c);

                FileOutputStream fos = new FileOutputStream(f2);

                if ( fos != null ) {
                    b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                }

                //setWallpaper( b );

            }
            catch( Exception e ){
                Log.e("testSaveView", "Exception: " + e.toString() );
            }
        }
    }

    //dp 크기를 px 크기로 변환
    public int dpToPx(int sizeInDp) {
        int pxVal = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, sizeInDp, getResources().getDisplayMetrics()
        );
        return pxVal;
    }
}