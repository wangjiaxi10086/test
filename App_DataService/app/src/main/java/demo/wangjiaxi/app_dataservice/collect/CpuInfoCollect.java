package demo.wangjiaxi.app_dataservice.collect;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

//import com.android.internal.os.ProcessCpuTracker;

import java.text.SimpleDateFormat;
import java.util.Date;


//import com.sunmi.internal.dataservice.cpu.SunmiCpuTraker;

import demo.wangjiaxi.app_dataservice.model.InfoRecord;
import demo.wangjiaxi.app_dataservice.utils.InfoRecordHelper;
import sunmi.dataservice.cpu.SunmiCpuTraker;

/**
 * Created by wangjiaxi on 17-9-20.
 */

public class CpuInfoCollect extends Handler {
    static final String TAG = "wjx1";

    public static final int EVENT_CPU_COLLECT_TIME = 0;
    public static final int EVENT_CPU_COLLECT_HOURS = 1;
    //final ProcessCpuTracker mProcessCpuTracker = new ProcessCpuTracker(false);
    SunmiCpuTraker mProcessCpuTracker;
    private InfoRecordHelper mInfoHelper;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public CpuInfoCollect(Looper looper, Context context) {
        super(looper);
        mInfoHelper = InfoRecordHelper.getInstance(context);
        //mProcessCpuTracker.init();
        mProcessCpuTracker = new SunmiCpuTraker();
    }

    @Override
    public void handleMessage(Message msg) {
        Log.e(TAG, "wjx--------handleMessage----");

        switch (msg.what) {
            case EVENT_CPU_COLLECT_TIME:
                Log.e(TAG, "wjx--------handleMessage------CpuCollectService------");
                handleCpuCollect();
                Message m = obtainMessage(EVENT_CPU_COLLECT_TIME);
                sendMessageDelayed(m, 10000);
                break;
            case EVENT_CPU_COLLECT_HOURS:
                handleCpuCollect();
                break;
            default:
                Log.e(TAG,"no message to handle");
                break;
        }
        /*if (msg.what == EVENT_CPU_COLLECT_TIME) {
            android.util.Log.e("wjx", "wjx--------handleMessage------CpuCollectService------");
            Message m = obtainMessage(EVENT_CPU_COLLECT_TIME);
            sendMessageDelayed(m, 2000);
        }*/
    }

    public void handleCpuCollect() {
//        float ratio = updateCpuStatsNow();
        float ratio = mProcessCpuTracker.updateCpuStatsNow();
        Log.e(TAG, "-------cpu collect:-----" + ratio);
        String date = TIME_FORMAT.format(new Date());
        Log.e(TAG, "-------cpu collect date:-----" + date);

        long time=System.currentTimeMillis();
        Log.e(TAG, "-------cpu collect time:-----" + time);

        InfoRecord record = new InfoRecord();
        record.setCpu_usage(ratio);
        record.setTime(String.valueOf(time));

        mInfoHelper.addInfoRecord(record);
    }

    /*public float updateCpuStatsNow() {
        mProcessCpuTracker.update();

        int user = mProcessCpuTracker.getLastUserTime();
        int system = mProcessCpuTracker.getLastSystemTime();
        int iowait = mProcessCpuTracker.getLastIoWaitTime();
        int irq = mProcessCpuTracker.getLastIrqTime();
        int softIrq = mProcessCpuTracker.getLastSoftIrqTime();
        int idle = mProcessCpuTracker.getLastIdleTime();

        int total = user + system + iowait + irq + softIrq + idle;
        if (total == 0) total = 1;

        return ((float)(user+system+iowait+irq+softIrq)*100) / total;
    }*/
}
