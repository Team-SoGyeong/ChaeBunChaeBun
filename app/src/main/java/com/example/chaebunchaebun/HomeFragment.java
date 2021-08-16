package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private ArrayList<MainRecyclerData> itemList;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private LinearLayoutManager mLayoutManager;

    View homeView;
    ActionBar.Tab tabNew, tabSoon, tabLast, tabMy;
    ViewGroup viewGroup;
    ViewPager vp;
    TabLayout tabLayout;
    LinearLayout searchView, homeLocation;
    ImageView iconLike, iconNotice;
    TextView homeLocationText;
    ImageButton writing;
    int recyclerPosition = -1;
    String[] address = {"",};
    String userId = "1";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        String resultText = "[NULL]";

        try {
            resultText = new HomeTask("home/" + userId).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(resultText);
            String data = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String fullAddress = subJsonObject.getString("full_address");
                int userId = subJsonObject.getInt("user_id");
                String locationCode = subJsonObject.getString("address_id");

                address = fullAddress.split(" ");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) homeView.findViewById(R.id.recycler_view);
        vp = homeView.findViewById(R.id.view_pager);
        tabLayout = homeView.findViewById(R.id.tab_layout);
        searchView = homeView.findViewById(R.id.view_search);
        iconLike = homeView.findViewById(R.id.ic_like);
        homeLocationText = homeView.findViewById(R.id.home_location_text);
        homeLocation = homeView.findViewById(R.id.home_location);
        writing = homeView.findViewById(R.id.btn_start);
        iconNotice = homeView.findViewById(R.id.ic_notice);

        homeLocationText.setText(address[address.length - 1]);

        itemList = new ArrayList<MainRecyclerData>();

        itemList.add(new MainRecyclerData(R.drawable.home_onion));
        itemList.add(new MainRecyclerData(R.drawable.home_garlic));
        itemList.add(new MainRecyclerData(R.drawable.home_green_onion));
        itemList.add(new MainRecyclerData(R.drawable.home_carrot));
        itemList.add(new MainRecyclerData(R.drawable.home_mushroom));
        itemList.add(new MainRecyclerData(R.drawable.home_green_vege));
        itemList.add(new MainRecyclerData(R.drawable.home_cabbage));
        itemList.add(new MainRecyclerData(R.drawable.home_radish));
        itemList.add(new MainRecyclerData(R.drawable.home_potato));
        itemList.add(new MainRecyclerData(R.drawable.home_sweet_potato));
        itemList.add(new MainRecyclerData(R.drawable.home_etc));

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(itemList);
        mRecyclerView.setAdapter(mainRecyclerAdapter);

        mainRecyclerAdapter.setOnItemClickListener(new MainRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", pos);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        MainVPAdapter adapter = new MainVPAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        iconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), HomeLikeActivity.class));
            }
        });

      writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarningDialogFragment e = WarningDialogFragment.getInstance();
                e.show(getChildFragmentManager(), WarningDialogFragment.TAG_EVENT_DIALOG);
            }
        });

      homeLocation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Bundle args = new Bundle();
              args.putString("location", address[address.length - 1]);
              LocationDialogFragment e = LocationDialogFragment.getInstance();
              e.setArguments(args);
              e.show(getChildFragmentManager(), LocationDialogFragment.TAG_EVENT_DIALOG);
          }
      });

      iconNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               QuestionCompleteDialogFragment e = QuestionCompleteDialogFragment.getInstance();
               e.show(getChildFragmentManager(), QuestionCompleteDialogFragment.TAG_EVENT_DIALOG);
           }
       });
      
        // Inflate the layout for this fragment
        return homeView;
    }

    public void getRecyclerPosition(int pos) {
        this.recyclerPosition = pos;
    }
}