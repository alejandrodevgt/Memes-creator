package dev.ale.android.memescreator;

/**
 * Created by Norma on 24/05/2018.
 */

public class ListItem {

    private String id;
    private String desc;
    private String imageUrl;

    public ListItem(String id, String desc, String imageUrl) {
        this.id = id;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

