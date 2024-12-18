package Ex14_;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if(parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}


// vashiot kod ovde
class Car{
    private String manufacturer;
    private String model;
    private int price;
    private float power;

    public Car(String manufacturer, String model, int price, float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public float getPower() {
        return power;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%.0fKW) %d",manufacturer,model,power,price);
    }
}
class CarCollection{
    List<Car>cars;

    public CarCollection() {
        cars = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void sortByPrice(boolean ascending){
        Comparator<Car> comparator= Comparator.comparing(Car::getPrice);
        if (ascending){
            cars = cars.stream()
                    .sorted(comparator.thenComparing(Car::getPower))
                    .collect(Collectors.toList());
        }else {
            cars = cars.stream()
                    .sorted(comparator.thenComparing(Car::getPower).reversed())
                    .collect(Collectors.toList());
        }
    }
    public List<Car> filterByManufacturer(String manufacturer){
        Comparator<Car> comparator = Comparator.comparing(Car::getModel);
        return cars.stream()
                .filter(c->c.getManufacturer().equalsIgnoreCase(manufacturer))
                .sorted(comparator).collect(Collectors.toList());
    }
    public List<Car> getList(){
        return cars;
    }
}
