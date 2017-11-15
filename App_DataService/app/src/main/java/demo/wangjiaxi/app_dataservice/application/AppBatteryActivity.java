package demo.wangjiaxi.app_dataservice.application;

import android.app.Activity;
import android.os.Bundle;

//import com.sunmi.internal.dataservice.battery.BatteryInfo;

import demo.wangjiaxi.app_dataservice.R;
import sunmi.dataservice.battery.BatteryInfo;

/**
 * 作者：will-wjx
 * 时间：17-10-26:下午4:14
 * 邮箱：103444209@qq.com
 * 说明：
 */

public class AppBatteryActivity extends Activity {

    BatteryInfo batteryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_battery_list);
        batteryInfo = new BatteryInfo(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        batteryInfo.init();
        batteryInfo.getBatteryUsage();
    }
}
