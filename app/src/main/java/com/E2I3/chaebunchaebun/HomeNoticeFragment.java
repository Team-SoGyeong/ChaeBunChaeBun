package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeNoticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeNoticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View homeNoticeView;

    private RecyclerView homeNoticeList;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    TextView homeNoticeNolist;
    ImageView homeNoticeBack;

    String userId = "";

    public HomeNoticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeNoticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeNoticeFragment newInstance(String param1, String param2) {
        HomeNoticeFragment fragment = new HomeNoticeFragment();
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
        homeNoticeView = inflater.inflate(R.layout.fragment_home_notice, container, false);

        homeNoticeBack = (ImageView) homeNoticeView.findViewById(R.id.home_notice_back);
        homeNoticeList = homeNoticeView.findViewById(R.id.mypage_heart_list);
        homeNoticeNolist = homeNoticeView.findViewById(R.id.mypage_heart_nolist);

        /*if(homeListItems.isEmpty()) {
            homeNoticeNolist.setVisibility(View.VISIBLE);
            homeNoticeList.setVisibility(View.GONE);
        } else {
            homeNoticeNolist.setVisibility(View.GONE);
            homeNoticeList.setVisibility(View.VISIBLE);
        }
*/
        return homeNoticeView;
    }

    public void getMyNoticeList(String state) {
        homeListItems.clear();
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("common/notice/" + userId).execute().get();
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
                int categoryId = subJsonObject.getInt("category_id");
                int postId = subJsonObject.getInt("post_id");
                int userId = subJsonObject.getInt("wish_id");
                String img = subJsonObject.getString("url");
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
                int membersInt = subJsonObject.getInt("members");
                String member = String.valueOf(membersInt) + "명";
                String perPrice = subJsonObject.getString("per_price");
                String writtenBy = subJsonObject.getString("written_by");
                int isAuth = subJsonObject.getInt("isAuth");
                String content = subJsonObject.getString("contents");

                homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth, postId, userId, categoryId, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}