package demo.wangjiaxi.app_dataservice.application;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;


//import com.sunmi.internal.dataservice.appusage.AppUsageInfo;
//import com.sunmi.internal.dataservice.appusage.ApplicationsState;

import java.util.ArrayList;
import java.util.Comparator;

import demo.wangjiaxi.app_dataservice.R;
import demo.wangjiaxi.app_dataservice.utils.AppContext;
import sunmi.dataservice.appusage.AppUsageInfo;
import sunmi.dataservice.appusage.ApplicationsState;


/**
 * Created by wangjiaxi on 17-9-30.
 */

public class ManageApplicationActivity extends Activity implements AppUsageInfo.Callbacks{
    static final String TAG = "ManageApplicationActivity";

    public static final int SIZE_TOTAL = 0;
    public static final int SIZE_INTERNAL = 1;
    public static final int SIZE_EXTERNAL = 2;

    public ApplicationsAdapter mApplications;

    private View mListContainer;

    // This is the actual mapping to filters from FILTER_ constants above, the order must
    // be kept in sync.
    public static final ApplicationsState.AppFilter[] FILTERS = new ApplicationsState.AppFilter[] {
            new ApplicationsState.CompoundFilter(ApplicationsState.FILTER_PERSONAL_WITHOUT_DISABLED_UNTIL_USED,
                    ApplicationsState.FILTER_ALL_ENABLED),     // All apps label, but personal filter
            ApplicationsState.FILTER_EVERYTHING,  // All apps
            ApplicationsState.FILTER_ALL_ENABLED, // Enabled
            ApplicationsState.FILTER_DISABLED,    // Disabled
    };

    public static final int sort_order_size          = 1;
    // sort order
    private int mSortOrder = sort_order_size;

    // whether showing system apps.
    private boolean mShowSystem;

    //private ApplicationsState mApplicationsState;
    private AppUsageInfo mAppUsageInfo;

    private String mCurrentPkgName;

    // ListView used to display list
    private ListView mListView;

    // Size resource used for packages whose size computation failed for some reason
    CharSequence mInvalidSizeStr;

    public int mListType;
    public int mFilter;

    private ArrayList<ApplicationsState.AppEntry> appEntries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_applications_apps);

        mAppUsageInfo = new AppUsageInfo(getApplication(), false, this);

        mListContainer = (View) findViewById(R.id.list_container);


        mInvalidSizeStr = getText(R.string.invalid_size_value);

        if (mListContainer != null) {
            // Create adapter and list view here
            View emptyView = mListContainer.findViewById(R.id.empty);
            ListView lv = (ListView) mListContainer.findViewById(R.id.list);
            if (emptyView != null) {
                lv.setEmptyView(emptyView);
            }
            lv.setSaveEnabled(true);
            lv.setItemsCanFocus(true);
            lv.setTextFilterEnabled(true);
            mListView = lv;

            mApplications = new ApplicationsAdapter(mAppUsageInfo, this);

            mListView.setAdapter(mApplications);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mApplications != null) {
            mApplications.resume();
            //mApplications.updateLoading();
        }
    }

    @Override
    public void onGetAppInfoComplete(ArrayList<ApplicationsState.AppEntry> arrayList) {
        appEntries = arrayList;
        mApplications.updateAppList(appEntries);
    }

    static class ApplicationsAdapter extends BaseAdapter {
        private ApplicationsState mState;
        private ApplicationsState.Session mSession;
        private AppUsageInfo mAppUsage;
        private final ManageApplicationActivity mManageApplications;
        private final Context mContext;
        private final ArrayList<View> mActive = new ArrayList<View>();
        private int mFilterMode;
        private ArrayList<ApplicationsState.AppEntry> mBaseEntries;
        private ArrayList<ApplicationsState.AppEntry> mEntries;
        private PackageManager mPm;
        private final LayoutInflater mInflater;
        private boolean mResumed;
        private int mLastSortMode=-1;
        private int mWhichSize = SIZE_TOTAL;
        private boolean mHasReceivedLoadEntries;
        CharSequence mCurFilterPrefix;


        public ApplicationsAdapter(AppUsageInfo appUsage, ManageApplicationActivity manageApplications) {
            mAppUsage = appUsage;
            mManageApplications = manageApplications;
            mContext = AppContext.getContext();
            mPm = mContext.getPackageManager();
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void updateAppList(ArrayList<ApplicationsState.AppEntry> entries) {
            mEntries = entries;
            notifyDataSetChanged();
        }

        public void resume() {

            mAppUsage.startAppUsageSizeInfo();
        }

        @Override
        public int getCount() {
            return mEntries != null ? mEntries.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mEntries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mEntries.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            AppViewHolder holder = AppViewHolder.createOrRecycle(mInflater,
                    convertView);
            convertView = holder.rootView;

            // Bind the data efficiently with the holder
            ApplicationsState.AppEntry entry = mEntries.get(position);
            synchronized (entry) {
                holder.entry = entry;
                if (entry.label != null) {
                    holder.appName.setText(entry.label);
                }
                //mState.ensureIcon(entry);
                if (entry.icon != null) {
                    holder.appIcon.setImageDrawable(entry.icon);
                }
                updateSummary(holder);
                if ((entry.info.flags& ApplicationInfo.FLAG_INSTALLED) == 0) {
                    holder.disabled.setVisibility(View.VISIBLE);
                    holder.disabled.setText(R.string.not_installed);
                } else if (!entry.info.enabled) {
                    holder.disabled.setVisibility(View.VISIBLE);
                    holder.disabled.setText(R.string.disabled);
                } else {
                    holder.disabled.setVisibility(View.GONE);
                }
            }

            mActive.remove(convertView);
            mActive.add(convertView);
            return convertView;
        }


        private void updateSummary(AppViewHolder holder) {
            switch (mManageApplications.mListType) {
                default:
                    holder.updateSizeText(mManageApplications.mInvalidSizeStr, mWhichSize);
                    break;
            }
        }
    }
}
