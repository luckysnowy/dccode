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

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
public class regActivity extends AppCompatActivity {
    private Button z;
    private Button fd;
    EditText username, password, cpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        Bmob.initialize(this, "145acd0f031af11dc00f99b65fad68a3");
        password = (EditText) findViewById(R.id.ps1);
        cpassword = (EditText) findViewById(R.id.ps2);
        username = (EditText) findViewById(R.id.usr);
        z=(Button) findViewById(R.id.zc);
        //注册按钮
        z.setOnClickListener(new OnClickListener(){
            @Override
            public  void onClick(View v2){
                if(isConnectInternet()==true){//判断是否联网
                    //Toast.makeText(regActivity.this, "网络连接上了", Toast.LENGTH_SHORT).show();
                    if(checkEdit()==true) {//判断输入是否合法
                        //BmobUser user =new BmobUser();
                        User user=new User();
                        user.setUsername(username.getText().toString());
                        user.setPassword(password.getText().toString());

                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId,BmobException e) {
                                if(e==null){
                                    Toast.makeText(regActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(regActivity.this, "注册失败！可能用户名已存在！", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(regActivity.this, "网络连接不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //返回登录按钮
        fd=(Button) findViewById(R.id.fdl);

        fd.setOnClickListener(new OnClickListener(){
            @Override
            public  void onClick(View v2){
                Intent intent=new Intent(regActivity.this,loginActivity.class);
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
            Toast.makeText(regActivity.this, "再按一次返回登录界面", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }
    //检查注册信息
    public boolean checkEdit() {
        if (username.getText().toString().equals("")) {
            Toast.makeText(regActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().equals("")) {
            Toast.makeText(regActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cpassword.getText().toString().equals(password.getText().toString())) {
            Toast.makeText(regActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isConnectInternet() {

        ConnectivityManager conManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE );

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null ){ // 注意，这个判断一定要的哦，要不然会出错

            return networkInfo.isAvailable();


        }
        return false ;
    }

    /*public void islogin(final String usname, final String pas) {
        BmobQuery<User> bq = new BmobQuery<User>();
        bq.addWhereEqualTo("username", usname);
        bq.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null) {
                    if(usname.equals(object.get(0).getUsername())){//存在，不能注册
                        Toast.makeText(regActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    }else{//不存在，可以注册
                        Toast.makeText(regActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }else{//未知错误
                    Toast.makeText(regActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                }
            }
        });*

    }*/
}
