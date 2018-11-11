package com.lynx.formi.ulsucanteen.presentation.menu.categories.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;
import com.lynx.formi.ulsucanteen.other.utils.OnListItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private OnListItemClickListener onListItemClickListener = null;

    public void setOnListItemClickListener(final OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    private final Context ctx;
    private final List<Category> categoryList;

    public CategoryAdapter(Context ctx){
        this.ctx = ctx;
        this.categoryList = new ArrayList<>();
    }

    public void setCategoryList(final List<Category> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.txtTitle.setText(category.getTitle());
        Glide.with(ctx).load(category.getImgUrl()).into(holder.imgPhoto);
//        Picasso.with(ctx).load(category.imgUrl).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgPhoto;
        private TextView txtTitle;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtTitle = itemView.findViewById(R.id.txtTitle);

            itemView.setOnClickListener((v)->{
                if(onListItemClickListener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        onListItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
