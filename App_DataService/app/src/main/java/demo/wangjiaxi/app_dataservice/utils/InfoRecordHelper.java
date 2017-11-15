package demo.wangjiaxi.app_dataservice.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import demo.wangjiaxi.app_dataservice.db.ProviderConstant;
import demo.wangjiaxi.app_dataservice.model.InfoRecord;


/**
 * Created by wangjiaxi on 17-9-21.
 */

public class InfoRecordHelper {
    private static final String TAG = "wjx1";
    private static InfoRecordHelper mInstance;
    private Context mContext;

    public InfoRecordHelper(Context context) {
        super();
        mContext = context;
    }

    public static InfoRecordHelper getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new InfoRecordHelper(context);
        }
        return mInstance;
    }

    public void addInfoRecord(InfoRecord infoRecord) {
        ContentValues values = infoRecord.getDbContentValues();
        ContentResolver cr = mContext.getContentResolver();
        cr.insert(ProviderConstant.CONTENT_INFO_RECORD_URI, values);
    }
}
