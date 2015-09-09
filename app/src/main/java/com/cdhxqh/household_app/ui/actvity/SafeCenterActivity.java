package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.model.ProductModel;
import com.cdhxqh.household_app.ui.widget.SwitchButtonIs;
import com.cdhxqh.household_app.ui.widget.dialog.DateTimePickerDialog;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by hexian on 2015/9/7.
 */
public class SafeCenterActivity extends BaseActivity {

    private static final int PICK_CONTACT = 2;

    // 常用联系人
    ImageView ctrImg;

    /**
     *已选择联系人
     */
    ArrayList<Contacters> contactersList;

    // 保存选择的联系人
    EditText selectUser;

    // 返回按钮
    ImageView backImg;

    // 标题
    TextView titleView;

    // 右上方添加按钮
    ImageView settingImg;

    // 报警类型
    EditText type;

    // 协助地址
    EditText address;

    // 下一步按钮
    Button nextBtn;

    ProductModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_center_service);
        Log.i("TAG", "TAG");
        findViewById();
        initView();
        getData();
    }

    public void findViewById() {
        backImg =  (ImageView)findViewById(R.id.back_imageview_id);
        titleView =  (TextView)findViewById(R.id.title_text_id);
        settingImg =  (ImageView)findViewById(R.id.title_add_id);
        ctrImg =  (ImageView)findViewById(R.id.contactser);
        selectUser =  (EditText)findViewById(R.id.selectuser);
        type = (EditText)findViewById(R.id.alarm_type);
        address = (EditText)findViewById(R.id.address);
        nextBtn = (Button)findViewById(R.id.nextBtn);
    }

    public void initView(){
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setText("安全服务中心");
        settingImg.setVisibility(View.GONE);
        ctrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SafeCenterActivity.this, AlarmActivity.class);
                intent.putExtra("contactList", (Serializable) contactersList);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用数据
                String text = selectUser.getText().toString();
                String addr = address.getText().toString();
                // 提交数据
            }
        });
    }

    public void getData(){
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                model = (ProductModel)bundle.getSerializable("PRODUCTMODEL");
            }
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        contactersList = (ArrayList<Contacters>) data.getSerializableExtra("contactList");
        ArrayList<Contacters> contactersLists = new ArrayList<Contacters>();
        for(int i = 0; i<contactersList.size(); i++) {
            if(contactersList.get(i).isFlag() == true) {
                Contacters context = new Contacters();
                context = contactersList.get(i);
                contactersLists.add(context);
            }
        }
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(contactersLists.size() > 0) {
                selectUser.setText("已选择" + contactersLists.size() + "人");
            }else {
                selectUser.setText("可选择多人");
            }
        }
    }

}
