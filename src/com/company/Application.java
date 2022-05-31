package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Application {
    private final HashMap<Integer, Traveler> travelerMap = new HashMap<>();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    //метод для добавления в файл с консоли
    public void add(String name, String route, String date, String id) throws IOException{
        String[] temp = route.split("::");
        Route route1 = new Route(temp[0].trim(), temp[1].trim());
        Traveler traveler = new Traveler(name, date, route1);
        travelerMap.putAll(getInformOnFile());
        travelerMap.put(Integer.parseInt(id), checkRepeat(Integer.parseInt(id), traveler));
        clearFile();
        try(FileWriter printWriter = new FileWriter("inform.txt", true)) {
            for(Map.Entry<Integer, Traveler> entry : travelerMap.entrySet()) {
                printWriter.write(createString(entry.getKey(), entry.getValue()));
                printWriter.write("\n");
            }
        }
        travelerMap.clear();
    }

    //метод для проверки повторов
    public Traveler checkRepeat(int id, Traveler traveler) throws IOException{
        Traveler traveler1 = travelerMap.get(id); // в файле
        if(traveler1 != null) {
            Route route = traveler1.getRouteOfTraveler();
            Route route1 = traveler.getRouteOfTraveler();
            System.out.println("Найдены повторы!");
            System.out.println("| 1 | Оставить в файле " + traveler1.getName() + " " + route.getStartPoint() + "-" +
                    route.getEndPoint() + " " + traveler1.getDateOfTravel() + "|");
            System.out.println("| 2 | Добавить в файл " + traveler.getName() + " " + route1.getStartPoint() + "-" +
                    route1.getEndPoint() + " " + traveler.getDateOfTravel() + "|");
            travelerMap.remove(id);
            if (Integer.parseInt(reader.readLine()) == 1) return traveler1;
            else return traveler;
        }
        return traveler;
    }

    //метод для записи даты в файл
    public void addTimeOnFile() throws IOException {
        Date date = new Date();
        String tempDate = date.toString();
        try(FileWriter writer = new FileWriter("date.txt", true)) {
            writer.write(tempDate + "\n");
        } catch (FileNotFoundException e){
            System.err.println("Файл не найден!");
        }
    }

    //создание строки для записи в файл
    public String createString(int a, Traveler traveler){
        StringBuilder line = new StringBuilder();
        Route route = traveler.getRouteOfTraveler();
        line.append(a).append("/").append(traveler.getName()).append("/");
        line.append(route.getStartPoint()).append("::").append(route.getEndPoint());
        line.append("/").append(traveler.getDateOfTravel());
        return line.toString();
    }

    //получение информации из файла
    public HashMap<Integer, Traveler> getInformOnFile(){
        HashMap<Integer, Traveler> tempMap = new HashMap<>();
        try{
            BufferedReader reader;
            reader = new BufferedReader(
                    new FileReader(("inform.txt")));
            String line = reader.readLine();
            while (line != null){
                String[] temp = line.split("/");
                String[] route = temp[2].split("::");
                Route tempRoute = new Route(route[0].trim(), route[1].trim());
                Traveler traveler = new Traveler(temp[1], temp[3], tempRoute);
                tempMap.put(Integer.parseInt(temp[0]), traveler);
                line = reader.readLine();
            }
        } catch (IOException e){
            System.err.println("Файл не найден!");
        }
        return tempMap;
    }

    //поиск попутчиков
    public void searchByRoute(String route, String route1){
        HashMap<Integer, Traveler> mapTravel = new HashMap<>();
        for(Map.Entry<Integer, Traveler> entry : getInformOnFile().entrySet()){
            Route route2 = entry.getValue().getRouteOfTraveler();
            if(route.equals(route2.getStartPoint()) && route1.equals(route2.getEndPoint()))
                mapTravel.put(entry.getKey(), entry.getValue());
        }
    }

    //редактирование записи
    public void editData(int id, int button) throws IOException{
        travelerMap.putAll(getInformOnFile());
        Traveler copy = travelerMap.get(id);
        Route route = travelerMap.get(id).getRouteOfTraveler();
        travelerMap.remove(id);
        try {
            switch (button) {
                case 1 -> copy.setName(reader.readLine());
                case 2 -> {
                    System.out.println("Введите новый начальный пункт");
                    route.setStartPoint(reader.readLine());
                    System.out.println("Введите новый конечный пункт");
                    route.setEndPoint(reader.readLine());
                    travelerMap.get(id).setRouteOfTraveler(route);
                }
                case 3 -> travelerMap.get(id).setDateOfTravel(reader.readLine());
            }
        } catch (IOException e){
            System.err.println();
        }
        travelerMap.put(id, copy);
        try(FileWriter printWriter = new FileWriter("inform.txt", false)) {
            for(Map.Entry<Integer, Traveler> entry : travelerMap.entrySet()) {
                printWriter.write(createString(entry.getKey(), entry.getValue()));
                printWriter.write("\n");
            }
        }
    }

    //просмотр файла
    public void showInform(){
        showInform(getInformOnFile());
    }

    //метод для просмотра данных
    private void showInform(HashMap<Integer, Traveler> temp) {
        if(temp.isEmpty())
            System.out.println("Список пуст!");
        for(Map.Entry<Integer, Traveler> entry : temp.entrySet()){
            Traveler traveler = entry.getValue();
            Route route = traveler.getRouteOfTraveler();
            System.out.println(entry.getKey() + " " + traveler.getName() + " "
                    + route.getStartPoint() + " " + route.getEndPoint() + " " + traveler.getDateOfTravel());
        }
    }

    //удаление по id
    public void remove(int id) throws  IOException{
        travelerMap.putAll(getInformOnFile());
        travelerMap.remove(id);
        try(FileWriter printWriter = new FileWriter("inform.txt", false)) {
            for(Map.Entry<Integer, Traveler> entry : travelerMap.entrySet()) {
                printWriter.write(createString(entry.getKey(), entry.getValue()));
                printWriter.write("\n");
            }
        }
        travelerMap.clear();
    }

    //очистка файла
    public void clearFile() throws IOException{
        try (PrintWriter writer = new PrintWriter("inform.txt", StandardCharsets.UTF_8)){
            writer.write("");
        }
    }

    //сортировка файла по id
    public void sortFile() throws IOException{
        HashMap<Integer, Traveler> tempMap = getInformOnFile();
        ArrayList<String> listRoute = new ArrayList<>();
        for(Map.Entry<Integer, Traveler> entry : tempMap.entrySet()){
            Route route = entry.getValue().getRouteOfTraveler();
            listRoute.add(route.getStartPoint() + "::" + route.getEndPoint());
        }
        clearFile();
        Collections.sort(listRoute);
        try (FileWriter writer = new FileWriter("inform.txt", false)) {
            writer.write("");
            for (String x : listRoute) {
                for(Map.Entry<Integer, Traveler> entry : tempMap.entrySet()){
                    if(x.equals(entry.getValue().getRouteOfTraveler().getStartPoint() + "::" +
                            entry.getValue().getRouteOfTraveler().getEndPoint())){
                        writer.write(createString(entry.getKey(), entry.getValue()));
                        writer.write("\n");
                    }
                }
            }
        } catch (IOException e){
            System.out.println();
        }
    }
}
