package me.codekiller.com.shavanti.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import me.codekiller.com.shavanti.Listener.MultiImageOnClickListener;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.UI.JuheNewsDetail.JuheNewsDetailActivity;

public class NewsRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<JuheNews.ResultBean.DataBean> dataBeans;

    public NewsRecyclerAdapter(Context context, List<JuheNews.ResultBean.DataBean> dataBeans){
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ViewHolderZeroPic(LayoutInflater.from(context).inflate(R.layout.simple_news_item_0pic, parent, false));
            case 1:
                return new ViewHolderOnePic(LayoutInflater.from(context).inflate(R.layout.simple_news_item_1pic, parent, false));
            case 2:
                return new ViewHolderTwoPic(LayoutInflater.from(context).inflate(R.layout.simple_news_item_2pic, parent, false));
            case 3:
                return new ViewHolderThreePic(LayoutInflater.from(context).inflate(R.layout.simple_news_item_3pic, parent, false));
             default:
                 return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final JuheNews.ResultBean.DataBean bean = dataBeans.get(position);
        int type = holder.getItemViewType();
        switch (type){
            case 0:
                ViewHolderZeroPic zeroHolder = ((ViewHolderZeroPic) holder);
                zeroHolder.title.setText(bean.getTitle());

                zeroHolder.date.setText(bean.getDate());
                zeroHolder.author.setText(bean.getAuthor_name());
                //绑定图片点击监听
                ArrayList<String> images0 = new ArrayList<>();
                images0.add(bean.getThumbnail_pic_s());
                images0.add(bean.getThumbnail_pic_s02());
                images0.add(bean.getThumbnail_pic_s03());
                //设置整体点击监听
                zeroHolder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, JuheNewsDetailActivity.class);
                        intent.putExtra("toolbar_title", context.getResources().getString(R.string.simple_news)+"·"+bean.getCategory());
                        intent.putExtra("uniquekey", bean.getUniquekey());
                        intent.putExtra("title", bean.getTitle());
                        intent.putExtra("date", bean.getDate());
                        intent.putExtra("category", bean.getCategory());
                        intent.putExtra("author_name", bean.getAuthor_name());
                        intent.putExtra("url", bean.getUrl());
                        intent.putExtra("thumbnail_pic_s", bean.getThumbnail_pic_s());
                        intent.putExtra("thumbnail_pic_s02", bean.getThumbnail_pic_s02());
                        intent.putExtra("thumbnail_pic_s03", bean.getThumbnail_pic_s03());
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                ViewHolderOnePic oneHolder = ((ViewHolderOnePic) holder);
                oneHolder.title.setText(bean.getTitle());

                DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                        .setUri(bean.getThumbnail_pic_s())
                        .setOldController(oneHolder.newsPic.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                oneHolder.newsPic.setController(controller1);
                oneHolder.date.setText(bean.getDate());
                oneHolder.author.setText(bean.getAuthor_name());
                //绑定图片点击监听
                ArrayList<String> images1 = new ArrayList<>();
                images1.add(bean.getThumbnail_pic_s());
                images1.add(bean.getThumbnail_pic_s02());
                images1.add(bean.getThumbnail_pic_s03());
                oneHolder.newsPic.setOnClickListener(new MultiImageOnClickListener(context, 1, images1));
                //设置整体点击监听
                oneHolder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, JuheNewsDetailActivity.class);
                        intent.putExtra("toolbar_title", context.getResources().getString(R.string.simple_news)+"·"+bean.getCategory());
                        intent.putExtra("uniquekey", bean.getUniquekey());
                        intent.putExtra("title", bean.getTitle());
                        intent.putExtra("date", bean.getDate());
                        intent.putExtra("category", bean.getCategory());
                        intent.putExtra("author_name", bean.getAuthor_name());
                        intent.putExtra("url", bean.getUrl());
                        intent.putExtra("thumbnail_pic_s", bean.getThumbnail_pic_s());
                        intent.putExtra("thumbnail_pic_s02", bean.getThumbnail_pic_s02());
                        intent.putExtra("thumbnail_pic_s03", bean.getThumbnail_pic_s03());
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
                ViewHolderTwoPic twoHolder = ((ViewHolderTwoPic) holder);
                twoHolder.title.setText(bean.getTitle());

                DraweeController twoController = Fresco.newDraweeControllerBuilder()
                        .setUri(bean.getThumbnail_pic_s())
                        .setOldController(twoHolder.newsPic.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                twoHolder.newsPic.setController(twoController);
                DraweeController twoController2 = Fresco.newDraweeControllerBuilder()
                        .setUri(bean.getThumbnail_pic_s02())
                        .setOldController(twoHolder.newsPic2.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                twoHolder.newsPic2.setController(twoController2);
                twoHolder.date.setText(bean.getDate());
                twoHolder.author.setText(bean.getAuthor_name());
                //绑定图片点击监听
                ArrayList<String> images2 = new ArrayList<>();
                images2.add(bean.getThumbnail_pic_s());
                images2.add(bean.getThumbnail_pic_s02());
                images2.add(bean.getThumbnail_pic_s03());
                twoHolder.newsPic.setOnClickListener(new MultiImageOnClickListener(context, 1, images2));
                twoHolder.newsPic2.setOnClickListener(new MultiImageOnClickListener(context, 2, images2));
                //设置整体点击监听
                twoHolder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, JuheNewsDetailActivity.class);
                        intent.putExtra("toolbar_title", context.getResources().getString(R.string.simple_news)+"·"+bean.getCategory());
                        intent.putExtra("uniquekey", bean.getUniquekey());
                        intent.putExtra("title", bean.getTitle());
                        intent.putExtra("date", bean.getDate());
                        intent.putExtra("category", bean.getCategory());
                        intent.putExtra("author_name", bean.getAuthor_name());
                        intent.putExtra("url", bean.getUrl());
                        intent.putExtra("thumbnail_pic_s", bean.getThumbnail_pic_s());
                        intent.putExtra("thumbnail_pic_s02", bean.getThumbnail_pic_s02());
                        intent.putExtra("thumbnail_pic_s03", bean.getThumbnail_pic_s03());
                        context.startActivity(intent);
                    }
                });
                break;
            case 3:
                ViewHolderThreePic threeHolder = ((ViewHolderThreePic) holder);
                threeHolder.title.setText(bean.getTitle());

                DraweeController threeController = Fresco.newDraweeControllerBuilder()
                        .setUri(bean.getThumbnail_pic_s())
                        .setOldController(threeHolder.newsPic.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                threeHolder.newsPic.setController(threeController);
                DraweeController threeController2 = Fresco.newDraweeControllerBuilder()
                        .setUri(bean.getThumbnail_pic_s02())
                        .setOldController(threeHolder.newsPic2.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                threeHolder.newsPic2.setController(threeController2);
                DraweeController threeController3 = Fresco.newDraweeControllerBuilder()
                        .setUri(bean.getThumbnail_pic_s03())
                        .setOldController(threeHolder.newsPic3.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                threeHolder.newsPic3.setController(threeController3);
                threeHolder.date.setText(bean.getDate());
                threeHolder.author.setText(bean.getAuthor_name());
                //绑定图片点击监听
                ArrayList<String> images3 = new ArrayList<>();
                images3.add(bean.getThumbnail_pic_s());
                images3.add(bean.getThumbnail_pic_s02());
                images3.add(bean.getThumbnail_pic_s03());
                threeHolder.newsPic.setOnClickListener(new MultiImageOnClickListener(context, 1, images3));
                threeHolder.newsPic2.setOnClickListener(new MultiImageOnClickListener(context, 2, images3));
                threeHolder.newsPic3.setOnClickListener(new MultiImageOnClickListener(context, 3, images3));
                //设置整体点击监听
                threeHolder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, JuheNewsDetailActivity.class);
                        intent.putExtra("toolbar_title", context.getResources().getString(R.string.simple_news)+"·"+bean.getCategory());
                        intent.putExtra("uniquekey", bean.getUniquekey());
                        intent.putExtra("title", bean.getTitle());
                        intent.putExtra("date", bean.getDate());
                        intent.putExtra("category", bean.getCategory());
                        intent.putExtra("author_name", bean.getAuthor_name());
                        intent.putExtra("url", bean.getUrl());
                        intent.putExtra("thumbnail_pic_s", bean.getThumbnail_pic_s());
                        intent.putExtra("thumbnail_pic_s02", bean.getThumbnail_pic_s02());
                        intent.putExtra("thumbnail_pic_s03", bean.getThumbnail_pic_s03());
                        context.startActivity(intent);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        JuheNews.ResultBean.DataBean bean = dataBeans.get(position);
        int type = 0;
        if (bean.getThumbnail_pic_s() != null){
            type++;
        }
        if (bean.getThumbnail_pic_s02() != null){
            type++;
        }
        if (bean.getThumbnail_pic_s03() != null){
            type++;
        }
        return type;
    }

    class ViewHolderZeroPic extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView title;
        private TextView date;
        private TextView author;

        public ViewHolderZeroPic(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.simple_news_item);
            title = itemView.findViewById(R.id.title);
            date  = itemView.findViewById(R.id.news_date);
            author = itemView.findViewById(R.id.news_author);
        }
    }

    class ViewHolderOnePic extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView title;
        private SimpleDraweeView newsPic;
        private TextView date;
        private TextView author;

        public ViewHolderOnePic(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.simple_news_item);
            title = itemView.findViewById(R.id.title);
            newsPic = itemView.findViewById(R.id.news_pic);
            date  = itemView.findViewById(R.id.news_date);
            author = itemView.findViewById(R.id.news_author);
        }
    }

    class ViewHolderTwoPic extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView title;
        private SimpleDraweeView newsPic;
        private SimpleDraweeView newsPic2;
        private TextView date;
        private TextView author;

        public ViewHolderTwoPic(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.simple_news_item);
            title = itemView.findViewById(R.id.title);
            newsPic = itemView.findViewById(R.id.news_pic);
            newsPic2 = itemView.findViewById(R.id.news_pic2);
            date  = itemView.findViewById(R.id.news_date);
            author = itemView.findViewById(R.id.news_author);
        }
    }

    class ViewHolderThreePic extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView title;
        private SimpleDraweeView newsPic;
        private SimpleDraweeView newsPic2;
        private SimpleDraweeView newsPic3;
        private TextView date;
        private TextView author;

        public ViewHolderThreePic(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.simple_news_item);
            title = itemView.findViewById(R.id.title);
            newsPic = itemView.findViewById(R.id.news_pic);
            newsPic2 = itemView.findViewById(R.id.news_pic2);
            newsPic3 = itemView.findViewById(R.id.news_pic3);
            date  = itemView.findViewById(R.id.news_date);
            author = itemView.findViewById(R.id.news_author);
        }
    }
}
