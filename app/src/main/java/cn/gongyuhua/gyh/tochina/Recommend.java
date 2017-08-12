package cn.gongyuhua.gyh.tochina;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Recommend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recommend extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Recommend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Recommend.
     */
    // TODO: Rename and change types and number of parameters
    public static Recommend newInstance(String param1, String param2) {
        Recommend fragment = new Recommend();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =inflater.inflate(R.layout.recommend, container, false);
        Button bt=rootview.findViewById(R.id.button3);
        //Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Permanen.ttf");
        TextView recommend_attraction_text=rootview.findViewById(R.id.recommend_attraction_text);
        TextView recommend_for_you_text=rootview.findViewById(R.id.recommend_for_you_text);
        //recommend_attraction_text.setTypeface(typeFace);
        //recommend_for_you_text.setTypeface(typeFace);

        bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {//当click事件发生时会调用这个onclick方法
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                }
            });
        return rootview;
    }

}
