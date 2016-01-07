package com.ppg.magicmirror.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppg.magicmirror.R;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBAlbumsGraphObject;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBFullAlbumData;
import com.squareup.picasso.Picasso;

/**
 * Created by Daryl on 1/5/2016.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{

    FBFullAlbumData albums;
    private Context context;

    public AlbumAdapter(FBFullAlbumData fbalbums,Context context){
        this.context = context;
        albums = fbalbums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.album_adapter_item, parent, false);

        // Return a new holder instance
        AlbumAdapter.ViewHolder viewHolder = new AlbumAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.ViewHolder holder, int position) {
        holder.albumName.setText(albums.fbAlbumsGraphObject.Albums.get(position).name);
        Picasso.with(context).load(albums.fbPictures.get(position).images.get(0).getSource()).into(holder.albumPicture);

    }

    @Override
    public int getItemCount() {
        return albums.fbAlbumsGraphObject.Albums.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView albumPicture;
        public TextView albumName;


        public ViewHolder(View itemView) {
            super(itemView);

            albumPicture = (ImageView) itemView.findViewById(R.id.album_photo_adapter_item);
            albumName = (TextView) itemView.findViewById(R.id.album_name_adapter_item);
        }
    }
}
