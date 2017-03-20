package app.informatik.lernen;

/**
 * Created by fabia on 2/16/2017.
 */

public class Thema {

    private int completeCode = -1;

    private String titel ="";
    private String text="";
    private String img_link="";

    public Thema() {

    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public int getCompleteCode() {
        return completeCode;
    }

    public void setCompleteCode(int c) {
        completeCode = c;
    }
}
