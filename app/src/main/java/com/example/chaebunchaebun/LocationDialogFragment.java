package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LocationDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public LocationDialogFragment() {}
    public static LocationDialogFragment getInstance() {
        LocationDialogFragment e = new LocationDialogFragment();

        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View locationDialog = inflater.inflate(R.layout.dialog_home_location, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button locationDialogfine = (Button) locationDialog.findViewById(R.id.location_dialog_fine);
        Button locationDialogChange = (Button) locationDialog.findViewById(R.id.location_dialog_change);
        EditText locationDialogEdt = (EditText) locationDialog.findViewById(R.id.location_dialog_edt);

        Bundle mArgs = getArguments();
        String location = mArgs.getString("location");
        locationDialogEdt.setText(location);

        locationDialogEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //getActivity().startActivity(new Intent(getActivity(), SearchLocationActivity.class));
                    String change = locationDialogEdt.getText().toString();
                    Intent intent = new Intent(getActivity(), SearchLocationActivity.class);
                    intent.putExtra("searchLocation", change);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        locationDialogfine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);

        return locationDialog;
    }
}
