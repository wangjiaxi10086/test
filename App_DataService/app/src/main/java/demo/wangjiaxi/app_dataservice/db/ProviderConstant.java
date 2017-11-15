package demo.wangjiaxi.app_dataservice.db;

import android.net.Uri;

/**
 * @author wangjiaxi
 * for collect info
 */
public class ProviderConstant {
    public static final String AUTHORITY = "sunmi_info_provider";
    public static final String TAG = "ProviderConstant";

    public static final Uri CONTENT_INFO_RECORD_URI = Uri.parse("content://" + AUTHORITY + "/info_record");


    public interface InfoRecordColumns {
        /*public static final String ID = "_id";
        public static final String TITLE = "_title";
        public static final String PERSON_NAME = "_personName";
        public static final String PERSON_TELEPHONE = "_telephone";
        public static final String MODULE = "_module";
        public static final String PROBABILITY = "_probability";
        public static final String HAPPEN_TIME = "_happen_time";
        public static final String FILE_NAME = "_file_name";
        public static final String FILE_PATH = "_file_path";
        public static final String DESCRIPTION = "_description";
        public static final String UPLOAD_STATUS = "_upload_status";
        public static final String ATTACHMENT = "_attachment";*/

        public static final String ID = "_id";
        public static final String TIME = "time";
        public static final String CPU_USAGE = "CpuUsage";
    }
}
