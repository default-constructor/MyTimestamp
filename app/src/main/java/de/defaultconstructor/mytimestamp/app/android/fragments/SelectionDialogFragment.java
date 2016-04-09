package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import de.defaultconstructor.mytimestamp.R;

/**
 * Created by Thomas Reno on 28.02.2016.
 */
public class SelectionDialogFragment extends DialogFragment {

    public static final String TAG = "fragment_dialog_selection";

    public static SelectionDialogFragment newInstance(String title, String[] listItems) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArray("list-items", listItems);
        SelectionDialogFragment fragment = new SelectionDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        this.selectItems = getArguments().getStringArray("list-items");
        this.builder = new AlertDialog.Builder(getActivity());
        this.builder.setItems(this.selectItems, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Callback callback = null;
                try {
                    callback = (Callback) getTargetFragment();
                } catch (ClassCastException e) {
                    Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", e);
                    throw e;
                }
                if (null != callback) {
                    callback.onSelected(SelectionDialogFragment.this.selectItems[which]);
                }
            }
        });
        builder.setTitle(getArguments().getString("title"));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_dialog, null));
        return this.builder.create();
    }

    private AlertDialog.Builder builder;

    private String[] selectItems;

    public interface Callback {
        void onSelected(String result);
    }
}
