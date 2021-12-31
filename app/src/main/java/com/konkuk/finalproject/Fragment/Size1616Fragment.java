package com.konkuk.finalproject.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.konkuk.finalproject.R;

public class Size1616Fragment extends Fragment {

    private final int pixelNum = 16; //픽셀의 개수

    TableLayout canvusLayout;


    int color = Color.argb(00, 00, 00, 00);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_size1616, container, false);

        canvusLayout = (TableLayout) v.findViewById(R.id.size1616_canvus_layout);

        TableRow[] rows = new TableRow[pixelNum];
        Button[][] pixels = new Button[pixelNum][pixelNum];

        TableLayout.LayoutParams pm = new TableLayout.LayoutParams(0, 0, 1);
        TableRow.LayoutParams rowPm = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);

        int i, j;
        for(i=0; i < pixelNum; i += 1) {
            rows[i] = new TableRow(v.getContext());
            rows[i].setLayoutParams(pm);
            canvusLayout.addView(rows[i]);

            for (j = 0; j < pixelNum; j += 1) {
                pixels[i][j]= new Button(v.getContext());
                pixels[i][j].setBackgroundResource(R.drawable.custom_invisible_btn);
                pixels[i][j].setLayoutParams(rowPm);

//                pixels[i][j].setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Bundle bundle = getArguments(); //번들 안의 텍스트 불러오기
//                        color = v.getResources().getColor(bundle.getInt("color"));
//                        Log.d("RECV_COLOR_FROM_ACT", Integer.toString(color));
//                        pixels[i][j].setBackgroundColor(color);
//                    }
//                });

                int finalI = i;
                int finalJ = j;
                pixels[i][j].setOnClickListener(new Size1616Fragment.PixelClickListener(finalI, finalJ) {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = getArguments(); //번들 안의 텍스트 불러오기
                        //assert bundle != null;
                        if(bundle != null) {
                            int color = v.getResources().getColor(bundle.getInt("color"));
                            Log.d("RECV_COLOR_FROM_ACT", Integer.toString(color));
                            pixels[finalI][finalJ].setBackgroundColor(color);
                        }
                        else {
                            pixels[finalI][finalJ].setBackgroundColor(color);
                        }
                    }
                });

                rows[i].addView(pixels[i][j]);
            }
        }

        return v;
    }

    private abstract class PixelClickListener implements View.OnClickListener {
        protected int xAxis, yAxis;

        public PixelClickListener(int finalI, int finalJ) {
            this.xAxis = finalI;
            this.yAxis = finalJ;
        }
    }
}
