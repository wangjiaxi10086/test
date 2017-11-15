package demo.wangjiaxi.app_dataservice.application;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

//import com.sunmi.internal.dataservice.appusage.AppInformation;
//import com.sunmi.internal.dataservice.appusage.AppUsageTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo.wangjiaxi.app_dataservice.R;
import sunmi.dataservice.appusage.AppInformation;
import sunmi.dataservice.appusage.AppUsageTime;

/**
 * Created by wangjiaxi on 17-10-17.
 */

public class AppUsageActivity extends Activity{
    private int style;
    private long totalTime;
    private int totalTimes;

    private ArrayList<AppInformation> list;
    private List<UsageStats> result;
    private AppUsageTime appUsageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_app_statistics_list);

        appUsageTime = new AppUsageTime(this);

        Button buttonday = (Button) findViewById(R.id.daybuttonlist3);
        buttonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResume();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.list = new ArrayList<>();
        List<Map<String,Object>> datalist = null;
        //调用接口，获取usagestats列表
        try {
            appUsageTime.setUsageStatsList();
            //appUsageTime.getYesTodayUsageStats();
            list = appUsageTime.getShowList();
            android.util.Log.e("wjx1","wjx------onResume---list------------" + list.size());
            datalist = getDataList(list);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        ListView listView = (ListView)findViewById(R.id.AppStatisticsList);
        /*SimpleAdapter adapter = new SimpleAdapter(this,datalist,R.layout.inner_list,
                new String[]{"label","info","times","icon"},
                new int[]{R.id.label,R.id.info,R.id.times,R.id.icon});*/
        SimpleAdapter adapter = new SimpleAdapter(this,datalist,R.layout.inner_list,
                new String[]{"label","info","times"},
                new int[]{R.id.label,R.id.info,R.id.times});
        listView.setAdapter(adapter);

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if(view instanceof ImageView && o instanceof Drawable){

                    ImageView iv=(ImageView)view;
                    iv.setImageDrawable((Drawable)o);
                    return true;
                }
                else return false;
            }
        });
    }

    private List<Map<String,Object>> getDataList(ArrayList<AppInformation> ShowList) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();
        /*map.put("label","全部应用");
        map.put("info","运行时间: " + DateUtils.formatElapsedTime(totalTime / 1000));
        map.put("times","本次开机操作次数: " + totalTimes);
        map.put("icon",R.drawable.use);
        dataList.add(map);*/

        for(AppInformation appInformation : ShowList) {
            map = new HashMap<String,Object>();
            map.put("label",appInformation.getLabel());
            map.put("info","运行时间: " + DateUtils.formatElapsedTime(appInformation.getUsedTimebyDay() / 1000));
            /*map.put("times","本次开机操作次数: " + appInformation.getTimes());*/
            /*map.put("icon",appInformation.getIcon());*/
            dataList.add(map);
        }

        return dataList;
    }

}
