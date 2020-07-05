package com.azhar.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.azhar.myblog.model.ModelMain;
import com.azhar.myblog.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by Azhar Rivaldi on 22-12-2019.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ModelMain> items;
    private MainAdapter.onSelectData onSelectData;
    private Context mContext;

    public interface onSelectData {
        void onSelected(ModelMain modelListLagu);
    }

    public MainAdapter(Context context, List<ModelMain> items, MainAdapter.onSelectData xSelectData) {
        this.mContext = context;
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artikel, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelMain data = items.get(position);

        //Get Image
        Document document = Jsoup.parse(data.getContent());
        Elements element = document.select("img");

        Glide.with(mContext)
                .load(element.get(0).attr("src"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imgThumb);

        Glide.with(mContext)
                .load(data.getAuthorImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imgAuthor);

        holder.tvTitle.setText(data.getTitle());
        holder.tvAuthor.setText(data.getAuthor());
        holder.tvDatePost.setText(data.getPublished());
        holder.rlArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Class Holder
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvAuthor;
        public TextView tvDatePost;
        public RelativeLayout rlArtikel;
        public ImageView imgThumb;
        public ImageView imgAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            rlArtikel = itemView.findViewById(R.id.rlArtikel);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            imgAuthor = itemView.findViewById(R.id.imgAuthor);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDatePost = itemView.findViewById(R.id.tvDatePost);
        }
    }

}
