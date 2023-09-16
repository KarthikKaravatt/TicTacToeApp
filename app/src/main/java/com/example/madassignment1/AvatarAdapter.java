package com.example.madassignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ImageViewHolder> {
    private final List<Avatar> images;
    private final AvatarSelectListener listener;

    public AvatarAdapter(List<Avatar> images, AvatarSelectListener listener) {
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_item, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Avatar image = images.get(position);
        holder.imageView.setImageResource(image.getImageResourceId());

        // Set an OnClickListener to handle avatar selection
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int drawableResourceId = image.getImageResourceId();
                if (listener != null) {
                    listener.onAvatarSelected(drawableResourceId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }
    }
}