package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.view.View;
import android.widget.Button;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import static com.kakao.util.helper.Utility.getPackageInfo;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "사용자";
    ImageView signIn, signUp, loginV1, set_invaild;
    protected SessionCallback sessionCallback = new SessionCallback();
    Session session;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_start);

        loginV1 = (ImageButton) findViewById(R.id.loginV1);
        signIn = (ImageView) findViewById(R.id.sign_in);
        signUp = (ImageView) findViewById(R.id.sign_up);
        set_invaild = (ImageView) findViewById(R.id.set_invaild);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        Intent intent = new Intent(getBaseContext(), NavigationActivity.class);
/*  //소셜로그인 토큰으로 처리하는 예제 (실패 / 오류: 셧다운, lateinit property hosts has not been initialized
        loginV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this,(oAuthToken, error) ->{
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error);
                    }
                    else if (oAuthToken != null) {
                        Log.i(TAG, "로그인 성공 ${token.accessToken}");

                        // 사용자 정보 요청
                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", meError);
                            } else {
                                System.out.println("로그인 됐다");
                                Log.i(TAG, user.toString());

                                Account user1 = user.getKakaoAccount();
                                System.out.println("유저 어카운트"+user1);
                            }
                            return null;
                        });
                    }
                    return null;
                }
                );
            }
        });

        set_invaild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(error->{
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error);
                    }
                    else {
                        Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨");
                    }
                    return null;
                });

            }
        });
*/

        loginV1.setOnClickListener(v -> {
            if (Session.getCurrentSession().checkAndImplicitOpen()) {
                Log.d(TAG, "onClick: 로그인 세션살아있음");
                // 카카오 로그인 시도 (창이 안뜬다.)
                sessionCallback.requestMe();
                startActivity(intent);
            } else {
                Log.d(TAG, "onClick: 로그인 세션끝남");
                // 카카오 로그인 시도 (창이 뜬다.)
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetNicknameActivity.class);
                startActivity(intent);
            }
        });

        set_invaild.setOnClickListener(v -> {
            Log.d(TAG, "onCreate:click ");
            UserManagement.getInstance()
                    .requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            super.onSessionClosed(errorResult);
                            Log.d(TAG, "onSessionClosed: "+errorResult.getErrorMessage());

                        }
                        @Override
                        public void onCompleteLogout() {
                            if (sessionCallback != null) {
                                Session.getCurrentSession().removeCallback(sessionCallback);
                            }
                            Log.d(TAG, "onCompleteLogout:logout ");
                        }
                    });
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getKeyHash(){
        try{
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            if(packageInfo == null) return null;
            for(Signature signature: packageInfo.signatures){
                try{
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                }catch (NoSuchAlgorithmException e){
                    Log.w("getKeyHash", "Unable to get MessageDigest. signature="+signature, e);
                }
            }
        }catch(PackageManager.NameNotFoundException e){
            Log.w("getPackageInfo", "Unable to getPackageInfo");
        }
        return null;
    }


}