package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "user";

    private RecyclerView searchList;
    private ArrayList<SearchListItem> searchListItems;
    private SearchListAdapter searchListAdapter;
    private LinearLayoutManager sLayoutManager;

    EditText search_text;
    ListView search_list;
    ImageView search_back;
    TextView search_no_tv, search_suggest;
    ImageButton search_no_startbtn;
    int locationcode = 0;
    String userId = null;

    public SearchDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDetailFragment newInstance(String param1, String param2) {
        SearchDetailFragment fragment = new SearchDetailFragment();
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
        View searchPost = inflater.inflate(R.layout.fragment_search_detail, container, false);

        search_text = (EditText) searchPost.findViewById(R.id.search_text);
        search_suggest = (TextView) searchPost.findViewById(R.id.search_suggest);
        search_list = (ListView) searchPost.findViewById(R.id.jrv_search_list);
        search_back = (ImageView) searchPost.findViewById(R.id.search_back);
        search_no_tv = (TextView) searchPost.findViewById(R.id.search_no_tv);
        search_no_startbtn = (ImageButton) searchPost.findViewById(R.id.search_no_startbtn);
        searchList = (RecyclerView) searchPost.findViewById(R.id.main_search_recyclerview);

        search_no_tv.setVisibility(View.GONE);
        search_no_startbtn.setVisibility(View.GONE);

        searchListItems = new ArrayList<SearchListItem>();

        locationcode = getArguments().getInt("locationCode");
        userId = getArguments().getString("userId");

        //search_suggest.setVisibility(View.GONE);
        //search_list.setVisibility(View.GONE);

        ArrayList<String> main_list = new ArrayList<String>();
        main_list.add("콩나물");
        main_list.add("토마토");
        main_list.add("호박");
        main_list.add("오이");
        main_list.add("브로콜리");

        ArrayAdapter<String> mainAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_searchlist_main, R.id.searchlist_main_tv, main_list);
        search_list.setAdapter(mainAdapter);

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String recommend = main_list.get(position);
                search_text.setText(recommend);
                search_list.setVisibility(View.GONE);
                setSearchList(recommend, locationcode);
                if(searchListItems.isEmpty()){
                    search_no_tv.setVisibility(View.VISIBLE);
                    search_no_startbtn.setVisibility(View.VISIBLE);
                    search_suggest.setVisibility(View.GONE);
                    searchList.setVisibility(View.GONE);
                } else {
                    search_no_tv.setVisibility(View.GONE);
                    search_no_startbtn.setVisibility(View.GONE);
                    search_suggest.setVisibility(View.GONE);
                    searchList.setVisibility(View.VISIBLE);

                    sLayoutManager = new LinearLayoutManager(getContext());
                    searchList.setLayoutManager(sLayoutManager);
                    searchListAdapter = new SearchListAdapter(searchListItems);
                    searchList.setAdapter(searchListAdapter);
                    searchListAdapter.setOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int pos) {
                            String postId = String.valueOf(searchListAdapter.getItem(pos).getPostId());
                            int categoryId = searchListAdapter.getItem(pos).getCategoryId();
                            System.out.println("리스트클릭" + postId);
                            Bundle articleBundle = new Bundle();
                            articleBundle.putString("userId", userId);
                            articleBundle.putString("postId", postId);
                            articleBundle.putInt("categoryId", categoryId);
                            FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            ArticleFragment articleFragment = new ArticleFragment();
                            articleFragment.setArguments(articleBundle);
                            articleTransaction.replace(R.id.search_frame, articleFragment);
                            articleTransaction.addToBackStack(null);
                            articleTransaction.commit();
                        }
                    });
                }
            }
        });

        search_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    search_list.setVisibility(View.GONE);
                    String searchText = search_text.getText().toString();
                    setSearchList(searchText, locationcode);
                    if(searchListItems.isEmpty()){
                        search_no_tv.setVisibility(View.VISIBLE);
                        search_no_startbtn.setVisibility(View.VISIBLE);
                        search_suggest.setVisibility(View.GONE);
                        searchList.setVisibility(View.GONE);
                    } else {
                        search_no_tv.setVisibility(View.GONE);
                        search_no_startbtn.setVisibility(View.GONE);
                        search_suggest.setVisibility(View.GONE);
                        searchList.setVisibility(View.VISIBLE);
                        sLayoutManager = new LinearLayoutManager(getContext());
                        searchList.setLayoutManager(sLayoutManager);
                        searchListAdapter = new SearchListAdapter(searchListItems);
                        searchList.setAdapter(searchListAdapter);
                        searchListAdapter.setOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int pos) {
                                int postId = searchListAdapter.getItem(pos).getPostId();
                                int categoryId = searchListAdapter.getItem(pos).getCategoryId();
                                Bundle articleBundle = new Bundle();
                                articleBundle.putString("userId", userId);
                                articleBundle.putString("postId", String.valueOf(postId));
                                articleBundle.putInt("categoryId", categoryId);
                                FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                articleTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

                                ArticleFragment articleFragment = new ArticleFragment();
                                articleFragment.setArguments(articleBundle);
                                articleTransaction.replace(R.id.search_frame, articleFragment);
                                articleTransaction.addToBackStack(null);
                                articleTransaction.commit();
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        search_no_startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putInt("locationCode", locationcode);
                WarningDialogFragment e = WarningDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), WarningDialogFragment.TAG_EVENT_DIALOG);
            }
        });

        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        return searchPost;
    }

    public void setSearchList(String searchText, int locationcode) {
        PostTask searchLocationTask = new PostTask();
        searchListItems.clear();
        try {
            String response = searchLocationTask.execute("home/" + locationcode +"/" + searchText, searchText).get();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
//                String people = subJsonObject.getString("members");
                String people = subJsonObject.getString("post_addr");
                String price = subJsonObject.getString("per_price");
                int isAuth = subJsonObject.getInt("isAuth");
                int postId = subJsonObject.getInt("post_id");
                int categoryId = subJsonObject.getInt("category_id");
                String witten = subJsonObject.getString("witten_by");
                String content = subJsonObject.getString("contents");

                searchListItems.add(new SearchListItem(title, buyDate, people, price, isAuth, postId, categoryId, witten, content));
            }
        } catch(JSONException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}