package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.AppManager;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.model.Item;
import com.cdhxqh.household_app.ui.fragment.AlarmFragment;
import com.cdhxqh.household_app.ui.fragment.CommonContactFragment;
import com.cdhxqh.household_app.ui.fragment.FileManagementFragment;
import com.cdhxqh.household_app.ui.fragment.HelpCenterFragement;
import com.cdhxqh.household_app.ui.fragment.MyDeviceFragment;
import com.cdhxqh.household_app.ui.fragment.NavigationDrawerFragment;
import com.cdhxqh.household_app.ui.fragment.ProductFragment;
import com.cdhxqh.household_app.ui.fragment.SettingFragment;
import com.cdhxqh.household_app.ui.widget.listview.WrapWidthListView;
import com.cdhxqh.household_app.ui.widget.menu.PopMenu;
import com.cdhxqh.household_app.ui.widget.menu.impl.DeviceMenu;
import com.cdhxqh.household_app.utils.AccountUtils;
import com.videogo.openapi.EzvizAPI;

import java.util.ArrayList;


public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = "MainActivity11";

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewGroup mDrawerLayout;

    private CharSequence mTitle;

    private View mActionbarCustom;

    private TextView text;

    private static final int PICK_CONTACT = 2;

    private static final int TYPE_CONTACT = 1;

    private static final int EDIT_CONTACT = 3;

    private static final int DELE_CONTACT = 4;

    public String a = "de";

    private String[] mFavoriteTabTitles;
    private String[] mFavoriteTabPaths;
    private String[] mMainTitles;

    Toolbar toolbar;

    ImageView deviceItem;
    MenuItem alarmIte;
    MenuItem servicesItem;
    MenuItem helpItem;
    ImageView linkItem01;
    ImageView linkItem02;
    //int childCount;
    boolean menuCreateFlag = false;
    Menu menuBar;  // 缓存menu

    TextView atnrTitle;  // actionbar中间显示的文字

    private NavigationDrawerFragment product_fragment;

    static Intent intent;
    TextView loginView;

    DeviceMenu myDie; // 我的设备
    static final int DEVICEM_ADD = 0;
    static final int DEVICEM_DEL = 1;
    static final int DEVICEM_EDIT = 2;

    DeviceMenu likMan; // 我的设备
    static final int LINK_MAN_ADD = 3;
    static final int DEVICEM_SEARCH = 4;
    String username = "";

    FragmentTransaction fragmentTransaction;

    CommonContactFragment commonContactFragment = new CommonContactFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();

        getData();

        initView();

        initMenu();

    }

    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            username = bundle.getString("username");
        } else {
            SharedPreferences myshared = this.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
            Constants.LOGIN_SESSIONID = myshared.getString(Constants.SESSIONIDTRUE, "");
            username = ec_user.getUserName();
        }
        Constants.USER_ID = myshared.getInt(Constants.LOGINUSERID, -10);
        Constants.TOKEN_URL = myshared.getString(Constants.TOKEN, "");
        EzvizAPI.getInstance().setAccessToken(Constants.TOKEN_URL);
    }


    /**
     * 初始话界面控件*
     */
    private void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loginView = (TextView) findViewById(R.id.txt_member);
        mDrawerLayout = (ViewGroup) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_drawer);

        product_fragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_drawer);
    }

    //设置事件监听
    private void initView() {
        toolbar.setNavigationIcon(R.drawable.ic_menu_list);  // 设置图标
        setSupportActionBar(toolbar);  // 设置ActionBar
        toolbar.setTitleTextColor(getResources().getColor(R.color.transition_bg)); //设置标题颜色
        mFavoriteTabTitles = getResources().getStringArray(R.array.title_drawers);
        // getSupportActionBar().setTitle(mFavoriteTabTitles[0]);
        getSupportActionBar().setTitle("");

        atnrTitle = (TextView) findViewById(R.id.actionbar_title_text);
        loginView.setText(username);
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

        // 我的设备
        deviceItem = (ImageView) findViewById(R.id.menu_mydevice);
        // 常用联系人
        linkItem01 = (ImageView) findViewById(R.id.menu_linkman_add);
        linkItem02 = (ImageView) findViewById(R.id.menu_linkman_del);

        deviceItem.setOnClickListener(new View.OnClickListener() {  // 注册点击事件
            @Override
            public void onClick(View v) {
                myDie.showAsDropDown(deviceItem);
                WrapWidthListView listView = (WrapWidthListView) myDie.getListView();
                ImageView img = myDie.getHintImg();
                int width = listView.getWidth(0);
                img.measure(width, img.getMeasuredHeight());
            }
        });

        linkItem01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 注册点击事件
                likMan.showAsDropDown(linkItem01);
                WrapWidthListView listView = (WrapWidthListView) likMan.getListView();
                ImageView img = likMan.getHintImg();
                int width = listView.getWidth(0);
                img.measure(width, img.getMeasuredHeight());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.d("switchButton", String.valueOf(id));

        if (id == R.id.action_settings) {
            return false;
        } else
        if(id == R.id.menu_help_center){
            HelpCenterFragement fragement = (HelpCenterFragement)this.getFragmentManager().findFragmentByTag("HelpCenterFragement");
            boolean flag = fragement.onoff;
            Intent intents=new Intent(MainActivity.this, Activity_Help_Center_Search.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("FLAG", flag);
            intents.putExtras(bundle);
            startActivity(intents);
        } else
        if(id == R.id.menu_alarm){
            Bundle bundle = new Bundle();
            openActivity(Activity_Alarm_Del.class, bundle);
        }

        return super.onOptionsItemSelected(item);
    }

    int mSelectPos = 0;

    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        mSelectPos = position;

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        switch (position) {
            case 0: {// 我的设备
                fragmentTransaction.replace(R.id.container, new MyDeviceFragment());
                fragmentTransaction.commit();
                invalidateOptionsMenu();
                break;
            }
            case 1: {// 报警记录
                AlarmFragment fragement = new AlarmFragment();
                fragmentTransaction.replace(R.id.container,fragement);
                fragmentTransaction.commit();
                break;
            }
            case 2: {// 安全服务中心
                fragmentTransaction.replace(R.id.container, new ProductFragment());
                fragmentTransaction.commit();
                break;
            }
          /*  case 3: {// 文件管理
                fragmentTransaction.replace(R.id.container, new FileManagementFragment());
                fragmentTransaction.commit();
                break;
            }*/
            case 3: {  // 帮助中心
                try {
                    HelpCenterFragement fragement = new HelpCenterFragement();
                    fragmentTransaction.replace(R.id.container,fragement, "HelpCenterFragement" );
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4: {// 常用联系人
                fragmentTransaction.replace(R.id.container, commonContactFragment);
                fragmentTransaction.commit();
                break;
            }
            case 5: {// 关于我们
                Bundle bundle = new Bundle();
                openActivity(ActivityAbout.class, bundle);
                break;
            }
            default: {// 设置
                fragmentTransaction.replace(R.id.container, new SettingFragment());
                fragmentTransaction.commit();
                break;
            }
        }
        // invalidateOptionsMenu通知系统重新调用onCreateOptionMenu()方法重新生成ActionBar,需在API11以上才可使用，但在V4包的FragmentActivity也可使用该方法
        invalidateOptionsMenu();
    }

    /**
     * 判断sessionid是否过期
     */
    public void text() {
        if (myshared.contains(Constants.SESSIONID)) {
            long result = HttpManager.getCurrentTime() - myshared.getLong(Constants.SESSIONID, HttpManager.getCurrentTime());
            double i = Math.ceil(result / 60000);

            if (Math.ceil(result / 60000) >= new Double(1)) {
                Intent intent = new Intent();
                intent.setClass(this, Activity_Login.class);
                startActivityForResult(intent, 0);
                SharedPreferences.Editor edit = myshared.edit();
                edit.remove(Constants.SESSIONID);
                edit.commit();
                AccountUtils.removeAll(this);
                finish();
            }
        }
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
        if (atnrTitle != null) {
            if(mSelectPos!=5){// mSelectPos==5 表示点击的是关于我们这个选项,这个地方可能会发生变化
                atnrTitle.setText(mTitle);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if(!menuCreateFlag){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);

        menuBar = menu;

        // 报警记录
        alarmIte = menu.findItem(R.id.menu_alarm);

        // 文件管理
        servicesItem = menu.findItem(R.id.menu_security_services);

        // 帮助中心
        helpItem = menu.findItem(R.id.menu_help_center);


        menuCreateFlag = true;
        //}

        //if(menuCreateFlag){
        menu = menuBar;
        hideMenu(); // 一开始全部隐藏,后面的判断更具需要逐个显示

        if (mSelectPos == 0) { // 我的设备
            deviceItem.setVisibility(View.VISIBLE);
        } else if (mSelectPos == 1) { // 报警记录
            // alarmIte.setVisible(true);
        } else if (mSelectPos == 2) { // 安全服务中心

        } /*else if (mSelectPos == 3) { // 文件管理
            servicesItem.setVisible(true);
        }*/ else if (mSelectPos == 3) { // 帮助中心
            helpItem.setVisible(true);
        } else if (mSelectPos == 4) {// 常用联系人
            // linkItem01.setVisibility(View.VISIBLE);
            // linkItem02.setVisibility(View.VISIBLE);
        } else if (mSelectPos == 5) {// 关于我们

        } else if (mSelectPos == 6) {// 设置

        }
        //}

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void hideMenu() {
        if (menuCreateFlag) {
            deviceItem.setVisibility(View.GONE);
            alarmIte.setVisible(false);
            servicesItem.setVisible(false);
            helpItem.setVisible(false);
            linkItem01.setVisibility(View.GONE);
            linkItem02.setVisibility(View.GONE);
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

    private void initMenu() {
        // 我的设备
        myDie = new DeviceMenu(this, null, null, R.layout.menu_mydevice, R.layout.menu_mydevice_item, R.id.head_hint_img);
        myDie.addItem("添加", DEVICEM_ADD, R.drawable.ic_menu_addb);
        myDie.addItem("删除", DEVICEM_DEL, R.drawable.ic_menu_deletesb);
        myDie.addItem("编辑", DEVICEM_EDIT, R.drawable.ic_menu_eidt);
        myDie.update();
        myDie.setOnItemSelectedListener(new PopMenu.OnItemSelectedListener() {
            @Override
            public void selected(View view, Item item, int position) {
                switch (item.id) {
                    case DEVICEM_ADD: { //添加
//                        Bundle bundle = new Bundle();
                        openActivity(AddEquipmentActivity.class, null);
                        break;
                    }
                    case DEVICEM_DEL: { //删除
                        Bundle bundle = new Bundle();
                        openActivity(Activity_Equip_delete.class, bundle);
                        break;
                    }
                    case DEVICEM_EDIT: { //编辑
                        Bundle bundle = new Bundle();
                        openActivity(Activity_Equip_edit.class, bundle);
                        Toast.makeText(MainActivity.this, "编辑", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        });

        final Activity activity = this;
        // 常用联系人
        likMan = new DeviceMenu(this, null, null, R.layout.menu_linkman, R.layout.menu_mydevice_item, R.id.head_hint_img);
        likMan.addItem("直接添加", LINK_MAN_ADD, R.drawable.ic_menu_add);
        likMan.addItem("选择联系人", DEVICEM_SEARCH, R.drawable.ic_menu_delete);
        likMan.update();
        likMan.setOnItemSelectedListener(new PopMenu.OnItemSelectedListener() {
            @Override
            public void selected(View view, Item item, int position) {
                switch (item.id) {
                    case LINK_MAN_ADD: {
                        Intent intent = new Intent(activity, AddContacterActivity.class);
                        intent.putExtra("edit", "edit");
                        startActivityForResult(intent, TYPE_CONTACT);
                        break;
                    }
                    case DEVICEM_SEARCH: {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT);
                        break;
                    }
                }
            }
        });

        linkItem02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra("FragmentName").equals("CommonContactFragment")) {
                    Intent intents = new Intent();
                    intents.putExtra("contactList", (ArrayList<Contacters>) intent.getSerializableExtra("contactList"));
                    intents.setClass(MainActivity.this, ViewUserActivity.class);
                    startActivityForResult(intents, DELE_CONTACT);
                }
                Toast.makeText(MainActivity.this, "常用联系人", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Contacters contacters = new Contacters();

        switch (reqCode) {
            case (PICK_CONTACT): {
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        ContentResolver reContentResolverol = getContentResolver();
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        contacters.setName(name);
                        //条件为联系人ID
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null,
                                null);
                        while (phone.moveToNext()) {
                            String usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contacters.setPhone(usernumber);
                        }
                    }
                }
                break;
            }
            case (TYPE_CONTACT): {
                if (resultCode == Activity.RESULT_OK) {
                    contacters = (Contacters) data.getSerializableExtra("contactList");
                }
                break;
            }
            case (EDIT_CONTACT): {
                if (resultCode == RESULT_OK) {
                    contacters = (Contacters) data.getSerializableExtra("contactList");
                }
                break;
            }
            case (DELE_CONTACT): {
                if (resultCode == RESULT_OK) {
                    ArrayList<Contacters> contacterses = (ArrayList<Contacters>)data.getSerializableExtra("contactList");
                    commonContactFragment.updateList(contacterses);
                }
                break;
            }

        }

        if (contacters.getName() != null && contacters.getPhone() != null) {
            if (contacters.getType() == null || contacters.getType().toString().trim().equals("")) {
                contacters.setType("未分类");
            }
            commonContactFragment.setData(contacters);
        }
    }

}
