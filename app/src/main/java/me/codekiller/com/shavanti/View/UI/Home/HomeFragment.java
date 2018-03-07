package me.codekiller.com.shavanti.View.UI.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.codekiller.com.shavanti.Adapter.MainRecyclerAdapter;
import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HomeContract.View{
    private RecyclerView recyclerView;
    private MainRecyclerAdapter adapter;
    private HomeContract.Presenter presenter;

    private List<BaseBean> beans = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (beans.isEmpty()) {
            presenter.loadLocal(beans);
        }
    }

    @Override
    public void initViews(View view) {

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainRecyclerAdapter(getContext(), beans);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResults() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadToday() {
        presenter.loadToday(beans);
    }

    @Override
    public void showError(String msg) {

    }

}
