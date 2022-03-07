package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.net.Uri;
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

    ArrayList<Integer> deadlinePostId;
    ArrayList<String> deadlineTitle;
    ArrayList<String> deadlineNickname;

    View homeView;
    ActionBar.Tab tabNew, tabSoon, tabLast, tabMy;
    ViewGroup viewGroup;
    ViewPager vp;
    TabLayout tabLayout;
    LinearLayout searchView, homeLocation, homeMypage;
    ImageView iconLike, iconNotice;
    TextView homeLocationText, homeNickname;
    ImageButton writing, btnOnion, btnGarlic, btnGreenOnion, btnCarrot, btnMushroom,
        btnCabbage, btnRadish, btnPotato, btnSweetPotato, btnOther,
        linkAsk, linkService, linkPersonal;
    int recyclerPosition = -1;
    String[] address = {"",};
    String userId = null;
    String nickname = "";
    int locationCode = 0;
    int isNew = 0;

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

        deadlinePostId = new ArrayList<Integer>();
        deadlineTitle = new ArrayList<String>();
        deadlineNickname = new ArrayList<String>();
        getHome();
        getDeadlineList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);

        //mRecyclerView = (RecyclerView) homeView.findViewById(R.id.recycler_view);
        vp = homeView.findViewById(R.id.view_pager);
        tabLayout = homeView.findViewById(R.id.tab_layout);
        searchView = homeView.findViewById(R.id.view_search);
        iconLike = homeView.findViewById(R.id.ic_like);
        homeLocationText = homeView.findViewById(R.id.home_location_text);
        homeLocation = homeView.findViewById(R.id.home_location);
        writing = homeView.findViewById(R.id.btn_start);
        iconNotice = homeView.findViewById(R.id.ic_notice);
        homeMypage = homeView.findViewById(R.id.btn_mypage);
        homeNickname = homeView.findViewById(R.id.tv_nickname);

        btnOnion = homeView.findViewById(R.id.btn_home_onion);
        btnGarlic = homeView.findViewById(R.id.btn_home_garlic);
        btnGreenOnion = homeView.findViewById(R.id.btn_home_greenonion);
        btnCarrot = homeView.findViewById(R.id.btn_home_carrot);
        btnMushroom = homeView.findViewById(R.id.btn_home_mushroom);
        btnCabbage = homeView.findViewById(R.id.btn_home_cabbage);
        btnRadish = homeView.findViewById(R.id.btn_home_radish);
        btnPotato = homeView.findViewById(R.id.btn_home_potato);
        btnSweetPotato = homeView.findViewById(R.id.btn_home_sweetpotato);
        btnOther = homeView.findViewById(R.id.btn_home_other);

        linkAsk = homeView.findViewById(R.id.link_home_ask);
        linkService = homeView.findViewById(R.id.link_home_service);
        linkPersonal = homeView.findViewById(R.id.link_home_personal);

        homeLocationText.setText(address[address.length - 1]);
        homeNickname.setText(nickname);

        if(isNew == 0) {
            iconNotice.setImageResource(R.drawable.home_btn_notification_2);
        } else {
            iconNotice.setImageResource(R.drawable.home_btn_notification);
        }

        iconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MypageMyHeartActivity.class);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
            }
        });

        iconNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putString("userId", userId);
                FragmentTransaction noticeTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeNoticeFragment homeNoticeFragment = new HomeNoticeFragment();
                homeNoticeFragment.setArguments(categoryBundle);
                noticeTransaction.replace(R.id.bottom_frame, homeNoticeFragment);
                noticeTransaction.addToBackStack(null);
                noticeTransaction.commit();
            }
        });

        homeMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MypageMypostingActivity.class);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        btnOnion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 0);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnGarlic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 1);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnGreenOnion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 2);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnCarrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 3);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnMushroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 4);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnCabbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 5);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnRadish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 6);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnPotato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 7);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnSweetPotato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 8);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle categoryBundle = new Bundle();
                categoryBundle.putInt("vegetable", 9);
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                categoryTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

//                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });

        /*itemList = new ArrayList<MainRecyclerData>();

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
                categoryBundle.putString("userId", userId);
                FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(categoryBundle);
                categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
                categoryTransaction.addToBackStack(null);
                categoryTransaction.commit();
            }
        });*/

        MainVPAdapter adapter = new MainVPAdapter(getChildFragmentManager(), String.valueOf(locationCode), userId);
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        if(!(deadlinePostId.isEmpty())){
            Bundle args = new Bundle();
            args.putString("userId", userId);
            args.putString("title", deadlineTitle.get(0));
            args.putString("nickname", deadlineNickname.get(0));
            args.putInt("postId", deadlinePostId.get(0));
            QuestionCompleteDialogFragment questionCompleteDialogFragment = QuestionCompleteDialogFragment.getInstance();
            questionCompleteDialogFragment.setArguments(args);
            questionCompleteDialogFragment.show(getChildFragmentManager(), "IsComplete");
        }

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("locationCode", locationCode);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

      writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("userId", userId);
                WarningDialogFragment e = WarningDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), WarningDialogFragment.TAG_EVENT_DIALOG);
            }
        });

      homeLocation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Bundle args = new Bundle();
              args.putString("userId", userId);
              args.putString("location", address[address.length - 1]);
              LocationDialogFragment e = LocationDialogFragment.getInstance();
              e.setArguments(args);
              e.show(getChildFragmentManager(), LocationDialogFragment.TAG_EVENT_DIALOG);
          }
      });

      linkAsk.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent email = new Intent(Intent.ACTION_SEND);
              email.setType("plain/text");
              String[] address = {"dragoncat84@naver.com"};
              email.putExtra(Intent.EXTRA_EMAIL, address);
              email.putExtra(Intent.EXTRA_SUBJECT, "[채분채분 문의하기]");
              email.putExtra(Intent.EXTRA_TEXT, "문의할 내용을 적어주세요!");
              startActivity(email);
              getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
          }
      });

      linkService.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://irradiated-mapusaurus-27a.notion.site/34a2eb86c548473b8ea1c9aaa5a72217"));
              startActivity(intent);
              getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
          }
      });

      linkPersonal.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://irradiated-mapusaurus-27a.notion.site/7535a823135d437da69575e15cc49467"));
              startActivity(intent);
              getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
          }
      });

      /*iconNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               QuestionCompleteDialogFragment e = QuestionCompleteDialogFragment.getInstance();
               e.show(getChildFragmentManager(), QuestionCompleteDialogFragment.TAG_EVENT_DIALOG);
           }
       });*/
      
        // Inflate the layout for this fragment
        return homeView;
    }

    /*public void getRecyclerPosition(int pos) {
        this.recyclerPosition = pos;
    }*/

    public void getHome(){
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("home/" + userId).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new   JSONObject(resultText);
            String data = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String fullAddress = subJsonObject.getString("full_address");
                nickname = subJsonObject.getString("nickname");
                int userId = subJsonObject.getInt("user_id");
                locationCode = subJsonObject.getInt("address_id");
                isNew = subJsonObject.getInt("isNew");

                address = fullAddress.split(" ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getDeadlineList() {
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("home/mydeadline/" + userId).execute().get();
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
                deadlinePostId.add(subJsonObject.getInt("post_id"));
                deadlineTitle.add(subJsonObject.getString("post_title"));
                deadlineNickname.add(subJsonObject.getString("title"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}