package practica1sd;

public class Circulo {
private static double radio = 5;
private double area;

public Circulo(double r) { 
    this.area = Math.PI * Math.pow(r, 2);
}
public double getArea() {
    return area;
}
public void showArea() {
    System.out.println("Area: " + getArea());
}

public static void main(String[] args) {
    Circulo c = new Circulo(radio);
    c.showArea();
    }
}
