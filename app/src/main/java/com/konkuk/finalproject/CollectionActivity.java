package com.konkuk.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CollectionActivity extends AppCompatActivity {

    TextView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.main_logo);
        actionBar.setTitle("");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        back = (TextView) findViewById(R.id.collection_back_tv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        final GridView gv = (GridView) findViewById(R.id.collection_gridView);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        Integer[] posterID = { R.drawable.dotpix_ex1, R.drawable.dotpix_ex2,
                R.drawable.dotpix_ex3, R.drawable.dotpix_ex4, R.drawable.dotpix_ex5,
                R.drawable.dotpix_ex6, R.drawable.dotpix_ex7, R.drawable.dotpix_ex8,
                R.drawable.dotpix_ex9, R.drawable.dotpix_ex10, R.drawable.dotpix_ex11,
                R.drawable.dotpix_ex12, R.drawable.dotpix_ex13, R.drawable.dotpix_ex14,
                R.drawable.dotpix_ex15, R.drawable.dotpix_ex16, R.drawable.dotpix_ex17,
                R.drawable.dotpix_ex18, R.drawable.dotpix_ex19, R.drawable.dotpix_ex20,
                R.drawable.dotpix_ex21, R.drawable.dotpix_ex22, R.drawable.dotpix_ex23,
                R.drawable.dotpix_ex24, R.drawable.dotpix_ex25};

        public MyGridAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new GridView.LayoutParams(200, 300));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);

            imageview.setImageResource(posterID[position]);

            final int pos = position;
            imageview.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    View dialogView = (View) View.inflate(CollectionActivity.this, R.layout.dialog_collection, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(CollectionActivity.this);
                    ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.collection_dialog_poster_iv);
                    ivPoster.setImageResource(posterID[pos]);
                    dlg.setTitle("자세히 보기");
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageview;
        }
    }
}
