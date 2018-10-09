package com.lynx.formi.ulsucanteen.presentation.menu.eat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;

import java.util.ArrayList;
import java.util.List;

public class EatAdapter extends RecyclerView.Adapter<EatAdapter.EatViewHolder> {

    public interface EatClickListener {
        void onButtonAddClickListener(int position);
        void onItemClickListener(int position);
    }

    private EatClickListener eatClickListener = null;

    public void setEatClickListener(EatClickListener eatClickListener) {
        this.eatClickListener = eatClickListener;
    }

    private Context ctx;
    private List<Food> foodList;

    public EatAdapter(Context ctx) {
        this.ctx = ctx;
        foodList = new ArrayList<>();
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public EatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eat_item, parent, false);
        return new EatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EatViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.txtTitle.setText(food.title);
        holder.txtPrice.setText(food.price);
        Glide.with(ctx).load(food.imgUrl).into(holder.imgPhoto);
//        Picasso.with(ctx).load(food.imgUrl).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class EatViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView txtTitle, txtPrice;
        private Button btnAddLoot;

        private View cardEat;

        public EatViewHolder(View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgEatPhoto);
            txtPrice = itemView.findViewById(R.id.txtEatPrice);
            txtTitle = itemView.findViewById(R.id.txtEatTitle);
            btnAddLoot = itemView.findViewById(R.id.btnShowDialog);
            btnAddLoot.setOnClickListener((v) -> {
                if (eatClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        eatClickListener.onButtonAddClickListener(position);
                    }
                }
            });

            cardEat = itemView.findViewById(R.id.eatCard);
            cardEat.setOnClickListener((v)->{
                if(eatClickListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        eatClickListener.onItemClickListener(position);
                    }
                }
            });
        }
    }
}
