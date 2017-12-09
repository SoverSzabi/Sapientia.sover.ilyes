package com.ilyes.sover.sapientiasoverilyes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Hunor on 12/4/2017.
 */

public class AdvertismentAdapter extends RecyclerView.Adapter<AdvertismentAdapter.AdvertismentViewHolder> {

    private List<Advertisment> list;
    Context context;

    public AdvertismentAdapter(Context context, List<Advertisment> list) {
        this.context= context;
        this.list   = list;
    }

    @Override
    public AdvertismentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdvertismentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final AdvertismentViewHolder holder, int position) {

        Advertisment advertisment = list.get(position);

        holder.textTitle.setText(advertisment.title);
        holder.textDescription.setText(advertisment.description);
        holder.textLocation.setText(advertisment.location);

        String imageURL = advertisment.getImageURL();

        Glide.with(context).load(imageURL).into(holder.imageView);

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(holder.getAdapterPosition(), 0, 0, "Update");
                menu.add(holder.getAdapterPosition(), 1, 0, "Delete");
            }
        });
    }

    @Override
    public int getItemCount() {return list.size();}

    class AdvertismentViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDescription, textLocation;
        ImageView imageView;

        public  AdvertismentViewHolder(View itemView) {
            super(itemView);

            textTitle       = itemView.findViewById(R.id.titleTextView);
            textDescription = itemView.findViewById(R.id.descriptionTextView);
            textLocation    = itemView.findViewById(R.id.locationTextView);

            imageView       = itemView.findViewById(R.id.imageView);
        }
    }
}
