package com.example.dell.artravel;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.dyhdyh.compat.mmrc.MediaMetadataRetrieverCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link video.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link video#newInstance} factory method to
 * create an instance of this fragment.
 */
public class video extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private VideoView video;
    private Button buttonDownload;
    private List<String> videos;
    private int num=6;

    private OnFragmentInteractionListener mListener;

    public video() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment video.
     */
    // TODO: Rename and change types and number of parameters
    public static video newInstance(String param1, String param2) {
        video fragment = new video();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate( R.layout.fragment_video, container, false);


        videos=new ArrayList<String>(  );
        videos.add( "http://f.us.sinaimg.cn/003A3DQClx07nbwXOlkc01040201kjI30k010.mp4?label=mp4_hd&template=720x404.28&Expires=1545776862&ssig=qRtmaQ7aJv&KID=unistore,video" );
        videos.add( "http://us.sinaimg.cn/0028KznYjx0756fby9Yj010401009aZI0k01.mp4?label=mp4_hd&Expires=1545786721&ssig=tY7svMujfE&KID=unistore,video" );
        videos.add("http://f.us.sinaimg.cn/0042OGXcgx07mXAM9RNB010402001FCO0k010.mp4?label=mp4_hd&template=852x480.28&Expires=1545786799&ssig=kCKOEGr3V2&KID=unistore,video");
        videos.add("http://f.us.sinaimg.cn/000BqOkJlx07juftOEzK010402001RG30k010.mp4?label=mp4_hd&template=480x480.28&Expires=1545786998&ssig=MlIKPyScL2&KID=unistore,video");
        videos.add( "http://f.us.sinaimg.cn/002CoBAzlx07gHmiaOOs01040200Zxb30k01.mp4?label=mp4_hd&template=28&Expires=1545787098&ssig=hE%2Fk7GkPfF&KID=unistore,video" );
        videos.add( "http://us.sinaimg.cn/002tSf8tjx07ac3DncI701040100DRKs0k01.mp4?label=mp4_hd&Expires=1545787138&ssig=0fDPvjHFaN&KID=unistore,video" );

        //视频播放
        video = view.findViewById(R.id.videoView2);
        //initView(videos.get( 0 ));




        //下载视频
        buttonDownload=view.findViewById(R.id.download);
        buttonDownload.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
                // 创建文件夹，在存储卡下
                String dirName = Environment.getExternalStorageDirectory().getPath() + "/Download/myVideo";
                File file = new File( dirName );
                // 文件夹不存在时创建
                if (!file.exists()) {
                    file.mkdir();
                }

                // 下载后的文件名
                int i = path.lastIndexOf( "/" ); // 取的最后一个斜杠后的字符串为名
                final String fileName = dirName + path.substring( i, path.length() );
                File file1 = new File( fileName );
                if (file1.exists()) {
                    // 如果已经存在, 就不下载了, 去播放
                    Toast.makeText( getActivity(), "已下载", Toast.LENGTH_SHORT ).show();
                } else {
                    new Thread( new Runnable() {
                        @Override
                        public void run() {
                            downloadIt( path, fileName );
                        }
                    } ).start();
                }
            }
        });

        tv();
        videoOnlineList();
        //videoDownloadList();

        return view;
    }

    //播放视频
    private void initView(String url) {
        //针对于本地视频
        //String path = Environment.getExternalStorageDirectory().getPath()+"/"+"https://www.bilibili.com/video/av20997550?spm_id_from=333.334.b_63686965665f7265636f6d6d656e64.1";//获取视频路径

        //针对于某个视频
        String path=url;
        Uri uri = Uri.parse(path);//将路径转换成uri
        video.stopPlayback();
        video.setVideoURI(uri);//为视频播放器设置视频路径
        video.setMediaController(new MediaController(getActivity()));//显示控制栏
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video.start();//开始播放视频
            }
        });

        //针对于某个台
        /*String url = "http://ivi.bupt.edu.cn/hls/cctv15.m3u8";
        video.setVideoPath( url );
        video.requestFocus();
        video.start();
        video.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);*/
    }


    // 下载具体操作
    private void downloadIt(String path,String filename) {
        try {
            URL url = new URL(path);
            // 打开连接
            URLConnection conn = url.openConnection();
            // 打开输入流
            InputStream is = conn.getInputStream();
            // 创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(filename);
            // 写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完成后关闭流
            Log.e(TAG, "download-finish");
            os.close();
            is.close();
            //            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "e.getMessage() --- " + e.getMessage());
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    void tv()
    {
        RecyclerView mRecyclerView;

        mRecyclerView =view.findViewById(R.id.tv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /*List<String> pic = new ArrayList();
        pic.add( "R.drawable.doe5" );*/

        List<String> name = new ArrayList();
        name.add("旅游卫视");

        List<String> item = new ArrayList();
        item.add("2018年");

        List<String> duration=new ArrayList(  );
        duration.add("当前播放");


        //添加适配器，并监听每一个项目
        videoAdapter mAdapter;

        mAdapter = new videoAdapter(name,item,duration, R.layout.activity_video_online,this.getActivity());

        mAdapter.setOnItemClickListener(new videoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                if(position==0)
                {
                    String url = "http://223.110.243.155:80/PLTV/3/224/3221225557/index.m3u8";
                    video.setVideoPath( url );
                    video.requestFocus();
                    video.start();
                    //video.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        //分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL));
    }


    void videoOnlineList()
    {
        RecyclerView mRecyclerView;

        mRecyclerView =view.findViewById(R.id.videoOnline);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        /*List<Bitmap> pic=new ArrayList(  );
        for(int i=0;i<num;i++)
        {
            pic.add(gainPicture(videos.get(i)));
        }*/


        /*List<String> name = new ArrayList();
        name.add(gainTitle( videos.get(0) ));
        name.add(gainTitle( videos.get(1) ));
        name.add(gainTitle( videos.get(2) ));
        name.add(gainTitle( videos.get(3) ));
        name.add(gainTitle( videos.get(4) ));
        name.add(gainTitle( videos.get(5) ));

        List<String> author = new ArrayList();
        author.add(gainAuthor( videos.get(0) ));
        author.add(gainAuthor( videos.get(1) ));
        author.add(gainAuthor( videos.get(2) ));
        author.add(gainAuthor( videos.get(3) ));
        author.add(gainAuthor( videos.get(4) ));
        author.add(gainAuthor( videos.get(5) ));

        List<String> duration=new ArrayList(  );
        duration.add(gainDuration( videos.get(0) ));
        duration.add(gainDuration( videos.get(1) ));
        duration.add(gainDuration( videos.get(2) ));
        duration.add(gainDuration( videos.get(3) ));
        duration.add(gainDuration( videos.get(4) ));
        duration.add(gainDuration( videos.get(5) ));*/

        /*List<String> pic=new ArrayList(  );
        pic.add( "R.drawable.doe1" );
        pic.add( "R.drawable.doe2" );
        pic.add( "R.drawable.doe3" );
        pic.add( "R.drawable.doe4" );
        pic.add( "R.drawable.doe5" );
        pic.add( "R.drawable.doe1" );*/

        List<String> name = new ArrayList();
        name.add("片名:旅游新闻");
        name.add("片名;去哪儿旅行");
        name.add("片名:古塔");
        name.add("片名:带着微博去旅行");
        name.add("片名:行走");
        name.add("片名:远行文化");

        List<String> item = new ArrayList();
        item.add("来源:优酷");
        item.add("来源:腾讯视频");
        item.add("来源:优酷");
        item.add("来源:微博");
        item.add("来源:央视网");
        item.add("来源:腾讯视频");

        List<String> duration=new ArrayList(  );
        duration.add("时长:66342ms");
        duration.add("时长:23122ms");
        duration.add("时长:7122ms");
        duration.add("时长:23123ms");
        duration.add("时长:9831ms");
        duration.add("时长:1231ms");



        //添加适配器，并监听每一个项目
        videoAdapter mAdapter;

        mAdapter = new videoAdapter(name,item,duration, R.layout.activity_video_online,this.getActivity());

        mAdapter.setOnItemClickListener(new videoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                if(position==0)
                {
                    initView( videos.get(0) );
                }
                else if(position==1)
                {
                    initView( videos.get(1) );
                }
                else if(position==2)
                {
                    initView( videos.get(2) );
                }
                else if(position==3)
                {
                    initView( videos.get(3) );
                }
                else if(position==4)
                {
                    initView( videos.get(4) );
                }
                else if(position==5)
                {
                    initView( videos.get(5) );
                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        //分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL));
    }


    /*void videoDownloadList()
    {
        RecyclerView mRecyclerView;

        mRecyclerView =view.findViewById(R.id.videoDownload);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        List<String> name = new ArrayList();
        name.add("Trainer:Lionel Messi");
        name.add("Trainer;Miroslav Klose");
        name.add("Trainer:Roque Santa Cruz");
        name.add("Trainer:Eidur Gudjohnsen");
        name.add("Trainer:Alberto Gilardino");
        name.add("Trainer:David Beckham");

        List<String> item = new ArrayList();
        item.add("Sport:yoga");
        item.add("Sport:spinning");
        item.add("Sport:aerobics");
        item.add("Sport:boxing");
        item.add("Sport:fitball");
        item.add("Sport:karate");


        //添加适配器，并监听每一个项目
        videoAdapter mAdapter;

        mAdapter = new videoAdapter(name,item,item, R.layout.activity_video_online,this.getActivity());

        mAdapter.setOnItemClickListener(new videoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                if(position==0)
                {

                }
                else if(position==1)
                {

                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        //分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL));
    }*/


    public String gainTitle(String url){
            //自动 - 推荐
            MediaMetadataRetrieverCompat mmrc = MediaMetadataRetrieverCompat.create();
            mmrc.setDataSource( url,new HashMap<String, String>() );
            String title = mmrc.extractMetadata(MediaMetadataRetrieverCompat.METADATA_KEY_TITLE);
            /*String album = mmrc.extractMetadata(MediaMetadataRetrieverCompat.METADATA_KEY_ALBUM);
            String albumArtist = mmrc.extractMetadata(MediaMetadataRetrieverCompat.METADATA_KEY_ALBUMARTIST);
            String author = mmrc.extractMetadata(MediaMetadataRetrieverCompat.METADATA_KEY_AUTHOR);
            String duration = mmrc.extractMetadata(MediaMetadataRetrieverCompat.METADATA_KEY_DURATION);
            //Bitmap bitmap=mmrc.getFrameAtTime();
            //Bitmap bitmap=mmrc.getFrameAtTime(0, MediaMetadataRetriever.OPTION_NEXT_SYNC);
            //String time=mmrc.extractMetadata( MediaMetadataRetriever.METADATA_KEY_DURATION);

            TextView t=view.findViewById(R.id.content);
            t.setText( "title:"+title+"  author"+author );
            /*ImageSpan imgSpan = new ImageSpan(getActivity(), bitmap);
            SpannableString spanString = new SpannableString("icon");
            spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.setText(spanString);*/
            return "片名："+title;
    }


    public String gainAuthor(String url){
        MediaMetadataRetrieverCompat mmrc = MediaMetadataRetrieverCompat.create();
        mmrc.setDataSource( url,new HashMap<String, String>() );
        String author = mmrc.extractMetadata(MediaMetadataRetrieverCompat.METADATA_KEY_AUTHOR);
        return "来源："+author;
    }

    public String gainDuration(String url){
        MediaMetadataRetrieverCompat mmrc = MediaMetadataRetrieverCompat.create();
        mmrc.setDataSource( url,new HashMap<String, String>() );
        String duration = mmrc.extractMetadata(MediaMetadataRetrieverCompat.METADATA_KEY_DURATION);
        return "时长："+duration;
    }

    public Bitmap gainPicture(String url){
        MediaMetadataRetrieverCompat mmrc = MediaMetadataRetrieverCompat.create();
        mmrc.setDataSource( url,new HashMap<String, String>() );
        Bitmap bitmap=mmrc.getFrameAtTime(0, MediaMetadataRetriever.OPTION_NEXT_SYNC);
        return bitmap;
    }

}
