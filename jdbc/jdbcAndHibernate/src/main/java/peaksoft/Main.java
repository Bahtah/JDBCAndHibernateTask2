package peaksoft;

import peaksoft.dao.UserDao;
import peaksoft.dao.UserDaoHibernateImpl;
import peaksoft.model.User;
import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;
import peaksoft.util.Util;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        User user = new User("Zamira", "Samira", (byte)21);
        //userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        //userService.removeUserById(1);
        //System.out.println(userService.getAllUsers());
        //userService.cleanUsersTable();
        //userService.dropUsersTable();

        //dbcQuery();
        //dateMethod();
    }

    private static void jdbcQuery() {
        UserService userService = new UserServiceImpl();

        /*-----------------------------------------Создание таблицы-----------------------------------*/
        userService.createUsersTable();


        /*-----------------------------------------Добавление пользователя----------------------------*/
        userService.saveUser("Нейм", "ЛастНеймович", (byte) 20);


        /*-----------------------------------------Создание и вывод на консоль List-------------------*/

        User user = new User("Бредд", "Пит", (byte) 20);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());

        User user2 = new User("Замира", "Келдибаева", (byte) 21);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        User user3 = new User("Нурбек", "Камилов", (byte) 22);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        List<User> userList = userService.getAllUsers();
        System.out.println(userList);

        /*-----------------------------------------Очистить таблицу------------------------------------*/

        userService.cleanUsersTable();

        /*-----------------------------------------Удалить таблицу-------------------------------------*/

        userService.dropUsersTable();
    }

    public static void dateMethod() {
        UserService userService = new UserServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Создать таблицу user");
            System.out.println("2. Добавить пользователя");
            System.out.println("3. Удалить пользователя по id");
            System.out.println("4. Удалить таблицу");
            System.out.println("5. Очистить таблицу ");
            System.out.println("6. Получить LIST пользователей");
            System.out.println("7. Выйти");

            int command = scanner.nextInt();

            switch (command){
                case 1:
                    userService.createUsersTable();
                    break;
                case 2:
                   try {
                       System.out.println("Введите имя");
                       String name = scanner.next();

                       System.out.println("Введите фамилию");
                       String lastname = scanner.next();

                       System.out.println("Введите возраст");
                       int age = scanner.nextInt();

                       userService.saveUser(name, lastname, (byte) age);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                    break;
                case 3:
                    System.out.println("Введите id пользователя");
                    long id = scanner.nextLong();
                    userService.removeUserById(id);
                    break;
                case 4:
                    userService.dropUsersTable();
                    break;
                case 5:
                    userService.cleanUsersTable();
                    break;
                case 6:
                    System.out.println("Создайте обьект пользователя");
                    User user;
                    user = new User();
                    System.out.println("ID");
                    long ID = scanner.nextLong();
                    user.setId(ID);

                    System.out.println("Введите имя");
                    String name = scanner.next();
                    user.setName(name);

                    System.out.println("Введите фамилию");
                    String lastName = scanner.next();
                    user.setLastName(lastName);

                    System.out.println("Введите возраст");
                    long age = scanner.nextLong();
                    user.setAge((byte)age);

                    userService.saveUser(user.getName(), user.getLastName(), user.getAge());

                    List<User> userList = userService.getAllUsers();
                    System.out.println(userList);
                    break;
                case 7:
                    System.out.println("Вы закончили работу");
                    System.exit(0);
            }
        }
    }
}
