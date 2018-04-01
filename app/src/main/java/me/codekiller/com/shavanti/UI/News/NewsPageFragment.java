package me.codekiller.com.shavanti.UI.News;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.codekiller.com.shavanti.Adapter.NewsRecyclerAdapter;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsPageFragment extends Fragment implements NewsPageContract.View{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private List<JuheNews.ResultBean.DataBean> dataBeans = new ArrayList<>();
    private NewsRecyclerAdapter adapter;
    private NewsPageContract.Presenter presenter;

    private String type;
    private boolean isViewCreated = false;
    private boolean isUIVisible = false;

    public NewsPageFragment() {
        // Required empty public constructor
    }

    public static NewsPageFragment getInstance(){
        return new NewsPageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_page, container, false);

        type = getArguments().getString("type");

        initViews(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUIVisible = isVisibleToUser;
        if (isUIVisible) {
            lazyLoad();
        }
    }

    @Override
    public void initViews(View view) {
        refreshLayout = view.findViewById(R.id.news_page_swipe);
        recyclerView = view.findViewById(R.id.news_page_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsRecyclerAdapter(getContext(), dataBeans);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(type);
            }
        });
        refreshLayout.setColorSchemeResources(R.color.toutiao_ico_blue);
    }

    @Override
    public void setPresenter(NewsPageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopLoading() {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDataLoaded(List<JuheNews.ResultBean.DataBean> dataBeans) {
        int updateCount = 0;
        for (JuheNews.ResultBean.DataBean dataBean : dataBeans){
            if (!this.dataBeans.contains(dataBean)){
                this.dataBeans.add(dataBean);
                updateCount++;
            }
        }
        adapter.notifyDataSetChanged();
        if (updateCount > 0){
            Resources resources = getContext().getResources();
            Toast.makeText(getContext(), resources.getString(R.string.news_update_count_part1)+updateCount+resources.getString(R.string.news_update_count_part2), Toast.LENGTH_SHORT).show();
        }
    }

    private void lazyLoad(){
        if (isViewCreated && isUIVisible && dataBeans.isEmpty()){
            presenter.loadData(type);
        }
    }
}
