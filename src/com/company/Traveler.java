package com.company;

public class Traveler {
    private String name;
    private Route routeOfTraveler;
    private String dateOfTravel;
    public void setName(String name) {
        this.name = name;
    }

    public void setRouteOfTraveler(Route routeOfTraveler) {
        this.routeOfTraveler = routeOfTraveler;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }
    public Traveler(String name,String dateOfTravel, Route routeOfTraveler) {
        this.name = name;
        this.dateOfTravel = dateOfTravel;
        this.routeOfTraveler = routeOfTraveler;
    }

    public String getName() {
        return name;
    }

    public Route getRouteOfTraveler() {
        return routeOfTraveler;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }
}
