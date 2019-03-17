package com.example.dell.artravel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class diaryAdapter extends RecyclerView.Adapter<diaryAdapter.ViewHolder> {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diary_adapter );
    }*/

    private List<String> name1;
    private List<String> spot1;
    private List<String> mood1;
    private List<String> content1;

    private int rowLayout;
    private Context mContext;
    private View v;
    private diaryAdapter.OnItemClickListener onItemClickListener;

    public diaryAdapter(List<String> name1, List<String> spot1, List<String> mood1, List<String> content1,int rowLayout, Context context) {
        this.name1 = name1;
        this.spot1=spot1;
        this.mood1 = mood1;
        this.content1=content1;

        this.rowLayout = rowLayout;
        this.mContext = context;
    }


    // ① 定义点击回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        //void onItemLongClick(View view, int position);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(diaryAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public diaryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new diaryAdapter.ViewHolder(v);  //this is the major change here.
    }


    public int getItemCount() {
        return name1 == null ? 0 : name1.size();
    }


    public void onBindViewHolder(final diaryAdapter.ViewHolder viewHolder, int position) {

        viewHolder.name1.setText(name1.get(position));
        viewHolder.spot1.setText(spot1.get(position));
        viewHolder.mood1.setText(mood1.get(position));
        viewHolder.content1.setText(content1.get(position));


        /*viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView)v;
                Toast.makeText(mContext,tv.getText(), Toast.LENGTH_SHORT).show();
            }
        });*/

        //viewHolder.Pic.setImageResource(R.drawable.trainer1);


        //③ 对RecyclerView的每一个itemView设置点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(viewHolder.itemView, pos);
                }
            }
        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView Pic;
        public TextView name1,spot1,mood1,content1;

        public ViewHolder(View itemView) {
            super(itemView);
            //Pic= itemView.findViewById(R.id.videoPicture);

            name1 =itemView.findViewById(R.id.name1);
            spot1 =itemView.findViewById(R.id.spot1);
            mood1 =itemView.findViewById(R.id.mood1);
            content1=itemView.findViewById(R.id.content1);
        }

    }
}
