import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organization {

    String name;
    Address address;
    String phoneNumber;
    String INN;
    String OGRN;
    LocalDate foundationDate;
    List<Securities> securitiesList = new ArrayList<Securities>();

    public String getName() {
        return name;
    }

    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    public void addSecurities(Securities s) {
        securitiesList.add(s);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public void setOGRN(String OGRN) {
        this.OGRN = OGRN;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }

    public List<Securities> getSecuritiesList() {
        return securitiesList;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", INN='" + INN + '\'' +
                ", OGRN='" + OGRN + '\'' +
                ", foundationDate='" + foundationDate + '\'' +
                ", securitiesList=" + securitiesList +
                '}';
    }
}
