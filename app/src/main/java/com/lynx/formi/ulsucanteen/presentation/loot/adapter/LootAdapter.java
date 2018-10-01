package com.lynx.formi.ulsucanteen.presentation.loot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LootAdapter extends RecyclerView.Adapter<LootAdapter.LootViewHolder> {

    private Context ctx;
    private List<Eat> eatList;

    public LootAdapter(Context ctx) {
        this.ctx = ctx;
        eatList = new ArrayList<>();
    }

    public void setEatList(List<Eat> eatList) {
        if(eatList == null) return;
        this.eatList = eatList;
    }

    @NonNull
    @Override
    public LootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loot_eat_item, parent, false);
        return new LootViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LootViewHolder holder, int position) {
        Eat eat = eatList.get(position);

        holder.txtTitle.setText(eat.title);
        holder.txtPrice.setText(eat.price);
        Picasso.with(ctx).load(eat.imgUrl).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return eatList.size();
    }

    public class LootViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgPhoto;
        private TextView txtTitle, txtPrice;

        public LootViewHolder(View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgLootEatPhoto);
            txtPrice = itemView.findViewById(R.id.txtLootEatPrice);
            txtTitle = itemView.findViewById(R.id.txtLootEatTitle);
        }
    }
}
