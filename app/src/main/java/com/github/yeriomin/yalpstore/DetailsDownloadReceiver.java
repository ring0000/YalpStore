package com.github.yeriomin.yalpstore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DetailsDownloadReceiver extends BroadcastReceiver {

    private Button button;

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (null == extras) {
            return;
        }
        long id = extras.getLong(DownloadManagerInterface.EXTRA_DOWNLOAD_ID);
        DownloadState state = DownloadState.get(id);
        if (null == state) {
            return;
        }
        state.setFinished(id);
        if (DownloadManagerFactory.get(context).success(id)) {
            state.setSuccessful(id);
        }
        if (!state.isEverythingFinished()) {
            return;
        }
        if (state.isEverythingSuccessful()) {
            button.setText(R.string.details_install);
        } else {
            button.setText(R.string.details_download);
        }
        button.setEnabled(true);
    }
}
