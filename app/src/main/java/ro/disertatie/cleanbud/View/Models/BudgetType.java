package ro.disertatie.cleanbud.View.Models;



public class BudgetType {

    long id;

    String title;

    int idImage;




    public BudgetType(String title, int idImage) {
        this.title = title;
        this.idImage = idImage;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
}
