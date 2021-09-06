package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

public class SessionCallback extends AppCompatActivity implements ISessionCallback {
    String kakao_email = "";
    String profile_img = "";
    String user_id = "";
    String kakao_gender = "";
    String kakao_age_range = "";
    String email, gender, ageRange;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestMe();
    }

    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
//        requestMe();
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    // 사용자 정보 요청
    public synchronized void requestMe() {
        UserManagement.getInstance()
                .me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                        user_id = String.valueOf(result.getId());
                        UserAccount kakaoAccount = result.getKakaoAccount();
                        if (kakaoAccount != null) {
                            // 이메일
                            if(kakaoAccount.getEmail() == null){ email = "premium"; }
                            else { email = kakaoAccount.getEmail(); }

                            if(kakaoAccount.getGender() == null){ gender = "etc"; }
                            else { gender = kakaoAccount.getGender().getValue(); }

                            if(kakaoAccount.getAgeRange() == null){ ageRange = "etc"; }
                            else { ageRange = kakaoAccount.getAgeRange().getValue(); }

                            kakao_email = email;
                            kakao_gender = gender;
                            kakao_age_range = ageRange;

                            Profile profile = kakaoAccount.getProfile();
                            if (profile ==null){
                                Log.d("KAKAO_API", "onSuccess:profile null ");
                            }else{
                                Log.d("KAKAO_API", "onSuccess:getProfileImageUrl "+profile.getProfileImageUrl());
                                Log.d("KAKAO_API", "onSuccess:getNickname "+profile.getNickname());
                                profile_img = profile.getProfileImageUrl();

                                setData(user_id, kakao_email, profile_img, kakao_gender, kakao_age_range);
                            }
                            if (email != null) {
                                Log.d("KAKAO_API", "onSuccess:email "+email);
                            }

                            // 프로필
                            Profile _profile = kakaoAccount.getProfile();

                            if (_profile != null) {

                                Log.d("KAKAO_API", "nickname: " + _profile.getNickname());
                                Log.d("KAKAO_API", "profile image: " + _profile.getProfileImageUrl());
                                Log.d("KAKAO_API", "thumbnail image: " + _profile.getThumbnailImageUrl());

                            } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                // 동의 요청 후 프로필 정보 획득 가능

                            } else {
                                // 프로필 획득 불가
                            }

                        }else{
                            Log.i("KAKAO_API", "onSuccess: kakaoAccount null");
                        }
                    }
                });
    }
    public void setData(String user_id, String kakao_email, String profile_img, String gender, String age_range) {
        Intent intent = new Intent(getApplicationContext(), SetNicknameActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("kakao_email", kakao_email);
        intent.putExtra("profile_img", profile_img);
        intent.putExtra("sex", gender);
        intent.putExtra("age_range", age_range);
        startActivity(intent);
    }
}