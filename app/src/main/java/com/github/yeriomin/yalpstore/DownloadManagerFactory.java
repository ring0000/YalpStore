package com.github.yeriomin.yalpstore;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class DownloadManagerFactory {

    private static final String DOWNLOAD_MANAGER_PACKAGE_NAME = "com.android.providers.downloads";

    static public DownloadManagerInterface get(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD || !nativeDownloadManagerEnabled(context)) {
            return new DownloadManagerFake(context);
        } else {
            return new DownloadManagerAdapter(context);
        }
    }

    static private boolean nativeDownloadManagerEnabled(Context context) {
        int state = context.getPackageManager().getApplicationEnabledSetting(DOWNLOAD_MANAGER_PACKAGE_NAME);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED
            );
        } else {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
            );
        }
    }
}
