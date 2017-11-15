package demo.wangjiaxi.app_dataservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.Message;

import java.util.Calendar;

import demo.wangjiaxi.app_dataservice.collect.CpuInfoCollect;


/**
 * Created by wangjiaxi on 17-9-22.
 */

public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        android.util.Log.e("wjx1", "--------TimeReceiver-----hour--" + hour + "--min--" + min);
        //if (min == 0) {
            HandlerThread collectThread = new HandlerThread(CpuInfoCollect.class.getName());
            collectThread.start();
            CpuInfoCollect mCollect = new CpuInfoCollect(collectThread.getLooper(), context);
            Message message = Message.obtain(mCollect, CpuInfoCollect.EVENT_CPU_COLLECT_HOURS);
            mCollect.sendMessage(message);
        //}
    }
}
