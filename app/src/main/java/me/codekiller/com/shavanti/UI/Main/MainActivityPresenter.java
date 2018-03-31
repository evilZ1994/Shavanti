package me.codekiller.com.shavanti.UI.Main;

import android.content.Context;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.codekiller.com.shavanti.Model.Data.DataManager;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;
    private DataManager dataManager;

    public MainActivityPresenter(MainActivityContract.View view, Context context){
        this.view = view;
        dataManager = DataManager.getInstance(context);
    }

    @Override
    public void checkHomePic() {
        Consumer<List<String>> consumer = new Consumer<List<String>>() {
            @Override
            public void accept(List<String> lackPics) throws Exception {
                if (!lackPics.isEmpty()){
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
