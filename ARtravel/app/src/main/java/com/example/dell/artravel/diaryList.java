package com.example.dell.artravel;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class diaryList extends AppCompatActivity {

    public List<String> name1,spot1,mood1,content1;
    //private SwipeRefreshLayout swipeRefreshLayout;
    diaryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diary_list );

        Bmob.initialize(this, "145acd0f031af11dc00f99b65fad68a3");
        name1 = new ArrayList();
        spot1 = new ArrayList();
        mood1 = new ArrayList();
        content1 = new ArrayList();

        BmobQuery<diary_db> bq = new BmobQuery<diary_db>();
        bq.findObjects(new FindListener<diary_db>() {
            @Override
            public void done(List<diary_db> list, BmobException e) {
                for (diary_db us : list) {
                    name1.add(us.getUsername());
                    spot1.add( us.getSpot() );
                    mood1.add( us.getMood() );
                    content1.add( us.getContent() );

                }
            }
        } );


        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr1);
        //swipeRefreshLayout.setOnRefreshListener(diaryList.this);


        RecyclerView mRecyclerView;

        mRecyclerView =findViewById(R.id.diaryList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mAdapter = new diaryAdapter(name1,spot1,mood1, content1,R.layout.activity_diary_adapter,diaryList.this);

        /*mAdapter.setOnItemClickListener(new diaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position==0)
                {
                    Intent intent=new Intent(getActivity(),Trainer_introduction.class);
                    startActivity(intent);
                }
                else if(position==1)
                {
                    Intent intent=new Intent(getActivity(),loginApp.class);
                    startActivity(intent);
                }

            }
        });*/

        mRecyclerView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();

        /*//整体监听
        mAdapter.setOnClickListener(new MyListAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(),"可以响应", Toast.LENGTH_SHORT).show();
            }
        });*/


        //分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

}
