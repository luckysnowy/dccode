package com.example.dell.artravel;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.google.gson.JsonObject;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static java.lang.Boolean.TRUE;

public class loginActivity extends AppCompatActivity {

    private Button llg;
    private Button lrg;
    private Button lqg;
    EditText username, password;
    private static final String TAG = "loginActivity";
    private static final String APP_ID = "1108047550";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Bmob.initialize(this, "145acd0f031af11dc00f99b65fad68a3");
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID, loginActivity.this.getApplicationContext());
        username = (EditText) findViewById(R.id.usl);
        password = (EditText) findViewById(R.id.ps);
        Tb();
        //登录按钮
        llg = (Button) findViewById(R.id.dl);

        llg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (checkEdit() == true) {//判断输入是否不为空
                    if (isConnectInternet() == true) {//联网状态
                        //User user = new User();
                       // user.setUsername(username.getText().toString());
                        //user.setPassword(password.getText().toString());
                        islogin(username.getText().toString(),password.getText().toString());
                    } else {//非联网状态，本地数据库登录

                        //Intent intent = new Intent(loginActivity.this, MainActivity.class);
                        //startActivity(intent);
                        UserService userService = new UserService(loginActivity.this);

                        boolean flag = userService.Login(username.getText().toString(), password.getText().toString());
                        if (flag) {
                            Toast.makeText(loginActivity.this, username.getText().toString() + "登录成功！",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            intent.putExtra("name", username.getText().toString());//将用户名从loginActicity传给MainActivity
                            startActivity(intent);
                        } else {
                            Toast.makeText(loginActivity.this, "登录失败！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
        //QQ登录按钮
        lqg = (Button) findViewById(R.id.ql);
        lqg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectInternet() == true) {//联网状态
                    mIUiListener = new BaseUiListener();
                    //all表示获取所有权限
                    mTencent.login(loginActivity.this, "all", mIUiListener);

                } else {
                    Toast.makeText(loginActivity.this, "网络连接不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //新用户按钮
        lrg = (Button) findViewById(R.id.zcj);
        lrg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent = new Intent(loginActivity.this, regActivity.class);
                startActivity(intent);
            }
        });

    }

    private long exitTime = 0;


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(loginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            // finish();
            moveTaskToBack(TRUE);
        }

    }

    public boolean isConnectInternet() {

        ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错

            return networkInfo.isAvailable();


        }
        return false;
    }

    //检查登录信息
    public boolean checkEdit() {
        if (username.getText().toString().equals("")) {
            Toast.makeText(loginActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().equals("")) {
            Toast.makeText(loginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(loginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        //是一个json串response.tostring，直接使用gson解析就好
                        Log.e(TAG, "登录成功" + response.toString());
                        //登录成功后进行Gson解析即可获得你需要的QQ头像和昵称
                        // Nickname  昵称
                        String name1 = null;
                        try {
                            JSONObject jo = (JSONObject) response;
                            name1 = jo.getString("nickname");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(loginActivity.this, name1 + "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
                        intent.putExtra("name", name1);//将用户名从loginActicity传给MainActivity
                        startActivity(intent);
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onError(UiError uiError) {
            Toast.makeText(loginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(loginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //同步数据库
    public void Tb() {
        if (isConnectInternet() == true) {
            final UserService userService = new UserService(loginActivity.this);
            userService.DeleteData();
            BmobQuery<User> bq = new BmobQuery<User>();
            bq.findObjects(new FindListener<User>() {
                               @Override
                               public void done(List<User> list, BmobException e) {
                                   for (User us : list) {
                                       SLUser suse = new SLUser();
                                       suse.setUsername(us.getUsername());
                                       suse.setPassword(us.getPassword());
                                       userService.Register(suse);
                                   }
                               }
                           }
            );
            Toast.makeText(loginActivity.this, "数据库同步成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(loginActivity.this, "网络未连接！", Toast.LENGTH_SHORT).show();
        }
    }

    public void islogin(final String usname, final String pas) {
        BmobQuery<User> bq = new BmobQuery<User>();
        bq.addWhereEqualTo("username", usname);
        bq.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null) {
                    if(usname.equals(object.get(0).getUsername())&&pas.equals(object.get(0).getPassword())){//可以登录
                        Toast.makeText(loginActivity.this, usname + "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
                        intent.putExtra("name", username.getText().toString());//将用户名从loginActicity传给MainActivity
                        startActivity(intent);
                    }else{//用户名与密码不匹配
                        Toast.makeText(loginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }else{//未知错误
                    Toast.makeText(loginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
