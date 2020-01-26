package licenta.books.cleanbud.View.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class IncomeType {
    @Id
    long id;
    private String titleIncome;

    private ToMany<Income> incomes;


    public ToMany<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(ToMany<Income> incomes) {
        this.incomes = incomes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleIncome() {
        return titleIncome;
    }

    public void setTitleIncome(String titleIncome) {
        this.titleIncome = titleIncome;
    }
}
