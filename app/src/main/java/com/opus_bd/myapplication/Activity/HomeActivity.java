package com.opus_bd.myapplication.Activity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.opus_bd.myapplication.Activity.Call.SinchService;
import com.opus_bd.myapplication.Activity.Call.VoiceCallBaseActivity;
import com.opus_bd.myapplication.Activity.Fargment.CallFragment;
import com.opus_bd.myapplication.Activity.Fargment.ChatFragment;
import com.opus_bd.myapplication.Adapter.TabAdapter;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.opus_bd.myapplication.Utils.Utilities;
import com.sinch.android.rtc.SinchError;

public class HomeActivity extends VoiceCallBaseActivity implements SinchService.StartFailedListener {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolbar();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatFragment(), "Chat");
        adapter.addFragment(new CallFragment(), "Call");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.app_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // Tools.setSystemBarColor(this);
    }



    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
        String user_id = SharedPrefManager.getInstance(this).getUserID();
        if (!user_id.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            Toast.makeText(this, "going to start with id "+user_id, Toast.LENGTH_SHORT).show();
            Utilities.showLogcatMessage("going to start with id "+user_id);
            getSinchServiceInterface().startClient(user_id);
        } else {
        }
    }

    @Override
    public void onStarted() {
        Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();

    }

    //Menu


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }*/



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSetting:
                Toast.makeText(getApplicationContext(),"Setting",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemNewGroup:
                Toast.makeText(getApplicationContext(),"New Group",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemLogOut:
                Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
