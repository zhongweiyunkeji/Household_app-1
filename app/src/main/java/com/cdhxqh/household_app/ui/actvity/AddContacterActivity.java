package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;

/**
 * Created by Administrator on 2015/8/15.
 */
public class AddContacterActivity extends BaseActivity {
    /**
     * 填写姓名
     */
    TextView name;

    /**
     *填写手机号
     */
    TextView phone;

    /**
     *分组
     */
    ImageView select_group;

    /**
     *提交
     */
    Button commit_id;

    /**
     *对象
     */
    Contacters contacts;

    TextView role_group;

    private static final int PICK_CONTACT = 1;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_contacter);
            findViewById();
            getData();
            initView();
        }

        protected void findViewById() {
            /**
             * name
             */
            name = (TextView) findViewById(R.id.name);

            /**
             * phone
             */
            phone = (TextView) findViewById(R.id.phone);

            /**
             * 分组
             */
            select_group = (ImageView) findViewById(R.id.select_group);
            /**
             * 添加联系人
             */
            commit_id = (Button) findViewById(R.id.commit_id);
            /**
             * 分组显示
             */
            role_group = (TextView) findViewById(R.id.role_group);
        }


        protected void initView() {
            name.setText(contacts.getName());
            phone.setText(contacts.getPhone());
            role_group.setText(contacts.getType());
            select_group.setOnClickListener(selectGroupOnClickListener);
//            commit_id.setOnClickListener(commitOnClickListener);
        }

        /**
         * 分组
         */
        final Activity activity = this;
        private View.OnClickListener selectGroupOnClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_Role_Group.class);
                startActivityForResult(intent, PICK_CONTACT);
                }
            };

        /**
         * 提交
         */
        private View.OnClickListener commitOnClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        };

    /**
     * 获取数据
     */
    private void getData() {
        contacts = (Contacters) getIntent().getSerializableExtra("contactList");
    }
}
