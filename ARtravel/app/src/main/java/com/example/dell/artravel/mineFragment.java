package com.example.dell.artravel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;
public class mineFragment extends Fragment {

    private Button lr;
    private ImageView img;
    private TextView t;

    static mineFragment newInstance(String s){
        mineFragment myFragment = new mineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("usname",s);
        myFragment.setArguments(bundle);
        return myFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine,container,false);
        Bundle bundle = getArguments();
        String data = bundle.getString("usname");
        t= (TextView) view.findViewById(R.id.username);
        if(data != null){
            t.setText(data);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lr = getActivity().findViewById(R.id.logout);
        lr.setOnClickListener(new View.OnClickListener() {
            @Override
           /** public void onClick(View v3) {
                startActivity(new Intent(getActivity(),loginActivity.class));
            }*/
            public void onClick(View v3) {
                startActivity(new Intent(getActivity(),loginActivity.class));
            }

        });
    }
}
