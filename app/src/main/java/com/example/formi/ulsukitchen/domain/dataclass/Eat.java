package com.example.formi.ulsukitchen.domain.dataclass;

import android.os.Parcel;
import android.os.Parcelable;

public class Eat implements Parcelable{

    public String imgUrl;
    public String title;
    public String id;
    public String categoryId;
    public String description;
    public String price;

    public String count;

    public Eat(){}

    protected Eat(Parcel in) {
        imgUrl = in.readString();
        title = in.readString();
        id = in.readString();
        categoryId = in.readString();
        description = in.readString();
        price = in.readString();
        count = in.readString();
    }

    public static final Creator<Eat> CREATOR = new Creator<Eat>() {
        @Override
        public Eat createFromParcel(Parcel in) {
            return new Eat(in);
        }

        @Override
        public Eat[] newArray(int size) {
            return new Eat[size];
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
        dest.writeString(categoryId);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(count);
    }
}
