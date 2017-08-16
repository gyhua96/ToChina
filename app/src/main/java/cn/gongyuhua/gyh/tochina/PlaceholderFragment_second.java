package cn.gongyuhua.gyh.tochina;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruanyulin on 17-8-15.
 */

//Tab41\42
public class PlaceholderFragment_second extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private View item;
    private View item1;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter_second recyclerAdapter_second;
    private int flag = 0;
    private List<String> data;
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment_second() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment_second newInstance(int sectionNumber) {
        PlaceholderFragment_second fragment = new PlaceholderFragment_second();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                rootView = inflater.inflate(R.layout.recommond,container,false);
                item = rootView;
                flag = 1;
                break;
            case 2:
                rootView = inflater.inflate(R.layout.enterance,container,false);
                item1 = rootView;
                flag = 2;
                break;
        }
        return rootView;
    }

    //@SuppressLint("MissingSuperCall")
    @Override
    public void onActivityCreated(Bundle save){
        super.onActivityCreated(save);
        if (flag == 1){
            initData();
            recyclerView = (RecyclerView) item.findViewById(R.id.recyclerrecommond);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerAdapter = new RecyclerAdapter(getContext(),data);
            recyclerView.setAdapter(recyclerAdapter);
            //recyclerView.setHasFixedSize(false);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            swipeRefreshLayout =(SwipeRefreshLayout) item.findViewById(R.id.refresh);
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

                @Override
                public void onRefresh() {
                    refresh();
                }
            });
        }else {
            initData();
            recyclerView1 = (RecyclerView) item1.findViewById(R.id.recyclerenterance);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerAdapter_second = new RecyclerAdapter_second(getContext(),data);
            recyclerView1.setAdapter(recyclerAdapter_second);
            //recyclerView.setHasFixedSize(false);
            recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            swipeRefreshLayout = (SwipeRefreshLayout) item1.findViewById(R.id.enfresh);
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

                @Override
                public void onRefresh() {
                    refresh();
                }
            });
        }
    }
    //data refresh
    protected void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"refresh data",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }).start();
    }
    protected void initData(){
        data = new ArrayList<String>();
        for (int i = 'a';i < 'z' ; i++){
            data.add(" " + (char)i);
        }
    }
    //tab41\42
    public static class SectionsPagerAdapter_second extends FragmentPagerAdapter {

        public SectionsPagerAdapter_second(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment_second.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "recommond";
                case 1:
                    return "recommond";
            }
            return null;
        }
    }
}
