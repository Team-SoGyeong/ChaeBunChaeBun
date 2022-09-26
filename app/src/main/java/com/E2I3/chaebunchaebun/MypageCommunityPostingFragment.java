package com.E2I3.chaebunchaebun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageCommunityPostingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageCommunityPostingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mypagePostingList;
    private ArrayList<MypageCommunityListItems> communityListItems;
    private MypageCommunityListAdapter communityListAdapter;
    private LinearLayoutManager mLayoutManager;

    TextView mypagePostingNolist;
    String state = "0";
    String platform = "0";
    String userId = null;
    boolean isMyPage = true;

    private TextView toastText;
    private Toast toast;

    public MypageCommunityPostingFragment() {
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
    public static MypageCommunityPostingFragment newInstance(String param1, String param2) {
        MypageCommunityPostingFragment fragment = new MypageCommunityPostingFragment();
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
        communityListItems = new ArrayList<MypageCommunityListItems>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mypagePosting = inflater.inflate(R.layout.fragment_mypage_community_myposting, container, false);

        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) mypagePosting.findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        mypagePostingList = mypagePosting.findViewById(R.id.mypage_posting_list);
        mypagePostingNolist = mypagePosting.findViewById(R.id.mypage_posting_nolist);

        if(communityListItems.isEmpty()) {
            mypagePostingNolist.setVisibility(View.VISIBLE);
            mypagePostingList.setVisibility(View.GONE);
        }
        else{
            mypagePostingNolist.setVisibility(View.GONE);
            mypagePostingList.setVisibility(View.VISIBLE);

            mLayoutManager = new LinearLayoutManager(getContext());
            mypagePostingList.setLayoutManager(mLayoutManager);

            communityListAdapter = new MypageCommunityListAdapter(communityListItems);
            communityListAdapter.setOnItemClickListener(new MypageCommunityListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    String postId = String.valueOf(communityListAdapter.getItem(pos).getPostId());
                    isMyPage = true;
                    Bundle articleBundle = new Bundle();
                    articleBundle.putString("userId", userId);
                    articleBundle.putString("postId", postId);
                    articleBundle.putBoolean("isMyPage", isMyPage);
                    FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    articleTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

                    ArticleEtcFragment articleEtcFragment = new ArticleEtcFragment();
                    articleEtcFragment.setArguments(articleBundle);
                    articleTransaction.replace(R.id.mypage_posting_frame, articleEtcFragment);
                    articleTransaction.addToBackStack(null);
                    articleTransaction.commit();
                }
            });

            communityListAdapter.setModalClickListener(new MypageCommunityListAdapter.OnModalClickListener() {
                @Override
                public void OnModlaClick(View view, int pos) {
                    String postId = String.valueOf(communityListAdapter.getItem(pos).getPostId());
                        Bundle args = new Bundle();
                        args.putString("userId", userId);
                        args.putString("postId", postId);
                        MyBottomSheetDialog myBottomSheetDialog = MyBottomSheetDialog.getInstance();
                        myBottomSheetDialog.setArguments(args);
                        myBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                }
            });

            mypagePostingList.setAdapter(communityListAdapter);
        }
        return mypagePosting;
    }

    public void getMyPostingList(String state){
        communityListItems.clear();
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("community/post/" + userId).execute().get();
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
                int postId = subJsonObject.getInt("postId");
                int userId = Integer.parseInt(this.userId);
                String content = subJsonObject.getString("contents");
                int like_count = subJsonObject.getInt("like_count");
                int comment_count = subJsonObject.getInt("comm_count");

                communityListItems.add(new MypageCommunityListItems(postId, userId, content, like_count, comment_count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}