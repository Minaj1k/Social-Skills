package cz.socialskills.minaj.socialskills;

/**
 * Created by Minaj on 21.05.2017.
 */

public class Slideshow {
    Slide[] slides;
    int place;

    public Slideshow(Slide[] slides) {
        this.slides = slides;
    }

    public Slide[] getSlides() {
        return slides;
    }

    public Slide getSlide(int i) { return slides[i];}

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
