package cn.gongyuhua.gyh.tochina;

/**
 * Created by ruanyulin on 17-8-15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by ruanyulin on 17-8-15.
 */

public class RecyclerAdapter_second extends RecyclerView.Adapter<RecyclerAdapter_second.MyHolder_second>{


    private Context context;
    List<String> ls;
    public RecyclerAdapter_second(Context context,List<String> ls){
        this.context=context;
        this.ls = ls;
    }
    @Override
    public MyHolder_second onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder_second holder = new MyHolder_second((LayoutInflater.from(context).inflate(R.layout.itementerance,parent,false)));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder_second holder, int position) {

    }


    @Override
    public int getItemCount() {
        return ls.size();
    }

    class MyHolder_second extends RecyclerView.ViewHolder{

        TextView textView = null;
        public MyHolder_second(View itemView) {
            super(itemView);
            //textView = (TextView) itemView.findViewById(R.id.itemtext);
        }
    }
}

