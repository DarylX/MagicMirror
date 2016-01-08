package com.ppg.magicmirror.adapters;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ppg.magicmirror.R;
import com.ppg.magicmirror.fragments.PhotoPickerFragment;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBFullAlbumData;
import com.squareup.picasso.Picasso;

/**
 * Created by Daryl on 1/5/2016.
 */
public class AlbumPickerAdapter extends RecyclerView.Adapter<AlbumPickerAdapter.ViewHolder>{

    FBFullAlbumData albums;
    private Context context;

    public AlbumPickerAdapter(FBFullAlbumData fbalbums, Context context){
        this.context = context;
        albums = fbalbums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.album_adapter_item, parent, false);

        // Return a new holder instance
        AlbumPickerAdapter.ViewHolder viewHolder = new AlbumPickerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumPickerAdapter.ViewHolder holder, int position) {
        holder.albumName.setText(albums.fbAlbumsGraphObject.Albums.get(position).name);
        Picasso.with(context)
                .load(albums.fbPictures.get(position).images.get(0).getSource())
                .resize(50, 50)
                .centerCrop()
                .into(holder.albumPicture);

        final int p = position;
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new fragment and transaction
                PhotoPickerFragment newFragment = new PhotoPickerFragment();
                newFragment.setAlbum(albums.fbAlbumsGraphObject.Albums.get(p));
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.picker_container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                Toast.makeText(context,albums.fbAlbumsGraphObject.Albums.get(p).name,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return albums.fbAlbumsGraphObject.Albums.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView albumPicture;
        public TextView albumName;
        public View view;


        public ViewHolder(View itemView) {
            super(itemView);

            albumPicture = (ImageView) itemView.findViewById(R.id.album_photo_adapter_item);
            albumName = (TextView) itemView.findViewById(R.id.album_name_adapter_item);
            view = itemView;
        }
    }
}
