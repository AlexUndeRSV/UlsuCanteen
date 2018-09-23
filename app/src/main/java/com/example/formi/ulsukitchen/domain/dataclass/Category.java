package com.example.formi.ulsukitchen.domain.dataclass;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable{
    public String imgUrl;
    public String title;
    public String id;

    public Category() {
    }

    public Category(String imgUrl, String title, String id) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.id = id;
    }

    private Category(Parcel in) {
        imgUrl = in.readString();
        title = in.readString();
        id = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
        dest.writeString(title);
        dest.writeString(id);
    }
}
