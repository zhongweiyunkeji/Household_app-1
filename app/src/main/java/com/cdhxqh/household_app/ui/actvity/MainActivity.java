package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.AppManager;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.fragment.CommonContactFragment;
import com.cdhxqh.household_app.ui.fragment.HelpCenterFragement;
import com.cdhxqh.household_app.ui.fragment.MyDeviceFragment;
import com.cdhxqh.household_app.ui.fragment.NavigationDrawerFragment;
import com.cdhxqh.household_app.ui.fragment.ProductFragment;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = "MainActivity11";

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewGroup mDrawerLayout;

    private CharSequence mTitle;

    private View mActionbarCustom;

    private TextView text;


    private String[] mFavoriteTabTitles;
    private String[] mFavoriteTabPaths;
    private String[] mMainTitles;

    Toolbar toolbar;

    MenuItem  deviceItem;
    MenuItem  alarmIte;
    MenuItem  servicesItem;
    MenuItem  helpItem;
    MenuItem  linkItem01;
    MenuItem  linkItem02;
    //int childCount;
    boolean menuCreateFlag = false;
    Menu menuBar;  // 缓存menu

    TextView atnrTitle;  // actionbar中间显示的文字

    private NavigationDrawerFragment product_fragment;

    static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById();


        initView();


    }


    /**
     * 初始话界面控件*
     */
    private void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (ViewGroup) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_drawer);

        product_fragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_drawer);
    }

    //设置事件监听
    private void initView() {
        toolbar.setNavigationIcon(R.drawable.ic_menu);  // 设置图标
        setSupportActionBar(toolbar);  // 设置ActionBar
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        mFavoriteTabTitles = getResources().getStringArray(R.array.title_drawers);
        // getSupportActionBar().setTitle(mFavoriteTabTitles[0]);
        getSupportActionBar().setTitle("");

        atnrTitle = (TextView)findViewById(R.id.actionbar_title_text);

        /*ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.actionbar_title, null);
        centerTextViwt = (TextView)view.findViewById(R.id.actionbar_01);
        toolbar.addView(view, lp); // 设置客户化View
        childCount = toolbar.getChildCount();*/


        mNavigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();

        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == 2131296460) {
            if(intent.getStringExtra("FragmentName").equals("CommonContactFragment")) {
                Intent intents=new Intent();
                intents.putExtra("contactList", (ArrayList<Contacters>) intent.getSerializableExtra("contactList"));
                intents.setClass(MainActivity.this, ViewUserActivity.class);
                startActivity(intents);
            }
        }

        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    int mSelectPos = 0;

    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        mSelectPos = position;

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        switch (position) {
            case 0:{
                fragmentTransaction.replace(R.id.container,new MyDeviceFragment());
                fragmentTransaction.commit();
                invalidateOptionsMenu();
                break;
            }
            case 1: {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TestActivity.class);
                startActivity(intent);
                break;
            }
            case 2:{
                fragmentTransaction.replace(R.id.container, new ProductFragment());
                fragmentTransaction.commit();
                break;
            }
            case 3: {
                break;
            }
            case 4:{  // 帮助中心
                try {
                    fragmentTransaction.replace(R.id.container, new HelpCenterFragement());
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5:{
                fragmentTransaction.replace(R.id.container, new CommonContactFragment());
                fragmentTransaction.commit();
                break;
            }
            case 6:{
                break;
            }
            default: {
                break;
            }
        }
        // invalidateOptionsMenu通知系统重新调用onCreateOptionMenu()方法重新生成ActionBar,需在API11以上才可使用，但在V4包的FragmentActivity也可使用该方法
        invalidateOptionsMenu();
    }

    @Override
    public void callbackFun(Intent arg) {
        intent = arg;
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<? extends Activity> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }



    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        mTitle = mFavoriteTabTitles[mSelectPos];
        actionBar.setDisplayShowCustomEnabled(true);
        // actionBar.setTitle(mTitle);
        if(atnrTitle!=null){
            atnrTitle.setText(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if(!menuCreateFlag){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_actionbar, menu);

            menuBar = menu;


            // 我的设备
            deviceItem = menu.findItem(R.id.menu_mydevice);



            // 报警记录
            alarmIte = menu.findItem(R.id.menu_alarm);

            // 文件管理
            servicesItem = menu.findItem(R.id.menu_security_services);

            // 帮助中心
            helpItem = menu.findItem(R.id.menu_help_center);

            // 常用联系人
            linkItem01 = menu.findItem(R.id.menu_linkman_add);
            linkItem02 = menu.findItem(R.id.menu_linkman_del);

            menuCreateFlag = true;
        //}

        //if(menuCreateFlag){
            menu = menuBar;
            hideMenu(); // 一开始全部隐藏,后面的判断更具需要逐个显示

            if(mSelectPos == 0){ // 我的设备
                deviceItem.setVisible(true);
            } else if(mSelectPos == 1){ // 报警记录
                alarmIte.setVisible(true);
            } else
            if(mSelectPos == 2){ // 安全服务中心

            } else
            if(mSelectPos == 3){ // 文件管理
                servicesItem.setVisible(true);
            } else
            if(mSelectPos == 4){ // 帮助中心
                helpItem.setVisible(true);
            } else
            if(mSelectPos == 5){// 常用联系人
                linkItem01.setVisible(true);
                linkItem02.setVisible(true);
            } else
            if(mSelectPos == 6){// 关于我们

            } else
            if(mSelectPos == 7){// 设置

            }
        //}

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void hideMenu() {
        if(menuCreateFlag){
            deviceItem.setVisible(false);
            alarmIte.setVisible(false);
            servicesItem.setVisible(false);
            helpItem.setVisible(false);
            linkItem01.setVisible(false);
            linkItem02.setVisible(false);
        }
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }
        exit();

    }

    private void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getString(R.string.try_agin_exit_text), Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }

}
