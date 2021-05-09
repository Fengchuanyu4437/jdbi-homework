package person;

import com.github.javafaker.Faker;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:person");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            PersonDao personDao = handle.attach((PersonDao.class));
            personDao.createTable();
            int number = 3;
         /*  try {
                number = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                System.out.println("first argument should be an integer.");
                System.exit(1);
            }
          */
            var faker = new Faker();
            faker.name().fullName();
            for (int i = 0; i < number; i++) {
                var person = Person.builder()
                        .id(getId())
                        .name(getName())
                        .birthDate(getDate())
                        .gender(getGender())
                        .email(getEmail())
                        .phone(getPhone())
                        .profession(getEProfession())
                        .married(getMarried())
                        .build();
                personDao.insert(person);
            }
            personDao.find(1).get();
            personDao.delete(2);
            personDao.findAll().stream().forEach(System.out::println);
        }
    }

    public static int count=0;
    public static int getId(){
        count++;
        return count;
    }
    public static String getName(){
        var faker = new Faker();
        return faker.name().fullName();
    }
    public static LocalDate getDate(){
        var faker = new Faker();
        Date date;
        date=faker.date().birthday();
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDate;
    }
    public static Person.Gender getGender(){
        var faker = new Faker();
        return faker.options().option(Person.Gender.values());
    }
    public static String getEmail(){
        var faker = new Faker();
        return faker.internet().emailAddress();
    }
    public static String getPhone(){
        var faker = new Faker();
        return faker.phoneNumber().cellPhone();
    }
    public static String getEProfession(){
        var faker = new Faker();
        return faker.company().profession();
    }
    public static boolean getMarried(){
        var faker = new Faker();
        return faker.bool().bool();
    }

}