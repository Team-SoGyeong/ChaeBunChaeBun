package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageMyheartDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageMyheartDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mypageHeartList;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    private TextView toastText;
    private Toast toast;

    TextView mypageHeartNolist;
    ImageView back;
    String state = "0";
    String platform = "0";
    String userId = "";
    boolean isMyWish, isMyPage, isMyHeart = true;
    boolean isMyComment , isMyPosting = false;

    ViewPager vp;
    TabLayout tabLayout;
    Button btn;

    public MypageMyheartDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MypageMyheartDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageMyheartDetailFragment newInstance(String param1, String param2) {
        MypageMyheartDetailFragment fragment = new MypageMyheartDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*public void getUserId(String userId){
        this.userId = userId;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        userId = getArguments().getString("userId");
        homeListItems = new ArrayList<HomeListItem>();
        getMyHeartList(state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myHeartDetail = inflater.inflate(R.layout.fragment_mypage_myheart_detail, container, false);

        /*vp = (ViewPager) myHeartDetail.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) myHeartDetail.findViewById(R.id.tab_layout);*/
        back = (ImageView) myHeartDetail.findViewById(R.id.id_back);

        /*MyHeartVPAdapter adapter = new MyHeartVPAdapter(getChildFragmentManager(), userId);
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);*/

        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) myHeartDetail.findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        mypageHeartList = myHeartDetail.findViewById(R.id.mypage_heart_list);
        mypageHeartNolist = myHeartDetail.findViewById(R.id.mypage_heart_nolist);

        /*
        //spinner
        Spinner spinner = myHeartDetail.findViewById(R.id.heart_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        if(homeListItems.isEmpty()) {
            mypageHeartNolist.setVisibility(View.VISIBLE);
            mypageHeartList.setVisibility(View.GONE);
        } else {
            mypageHeartNolist.setVisibility(View.GONE);
            mypageHeartList.setVisibility(View.VISIBLE);

            hLayoutManager = new LinearLayoutManager(getContext());
            mypageHeartList.setLayoutManager(hLayoutManager);
            MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(1);
            mypageHeartList.addItemDecoration(mainRecyclerDecoration);
            homeListAdapter = new HomeListAdapter(homeListItems);
            homeListAdapter.setOnItemClickListener(new HomeListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    if(state.equals("1")){
                        toastText.setText("완료된 채분은 접근할 수 없어요!");
                        toast.show();
                    } else {
                        String postId = String.valueOf(homeListAdapter.getItem(pos).getPostId());
                        int categoryId = homeListAdapter.getItem(pos).getCategoryId();
                        isMyPage = true;
                        Bundle articleBundle = new Bundle();
                        articleBundle.putString("userId", userId);
                        articleBundle.putString("postId", postId);
                        articleBundle.putInt("categoryId", categoryId);
                        articleBundle.putBoolean("isMyPage", isMyPage);
                        articleBundle.putBoolean("isMyPosting", isMyPosting);
                        articleBundle.putBoolean("isMyComment", isMyComment);
                        articleBundle.putBoolean("isMyHeart", isMyHeart);
                        FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        ArticleFragment articleFragment = new ArticleFragment();
                        articleFragment.setArguments(articleBundle);
                        articleTransaction.replace(R.id.mypage_myheart_frame, articleFragment);
                        articleTransaction.addToBackStack(null);
                        articleTransaction.commit();
                    }
                }
            });
            homeListAdapter.setModalClickListener(new HomeListAdapter.OnModalClickListener() {
                @Override
                public void OnModlaClick(View view, int pos) {
                    String postId = String.valueOf(homeListAdapter.getItem(pos).getPostId());
                    int categoryId = homeListAdapter.getItem(pos).getCategoryId();
                    if(state.equals("1")){
                        toastText.setText("완료된 채분은 접근할 수 없어요!");
                        toast.show();
                    } else {
                        Bundle args = new Bundle();
                        args.putString("userId", userId);
                        args.putString("postId", postId);
                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                        bottomSheetDialog.setArguments(args);
                        bottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                    }
                }
            });
            mypageHeartList.setAdapter(homeListAdapter);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return myHeartDetail;
    }

    public void getMyHeartList(String state) {
        homeListItems.clear();
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("mypage/scrap/" + userId + "/" + platform +"/" + state).execute().get();
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

                homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth, postId, userId, categoryId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}