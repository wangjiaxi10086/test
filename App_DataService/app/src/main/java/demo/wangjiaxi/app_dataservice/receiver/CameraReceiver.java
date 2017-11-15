package demo.wangjiaxi.app_dataservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 作者：will-wjx
 * 时间：17-10-23:下午7:23
 * 邮箱：103444209@qq.com
 * 说明：
 */

public class CameraReceiver extends BroadcastReceiver {
    String callApp;
    String label;
    @Override
    public void onReceive(Context context, Intent intent) {
        callApp = intent.getStringExtra("callingApp");
        label = intent.getStringExtra("label");

        android.util.Log.e("wjx1","wjx-----------CameraReceiver----callApp--" + callApp);
        android.util.Log.e("wjx1","wjx-----------CameraReceiver----label--" + label);
    }
}
