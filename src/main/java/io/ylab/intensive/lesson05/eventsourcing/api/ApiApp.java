package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        Person person1 = new Person(1001L, "Ivan", "Ivanov", "Ivanovich");
        Person person2 = new Person(1002L, "Petr", "Petrov", "Petrovich");
        Person person3 = new Person(1003L, "Александр", "Зайцев", "Иванович");
        personApi.savePerson(person1.getId(), person1.getName(), person1.getLastName(), person1.getMiddleName());
        personApi.savePerson(person2.getId(), person2.getName(), person2.getLastName(), person2.getMiddleName());
        personApi.savePerson(person3.getId(), person3.getName(), person3.getLastName(), person3.getMiddleName());
        System.out.println(personApi.findAll().toString());
        System.out.println(personApi.findPerson(person1.getId()));
        System.out.println(personApi.findPerson(person2.getId()));
        personApi.savePerson(person1.getId(), "Сидор", "Сидоров", "Сидорович");
        System.out.println(personApi.findAll().toString());
        personApi.deletePerson(person1.getId());
        System.out.println(personApi.findPerson(person1.getId()));
        personApi.deletePerson(person1.getId());
    }
}
