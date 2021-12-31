package com.konkuk.finalproject;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.konkuk.finalproject.Fragment.Size1616Fragment;
import com.konkuk.finalproject.Fragment.Size3232Fragment;
import com.konkuk.finalproject.Fragment.Size6464Fragment;
import com.konkuk.finalproject.Fragment.Size88Fragment;

import java.util.ArrayList;

public class CanvusActivity extends AppCompatActivity {

    int selectedCanvus; //선택된 캔버스 사이즈 : Default 기본 8x8 캔버스

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    Size88Fragment size88Fragment;
    Size1616Fragment size1616Fragment;
    Size3232Fragment size3232Fragment;
    Size6464Fragment size6464Fragment;

    int nowFragment=0;

    Button[] palette = new Button[9];
    int[] paletteId = {R.id.canvus_color1_btn, R.id.canvus_color2_btn, R.id.canvus_color3_btn, R.id.canvus_color4_btn, R.id.canvus_color5_btn,
                R.id.canvus_color6_btn, R.id.canvus_color7_btn, R.id.canvus_color8_btn, R.id.canvus_color9_btn};

    int[] colors = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
            R.color.color6, R.color.color7, R.color.color8, R.color.color9};

    Button eraser;
    TextView back;


    /* ---------- 추상함수 --------- */


    public abstract class PaletteClickListener implements View.OnClickListener {
        protected int index;

        public PaletteClickListener(int index) {
            this.index = index;
        }
    }


    /* ---------- 오버라이드 된 함수 --------- */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvus);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.main_logo);
        actionBar.setTitle("");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fragmentManager = getSupportFragmentManager();

        size88Fragment = new Size88Fragment();
        size1616Fragment = new Size1616Fragment();
        size3232Fragment = new Size3232Fragment();
        size6464Fragment = new Size6464Fragment();

        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.canvus_mainfrm, size88Fragment).commit(); //.commitAllowingStateLoss();

        int i;
        for(i=0; i<9; i+=1) {
            palette[i] = (Button)findViewById(paletteId[i]);
            palette[i].setOnClickListener( new PaletteClickListener(i){
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("color", colors[index]);
                    Log.d("SEND_COLOR_TO_FREGMENT", Integer.toString(colors[index]));

                    setColorSelect(index);

                    switch(nowFragment) {
                        case 0 :
                            size88Fragment.setArguments(bundle);
                            break;
                        case 1 :
                            size1616Fragment.setArguments(bundle);
                            break;
                        case 2 :
                            size3232Fragment.setArguments(bundle);
                            break;
                        case 3 :
                            size6464Fragment.setArguments(bundle);
                            break;
                    }
                }
            });
        }

        eraser = (Button) findViewById(R.id.canvus_tools_eraser_btn);
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("color", R.color.invisible);
                Log.d("SEND_COLOR_TO_FREGMENT", Integer.toString(R.color.invisible));

                switch(nowFragment) {
                    case 0:
                        size88Fragment.setArguments(bundle);
                        break;
                    case 1:
                        size1616Fragment.setArguments(bundle);
                        break;
                    case 2:
                        size3232Fragment.setArguments(bundle);
                        break;
                    case 3:
                        size6464Fragment.setArguments(bundle);
                        break;
                }
            }
        });

        //뒤로가기 text button -> 액티비티를 종료하고 mainActivity로 돌아감
        back = (TextView) findViewById(R.id.canvus_back_tv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }

    //우 상단 메뉴 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.canvus_menu, menu);

        return true;
    }

    //메뉴 아이템 선택처리
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_change_canvus: //캔버스 변경 메뉴 -> 대화상자 뜸
                changeCanvus();
                break;
//            case R.id.menu_init: //캔버스 초기화 메뉴
//                initCanvus();
//                break;
            default:
                break;
        }
        return true;
    }


    /* ---------- 새로 만든 함수 --------- */


    private void setColorSelect(int index) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(30*dm.density);

        LinearLayout.LayoutParams selectedBtn = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        selectedBtn.topMargin = 0;
        LinearLayout.LayoutParams elseBtn = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        elseBtn.topMargin = size;

        int i;
        for(i=0; i < palette.length; i+=1) {
            palette[i].setLayoutParams(elseBtn);
        }
        if(index != -1 )  //index값으로 -1이 들어오지 않았다면 해당하는 버튼의 마진을 없앰
            palette[index].setLayoutParams(selectedBtn);
        //index로 -1이 들어오면 모든 버튼을 원래대로 만들어 둠
    }

    //대화상자를 띄워 캔버스(프레그먼트)를 변경하는 함수
    private void changeCanvus() {
        final String[] canvusSizeList = new String[] {"8x8", "16x16", "32x32", "64x64"};
        final int [] items = new int[] {0, 1, 2, 3};
        final ArrayList<Integer> selectedItem = new ArrayList<Integer>();
        selectedItem.add(items[0]);

        AlertDialog.Builder dlg = new AlertDialog.Builder(CanvusActivity.this);
        dlg.setTitle("캔버스 사이즈를 선택해 주세요");

        dlg.setSingleChoiceItems(canvusSizeList, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItem.clear();
                selectedItem.add(items[which]);
                //Toast.makeText(getApplicationContext(), canvusSizeList[which], Toast.LENGTH_SHORT).show();
            }
        });

        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Fragment 전환
                transaction = fragmentManager.beginTransaction();

                switch(selectedItem.get(0)) {
                    case 0 :
                        setColorSelect(-1);
                        transaction.replace(R.id.canvus_mainfrm, size88Fragment).commitAllowingStateLoss();
                        nowFragment=0;
                        break;
                    case 1 :
                        setColorSelect(-1);
                        transaction.replace(R.id.canvus_mainfrm, size1616Fragment).commitAllowingStateLoss();
                        nowFragment=1;
                        break;
                    case 2 :
                        setColorSelect(-1);
                        transaction.replace(R.id.canvus_mainfrm, size3232Fragment).commitAllowingStateLoss();
                        nowFragment=2;
                        break;
                    case 3 :
                        setColorSelect(-1);
                        transaction.replace(R.id.canvus_mainfrm, size6464Fragment).commitAllowingStateLoss();
                        nowFragment=3;
                        break;
                    default :
                        break;
                }
                //Toast.makeText(getApplicationContext(), Integer.toString(selectedItem.get(0)), Toast.LENGTH_LONG).show();
            }
        });
        dlg.setNegativeButton("취소", null);

        dlg.show();
    }

//    public void initCanvus() {
//        switch(nowFragment) {
//            case 0 :
//
//                break;
//            case 1 :
//                transaction.detach(size1616Fragment);
//                transaction.attach(size1616Fragment);
//                break;
//            case 2 :
//                transaction.detach(size3232Fragment);
//                transaction.attach(size3232Fragment);
//                break;
//            case 3 :
//                transaction.detach(size6464Fragment);
//                transaction.attach(size6464Fragment);
//                break;
//        }
//    }
}
