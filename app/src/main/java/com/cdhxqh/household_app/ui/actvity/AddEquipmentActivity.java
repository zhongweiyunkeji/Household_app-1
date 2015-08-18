package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.widget.CartoonDisplay;

import java.io.File;

/**
 * Created by Administrator on 2015/8/18.
 */
public class AddEquipmentActivity extends BaseActivity {
    /**
     * 添加设备
     */
    private EditText equipment_UID;

    /**
     * 设备账号
     */
    private EditText equipment_name;

    /**
     * 设备密码
     */
    private EditText equipment_password;

    /**
     * 安装地点
     */
    private EditText installation_site;

    /**
     * 拍摄照片
     */
    private RelativeLayout shot_photo;

    /**
     * 第一栏拍照
     */
    private LinearLayout create_user;

    /**
     * 第二栏从相册中选择
     */
    private LinearLayout address_book;

    /**
     * 取消
     */
    private LinearLayout cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);
        findViewById();
        initView();
    }

    public void findViewById() {
        equipment_UID = (EditText) findViewById(R.id.equipment_UID);
        equipment_name = (EditText) findViewById(R.id.equipment_name);
        equipment_password = (EditText) findViewById(R.id.equipment_password);
        installation_site = (EditText) findViewById(R.id.installation_site);
        shot_photo = (RelativeLayout) findViewById(R.id.shot_photo);
        cancel = (LinearLayout) findViewById(R.id.cancel);
        create_user = (LinearLayout) findViewById(R.id.create_user);
        address_book = (LinearLayout) findViewById(R.id.address_book);
    }

    public void initView() {
        /**
         * 拍摄照片监听
         */
        shot_photo.setOnClickListener(shotPhotoOnClickListener);
        /**
         * 取消
         */
        cancel.setOnClickListener(cancelOnClickListener);
        /**
         * 第一栏拍照
         */
        create_user.setOnClickListener(createOnClickListener);
        /**
         * 第二栏从相册中选择
         */
        address_book.setOnClickListener(addressOnClickListener);
    }

    /**
     * 拍摄照片监听
     */
    final Activity activity = this;
    private View.OnClickListener shotPhotoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
        }
    };

    /**
     * 取消
     */
    private View.OnClickListener cancelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
        }
    };

    /**
     * 第一栏拍照
     */
    private View.OnClickListener createOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
            Intent intent = new Intent();
            intent.setAction("android.media.action.IMAGE_CAPTURE");
            intent.addCategory("android.intent.category.DEFAULT");
            File file = new File(Environment.getExternalStorageDirectory() + "/000.jpg");
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivity(intent);
        }
    };

    /**
     * 第二栏从相册中选择
     */
    private View.OnClickListener addressOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
        }
    };
}
