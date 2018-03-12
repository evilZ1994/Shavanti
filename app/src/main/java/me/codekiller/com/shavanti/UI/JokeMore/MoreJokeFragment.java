package me.codekiller.com.shavanti.UI.JokeMore;


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
 * Use the {@link MoreJokeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreJokeFragment extends Fragment implements MokeJokeContract.View{
    private RecyclerView recyclerView;
    private MoreItemAdapter adapter;

    private List<BaseBean> jokes = new ArrayList<>();
    private MokeJokeContract.Presenter presenter;
    private String date;

    public MoreJokeFragment() {
        // Required empty public constructor
    }

    public static MoreJokeFragment newInstance() {
        return new MoreJokeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_joke, container, false);

        Bundle bundle = getArguments();
        if (bundle != null){
           date = bundle.getString("date");
        }

        initViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (jokes.isEmpty()){
            presenter.loadJokes(jokes, date);
        }
    }

    @Override
    public void initViews(View view) {
        adapter = new MoreItemAdapter(getContext(), jokes);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(MokeJokeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showResults() {
        adapter.notifyDataSetChanged();
    }
}
