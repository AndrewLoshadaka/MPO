package com.company;

import java.io.*;
import java.time.LocalDateTime;

public class Main {
    private final Application application = new Application();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, InterruptedException {
        Main main = new Main();
        main.mainMenu();
    }

    public void mainMenu() throws IOException, InterruptedException {
        application.addTimeOnFile();
        System.out.println("\t\tМеню");
        System.out.println("/--------------------------------\\");
        System.out.println("| 1 | Добавить новую запись      |");
        System.out.println("| 2 | Посмотреть данные в файле  |");
        System.out.println("| 3 | Поиск записи для поездки   |");
        System.out.println("| 4 | Редактирование записи      |");
        System.out.println("| 5 | Удаление записи по id      |");
        System.out.println("| 6 | Сортировка файла           |");
        System.out.println("| 7 | Очистка файла              |");
        System.out.println("| 0 | Выход                      |");
        System.out.println("\\--------------------------------/");
        System.out.println("Введите номер команды");
        String button = reader.readLine();

        while (!checkInt(button) || (Integer.parseInt(button) > 10)){
            System.out.println("Введите число");
            button = reader.readLine();
        }
        switch (button) {
            case "1" -> enterData();
            case "2" -> application.showInform();
            case "3" -> showSearchData();
            case "4" -> selectParamForEdit();
            case "5" -> remove();
            case "6" -> {
                application.sortFile();
                System.out.println("Сортировка выполнена!");
                application.showInform();
            }
            case "7" -> {
                application.clearFile();
                System.out.println("Файл очищен!");
            }
            case "0" -> System.exit(1);
        }
        System.out.println("/--------------------------------\\");
        System.out.println("| 1 | Вернуться в меню           |");
        System.out.println("| 0 | Выход                      |");
        System.out.println("\\--------------------------------/");
        switch (reader.readLine()){
            case "1" -> mainMenu();
            case "0" -> System.exit(1);
        }
    }

    public void enterData() throws IOException, InterruptedException {
        String id, name, route, route1, date;
        System.out.println("Введите id: ");
        id = reader.readLine();
        while (!checkInt(id)) {
            System.out.println("Введите число!");
            id = reader.readLine();
        }
        System.out.println("Введите имя и фамилию или марку и номер автомобиля:");
        name = reader.readLine();
        System.out.println("Введите начало маршрута:");
        route = reader.readLine();
        System.out.println("Введите конец маршрута:");
        route1 = reader.readLine();
        System.out.println("Введите дату поездки в формате ГГГГ-ММ-ДД:");
        date = reader.readLine();
        while (!checkDate(date)) {
            System.out.println("Введите новую дату!");
            date = reader.readLine();
        }

        System.out.println("/---------------------------\\");
        System.out.println("| 1 | добавить в файл       |");
        System.out.println("| 0 | не добавлять в файл   |");
        System.out.println("\\---------------------------/");
        int button = Integer.parseInt(reader.readLine());
        switch (button) {
            case 1 -> {
                String setRoute = route + "::" + route1;
                application.add(name, setRoute, date, id);
                System.out.println("Запись добавлена!");
            }
            case 0 -> {
                System.out.println("Запись не добавлена!");
                mainMenu();
            }
        }
    }

    public void showSearchData() throws IOException {
        System.out.println("Введите начало маршрута:");
        String route = reader.readLine();
        System.out.println("Введите конец маршрута:");
        String route1 = reader.readLine();
        System.out.println("\nСписок попутчиков: ");
        application.searchByRoute(route, route1);
    }

    public void selectParamForEdit() throws IOException {
        //application.showInform();
        System.out.println("Введите id записи:");
        int id = Integer.parseInt(reader.readLine());
        System.out.println("/--------------------------\\");
        System.out.println("| 1 | Редактировать имя    |");
        System.out.println("| 2 | Редактировать маршрут|");
        System.out.println("| 3 | Редактировать дату   |");
        System.out.println("\\--------------------------/");
        System.out.println("Введите команду");
        int command = Integer.parseInt(reader.readLine());
        switch (command) {
            case 1 -> System.out.println("Введите новое имя");
            case 3 -> System.out.println("Введите новую дату в формате ГГГГ-ММ-ДД");
        }
        application.editData(id, command);
        System.out.println("Данные изменены!");
    }

    public void remove() throws IOException {
        application.showInform();
        System.out.println("Введите id:");
        application.remove(Integer.parseInt(reader.readLine()));
        System.out.println("Запись успешно удалена!");
    }

    //метод для проверки даты на корректность
    public boolean checkDate(String date) {
        if(date.toCharArray().length != 10) return false;
        LocalDateTime dateTime = LocalDateTime.now();
        String[] check = dateTime.toString().substring(0, 10).split("-");
        String[] dateArray = date.split("-");
        if(dateArray.length != 3) return false;
        if(!checkInt(dateArray[0]) || !checkInt(dateArray[1]) || !checkInt(dateArray[2])) return false;
        if (Integer.parseInt(dateArray[1]) > 12) return false;
        if ((dateArray[1].equals("01") || dateArray[1].equals("03") || dateArray[1].equals("05") || dateArray[1].equals("07") ||
                dateArray[1].equals("10") || dateArray[1].equals("12")) && Integer.parseInt(dateArray[2]) > 31)
            return false;
        if ((dateArray[1].equals("04") || dateArray[1].equals("06") || dateArray[1].equals("09") || dateArray[1].equals("11"))
                && Integer.parseInt(dateArray[2]) > 30) return false;
        if (dateArray[1].equals("02") && Integer.parseInt(dateArray[2]) > 28) return false;
        return Integer.parseInt(dateArray[0] + dateArray[1] + dateArray[2]) >= Integer.parseInt(check[0] + check[1] + check[2]);
    }

    //метод для проверки int
    public boolean checkInt(String data) {
        try {
            Integer.parseInt(data);
            return Integer.parseInt(data) >= 0;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}


