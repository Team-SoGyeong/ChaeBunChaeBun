package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryOnionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryOnionFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView categoryOnionList;
    private ArrayList<CategoryListItem> categoryListItems;
    private CategoryListAdapter categoryListAdapter;
    private LinearLayoutManager cLayoutManager;

    TextView categoryNoList;

    public CategoryOnionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryOnionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryOnionFragment newInstance(String param1, String param2) {
        CategoryOnionFragment fragment = new CategoryOnionFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View categoryOnion = inflater.inflate(R.layout.fragment_category_onion, container, false);

        categoryOnionList = categoryOnion.findViewById(R.id.onion_list);
        categoryNoList = categoryOnion.findViewById(R.id.onion_nonlist);
        categoryNoList.setVisibility(View.GONE);

        categoryListItems = new ArrayList<CategoryListItem>();

        categoryListItems.add(new CategoryListItem("깐 양파 한망 채분해요~", "심장이 분분", "07/11",
                "볶음밥 먹으려고 샀는데 너무 많아서 소분합니다\n산지 얼마 되지 않아서 오래 모집합니다!", "일주일 전 구매",
                "5명", "200원", "5", "3"));
        categoryListItems.add(new CategoryListItem("긴 제목 테스트 양파양파양파양파양파양파양파양파양파양파양파양파양파양파양파양파양파양파양파양파양파",
                "심장이 분분", "07/11",
                "볶음밥 먹으려고 샀는데 너무 많아서 소분합니다\n산지 얼마 되지 않아서 오래 모집합니다!\n세 줄 이상 테스트를 위해\n더 추가해보는 내용들",
                "1일 전 구매", "5명", "200원", "10", "13"));

        cLayoutManager = new LinearLayoutManager(getContext());
        categoryOnionList.setLayoutManager(cLayoutManager);
        categoryListAdapter = new CategoryListAdapter(categoryListItems);
        categoryOnionList.setAdapter(categoryListAdapter);

        categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                ArticleFragment articleFragment = new ArticleFragment();
                articleTransaction.replace(R.id.bottom_frame, articleFragment);
                articleTransaction.addToBackStack(null);
                articleTransaction.commit();
            }
        });

        return categoryOnion;
    }
}