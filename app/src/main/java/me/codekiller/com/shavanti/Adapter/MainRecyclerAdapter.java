package me.codekiller.com.shavanti.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import me.codekiller.com.shavanti.Listener.MultiImageOnClickListener;
import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.DateTitle;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import me.codekiller.com.shavanti.Model.Bean.OneArticle;
import me.codekiller.com.shavanti.Model.Bean.OnePic;
import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.UI.FunnyPicMore.MoreFunnyPicActivity;
import me.codekiller.com.shavanti.UI.JokeMore.MoreJokeActivity;
import me.codekiller.com.shavanti.UI.JuheNewsDetail.JuheNewsDetailActivity;
import me.codekiller.com.shavanti.UI.OneArticle.OneArticleActivity;
import me.codekiller.com.shavanti.UI.ImageView.ZoomableViewActivity;
import me.codekiller.com.shavanti.Utils.DateUtil;
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
        }else if (viewType == OnePic.class.hashCode()){
            return new OnePicViewHolder(layoutInflater.inflate(R.layout.one_pic_item, parent, false));
        }else if (viewType == OneArticle.class.hashCode()){
            return new OneArticleViewHolder(layoutInflater.inflate(R.layout.one_article_item, parent, false));
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
            //来福岛笑话
            final LaifudaoJoke joke = (LaifudaoJoke)beans.get(position);
            ((JokeViewHolder)holder).type.setText(context.getResources().getString(R.string.joke_title));
            ((JokeViewHolder)holder).title.setText(joke.getTitle());
            ((JokeViewHolder)holder).content.setText(Html.fromHtml(joke.getContent()));
            ((JokeViewHolder)holder).checkMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MoreJokeActivity.class);
                    intent.putExtra("date", joke.getKeyDate());
                    context.startActivity(intent);
                }
            });
        }else if (viewType == LaifudaoPic.class.hashCode()){
            //来福岛搞笑图片
            final LaifudaoPic pic = (LaifudaoPic)beans.get(position);
            ((FunnyPicViewHolder)holder).type.setText(context.getResources().getString(R.string.funny_pic_title));
            ((FunnyPicViewHolder)holder).title.setText(pic.getTitle());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(pic.getThumburl()))
                    .setAutoPlayAnimations(true)
                    .build();
            ((FunnyPicViewHolder)holder).funnyPic.setAspectRatio((float)pic.getWidth()/pic.getHeight());
            ((FunnyPicViewHolder)holder).funnyPic.setController(controller);
            ((FunnyPicViewHolder)holder).funnyPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ZoomableViewActivity.class);
                    intent.putExtra("image_uri", pic.getThumburl());
                    intent.putExtra("title", pic.getTitle());
                    context.startActivity(intent);
                }
            });
            ((FunnyPicViewHolder)holder).checkMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MoreFunnyPicActivity.class);
                    intent.putExtra("date", pic.getKeyDate());
                    context.startActivity(intent);
                }
            });
        }else if (viewType == JuheNews.ResultBean.DataBean.class.hashCode()){
            //聚合新闻头条
            final JuheNews.ResultBean.DataBean juheNews = (JuheNews.ResultBean.DataBean) beans.get(position);
            ((JuheNewsViewHolder)holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, JuheNewsDetailActivity.class);
                    intent.putExtra("toolbar_title", context.getResources().getString(R.string.news_title));
                    intent.putExtra("uniquekey", juheNews.getUniquekey());
                    intent.putExtra("title", juheNews.getTitle());
                    intent.putExtra("date", juheNews.getDate());
                    intent.putExtra("category", juheNews.getCategory());
                    intent.putExtra("author_name", juheNews.getAuthor_name());
                    intent.putExtra("url", juheNews.getUrl());
                    intent.putExtra("thumbnail_pic_s", juheNews.getThumbnail_pic_s());
                    intent.putExtra("thumbnail_pic_s02", juheNews.getThumbnail_pic_s02());
                    intent.putExtra("thumbnail_pic_s03", juheNews.getThumbnail_pic_s03());

                    context.startActivity(intent);
                }
            });
            ((JuheNewsViewHolder)holder).type.setText(context.getResources().getString(R.string.news_title));
            ((JuheNewsViewHolder)holder).title.setText(juheNews.getTitle());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(juheNews.getThumbnail_pic_s()))
                    .setOldController(((JuheNewsViewHolder)holder).newsPic.getController())
                    .setAutoPlayAnimations(true)
                    .build();
            ((JuheNewsViewHolder)holder).newsPic.setController(controller);
            DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(juheNews.getThumbnail_pic_s02()))
                    .setOldController(((JuheNewsViewHolder)holder).newsPic2.getController())
                    .setAutoPlayAnimations(true)
                    .build();
            ((JuheNewsViewHolder)holder).newsPic2.setController(controller2);
            DraweeController controller3 = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(juheNews.getThumbnail_pic_s03()))
                    .setOldController(((JuheNewsViewHolder)holder).newsPic3.getController())
                    .setAutoPlayAnimations(true)
                    .build();
            ((JuheNewsViewHolder)holder).newsPic3.setController(controller3);
            //给图片设置点击事件
            ArrayList<String> images = new ArrayList<>();
            images.add(juheNews.getThumbnail_pic_s());
            images.add(juheNews.getThumbnail_pic_s02());
            images.add(juheNews.getThumbnail_pic_s03());
            ((JuheNewsViewHolder)holder).newsPic.setOnClickListener(new MultiImageOnClickListener(context, 1, images));
            ((JuheNewsViewHolder)holder).newsPic2.setOnClickListener(new MultiImageOnClickListener(context, 2, images));
            ((JuheNewsViewHolder)holder).newsPic3.setOnClickListener(new MultiImageOnClickListener(context, 3, images));
        }else if (viewType == OnePic.class.hashCode()){
            //One图片
            final OnePic onePic = (OnePic) beans.get(position);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(onePic.getImgUrl()))
                    .build();
            ((OnePicViewHolder)holder).pic.setController(controller);
            ((OnePicViewHolder)holder).pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ZoomableViewActivity.class);
                    intent.putExtra("image_uri", onePic.getImgUrl());
                    intent.putExtra("title", DateUtil.dateFormat(onePic.getKeyDate(), context));
                    context.startActivity(intent);
                }
            });
            ((OnePicViewHolder)holder).description.setText(onePic.getDescription());
        }else if (viewType == OneArticle.class.hashCode()){
            //One文章
            final OneArticle article = (OneArticle) beans.get(position);
            ((OneArticleViewHolder)holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OneArticleActivity.class);
                    intent.putExtra("title", article.getTitle());
                    intent.putExtra("author", article.getAuthor());
                    intent.putExtra("description", article.getDescription());
                    intent.putExtra("article", article.getArticle());
                    context.startActivity(intent);
                }
            });
            ((OneArticleViewHolder)holder).title.setText(article.getTitle());
            ((OneArticleViewHolder)holder).description.setText(article.getDescription());
            if (article.getAuthor() == null || article.getAuthor().length() == 0){
                ((OneArticleViewHolder)holder).author.setVisibility(View.GONE);
            }else {
                ((OneArticleViewHolder)holder).author.setVisibility(View.VISIBLE);
                ((OneArticleViewHolder)holder).author.setText(article.getAuthor());
            }
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
        private TextView checkMore;

        public JokeViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            checkMore = itemView.findViewById(R.id.check_more);
        }
    }

    public class FunnyPicViewHolder extends RecyclerView.ViewHolder{
        private HandWriteTextView type;
        private TextView title;
        private SimpleDraweeView funnyPic;
        private TextView checkMore;

        public FunnyPicViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.title);
            funnyPic = itemView.findViewById(R.id.funny_pic);
            checkMore = itemView.findViewById(R.id.check_more);
        }
    }

    public class JuheNewsViewHolder extends RecyclerView.ViewHolder{
        private CardView card;
        private HandWriteTextView type;
        private TextView title;
        private SimpleDraweeView newsPic;
        private SimpleDraweeView newsPic2;
        private SimpleDraweeView newsPic3;

        public JuheNewsViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.juhe_news_item);
            type = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.title);
            newsPic = itemView.findViewById(R.id.news_pic);
            newsPic2 = itemView.findViewById(R.id.news_pic2);
            newsPic3 = itemView.findViewById(R.id.news_pic3);
        }
    }

    public class OnePicViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView pic;
        private TextView description;

        public OnePicViewHolder(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.one_pic);
            description = itemView.findViewById(R.id.description);
        }
    }

    public class OneArticleViewHolder extends RecyclerView.ViewHolder{
        private CardView card;
        private TextView title;
        private TextView description;
        private TextView author;

        public OneArticleViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            author = itemView.findViewById(R.id.author);
        }
    }
}
