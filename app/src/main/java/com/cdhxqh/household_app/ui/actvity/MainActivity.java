package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.AppManager;
import com.cdhxqh.household_app.ui.fragment.HelpCenterFragement;
import com.cdhxqh.household_app.ui.fragment.NavigationDrawerFragment;
import com.cdhxqh.household_app.ui.fragment.ProductFragment;


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

    MenuItem searchItem;
    MenuItem composeItem;

    private NavigationDrawerFragment product_fragment;

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
        getSupportActionBar().setTitle(mFavoriteTabTitles[0]);

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
                invalidateOptionsMenu();
                break;
            }
            case 1: {
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
                    invalidateOptionsMenu();  // invalidateOptionsMenu通知系统重新调用onCreateOptionMenu()方法重新生成ActionBar,需在API11以上才可使用，但在V4包的FragmentActivity也可使用该方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5:{
                break;
            }
            case 6:{
                break;
            }
            default: {
                break;
            }
        }

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
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);
        searchItem = menu.findItem(R.id.action_search);
        composeItem = menu.findItem(R.id.action_compose);
        composeItem.setVisible(false);
        searchItem.setVisible(false);
        if(mSelectPos == 0){
            searchItem.setVisible(true);
        } else if(mSelectPos == 4){
            composeItem.setVisible(true);
        }

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
