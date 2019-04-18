package com.example.asus.mobiletracker.adapters;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.example.asus.mobiletracker.R;
import com.example.asus.mobiletracker.models.Products;


import java.util.List;

public class ListOfProductsAdapter extends RecyclerView.Adapter<ListOfProductsAdapter.MyViewHolder>{

    private final static String TAG = "ListOfProducts";

    private Context context;
    private List<Products> postList;
    private OnNoteListener MonNoteListener;


    public ListOfProductsAdapter(List<Products> postList,Context context,OnNoteListener onNoteListener) {

        this.postList = postList;
        this.context = context;
        this.MonNoteListener = onNoteListener;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView ID;
        public TextView title;
        private ImageView image;
        private ImageView image1;
        private TextView description;
        private TextView price;
        private TextView used_amount;



        OnNoteListener MonNoteListener;

        public MyViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);

//            title = itemView.findViewById(R.id.textView5);
//            description = itemView.findViewById(R.id.textView7);
//            image = itemView.findViewById(R.id.imageView3);
//            image1 = itemView.findViewById(R.id.imageView11);
//
//            price = itemView.findViewById(R.id.textView6);
//            ID = itemView.findViewById(R.id.id);
//            used_amount = itemView.findViewById(R.id.txt_used_amount);
//
//            likeButton = itemView.findViewById(R.id.like_button);

            this.MonNoteListener = onNoteListener;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            MonNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(itemView,MonNoteListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Products posts = postList.get(position);



//        holder.ID.getId();
//        holder.title.setText(posts.getTitle());
//        holder.description.setText(posts.getBody());
//        holder.price.setText("Price: $" + posts.getPrice());
//        holder.used_amount.setText(String.valueOf(posts.getUsed_by_people()));
//
//        //holder.timestamp.setText(recipe.getTimestamp());
////
//        Log.w(TAG,"Image is COOL");
//        Glide.with(context)
//                .load(postList.get(position).getImage_url())
//                .into(holder.image);

        Glide.with(context)
                .load("https://subshop.kz/images/organizations/slider2.jpg")
                .into(holder.image1);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public interface OnNoteListener{

        void onNoteClick(int position);


    }

}