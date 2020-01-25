package licenta.books.cleanbud.View.Models;


import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class User {
    @Id
    public long id;

    private String name;
    private String email;
    private String mobilePhone;
    private String password;

    @Backlink
    ToMany<Budget> budgets;


    public ToMany<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(ToMany<Budget> budgets) {
        this.budgets = budgets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
