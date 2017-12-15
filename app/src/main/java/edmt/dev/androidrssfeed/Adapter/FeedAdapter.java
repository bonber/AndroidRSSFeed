package edmt.dev.androidrssfeed.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edmt.dev.androidrssfeed.Common.GuardarImg;
import edmt.dev.androidrssfeed.Interface.ItemClickListener;
import edmt.dev.androidrssfeed.Model.RSSObject;
import edmt.dev.androidrssfeed.R;


/**
 * Created by reale on 5/5/2017.
 */

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{

    public TextView txtTitle,txtPubDate,txtContent;
    public ImageView imgThumbnail;
    private ItemClickListener itemClickListener;
    public ImageButton imgCompartir;
    public ImageButton imgWeb;

    public FeedViewHolder(View itemView) {
        super(itemView);

        txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView)itemView.findViewById(R.id.txtPubDate);
        txtContent = (TextView)itemView.findViewById(R.id.txtContent);
        imgThumbnail = (ImageView)itemView.findViewById(R.id.imgThumbnail);
        imgCompartir = (ImageButton)itemView.findViewById(R.id.imgCompartir);
        imgWeb = (ImageButton)itemView.findViewById(R.id.imgWeb);

        //Set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }


}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;
    private ImageButton imgCompartir;
    private ImageButton imgWeb;
    ImageButton search;


    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = inflater.inflate(R.layout.row,parent,false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, final int position) {

        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText("Publicado: "+rssObject.getItems().get(position).getPubDate());
        //holder.txtContent.setText(rssObject.getItems().get(position).getContent());
        holder.txtContent.setText(rssObject.getItems().get(position).getDescription());
        holder.imgCompartir.setImageResource(R.drawable.ic_compartir);
        holder.imgWeb.setImageResource(R.drawable.ic_web);
        if (!rssObject.getItems().get(position).getThumbnail().equals("")) {
            Picasso.with(mContext).load(rssObject.getItems().get(position).getThumbnail()).into(holder.imgThumbnail);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    holder.txtContent.setText(rssObject.getItems().get(position).getContent());
                }
            }

        });
        holder.imgCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, rssObject.getItems().get(position).getTitle()
                        +"\n"
                        +rssObject.getItems().get(position).getLink());
                mContext.startActivity(intent);
            }
        });
        holder.imgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                mContext.startActivity(browserIntent);
            }
        });
        holder.imgThumbnail.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                GuardarImg gi = new GuardarImg();
                Bitmap bmap;
                gi.SaveImage(mContext,holder.imgThumbnail.getDrawingCache());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }



}

