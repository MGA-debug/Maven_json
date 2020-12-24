import java.time.LocalDate;
import java.util.List;


public class Securities {

    String name;
    String id;
    Organization owner;
    LocalDate dateStart;
    LocalDate dateFinish;
    List<Currencys> currency = null;



    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(Organization owner) {
        this.owner = owner;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateFinish(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }

    public void setCurrency(List<Currencys> currency) {
        this.currency = currency;
    }

    public LocalDate getDateFinish() {
        return dateFinish;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public List<Currencys> getCurrency() {
        return currency;
    }

    public Organization getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Securities{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", currency=" + currency +
                '}';
    }
}

//switch (s) {
//        case "RUB":
//        for (Securities securities : ls) {
//        List<Currencys> c = securities.getCurrency();
//        c.stream().filter(x -> x.equals(Currencys.RUB)).forEach(x -> System.out.println(securities));
//        }
//        break;
//        case "USD":
//        for (Securities securities : ls) {
//        List<Currencys> c = securities.getCurrency();
//        c.stream().filter(x -> x.equals(Currencys.USD)).forEach(x -> System.out.println(securities));
//        }
//        break;
//        case "EU":
//        for (Securities securities : ls) {
//        List<Currencys> c = securities.getCurrency();
//        c.stream().filter(x -> x.equals(Currencys.EU)).forEach(x -> System.out.println(securities));
//        }

