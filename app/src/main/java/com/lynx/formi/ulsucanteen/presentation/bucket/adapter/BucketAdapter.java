package com.lynx.formi.ulsucanteen.presentation.bucket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.utils.OnCountChangeListener;
import com.lynx.formi.ulsucanteen.other.utils.OnListItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.LootViewHolder> {

    private OnCountChangeListener onCountChangeListener = null;

    public void setOnCountChangeListener(final OnCountChangeListener onCountChangeListener) {
        this.onCountChangeListener = onCountChangeListener;
    }

    private final Context ctx;
    private final List<Food> foodList;

    public BucketAdapter(Context ctx) {
        this.ctx = ctx;
        foodList = new ArrayList<>();
    }

    public void setFoodList(final List<Food> foodList) {
        if (foodList == null) return;
        this.foodList.clear();
        this.foodList.addAll(foodList);
    }

    @NonNull
    @Override
    public LootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loot_eat_item, parent, false);
        return new LootViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LootViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.txtCount.setText(food.count);

        holder.txtTitle.setText(food.title);
        holder.txtPrice.setText(String.valueOf(Integer.valueOf(food.price) * Integer.valueOf(food.count)));
        Glide.with(ctx).load(food.imgUrl).into(holder.imgPhoto);

        holder.btnIncrement.setOnClickListener((v) ->
        {
            if(onIncrementClick(Integer.valueOf(food.count), holder.btnIncrement, holder.btnDecrement)){
                food.count = String.valueOf(Integer.valueOf(food.count) + 1);

                if (onCountChangeListener != null)
                onCountChangeListener.onIncrementClick(food.id, Integer.valueOf(food.price));
            }

            holder.txtCount.setText(food.count);
            holder.txtPrice.setText(String.valueOf(Integer.valueOf(food.price) * Integer.valueOf(food.count)));
        });

        holder.btnDecrement.setOnClickListener((v) ->{
            if (onDecrementClick(Integer.valueOf(food.count), holder.btnDecrement, holder.btnIncrement)) {
                food.count = String.valueOf(Integer.valueOf(food.count) - 1);
            if (onCountChangeListener != null)
                onCountChangeListener.onDecrementClick(food.id, Integer.valueOf(food.price));
            }

            holder.txtCount.setText(food.count);
            holder.txtPrice.setText(String.valueOf(Integer.valueOf(food.price) * Integer.valueOf(food.count)));
        });
    }

    private boolean onDecrementClick(final int count, final ImageButton btnDecrement, final ImageButton btnIncrement) {
        if (count < 2) {
//            btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_default));
            btnDecrement.setClickable(false);
            return false;
        }

        if(count == 2){
//            btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_default));
            btnDecrement.setClickable(false);
            return true;
        }

//        btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_count));
        btnIncrement.setClickable(true);

        return true;
    }

    private boolean onIncrementClick(final int count, final ImageButton btnIncrement, final ImageButton btnDecrement) {
        if (count > 8) {
//            btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_default));
            btnIncrement.setClickable(false);
            return false;
        }

        if(count == 8){
//            btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_default));
            btnIncrement.setClickable(false);
            return true;
        }

//        btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_count));
        btnDecrement.setClickable(true);
        return true;
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class LootViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView txtTitle, txtPrice, txtCount;

        private ImageButton btnIncrement, btnDecrement;

        public LootViewHolder(View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgLootEatPhoto);
            txtPrice = itemView.findViewById(R.id.txtLootEatPrice);
            txtTitle = itemView.findViewById(R.id.txtLootEatTitle);

            btnDecrement = itemView.findViewById(R.id.btnLootCountMinus);
            btnIncrement = itemView.findViewById(R.id.btnLootCountPlus);
            txtCount = itemView.findViewById(R.id.txtLootEatCount);
        }
    }
}
