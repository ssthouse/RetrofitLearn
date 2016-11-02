package com.ssthouse.retrofitlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserInfo;
    private EditText etUserName;
    Button btnGetUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvUserInfo = (TextView) findViewById(R.id.id_tv_info);
        etUserName = (EditText) findViewById(R.id.id_et_username);
        btnGetUserInfo = (Button) findViewById(R.id.id_btn_get_user_info);

        btnGetUserInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    return;
                }
                tvUserInfo.setText("正在获取用户信息");
                getUserInfo(username, new Callback<GithubUser>() {
                    @Override
                    public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                        if (!response.isSuccessful()) {
                            return;
                        }
                        GithubUser githubUser = response.body();
                        setTvUserInfo(githubUser);
                    }

                    @Override
                    public void onFailure(Call<GithubUser> call, Throwable t) {
                    }
                });
            }
        });
    }

    private void getUserInfo(String username, Callback<GithubUser> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GithubService githubService = retrofit.create(GithubService.class);
        Call<GithubUser> githubUserCall = githubService.getGithubUser(username);
        githubUserCall.enqueue(callback);
    }

    private void setTvUserInfo(GithubUser githubUser) {
        if (githubUser == null) {
            tvUserInfo.setText("获取失败: 请检查用户名是否存在");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(githubUser.getName())
                .append("\n")
                .append(githubUser.getEmail())
                .append("\n")
                .append(githubUser.getLocation());
        tvUserInfo.setText(sb.toString());
    }


}
