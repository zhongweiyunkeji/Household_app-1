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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.model.ProductModel;
import com.cdhxqh.household_app.model.SaceConfig;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.widget.SwitchButtonIs;
import com.cdhxqh.household_app.ui.widget.dialog.DateTimePickerDialog;
import com.cdhxqh.household_app.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<Contacters> contactersList = new ArrayList<Contacters>(0);

    // 接收人
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

    // 保存按钮
    Button nextBtn;

    SaceConfig model;

    // 协助模式按钮
    ImageButton assistModule;

    // 反馈模式按钮
    ImageButton feedback;

    boolean httpFinish = false;

    String users = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_center_service);
        Log.i("TAG", "TAG");
        findViewById();
        getData();
        initView();
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
        assistModule = (ImageButton)findViewById(R.id.assistModule);
        feedback = (ImageButton)findViewById(R.id.feedback);
    }

    public void initView(){
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setText("安全服务中心");

        if(model!=null){
            boolean flag = model.getHelp()==1 ? true : false;
            assistModule.setSelected(flag); // 开启协助模式
            flag = model.getFeedback()==1 ? true : false;
            feedback.setSelected(flag);     // 需要协助人员反馈
            address.setText(model.getHelpaddress());  // 协助地址
            String[] array = model.getUids().split(",");
            selectUser.setText("已选择" + array.length + "人");
            users = model.getUids();
            Log.i("TAG", "TAG");
            // 请求http获取选中的联系人
            // HttpManager.sendHttpRequest(this, );
        }

        settingImg.setVisibility(View.GONE);
        ctrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SafeCenterActivity.this, AlarmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("users", users);
                bundle.putSerializable("contactList", (Serializable) contactersList);
                intent.putExtras(bundle);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(assistModule.isSelected()){
                    String addrText = address.getText().toString();
                    if(addrText == null){
                        addrText = "";
                    }
                    if("".equals(addrText.trim())){
                        ToastUtil.showMessage(SafeCenterActivity.this, "请填写协助地址");
                        return;
                    }
                }

                RequestParams maps = new RequestParams();
                maps.put("ca_id", model.getCaid());
                maps.put("uids", users);
                maps.put("help", assistModule.isSelected()?1:0);
                maps.put("feedback", feedback.isSelected()?1:0);
                maps.put("helpaddress", address.getText().toString());

                // 更新module使得界面可以及时更新过来
                model.setCaid(model.getAcid());
                model.setUids(users);
                model.setHelp(assistModule.isSelected() ? 1 : 0);
                model.setFeedback(feedback.isSelected() ? 1 : 0);
                model.setHelpaddress(address.getText().toString());

                HttpManager.sendHttpRequest(SafeCenterActivity.this, Constants.UPDATE_SAFE, maps, "post", false, new HttpCallBackHandle() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                        if (responseBody != null) {
                            try {
                                JSONObject object = new JSONObject(responseBody);
                                String errcode = object.getString("errcode");
                                String errmsg = object.getString("errmsg");
                                if ("SECURITY-GLOBAL-S-0".equals(errcode)) {
                                    ToastUtil.showMessage(SafeCenterActivity.this, "保存成功 ");
                                } else {
                                    ToastUtil.showMessage(SafeCenterActivity.this, errmsg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                        Log.i("TAG", "TAG");
                        Log.i("TAG", "TAG");
                        Log.i("TAG", "TAG");
                    }
                });
                // 提交数据
            }
        });

        assistModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = assistModule.isSelected();
                assistModule.setSelected(!isSelected);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = feedback.isSelected();
                feedback.setSelected(!isSelected);
            }
        });
    }

    public void getData(){
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                model = (SaceConfig)bundle.getSerializable("PRODUCTMODEL");
            }
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        contactersList = (ArrayList<Contacters>) data.getSerializableExtra("contactList");
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(contactersList.size() > 0) {
                users = "";
                selectUser.setText("已选择" + contactersList.size() + "人");
                for(int index=0; index<contactersList.size(); index++){
                    Contacters c = contactersList.get(index);
                    users = c.getUid() + ","+users;
                }
                users = users.trim();
                users = users.substring(0, users.length()-1);
                users = users.trim();
            }else {
                selectUser.setText("可选择多人");
                users = "";
            }
        }
    }

}