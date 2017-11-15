package demo.wangjiaxi.app_dataservice.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author wangjiaxi
 * for collect info
 */
public class InfoDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "InfoDbHelper";
    private static final String DB_NAME = "sunmi_info.db";
    private static final int DB_VERSION = 1;
    public interface TABLE {
        public static final String INFO_RECORD = "info_record";
    }

    private static InfoDbHelper mInstance;

    /*private static final String CREATE_INFO_RECORD_TABLE_SQL =
                    "CREATE TABLE " + TABLE.INFO_RECORD + "(" +
                        ProviderConstant.InfoRecordColumns.ID + " INTEGER PRIMARY KEY," +
                        ProviderConstant.InfoRecordColumns.TITLE + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.PERSON_NAME + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.PERSON_TELEPHONE + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.MODULE + " INTEGER NOT NULL DEFAULT 0 ," +
                        ProviderConstant.InfoRecordColumns.PROBABILITY + " INTEGER NOT NULL DEFAULT 0 ," +
                        ProviderConstant.InfoRecordColumns.HAPPEN_TIME + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.FILE_NAME + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.FILE_PATH + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.DESCRIPTION + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.ATTACHMENT + " TEXT NOT NULL DEFAULT '' ," +
                        ProviderConstant.InfoRecordColumns.UPLOAD_STATUS + " INTEGER NOT NULL DEFAULT 0 " +
    ")";*/

    private static final String CREATE_INFO_RECORD_TABLE_SQL =
            "CREATE TABLE " + TABLE.INFO_RECORD + "(" +
                    ProviderConstant.InfoRecordColumns.ID + " INTEGER PRIMARY KEY," +
                    ProviderConstant.InfoRecordColumns.TIME + " TEXT NOT NULL DEFAULT '' ," +
                    ProviderConstant.InfoRecordColumns.CPU_USAGE + " FLOAT NOT NULL DEFAULT 0 " +
                    ")";

    public InfoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void createInfoTable(SQLiteDatabase db) {
        db.execSQL(CREATE_INFO_RECORD_TABLE_SQL);
    }


    static synchronized InfoDbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InfoDbHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createInfoTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
}
