package me.codekiller.com.shavanti.UI.SimpleNews;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private AlertDialog cacheClearDialog;

    private List<JuheNews.ResultBean.DataBean> dataBeans = new ArrayList<>();
    private NewsRecyclerAdapter adapter;
    private NewsPageContract.Presenter presenter;

    private String type;
    //中文类型
    private String typeCN;
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

        setHasOptionsMenu(true);

        type = getArguments().getString("type");
        typeCN = getArguments().getString("typeCN");

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_simple_news, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_clear_news_cache){
            if (cacheClearDialog != null){
                cacheClearDialog.show();
            }
        } else if (item.getItemId() == R.id.menu_bookmarks){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews(View view) {
        refreshLayout = view.findViewById(R.id.news_page_swipe);
        recyclerView = view.findViewById(R.id.news_page_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager );
        adapter = new NewsRecyclerAdapter(getContext(), dataBeans);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(type);
            }
        });
        refreshLayout.setColorSchemeResources(R.color.toutiao_ico_blue);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(R.string.remind);
        dialogBuilder.setMessage(R.string.clear_news_cache_message);
        dialogBuilder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.clearNewsCache();
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, null);
        cacheClearDialog = dialogBuilder.create();
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
    public void onLocalLoaded(List<JuheNews.ResultBean.DataBean> dataBeans) {
        for (JuheNews.ResultBean.DataBean dataBean : dataBeans){
            if (!this.dataBeans.contains(dataBean)){
                this.dataBeans.add(dataBean);
            }
        }
        adapter.notifyDataSetChanged();
        //本地加载完成后，加载网络数据
        presenter.loadData(type);
    }

    @Override
    public void onDataLoaded(List<JuheNews.ResultBean.DataBean> dataBeans) {
        int updateCount = 0;
        List<JuheNews.ResultBean.DataBean> newData = new ArrayList<>();
        for (JuheNews.ResultBean.DataBean dataBean : dataBeans){
            if (!this.dataBeans.contains(dataBean)){
                this.dataBeans.add(dataBean);
                updateCount++;
                newData.add(dataBean);
            }
        }
        if (updateCount > 0){
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(this.dataBeans.size()-1);
            Resources resources = getContext().getResources();
            Toast.makeText(getContext(), resources.getString(R.string.news_update_count_part1)+updateCount+resources.getString(R.string.news_update_count_part2), Toast.LENGTH_SHORT).show();
        }
        //缓存新的数据
        if (!newData.isEmpty()){
            presenter.saveNewData(newData);
        }
    }

    @Override
    public void onCacheCleared(int count) {
        if (count > 0){
            Resources resources = getContext().getResources();
            Toast.makeText(getContext(), resources.getString(R.string.cache_clear_hint_part1)+count+resources.getString(R.string.cache_clear_hint_part2), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.no_cache_to_clear, Toast.LENGTH_SHORT).show();
        }
    }

    private void lazyLoad(){
        if (isViewCreated && isUIVisible && dataBeans.isEmpty()){
            presenter.loadLocal(typeCN);
        }
    }
}
