package com.brz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.brz.download.DiskIOExecutor;
import com.brz.mx5.R;
import com.brz.service.UpdateConfigRunnable;
import com.brz.system.TerminalConfigManager;
import com.brz.utils.SystemUtil;

/**
 * Created by macro on 2017/3/11.
 */

public class SettingDialogFragment extends DialogFragment {

    static class SingletonHolder {
        static SettingDialogFragment instance = new SettingDialogFragment();
    }

    public static SettingDialogFragment newInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View content = inflater.inflate(R.layout.dialog_settings, null);

        final EditText workId = (EditText) content.findViewById(R.id.workId);
        final EditText httpServer = (EditText) content.findViewById(R.id.httpServer);

        if (SystemUtil.isSystemLoaded()) {
            workId.setText(TerminalConfigManager.getInstance().getTerminalConfig().getWorkId());
            httpServer.setText(TerminalConfigManager.getInstance().getTerminalConfig().getHttpServer());
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(content)
                // Add action buttons
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        final String workIdStr = workId.getText().toString();
                        final String httpServerStr = httpServer.getText().toString();

                        if (!TextUtils.isEmpty(workIdStr)) {
                            UpdateConfigRunnable ur = new UpdateConfigRunnable(workIdStr, UpdateConfigRunnable.WORKID);
                            DiskIOExecutor.getInstance().execute(ur);
                        }

                        if (!TextUtils.isEmpty(httpServerStr)) {
                            UpdateConfigRunnable ur = new UpdateConfigRunnable(httpServerStr, UpdateConfigRunnable.HTTP);
                            DiskIOExecutor.getInstance().execute(ur);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SettingDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
