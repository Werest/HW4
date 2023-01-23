import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> personCollection = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            personCollection.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //1 Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        long countUnderAge = personCollection.stream().filter(x -> x.getAge() < 18).count();
        System.out.println("Кол-во несовершеннолетних = " + countUnderAge);

        //2 Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> listConscript = personCollection.stream()
                .filter(x -> (x.getAge() >= 18 && x.getAge() <= 27 && x.getSex() == Sex.MAN))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников:");
        listConscript.forEach(System.out::println);

        //3 Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
        // (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
        List<Person> workablePerson = personCollection.stream()
                .filter(x -> x.getEducation() == Education.HIGHER)
                .filter(x -> x.getAge() >= 18)
                .filter(x -> (x.getAge() < 65 && x.getSex() == Sex.MAN)
                        || (x.getAge() < 60 && x.getSex() == Sex.WOMAN))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        System.out.println("Список потенциально работоспособных людей с высшим образованием");
        workablePerson.forEach(System.out::println);


    }
}