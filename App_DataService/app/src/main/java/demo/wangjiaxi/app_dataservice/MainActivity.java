package demo.wangjiaxi.app_dataservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import demo.wangjiaxi.app_dataservice.application.AppBatteryActivity;
import demo.wangjiaxi.app_dataservice.application.AppUsageActivity;
import demo.wangjiaxi.app_dataservice.application.ManageApplicationActivity;
import demo.wangjiaxi.app_dataservice.collect.ReadSystemMemory;
import demo.wangjiaxi.app_dataservice.service.CpuCollectService;
import demo.wangjiaxi.app_dataservice.utils.AppContext;

public class MainActivity extends PreferenceActivity {

    static final String TAG = "wjx1";
    private static final String KEY_CPU = "cpu";
    private static final String KEY_APP = "app";
    private static final String KEY_APPTIME = "apptime";
    private static final String KEY_MEMORY = "systemmemory";
    private static final String KEY_BATTERY = "appbattery";

    SharedPreferences prefs;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContext.setContext(getApplicationContext());
        addPreferencesFromResource(R.xml.info_switch);
        prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        mContext = AppContext.getContext();

        if (prefs.getBoolean("cpu",false)) {
            Intent cpuCollect = new Intent(mContext, CpuCollectService.class);
            Log.e(TAG, "--------onPreferenceTreeClick start cpu monitor service----");
            mContext.startService(cpuCollect);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen screen, Preference preference) {
        String key = preference.getKey();

        if (KEY_CPU.equals(key)) {
            Log.e(TAG, "--------KEY_CPU control cpu monitor service----");
            Intent cpuCollect = new Intent(mContext, CpuCollectService.class);
            if (prefs.getBoolean("cpu",false)) {
                Log.e(TAG, "--------onPreferenceTreeClick start cpu monitor service----");
                mContext.startService(cpuCollect);
            } else {
                Log.e(TAG, "--------onPreferenceTreeClick stop service----");
                mContext.stopService(cpuCollect);
            }
        } else if (KEY_APP.equals(key)) {
            Intent appInfo = new Intent(MainActivity.this, ManageApplicationActivity.class);
            startActivity(appInfo);
        } else if (KEY_APPTIME.equals(key)) {
            Intent appTimeInfo = new Intent(MainActivity.this, AppUsageActivity.class);
            startActivity(appTimeInfo);
        } else if (KEY_MEMORY.equals(key)) {
            Intent systemMemoryInfo = new Intent(MainActivity.this, ReadSystemMemory.class);
            startActivity(systemMemoryInfo);
        } else if (KEY_BATTERY.equals(key)) {
            Intent appBatteryInfo = new Intent(MainActivity.this, AppBatteryActivity.class);
            startActivity(appBatteryInfo);
        }
        return true;
    }
}
