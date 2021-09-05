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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageMyHeartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageMyHeartFragment extends Fragment {
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
    String state = "0";
    String platform = "0";
    String userId = "1";

    //spinner
    TextView textView;
    String[] items = {"진행중인 채분", "완료된 채분"};

    // TODO: Rename and change types and number of parameters
    public static MypageMyHeartFragment newInstance(String param1, String param2) {
        MypageMyHeartFragment fragment = new MypageMyHeartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        homeListItems = new ArrayList<HomeListItem>();
        //getMyHeartList(state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myPageHeart = inflater.inflate(R.layout.fragment_mypage_myheart, container, false);

        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) myPageHeart.findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        mypageHeartList = myPageHeart.findViewById(R.id.mypage_heart_list);
        mypageHeartNolist = myPageHeart.findViewById(R.id.mypage_heart_nolist);

        //spinner
        Spinner spinner = myPageHeart.findViewById(R.id.heart_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = String.valueOf(position);
                getMyHeartList(state);

                if(homeListItems.isEmpty()) {
                    mypageHeartNolist.setVisibility(View.VISIBLE);
                    mypageHeartList.setVisibility(View.GONE);
                } else {
                    mypageHeartNolist.setVisibility(View.GONE);
                    mypageHeartList.setVisibility(View.VISIBLE);

                    hLayoutManager = new LinearLayoutManager(getContext());
                    mypageHeartList.setLayoutManager(hLayoutManager);
                    MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
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
                                Bundle articleBundle = new Bundle();
                                articleBundle.putString("postId", postId);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return myPageHeart;
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

                homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth, postId, userId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}