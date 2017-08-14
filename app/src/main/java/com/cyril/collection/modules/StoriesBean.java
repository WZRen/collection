package com.cyril.collection.modules;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cyril on 2017/8/11.
 */

public class StoriesBean implements Serializable {
    private ExtraField extraField;
    private String title;
    private String ga_prefix;
    private boolean multipic;
    private int type;
    private long id;
    private List<String> images;

    public StoriesBean(ExtraField extraField) {
        this.extraField = extraField;
    }

    public ExtraField getExtraField() {
        return extraField;
    }

    public void setExtraField(ExtraField extraField) {
        this.extraField = extraField;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
