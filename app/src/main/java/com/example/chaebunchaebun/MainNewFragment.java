package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainNewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView main_new_list;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    TextView main_new_text;
    String loationCode = "";
    String userId = "1";

    public MainNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainNewFragment newInstance(String param1, String param2) {
        MainNewFragment fragment = new MainNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getLocationCode(String locationCode){
        this.loationCode = locationCode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        homeListItems = new ArrayList<HomeListItem>();
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

                String lastList = subJsonObject.getString("last_list");
                JSONArray jsonLastListArray = new JSONArray(lastList);
                for(int j = 0; j < jsonLastListArray.length(); j++){
                    JSONObject subJsonObject2 = jsonLastListArray.getJSONObject(j);

                    String img = subJsonObject2.getString("url");
                    String title = subJsonObject2.getString("title");
                    String buyDate = subJsonObject2.getString("buy_date");
                    int membersInt = subJsonObject2.getInt("members");
                    String member = String.valueOf(membersInt) + "명";
                    String perPrice = subJsonObject2.getString("per_price");
                    String writtenBy = subJsonObject2.getString("witten_by");
                    int isAuth = subJsonObject2.getInt("isAuth");

                    homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainNew = inflater.inflate(R.layout.fragment_main_new, container, false);

        main_new_text = mainNew.findViewById(R.id.main_new_text);
        main_new_list = mainNew.findViewById(R.id.main_new_list);

        if(homeListItems.size() < 1) {
            main_new_text.setVisibility(View.VISIBLE);
        } else {
            main_new_text.setVisibility(View.GONE);

            hLayoutManager = new LinearLayoutManager(getContext());
            main_new_list.setLayoutManager(hLayoutManager);
            MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
            main_new_list.addItemDecoration(mainRecyclerDecoration);
            main_new_list.setLayoutManager(new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            homeListAdapter = new HomeListAdapter(homeListItems);
            homeListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ArticleFragment articleFragment = new ArticleFragment();
                    articleTransaction.replace(R.id.bottom_frame, articleFragment);
                    articleTransaction.addToBackStack(null);
                    articleTransaction.commit();
                }
            });
            homeListAdapter.setModalClickListener(new CategoryListAdapter.OnModalClickListener() {
                @Override
                public void OnModlaClick() {
                    //BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                    //bottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                    MyBottomSheetDialog myBottomSheetDialog = MyBottomSheetDialog.getInstance();
                    myBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                }
            });
            main_new_list.setAdapter(homeListAdapter);
        }

        return mainNew;
    }
}