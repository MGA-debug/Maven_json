import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {


        String path = "src/main/resources/JSON.json";
        List<Organization> list = FileEditor.parse(path); //метод для парсинга
  //      FileEditor.showOrgandDayFoundation(list);  //вывод названий и дат создания
      //  FileEditor.expiredContracts(list);  //истекшие контракты
        FileEditor.startOfWork(list);  //работа  с консолью

    }
}

class FileEditor {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/MM/y");
    static DateTimeFormatter dtfDot = DateTimeFormatter.ofPattern("d.MM.y");


    public static List<Organization> parse(String path) {
        Organization organization;
        Address address;
        List<Organization> list = new ArrayList<Organization>();


        try (FileReader fileReader = new FileReader(path)) {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray array = (JSONArray) jsonObject.get("organization");

            for (Object value : array) {
                organization = new Organization();
                JSONObject jb = (JSONObject) value;
                organization.setName((String) jb.get("name"));
                String fDate = (String) jb.get("foundationDate");
                organization.setFoundationDate(LocalDate.parse(fDate, dtf));
                organization.setINN((String) jb.get("INN"));
                organization.setOGRN((String) jb.get("OGRN"));
                organization.setPhoneNumber((String) jb.get("phoneNumber"));

                address = new Address();
                JSONObject structure = (JSONObject) jb.get("address");
                address.setCity((String) structure.get("city"));
                address.setPostalCode((String) structure.get("postalCode"));
                address.setCountry((String) structure.get("country"));
                address.setStreet((String) structure.get("streetAddress"));
                address.setHouseNumber((String) structure.get("houseNumber"));
                organization.setAddress(address);

                JSONArray securitiesArray = (JSONArray) jb.get("securities");
                for (Object o : securitiesArray) {
                    Securities securities = new Securities();
                    JSONObject jbSec = (JSONObject) o;
                    securities.setName((String) jbSec.get("name"));
                    securities.setId((String) jbSec.get("id"));
                    securities.setOwner(organization);

                    String ds = (String) jbSec.get("dateStart");
                    securities.setDateStart(LocalDate.parse(ds, dtf));

                    String df = (String) jbSec.get("dateFinish");
                    securities.setDateFinish(LocalDate.parse(df, dtf));
                    organization.addSecurities(securities);

                    JSONArray cArr = (JSONArray) jbSec.get("currency");
                    List<Currencys> lc = new ArrayList<>();
                    for (Object item : cArr) {
                        String cur = (String) item;
                        switch (cur) {
                            case "RUB":
                                lc.add(Currencys.RUB);
                                break;
                            case "EU":
                                lc.add(Currencys.EU);
                                break;
                            case "USD":
                                lc.add(Currencys.USD);
                                break;
                        }
                    }
                    securities.setCurrency(lc);

                }
                list.add(organization);

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void showOrgandDayFoundation(List<Organization> list) {
    list.forEach(x -> System.out.println("Название организации: " + x.getName() + ", дата основания: " + x.getFoundationDate()));
}


    public static void showOrgandDayFoundation(List<Organization> list, String s, DateTimeFormatter d) { //перегруженный для сроки с консоли, и форматера
        list.stream()
                .filter(x -> x.getFoundationDate().isAfter(LocalDate.parse(s, d)))
                .forEach(x -> System.out.println("Название организации: " + x.getName() + ", дата основания: " + x.getFoundationDate()));
    }


    public static void expiredContracts(List<Organization> list) {
        ArrayList<Securities> listSecurities = new ArrayList<>();
        LocalDate localDate = LocalDate.now();

        list.forEach(x -> listSecurities.addAll(x.getSecuritiesList()));           //все бумаги

       List<Securities> expired = listSecurities.stream()                     //с истешким сроком
                                .filter(x -> x.getDateFinish().isBefore(localDate))
                                .collect(Collectors.toList());

        System.out.println("Просрочены на текущий день: " + localDate);
        expired.forEach(x -> System.out.println("Владлец: "+x.getOwner().getName()+", код: "+x.getId()+", дата истечения: "+x.getDateFinish()));
        System.out.println("Количество ценных бумаг с истекшим сроком: " + expired.size());

    }


    public static void startOfWork(List<Organization> list) {
        List<Securities> ls = new ArrayList<>(); //список всех ценных бумаг
        list.forEach(x -> ls.addAll(x.getSecuritiesList()));

        System.out.println("Введите инетересующую вас валюту или дату. Например: USD, 25-12-2000");
        System.out.println("Для выхода введите exit");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String s = sc.nextLine();
            if (s.equals("exit")) break;

            if (s.matches("\\d{2}/\\d{2}/\\d{2,4}")) {
                showOrgandDayFoundation(list, s, dtf);
            } else if (s.matches("\\d{2}\\.\\d{2}\\.\\d{2,4}")) {
                showOrgandDayFoundation(list, s, dtfDot);
            } else if (s.matches("[A-Z]{2,3}")) {
                for (Securities securities : ls) {
                    List<Currencys> c = securities.getCurrency();
                    switch (s) {
                        case "RUB":
                            //т.к. есть только айдишники, вместо кодов вывожу держателя
                            c.stream().filter(x -> x.equals(Currencys.RUB)).
                                    forEach(x -> System.out.println("Держатель: "+securities.getOwner().getName()+", id: "+securities.getId()));
                            break;
                        case "USD":
                            c.stream().filter(x -> x.equals(Currencys.USD)).
                                    forEach(x -> System.out.println("Держатель: "+securities.getOwner().getName()+", id: "+securities.getId()));
                            break;
                        case "EU":
                            c.stream().filter(x -> x.equals(Currencys.EU)).
                                    forEach(x -> System.out.println("Держатель: "+securities.getOwner().getName()+", id: "+securities.getId()));
                            break;
                    }
                }
            } else
                System.out.println("По запросу ничего не найдено");
        }
    }
}



