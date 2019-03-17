package com.example.dell.artravel;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class diary extends AppCompatActivity {
    EditText spot,mood,content;
    TextView userna;

    String spotReal;
    String moodReal;
    String username;
    String contentReal;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diary );
        Bmob.initialize(this, "145acd0f031af11dc00f99b65fad68a3");

        //地点图标
        TextView spot=findViewById(R.id.spot);
        Drawable drawable1=getResources().getDrawable(R.drawable.spot);
        drawable1.setBounds(0,0,5,5);//第一0是距左边距离，第二0是距上边距离，30、35分别是长宽
        spot.setCompoundDrawables(drawable1,null,null,null);
        //spot.setCompoundDrawablePadding(10);

        //心情图标
        TextView mood=findViewById(R.id.mood);
        Drawable drawable2=getResources().getDrawable(R.drawable.mood);
        drawable1.setBounds(0,0,5,5);//第一0是距左边距离，第二0是距上边距离，30、35分别是长宽
        mood.setCompoundDrawables(drawable2,null,null,null);
        //spot.setCompoundDrawablePadding(10);

        Button submit=findViewById( R.id.submit );
        /*submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                upload();
            }
        });*/

        upload();
    }

    public void upload(){

        /*userna=findViewById(R.id.username);
        username=userna.getText().toString();*/

        submit=findViewById( R.id.submit );



        //if(contentReal!=null){

            //Toast.makeText(diary.this,contentReal, Toast.LENGTH_SHORT).show();
            submit.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View v2){
                    if(isConnectInternet()==true){//判断是否联网
                        spot=(EditText) findViewById( R.id.enterSpot );
                        spotReal=spot.getText().toString();

                        mood=(EditText) findViewById( R.id.enterMood );
                        moodReal=mood.getText().toString();

                        content=(EditText) findViewById( R.id.diary );
                        contentReal=mood.getText().toString();
                        Intent intent=getIntent();
                        username=intent.getStringExtra("name");

                        diary_db db=new diary_db();

                        db.setSpot( spotReal );
                        db.setMood( moodReal );
                        db.setUsername( username );
                        db.setContent( contentReal );
                        db.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId,BmobException e) {
                                if(e==null){
                                    Toast.makeText(diary.this, "提交成功！", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(diary.this, "提交失败！", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else{
                        Toast.makeText(diary.this, "网络连接不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(diary.this, "网络连接不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    //}

    //判断联网
    public boolean isConnectInternet() {

        ConnectivityManager conManager=(ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE );

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null ){ // 注意，这个判断一定要的哦，要不然会出错
            return networkInfo.isAvailable();
        }
        return false ;
    }


}
