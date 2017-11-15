package demo.wangjiaxi.app_dataservice.collect;

import android.app.Activity;
import android.os.BatteryManager;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

//import com.sunmi.internal.dataservice.battery.BatteryInfo;

import demo.wangjiaxi.app_dataservice.R;
import sunmi.dataservice.battery.BatteryInfo;

public class ReadSystemMemory extends Activity {

    TextView tv = null;
    BatteryInfo batteryInfo;

    private String getAvailMemory() {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
    }

    private String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    private String TotalMemory() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(getBaseContext(), mi.totalMem);// 将获取的内存大小规格化
    }

    private String getBatteryInfo() {
        /*BatteryManager batteryManager=(BatteryManager)getSystemService(BATTERY_SERVICE);
        int mCounter = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        int current_avg = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
        int current_lev = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        StringBuilder sb = new StringBuilder("");
        sb.append("current battery level:" + String.valueOf(current_lev));
        return sb.toString();*/
        batteryInfo.init();
        StringBuilder sb = new StringBuilder("");
        sb.append("current battery Temperature: " + String.valueOf(batteryInfo.getBatteryTemperature()));
        sb.append(" current battery level: " + String.valueOf(batteryInfo.getBatteryLevel()));
        sb.append(" current battery scale: " + String.valueOf(batteryInfo.getBatteryScal()));
        return sb.toString();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memoryinfo);
        batteryInfo = new BatteryInfo(getApplicationContext());

        tv = (TextView) findViewById(R.id.system_memory);
        /*tv.setText("手机总内存: " + this.getTotalMemory() + ", " + "可用内存: "
                + this.getAvailMemory());*/
        /*tv.setText("手机总内存: " + this.TotalMemory() + ", " + "可用内存: "
                + this.getAvailMemory());*/

        tv.setText("手机总内存: " + this.TotalMemory() + ", " + "可用内存: "
                + this.getAvailMemory() + ", " + "剩余电量： " + this.getBatteryInfo());
    }
}
