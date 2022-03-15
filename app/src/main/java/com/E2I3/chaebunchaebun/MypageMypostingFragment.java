package com.E2I3.chaebunchaebun;

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
    String state = "0";
    String platform = "0";
    String userId = null;
    boolean isMyWish, isMyPage, isMyPosting = true;
    boolean isMyComment , isMyHeart = false;

    private TextView toastText;
    private Toast toast;

    //spinner
    TextView textView;
    String[] items = {"진행중인 채분", "완료된 채분"};

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mypagePosting = inflater.inflate(R.layout.fragment_mypage_myposting, container, false);

        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) mypagePosting.findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        mypagePostingList = mypagePosting.findViewById(R.id.mypage_posting_list);
        mypagePostingNolist = mypagePosting.findViewById(R.id.mypage_posting_nolist);

        //spinner
        Spinner spinner = mypagePosting.findViewById(R.id.posting_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
        mypagePostingList.addItemDecoration(mainRecyclerDecoration);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = String.valueOf(position);
                getMyPostingList(state);

                if(homeListItems.isEmpty()) {
                    mypagePostingNolist.setVisibility(View.VISIBLE);
                    mypagePostingList.setVisibility(View.GONE);
                } else {
                    mypagePostingNolist.setVisibility(View.GONE);
                    mypagePostingList.setVisibility(View.VISIBLE);

                    hLayoutManager = new LinearLayoutManager(getContext());
                    mypagePostingList.setLayoutManager(hLayoutManager);
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
                                articleTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                
                                ArticleFragment articleFragment = new ArticleFragment();
                                articleFragment.setArguments(articleBundle);
                                articleTransaction.replace(R.id.mypage_posting_frame, articleFragment);
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
                            Bundle args = new Bundle();
                            args.putString("userId", userId);
                            args.putString("postId", postId);
                            args.putInt("categoryId", categoryId);
                            MyBottomSheetDialog myBottomSheetDialog = MyBottomSheetDialog.getInstance();
                            myBottomSheetDialog.setArguments(args);
                            myBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                        }
                    });
                    mypagePostingList.setAdapter(homeListAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return mypagePosting;
    }

    public void getMyPostingList(String state){
        homeListItems.clear();
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("mypage/mypost/" + userId + "/" + platform +"/" + state).execute().get();
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
                int userId = Integer.parseInt(this.userId);
                String img = subJsonObject.getString("url");
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
/*                int membersInt = subJsonObject.getInt("members");
                String member = String.valueOf(membersInt) + "명";
 */
                String member = subJsonObject.getString("post_addr");
                String perPrice = subJsonObject.getString("per_price");
                String writtenBy = subJsonObject.getString("witten_by");
                int isAuth = subJsonObject.getInt("isAuth");
                String content = subJsonObject.getString("contents");

                homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth, postId, userId, categoryId, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}