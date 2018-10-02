package com.lynx.formi.ulsucanteen.domain.dataclass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Category implements Parcelable{
    private String imgUrl;
    private String title;
    private String id;

    private List<Eat> food;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Eat> getFood() {
        return food;
    }

    public void setFood(List<Eat> food) {
        this.food = food;
    }
}
