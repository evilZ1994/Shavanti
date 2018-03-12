package me.codekiller.com.shavanti.UI.FunnyPicMore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.codekiller.com.shavanti.Adapter.MoreItemAdapter;
import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFunnyPicFragment extends Fragment implements MoreFunnyPicContract.View{

    private MoreFunnyPicContract.Presenter presenter;
    private RecyclerView recyclerView;
    private MoreItemAdapter adapter;
    private String date;

    private List<BaseBean> beans = new ArrayList<>();

    public MoreFunnyPicFragment() {
        // Required empty public constructor
    }

    public static MoreFunnyPicFragment newInstance(){
        return new MoreFunnyPicFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_funny_pic, container, false);
        Bundle bundle = getArguments();
        date = bundle.getString("date");

        initViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (beans.isEmpty()){
            presenter.loadPics(beans, date);
        }
    }

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MoreItemAdapter(getContext(), beans);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(MoreFunnyPicContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showResults() {
        adapter.notifyDataSetChanged();
    }
}
