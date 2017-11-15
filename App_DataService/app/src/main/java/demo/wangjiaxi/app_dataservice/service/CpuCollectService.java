package demo.wangjiaxi.app_dataservice.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import demo.wangjiaxi.app_dataservice.collect.CpuInfoCollect;
import demo.wangjiaxi.app_dataservice.receiver.TimeReceiver;


/**
 * Created by wangjiaxi on 17-9-20.
 */

public class CpuCollectService extends Service {
    static final String TAG = "wjx1";
    static final boolean DEBUG = true;
    CpuInfoCollect mCollect;
    HandlerThread collectThread;
    private Context mContext;
    private TimeReceiver mTimeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Log.e(TAG,"wjx------ddd----CpuCollectService--");
        //startCollectThread();
        mTimeReceiver = new TimeReceiver();
        initTimeReceiver();
    }

    //start collect info when the service start
    public  void startCollectThread() {
        Log.e(TAG,"wjx------ddd----startCollectThread--");
        collectThread = new HandlerThread(CpuInfoCollect.class.getName());
        collectThread.start();
        if (mCollect == null) {
            Log.e(TAG,"wjx------ddd----startCollectThread--mCollect----");
            mCollect = new CpuInfoCollect(collectThread.getLooper(), mContext);
            Message message = Message.obtain(mCollect, CpuInfoCollect.EVENT_CPU_COLLECT_TIME);
            mCollect.sendMessage(message);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "onStartCommand startId: " + startId + " flags: " + flags);
        return START_STICKY;
    }

    //start collect info when we need every hour
    private void initTimeReceiver() {
        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mTimeReceiver, timeFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (collectThread != null) {
            collectThread.quit();
        }
        finishTimeReceiver();
    }

    private void finishTimeReceiver() {
        if (mTimeReceiver != null) {
            unregisterReceiver(mTimeReceiver);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
