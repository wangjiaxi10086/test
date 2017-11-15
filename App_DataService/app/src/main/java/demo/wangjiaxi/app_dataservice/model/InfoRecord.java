package demo.wangjiaxi.app_dataservice.model;

import android.content.ContentValues;

import demo.wangjiaxi.app_dataservice.db.ProviderConstant;


/**
 * Created by wangjiaxi on 17-9-21.
 */

public class InfoRecord {

    private int id;
    private String time;
    private float cpu_usage;

    public InfoRecord() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public float getCpu_usage() {
        return cpu_usage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCpu_usage(float cpu_usage) {
        this.cpu_usage = cpu_usage;
    }

    @Override
    public String toString() {
        return "InfoRecord{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", cpu_usage='" + cpu_usage +
                '}';
    }

    public ContentValues getDbContentValues() {
        ContentValues values = new ContentValues();
        if (time != null) {
            values.put(ProviderConstant.InfoRecordColumns.TIME,
                    (String) time);
        }
        if (cpu_usage >= 0) {
            values.put(ProviderConstant.InfoRecordColumns.CPU_USAGE,
                    (float) cpu_usage);
        }

        return values;
    }
}
