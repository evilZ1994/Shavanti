package me.codekiller.com.shavanti.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.DateTitle;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.View.CustomViews.HandWriteTextView;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter {
    private List<BaseBean> beans;
    private Context context;
    private LayoutInflater layoutInflater;

    public MainRecyclerAdapter(Context context, List<BaseBean> beans){
        this.context = context;
        this.beans = beans;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DateTitle.class.hashCode()){
            return new DateTitleViewHolder(layoutInflater.inflate(R.layout.date_title_item, parent, false));
        }else if (viewType == LaifudaoJoke.class.hashCode()){
            return new JokeViewHolder(layoutInflater.inflate(R.layout.joke_item, parent, false));
        }else if (viewType == LaifudaoPic.class.hashCode()){
            return new FunnyPicViewHolder(layoutInflater.inflate(R.layout.funny_pic_item, parent, false));
        }else if (viewType == JuheNews.ResultBean.DataBean.class.hashCode()){
            return new JuheNewsViewHolder(layoutInflater.inflate(R.layout.juhe_news_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == DateTitle.class.hashCode()){
            DateTitle dateTitle = (DateTitle) beans.get(position);
            ((DateTitleViewHolder)holder).dateTitle.setText(dateTitle.getKeyDate());
        }else if (viewType == LaifudaoJoke.class.hashCode()){
            LaifudaoJoke joke = (LaifudaoJoke)beans.get(position);
            ((JokeViewHolder)holder).type.setText(context.getResources().getString(R.string.joke_title));
            ((JokeViewHolder)holder).title.setText(joke.getTitle());
            ((JokeViewHolder)holder).content.setText(joke.getContent());
        }else if (viewType == LaifudaoPic.class.hashCode()){
            LaifudaoPic pic = (LaifudaoPic)beans.get(position);
            ((FunnyPicViewHolder)holder).type.setText(context.getResources().getString(R.string.funny_pic_title));
            ((FunnyPicViewHolder)holder).title.setText(pic.getTitle());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(pic.getThumburl()))
                    .setAutoPlayAnimations(true)
                    .build();
            ((FunnyPicViewHolder)holder).funnyPic.setController(controller);
        }else if (viewType == JuheNews.ResultBean.DataBean.class.hashCode()){
            JuheNews.ResultBean.DataBean juheNews = (JuheNews.ResultBean.DataBean) beans.get(position);
            ((JuheNewsViewHolder)holder).type.setText(context.getResources().getString(R.string.news_title));
            ((JuheNewsViewHolder)holder).title.setText(juheNews.getTitle());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(juheNews.getThumbnail_pic_s()))
                    .setAutoPlayAnimations(true)
                    .build();
            ((JuheNewsViewHolder)holder).newsPic.setController(controller);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return beans.get(position).getClass().hashCode();
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class DateTitleViewHolder extends RecyclerView.ViewHolder{
        private HandWriteTextView dateTitle;

        public DateTitleViewHolder(View itemView) {
            super(itemView);
            dateTitle = itemView.findViewById(R.id.date_title);
        }
    }

    public class JokeViewHolder extends RecyclerView.ViewHolder{
        private HandWriteTextView type;
        private TextView title;
        private TextView content;

        public JokeViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
        }
    }

    public class FunnyPicViewHolder extends RecyclerView.ViewHolder{
        private HandWriteTextView type;
        private TextView title;
        private SimpleDraweeView funnyPic;

        public FunnyPicViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.title);
            funnyPic = itemView.findViewById(R.id.funny_pic);
        }
    }

    public class JuheNewsViewHolder extends RecyclerView.ViewHolder{
        private HandWriteTextView type;
        private TextView title;
        private SimpleDraweeView newsPic;

        public JuheNewsViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.title);
            newsPic = itemView.findViewById(R.id.news_pic);
        }
    }
}
