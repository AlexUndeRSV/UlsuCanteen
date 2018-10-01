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
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface OnCategoryClickListener{
        void onCategoryClick(String id, String title);
    }

    private OnCategoryClickListener onCategoryClickListener;

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    private Context ctx;
    private List<Category> categoryList;

    public CategoryAdapter(Context ctx){
        this.ctx = ctx;
        this.categoryList = new ArrayList<>();
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
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

        holder.txtTitle.setText(category.title);
        Glide.with(ctx).load(category.imgUrl).into(holder.imgPhoto);
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
                if(onCategoryClickListener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Category category = categoryList.get(position);
                        onCategoryClickListener.onCategoryClick(category.id, category.title);
                    }
                }
            });
        }
    }
}
