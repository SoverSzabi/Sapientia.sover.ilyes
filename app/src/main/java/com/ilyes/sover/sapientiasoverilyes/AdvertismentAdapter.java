package com.ilyes.sover.sapientiasoverilyes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Hunor on 12/4/2017.
 */

public class AdvertismentAdapter extends RecyclerView.Adapter<AdvertismentAdapter.AdvertismentViewHolder> {

    private List<Advertisment> list;
    private Context context;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

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
        holder.textLocation.setText(advertisment.location);

        String imageURL = advertisment.getImageURL();

        Glide.with(context).load(imageURL).into(holder.imageView);

    }


    @Override
    public int getItemCount() {return list.size();}

    class AdvertismentViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textLocation;
        ImageView imageView;

        public  AdvertismentViewHolder(View itemView) {
            super(itemView);

            textTitle       = itemView.findViewById(R.id.titleTextView);
            textLocation    = itemView.findViewById(R.id.locationTextView);
            imageView       = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
