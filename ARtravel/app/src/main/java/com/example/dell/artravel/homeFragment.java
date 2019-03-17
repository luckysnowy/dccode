package com.example.dell.artravel;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class homeFragment extends Fragment {
    static homeFragment newInstance(String s){
        homeFragment h = new homeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("usname",s);
        h.setArguments(bundle);
        return h;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadioButton btnpic=getActivity().findViewById(R.id.rb_tp);
        RadioButton btnarticle=getActivity().findViewById(R.id.rb_wz);
        RadioButton btnlist=getActivity().findViewById(R.id.rb_rj);
        TextView btnbooking=getActivity().findViewById(R.id.booking);
        Button btnupload=getActivity().findViewById( R.id.upload );

        btnpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ImageActivity.class);
                startActivity(intent);
            }
        });

        btnbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HeaderActivity.class);
                startActivity(intent);
            }
        });

        btnarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ScrollviewActivity.class);
                startActivity(intent);
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TextView us;
                //us=findViewById
                Bundle bundle = getArguments();
                String data = bundle.getString("usname");
                if(data != null){
                    Intent intent = new Intent(getActivity(),diary.class);
                    intent.putExtra("name",data);
                    startActivity(intent);
                }

            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),diaryList.class);
                //Intent intent = new Intent(getActivity(),dActicity.class);
                startActivity(intent);
            }
        });


    }

}
