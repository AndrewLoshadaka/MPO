package com.company;

/*класс машрут
* начальный и конечный пункты*/

public class Route {
    private String startPoint;
    private String endPoint;

    public Route(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }
}
