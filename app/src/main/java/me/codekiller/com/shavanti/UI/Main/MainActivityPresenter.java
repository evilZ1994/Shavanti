package me.codekiller.com.shavanti.UI.Main;

import android.content.Context;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.codekiller.com.shavanti.Model.Data.DataManager;
import me.codekiller.com.shavanti.Utils.NetworkUtil;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;
    private DataManager dataManager;
    private Context context;

    public MainActivityPresenter(MainActivityContract.View view, Context context){
        this.view = view;
        this.context = context;
        dataManager = DataManager.getInstance(context);
    }

    @Override
    public void checkHomePic() {
        //检查网络是否可用，网络未连接的话会导致异常
        if (NetworkUtil.isNetworkConnected(context)) {
            Consumer<List<String>> consumer = new Consumer<List<String>>() {
                @Override
                public void accept(List<String> lackPics) throws Exception {
                    if (!lackPics.isEmpty()) {
                        dataManager.downloadHomePic(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                            }
                        }, lackPics);
                    }
                }
            };
            dataManager.checkHomePic(consumer);
        }
    }
}
