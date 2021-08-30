package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageMypostingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageMypostingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mypagePostingList;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    TextView mypagePostingNolist;
    String state = "2";
    String platform = "0";
    String id = "1";

    public MypageMypostingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MypageMypostingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageMypostingFragment newInstance(String param1, String param2) {
        MypageMypostingFragment fragment = new MypageMypostingFragment();
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

        homeListItems = new ArrayList<HomeListItem>();
        homeListItems.clear();
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("mypage/mypost/" + id + "/" + platform +"/" + state).execute().get();
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

                int postId = subJsonObject.getInt("");
                int userId = subJsonObject.getInt("");
                String img = subJsonObject.getString("url");
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
                int membersInt = subJsonObject.getInt("members");
                String member = String.valueOf(membersInt) + "명";
                String perPrice = subJsonObject.getString("per_price");
                String writtenBy = subJsonObject.getString("witten_by");
                int isAuth = subJsonObject.getInt("isAuth");

                homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth, postId, userId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mypagePosting = inflater.inflate(R.layout.fragment_mypage_myposting, container, false);

        mypagePostingList = mypagePosting.findViewById(R.id.mypage_posting_list);
        mypagePostingNolist = mypagePosting.findViewById(R.id.mypage_posting_nolist);

        if(homeListItems.isEmpty()) {
            mypagePostingNolist.setVisibility(View.VISIBLE);
        } else {
            mypagePostingNolist.setVisibility(View.GONE);

            hLayoutManager = new LinearLayoutManager(getContext());
            mypagePostingList.setLayoutManager(hLayoutManager);
            MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
            mypagePostingList.addItemDecoration(mainRecyclerDecoration);
            /*mypagePostingList.setLayoutManager(new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });*/
            homeListAdapter = new HomeListAdapter(homeListItems);
            /*homeListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
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
            });*/
            mypagePostingList.setAdapter(homeListAdapter);
        }

        return mypagePosting;
    }
}