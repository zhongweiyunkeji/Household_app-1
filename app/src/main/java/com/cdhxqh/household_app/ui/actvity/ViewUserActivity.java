package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.adapter.ViewUserAdapter;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        getData();
        findViewById();
        initView();
    }

   private void findViewById() {
       userList = (RecyclerView) findViewById(R.id.userList);
    }

    private void initView() {
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
