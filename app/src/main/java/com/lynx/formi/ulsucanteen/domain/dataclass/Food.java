package com.lynx.formi.ulsucanteen.domain.dataclass;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable{

    public String imgUrl;
    public String title;
    public String id;
    public String categoryId;
    public String description;
    public String price;

    public String count;

    public Food(){}

    protected Food(Parcel in) {
        imgUrl = in.readString();
        title = in.readString();
        id = in.readString();
        categoryId = in.readString();
        description = in.readString();
        price = in.readString();
        count = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
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
