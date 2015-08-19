package com.cdhxqh.household_app.ui.actvity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ui.adapter.ViewPagerAdapter;


/**
 * Created by think on 2015/8/15.
 */
public class Activity_First_login extends BaseActivity implements OnClickListener,
        OnPageChangeListener ,OnTouchListener{

    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    private List<View> views;

    private ImageView[] dots;

    private int currentIndex;

    private int lastX = 0;

    private static final int[] pics = {R.drawable.welcom1,R.drawable.welcom2, R.drawable.welcom3,  R.drawable.welcom4};//改为添加自己的图片
    private static final int[] colors = {R.color.welcomebg1, R.color.welcomebg2, R.color.welcomebg3, R.color.welcomebg4};

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guide);
        views = new ArrayList<View>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            iv.setBackgroundColor(getResources().getColor(colors[i]));
            views.add(iv);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(views);
        viewPager.setOnTouchListener(this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            flag = bundle.getBoolean("ActivityAbout");
        }
        initBottomDots();

    }

    private void initBottomDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int index, float arg1, int dis) {
    }

    @Override
    public void onPageSelected(int index) {
        setCurDot(index);
    }

    private void setCurDot(int positon) {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = positon;
    }

    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if((lastX - event.getX()) >100 && (currentIndex == views.size() -1)){
                    if(flag){  // 如果是从设置页面过来
                        finish();
                        break;
                    }
                    SharedPreferences.Editor editor = myshared.edit();
                    editor.putBoolean(Constants.ISFIRST, false);
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(Activity_First_login.this,Activity_Login.class);
                    startActivity(intent);
                    this.finish();
                }
                break;
            default:
                break;
        }
        return false;
    }

}