package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.adapter.ViewUserAdapter;
import com.cdhxqh.household_app.ui.widget.CartoonDisplay;
import com.cdhxqh.household_app.ui.widget.ItemDivider;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/15.
 */
public class ViewUserActivity extends BaseActivity {
    /**
     *已选择联系人
     */
    static ArrayList<Contacters> contactersList;

    /**
     *ListView
     */
    RecyclerView userList;

    /**
     *适配器
     */
    ViewUserAdapter viewUserAdapter;

    /**
     * 全选
     */
    private CheckBox checkbox_all;

    /**
     *删除
     */
    private ImageView delete;

    /**
     *灰色屏幕
     */
    private RelativeLayout select_p;

    /**
     *取消
     */
    private LinearLayout cancel;

    /**
     *删除联系人
     */
    private LinearLayout select_user;

    /**
     *全选栏目
     */
    LinearLayout top_check;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        getData();
        findViewById();
        initView();
    }

   private void findViewById() {
       userList = (RecyclerView) findViewById(R.id.userList);

       /**
        * 全选
        */
       checkbox_all = (CheckBox) findViewById(R.id.checkbox_all);

       delete = (ImageView) findViewById(R.id.delete);

       select_p = (RelativeLayout) findViewById(R.id.select_p);

       cancel = (LinearLayout) findViewById(R.id.cancel);

       select_user = (LinearLayout) findViewById(R.id.select_user);

       top_check = (LinearLayout) findViewById(R.id.top_check);
    }

    private void initView() {
        checkbox_all.setOnCheckedChangeListener(checkOnClickListener);

        delete.setOnClickListener(deleteOnClickListener);

        select_p.setOnClickListener(selectOnClickListener);

        cancel.setOnClickListener(cancelOnClickListener);

        select_user.setOnClickListener(selectUserOnClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        userList.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        userList.setLayoutManager(layoutManager);
        userList.setItemAnimator(new DefaultItemAnimator());

        viewUserAdapter = new ViewUserAdapter(this, contactersList);

        userList.setAdapter(viewUserAdapter);
    }

    /**
     * 删除联系人
     */
    final Activity activity = this;
    private View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for(int i = 0; i<contactersList.size(); i++) {
                if (contactersList.get(i).isFlag() == true) {
                    new CartoonDisplay(activity, 1, "删除联系人").display();
                    return;
                }
            }
            new  AlertDialog.Builder(ViewUserActivity.this).setTitle("标题").setMessage("请选择联系人").setPositiveButton("确定", null).show();
        }
    };

    /**
     * 选择删除联系人
     */
    private View.OnClickListener selectUserOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<Contacters> contactersListUpdate = new ArrayList<Contacters>();
            contactersListUpdate = (ArrayList<Contacters>) contactersList.clone();
            for(int i = 0; i<contactersList.size(); i++) {
                if (contactersList.get(i).isFlag() == true) {
                    contactersListUpdate.remove(contactersList.get(i));
                }
            }
            viewUserAdapter.update(contactersListUpdate);
            viewUserAdapter.dataChanged();
            new CartoonDisplay(activity, 1, "删除联系人").display();
            checkbox_all.setChecked(false);
            contactersList  = contactersListUpdate;
            if(contactersListUpdate.size()<=0){
                top_check.setVisibility(View.GONE);
            }
        }
    };

    /**
     * 取消
     */
    private View.OnClickListener cancelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 1, "删除联系人").display();
        }
    };

    /**
     * 取消灰色屏幕
     */
    private View.OnClickListener selectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 1, "删除联系人").display();
        }
    };

    /**
     * 全选按钮事件
     */
    private CompoundButton.OnCheckedChangeListener checkOnClickListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                viewUserAdapter.setIsSelected();
            }else {
                viewUserAdapter.inverseSelected();
            }
        }
    };

   public static void update(ArrayList<Contacters> contactersList_a) {
         contactersList = contactersList_a;
    }

    public void getData() {
        contactersList = (ArrayList<Contacters>) getIntent().getSerializableExtra("contactList");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.putExtra("contactList", (Serializable) contactersList);
            setResult(RESULT_FIRST_USER, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
