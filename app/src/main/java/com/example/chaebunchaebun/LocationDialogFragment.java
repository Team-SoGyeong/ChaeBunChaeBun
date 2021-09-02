package com.example.chaebunchaebun;

import static android.app.Activity.RESULT_OK;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LocationDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";
    EditText locationDialogEdt;
    Button locationDialogChange;
    String userId = null;

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
        locationDialogChange = (Button) locationDialog.findViewById(R.id.location_dialog_change);
        locationDialogEdt = (EditText) locationDialog.findViewById(R.id.location_dialog_edt);

        Bundle mArgs = getArguments();
        String location = mArgs.getString("location");
        userId = mArgs.getString("userId");
        locationDialogEdt.setText(location);

        locationDialogEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //getActivity().startActivity(new Intent(getActivity(), SearchLocationActivity.class));
                    String change = locationDialogEdt.getText().toString();
                    Intent intent = new Intent(getContext(), SearchLocationActivity.class);
                    intent.putExtra("searchLocation", change);
                    startActivityForResult(intent, 1111);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 &&  resultCode == RESULT_OK){
            String dong = data.getStringExtra("dong");
            int addressCode = data.getIntExtra("code", 0);

            System.out.println("결과: " + dong + " " + addressCode);
            locationDialogEdt.setText(dong);

            locationDialogChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PutTask locationPutTask = new PutTask();
                    try {
                        String response = locationPutTask.execute("posts/location/" + userId + "/" + addressCode, userId, String.valueOf(addressCode)).get();
                        JSONObject jsonObject = new JSONObject(response);
                        int responseCode = jsonObject.getInt("code");
                        if(responseCode == 200){
                            Intent intent = new Intent(getActivity(), NavigationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            getActivity().startActivity(intent);
                            getActivity().overridePendingTransition(0, 0);
                        } else {
                            Toast.makeText(getContext(),"잘 못 된 접근입니다.",Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
