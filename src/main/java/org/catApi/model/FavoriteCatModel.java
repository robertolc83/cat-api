package org.catApi.model;

public class FavoriteCatModel {
    private String id;
    private String image_id;
    private ImageModel imageModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public ImageModel getImage() {
        return imageModel;
    }

    public void setImage(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
}
