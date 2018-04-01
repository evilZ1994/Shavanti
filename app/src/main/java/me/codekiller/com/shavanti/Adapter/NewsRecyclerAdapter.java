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

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<JuheNews.ResultBean.DataBean> dataBeans;

    public NewsRecyclerAdapter(Context context, List<JuheNews.ResultBean.DataBean> dataBeans){
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.simple_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final JuheNews.ResultBean.DataBean bean = dataBeans.get(position);
        holder.title.setText(bean.getTitle());

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(bean.getThumbnail_pic_s())
                .setOldController(holder.newsPic.getController())
                .setAutoPlayAnimations(true)
                .build();
        holder.newsPic.setController(controller);
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(bean.getThumbnail_pic_s02())
                .setOldController(holder.newsPic2.getController())
                .setAutoPlayAnimations(true)
                .build();
        holder.newsPic2.setController(controller2);
        DraweeController controller3 = Fresco.newDraweeControllerBuilder()
                .setUri(bean.getThumbnail_pic_s03())
                .setOldController(holder.newsPic3.getController())
                .setAutoPlayAnimations(true)
                .build();
        holder.newsPic3.setController(controller3);
        holder.date.setText(bean.getDate());
        holder.author.setText(bean.getAuthor_name());
        //绑定图片点击监听
        ArrayList<String> images = new ArrayList<>();
        images.add(bean.getThumbnail_pic_s());
        images.add(bean.getThumbnail_pic_s02());
        images.add(bean.getThumbnail_pic_s03());
        holder.newsPic.setOnClickListener(new MultiImageOnClickListener(context, 1, images));
        holder.newsPic2.setOnClickListener(new MultiImageOnClickListener(context, 2, images));
        holder.newsPic3.setOnClickListener(new MultiImageOnClickListener(context, 3, images));
        //设置整体点击监听
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JuheNewsDetailActivity.class);
                intent.putExtra("url", bean.getUrl());
                intent.putExtra("title", bean.getCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView title;
        private SimpleDraweeView newsPic;
        private SimpleDraweeView newsPic2;
        private SimpleDraweeView newsPic3;
        private TextView date;
        private TextView author;

        public ViewHolder(View itemView) {
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
