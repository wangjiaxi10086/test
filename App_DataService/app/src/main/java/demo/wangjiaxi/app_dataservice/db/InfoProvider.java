
package demo.wangjiaxi.app_dataservice.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * @author wangjiaxi
 * for collect info
 */
public class InfoProvider extends ContentProvider {
    private static final String TAG = "InfoProvider";
    private static final int URI_INFO_RECORD = 1;
    private static final int URI_INFO_RECORD_ID = 2;
    private static final UriMatcher mMatcher;

    private InfoDbHelper mHelper;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(ProviderConstant.AUTHORITY, "info_record", URI_INFO_RECORD);
        mMatcher.addURI(ProviderConstant.AUTHORITY, "info_record/#", URI_INFO_RECORD_ID);
    }
    @Override
    public boolean onCreate() {
        mHelper = InfoDbHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Cursor c = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String id = null;
        switch (mMatcher.match(uri)) {
            case URI_INFO_RECORD:
                c = db.query(InfoDbHelper.TABLE.INFO_RECORD, projection, selection, selectionArgs, null, null,
                        sortOrder);
                break;
            case URI_INFO_RECORD_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(InfoDbHelper.TABLE.INFO_RECORD, projection, ProviderConstant.InfoRecordColumns.ID + "=" + id
                        + parseSelection(selection), selectionArgs, null, null, sortOrder);
                break;
            default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        long insertedId = -1;
        switch (mMatcher.match(uri)) {
            case URI_INFO_RECORD:
                insertedId =  db.insert(InfoDbHelper.TABLE.INFO_RECORD, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if(insertedId > 0){
             getContext().getContentResolver().notifyChange(ProviderConstant.CONTENT_INFO_RECORD_URI, null);
        }
        return ContentUris.withAppendedId(uri, insertedId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String id = null;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        switch (mMatcher.match(uri)) {
            case URI_INFO_RECORD:
                count = db.delete(InfoDbHelper.TABLE.INFO_RECORD, selection, selectionArgs);
                break;
            case URI_INFO_RECORD_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(InfoDbHelper.TABLE.INFO_RECORD,
                        ProviderConstant.InfoRecordColumns.ID + " = " + id + parseSelection(selection), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (count > 0) {
                getContext().getContentResolver().notifyChange(ProviderConstant.CONTENT_INFO_RECORD_URI, null);
                getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        String id = null;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        switch (mMatcher.match(uri)) {
            case URI_INFO_RECORD:
                count = db.update(InfoDbHelper.TABLE.INFO_RECORD, values, selection, selectionArgs);
                break;
            case URI_INFO_RECORD_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(InfoDbHelper.TABLE.INFO_RECORD, values, ProviderConstant.InfoRecordColumns.ID  + "=" + id
                        + parseSelection(selection), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(ProviderConstant.CONTENT_INFO_RECORD_URI, null);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    private String parseSelection(String selection) {
        return (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
