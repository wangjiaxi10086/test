package demo.wangjiaxi.app_dataservice.application;

import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.sunmi.internal.dataservice.appusage.ApplicationsState;

import demo.wangjiaxi.app_dataservice.R;
import demo.wangjiaxi.app_dataservice.utils.AppContext;
import sunmi.dataservice.appusage.ApplicationsState;


/**
 * Created by wangjiaxi on 17-9-30.
 */

public class AppViewHolder {
    public ApplicationsState.AppEntry entry;

    public View rootView;
    public TextView appName;
    public ImageView appIcon;
    public TextView summary;
    public TextView disabled;
    public TextView code_size;
    public TextView data_size;
    public TextView cache_size;

    static public AppViewHolder createOrRecycle(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.preference_app, null);
            inflater.inflate(R.layout.widget_text_views,
                    (ViewGroup) convertView.findViewById(android.R.id.widget_frame));

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            AppViewHolder holder = new AppViewHolder();
            holder.rootView = convertView;
            holder.appName = (TextView) convertView.findViewById(android.R.id.title);
            holder.appIcon = (ImageView) convertView.findViewById(android.R.id.icon);
            holder.summary = (TextView) convertView.findViewById(R.id.widget_text1);
            holder.disabled = (TextView) convertView.findViewById(R.id.widget_text2);
            holder.code_size = (TextView) convertView.findViewById(R.id.code_size);
            holder.data_size = (TextView) convertView.findViewById(R.id.data_size);
            holder.cache_size = (TextView) convertView.findViewById(R.id.cache_size);
            convertView.setTag(holder);
            return holder;
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            return (AppViewHolder)convertView.getTag();
        }
    }

    void updateSizeText(CharSequence invalidSizeStr, int whichSize) {
        android.util.Log.e("wjx1","updateSizeText of "
                + entry.label + " " + entry + ": " + entry.sizeStr + "-whichSize--" + whichSize);
        if (entry.sizeStr != null) {
            switch (whichSize) {
                case ManageApplicationActivity.SIZE_INTERNAL:
                    summary.setText(entry.internalSizeStr);
                    code_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.codeSize));
                    data_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.dataSize));
                    cache_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.cacheSize));
                    break;
                case ManageApplicationActivity.SIZE_EXTERNAL:
                    summary.setText(entry.externalSizeStr);
                    code_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.codeSize));
                    data_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.dataSize));
                    cache_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.cacheSize));
                    break;
                default:
                    summary.setText(entry.sizeStr);
                    code_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.codeSize));
                    data_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.dataSize));
                    cache_size.setText(Formatter.formatFileSize(AppContext.getContext(), entry.cacheSize));
                    break;
            }
        } else if (entry.size == ApplicationsState.SIZE_INVALID) {
            summary.setText(invalidSizeStr);
        }
    }
}
