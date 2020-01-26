package licenta.books.cleanbud.View.Models;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Currency {
    @Id
    long id;
    private String currencyName;
    private float value;

    ToOne<User> user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }
}
