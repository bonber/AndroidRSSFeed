package edmt.dev.androidrssfeed.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
    private ItemClickListener compartirClickListener;
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

    public void msbox(String str,String str2)
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(mContext);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //finish();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

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
                    //Abrir contenido
                    /*msbox(rssObject.getItems().get(position).getTitle(),
                            rssObject.getItems().get(position).getContent());*/

                    //Abrir la URL
                    /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    mContext.startActivity(browserIntent);*/

                    //Compartir
                    /*Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, rssObject.getItems().get(position).getTitle()
                            +"\n"
                            +rssObject.getItems().get(position).getLink());
                    mContext.startActivity(intent);*/

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }



}

