package com.example.recyclerviewhorizon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;
    private boolean isLoading = false;


    List<Food> listFood;
    OnItemListener onItemListener;

    FoodAdapter(List<Food> listFood){
        this.listFood = listFood;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == listFood.size()-1 && isLoading){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Khai báo lớp đối tượng để convert kiểu int sang kiểu view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (TYPE_ITEM == viewType){
            View view = layoutInflater.inflate(R.layout.item_food,parent,false);
            return new FoodViewHolder(view);
        }else{
            View view = layoutInflater.inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM){
            ((FoodViewHolder)holder).onBindView(listFood.get(position) , position);
        }
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    class FoodViewHolder extends ViewHolder{

        ImageView img, img_star;
        TextView tvName,tvAddress, tvTag;
        ImageView imgDelete;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.textViewName);
            tvAddress = itemView.findViewById(R.id.textViewAddress);
            tvTag = itemView.findViewById(R.id.textviewTag);
            img_star = itemView.findViewById(R.id.star);
            imgDelete = itemView.findViewById(R.id.imageViewDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemListener != null){
                        onItemListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void onBindView(Food food , int position){
            img.setImageResource(food.getImage());
            tvName.setText(food.getName() + " " + position);
//            NumberFormat formatter = new DecimalFormat("#,###");
//            tvPrice.setText("Giá " + formatter.format(food.getPrice()));
            tvAddress.setText(food.getAddress());
            ArrayList<String> arrTag = food.getTag();
            String stringTag = "";
            for(String s : arrTag) {
                stringTag = stringTag + s + "/" ;
            }
            tvTag.setText(stringTag.substring(0,stringTag.length()-1));
            if(food.isStar()) {
                img_star.setImageResource(R.drawable.ic_star);
            } else {
                img_star.setImageResource(android.R.color.transparent);
            }
        }
    }

    class LoadingViewHolder extends ViewHolder{

        ProgressBar progressBarLoading;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBarLoading = itemView.findViewById(R.id.progressbarLoading);
        }
    }

    public void setOnItemListener(OnItemListener onItemListener){
        this.onItemListener = onItemListener;
    }

    public void addFooterLoading(){
        isLoading = true;
        listFood.add(null);
    }

    public void removeLoading(){
        isLoading = false;
        int position = listFood.size() - 1;
        listFood.remove(position);
        notifyItemRemoved(position);
    }

}