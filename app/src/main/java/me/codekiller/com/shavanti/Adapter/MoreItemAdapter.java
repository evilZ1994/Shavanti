package me.codekiller.com.shavanti.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.UI.ImageView.ZoomableViewActivity;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class MoreItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BaseBean> beans;
    private LayoutInflater layoutInflater;

    public MoreItemAdapter(Context context, List<BaseBean> beans){
        this.context = context;
        this.beans = beans;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LaifudaoJoke.class.hashCode()){
            return new JokeViewHolder(layoutInflater.inflate(R.layout.more_joke_item, parent, false));
        }else if (viewType == LaifudaoPic.class.hashCode()){
            return new FunnyPicViewHolder(layoutInflater.inflate(R.layout.more_funny_pic_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == LaifudaoJoke.class.hashCode()){
            LaifudaoJoke joke = (LaifudaoJoke)beans.get(position);
            ((JokeViewHolder)holder).title.setText(joke.getTitle());
            ((JokeViewHolder)holder).content.setText(Html.fromHtml(joke.getContent()));
        }else if (viewType == LaifudaoPic.class.hashCode()){
            final LaifudaoPic pic = (LaifudaoPic)beans.get(position);
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
        }
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return beans.get(position).getClass().hashCode();
    }

    public class JokeViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView content;

        public JokeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
        }
    }

    public class FunnyPicViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private SimpleDraweeView funnyPic;

        public FunnyPicViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            funnyPic = itemView.findViewById(R.id.funny_pic);
        }
    }
}
