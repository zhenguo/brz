package com.brz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.brz.mx5.R;

/**
 * Created by macro on 2017/3/11.
 */

public class AuthDialogFragment extends DialogFragment {

    private NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }

    static class SingletonHolder {
        static AuthDialogFragment instance = new AuthDialogFragment();
    }

    public static AuthDialogFragment newInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
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

        View content = inflater.inflate(R.layout.dialog_auth, null);
        final EditText pwd = (EditText) content.findViewById(R.id.password);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(content)
                // Add action buttons
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (pwd.getText().toString().equalsIgnoreCase("888888")) {
                            mListener.onDialogPositiveClick(AuthDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AuthDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
