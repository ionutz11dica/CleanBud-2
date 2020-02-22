package licenta.books.cleanbud.View.Utils;

public class SpinnerClass {

    Integer idImg;
    String text;

    public SpinnerClass(Integer idImg, String text) {
        this.idImg = idImg;
        this.text = text;
    }

    public Integer getIdImg() {
        return idImg;
    }

    public void setIdImg(Integer idImg) {
        this.idImg = idImg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

