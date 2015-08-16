package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by hexian on 2015/8/10.
 *
 * 流媒体视频控制(放大、缩小，加大、减小以及方向控制主类)
 *
 */
public class Activity_Video_Control extends BaseActivity {

    LinearLayout layout;
    ScrollView   scrollView;
    private TextView titlename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_control);
        findViewById();
        initView();
    }

    private void findViewById(){
        layout = (LinearLayout)findViewById(R.id.video_monitoring_layout);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        titlename = (TextView) findViewById(R.id.title_text_id);
        // linearLayout = (FixGridLayout)findViewById(R.id.linearLayout);
    }

    private void initView(){

        titlename.setText(getIntent().getExtras().getString("device_name"));
        /*linearLayout.setmCellHeight(200);
        linearLayout.setmCellWidth(200);*/




        /*for (int i = 0; i < 30; i++) {
            // CheckBox box = new CheckBox(this);
            // box.setText("第"+i+"个");
            TextView view = getTextView();
            if(i == 0){
                linearLayout.setmCellWidth(200);
                linearLayout.setmCellHeight(100);
            }
            linearLayout.addView(view);
        }*/
    }

    private TextView getTextView(){
        final TextView view = new TextView(this);
        view.setBackgroundColor(Color.parseColor("#0C4DA2"));
        view.setGravity(Gravity.CENTER);
        view.setWidth(180);
        // view.getLayout().getPar.setMargins(280, 0, 0, 0);

        view.post(new Runnable() {
            @Override
            public void run() {
                //把网络访问的代码放在这里
                view.setText(Html.fromHtml("<img src=\"http://192.168.1.99:8080/qdm/qrcode/checkbox_sel.png\"><br/>抓图", new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable = null;
                        InputStream in;
                        try {
                            in = new URL(source).openStream();
                            drawable = Drawable.createFromStream(in, ""); // 获取网路图片
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        return drawable;

                    }
                }, null));
            }
        });

        return view;
    }


}
