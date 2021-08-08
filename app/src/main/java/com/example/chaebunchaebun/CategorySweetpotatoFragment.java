package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategorySweetpotatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategorySweetpotatoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategorySweetpotatoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategorySweetpotatoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategorySweetpotatoFragment newInstance(String param1, String param2) {
        CategorySweetpotatoFragment fragment = new CategorySweetpotatoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ImageButton writing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View categorySweetpotato = inflater.inflate(R.layout.fragment_category_sweetpotato, container, false);

        writing = (ImageButton) categorySweetpotato.findViewById(R.id.sweetpotato_newbtn);
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), WarningActivity.class));
            }
        });
        // Inflate the layout for this fragment
        return categorySweetpotato;
    }
}