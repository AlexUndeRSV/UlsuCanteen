package com.lynx.formi.ulsucanteen.domain.dataclass;

import java.util.List;

public class Order {

    private List<Food> foodList;
    private String key;

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
