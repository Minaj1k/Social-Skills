package cz.socialskills.minaj.socialskills;

import android.graphics.Bitmap;

/**
 * Created by Minaj on 13.05.2017.
 */

public class Slide {
    int img;
    Bitmap bmp;
    String path;
    String text;

    public Slide(int img, String text) {
        this.img = img;
        this.text = text;
    }
    public Slide(Bitmap bmp, String text) {
        this.bmp = bmp;
        this.text = text;
    }
    public Slide(String path, String text){
        this.path = path;
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public String getText() {
        return text;
    }

    public String getPath() { return path; }

}
