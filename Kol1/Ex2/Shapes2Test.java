package Ex2;


//import java.io.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.DoubleSummaryStatistics;
//import java.util.List;
//
//
//// DoubleSummaryStatistics stats = areas.stream().mapToDouble(Double::doubleValue).summaryStatistics();
////
////            minArea = stats.getMin();
////            maxArea = stats.getMax();
////            averageArea = stats.getAverage();
//
//
//
//
//class IrregularCanvasException extends Exception
//{
//    public IrregularCanvasException(String message)
//    {
//        super(message);
//    }
//}
//
//
//class Shapes implements Comparable<Shapes>
//{
//    String ID;
//    int total_shapes;
//    int total_circles;
//    int total_squares;
//    double min_area;
//    double max_area;
//    double average_area;
//    double total_area;
//
//    public Shapes(String ID, int total_shapes, int total_circles, int total_squares, double min_area, double max_area, double average_area, double total_area)
//    {
//
//
//
//        this.ID = ID;
//        this.total_shapes = total_shapes;
//        this.total_circles = total_circles;
//        this.total_squares = total_squares;
//        this.min_area = min_area;
//        this.max_area = max_area;
//        this.average_area = average_area;
//        this.total_area = total_area;
//    }
//
//    public String getID() {
//        return ID;
//    }
//
//    public int getTotal_shapes() {
//        return total_shapes;
//    }
//
//    public int getTotal_circles() {
//        return total_circles;
//    }
//
//    public int getTotal_squares() {
//        return total_squares;
//    }
//
//    public double getMin_area() {
//        return min_area;
//    }
//
//    public double getMax_area() {
//        return max_area;
//    }
//
//    public double getAverage_area() {
//        return average_area;
//    }
//
//    public double getTotal_area() {
//        return total_area;
//    }
//
//
//
//    @Override
//    public int compareTo(Shapes o)
//    {
//        return Double.compare(this.getTotal_area(),o.getTotal_area());
//    }
//
//    @Override
//    public String toString()
//    {
//        return String.format("%s %d %d %d %.2f %.2f %.2f",ID,total_shapes,total_circles,total_squares,min_area,max_area,average_area);
//    }
//}
//
//
//class ShapesApplication
//{
//   int maxArea;
//   List<Shapes> shapesList;
//
//    public ShapesApplication(int maxArea)
//    {
//        this.maxArea = maxArea;
//        shapesList = new ArrayList<>();
//    }
//
//    public void readCanvases(InputStream inputStream)
//    {
//
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//
//
//
//        br.lines().forEach(line ->
//        {
//
//            double totalArea = 0;
//            int countAllShapes = 0;
//            int countAllCircles = 0;
//            int countAllSquares = 0;
//            double minArea = 0;
//            double max_Area = 0;
//            double averageArea=0;
//
//            List<Double> areas = new ArrayList<>();
//
//            String [] parts = line.split("\\s+");
//            String id = parts[0];
//            double a = 0;
//
//            for (int i=1;i< parts.length;i+=2)
//            {
//               if(parts[i].equals("C"))
//               {
//                   a = Math.PI * Math.pow(Integer.parseInt(parts[i+1]),2);
//                   countAllCircles++;
//               }
//                if(parts[i].equals("S"))
//               {
//                   a = Math.pow(Integer.parseInt(parts[i+1]),2);
//                   countAllSquares++;
//               }
//
//                totalArea += a;
//                countAllShapes++;
//                areas.add(a);
//
//            }
//
//
//
//
//
//            DoubleSummaryStatistics stats = areas.stream().mapToDouble(Double::doubleValue).summaryStatistics();
//
//            minArea = stats.getMin();
//            max_Area = stats.getMax();
//            averageArea = stats.getAverage();
//
//            //Shapes shapes = new Shapes(id,countAllShapes,countAllCircles,countAllSquares,minArea,max_Area,averageArea,totalArea);
//
//
//            shapesList.add (new Shapes(id,countAllShapes,countAllCircles,countAllSquares,minArea,max_Area,averageArea,totalArea));
//
//
//        });
//
//    }
//
//    public void printCanvases(OutputStream outputStream)
//    {
//
//        PrintWriter pw = new PrintWriter(outputStream);
//
//        Collections.sort(shapesList);
//        Collections.reverse(shapesList);
//
//
//        for (int i=0;i<shapesList.size();i++)
//        {
//            pw.println(shapesList.get(i));
//        }
//
//
//        pw.flush();
//
//    }
//}
//
//public class Shapes2Test {
//
//    public static void main(String[] args) {
//
//        ShapesApplication shapesApplication = new ShapesApplication(10000);
//
//        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
//        shapesApplication.readCanvases(System.in);
//
//        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
//        shapesApplication.printCanvases(System.out);
//
//
//    }
//}


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

// LOOK AGAIN

//list.stream().sorted(Collections.reverseOrder()).forEach(pw::println);

class IrregularCanvasException extends Exception
{
    IrregularCanvasException(String id, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", id, maxArea));
    }
}

enum Type {
    Square,
    Circle
}

class Shape implements Comparable<Shape>
{
    protected Type type;
    protected int size;

    Shape(Type type, int size) {
        this.type = type;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public double getArea() {
        return 0;
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(getArea(), o.getArea());
    }
}

class Square extends Shape {
    public Square(int size) {
        super(Type.Square, size);
    }

    @Override
    public double getArea() {
        return size * size;
    }
}

class Circle extends Shape {
    public Circle(int size) {
        super(Type.Circle, size);
    }

    @Override
    public double getArea() {
        return size * size * Math.PI;
    }
}

class Canvas implements Comparable<Canvas>
{
    private String canvasId;
    private ArrayList<Shape> shapes;

    Canvas()
    {
        canvasId = "";
        shapes = new ArrayList<>();
    }

    Canvas(String canvasId, ArrayList<Shape> shapes) {
        this.canvasId = canvasId;
        this.shapes = shapes;
    }

    public String getCanvasId() {
        return canvasId;
    }

    public double minArea() {
        return Collections.min(shapes).getArea();
    }

    public double maxArea() {
        return Collections.max(shapes).getArea();
    }

    public double averageArea() {
        return shapes.stream().mapToDouble(Shape::getArea).average().orElse(0);
    }

    public double getTotalArea() {
        return shapes.stream().mapToDouble(Shape::getArea).sum();
    }

    public long getSquares() {
        return shapes.stream().filter(x -> x.getType().equals(Type.Square)).count();
    }

    public long getCircles() {
        return shapes.stream().filter(x -> x.getType().equals(Type.Circle)).count();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f", canvasId, shapes.size(), getCircles(), getSquares(), minArea(), maxArea(), averageArea());
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(getTotalArea(), o.getTotalArea());
    }
}

class ShapesApplication
{
    private ArrayList<Canvas> list;
    private double maxArea;
    private

    ShapesApplication()
    {
        list = new ArrayList<>();
        maxArea = 0;
    }

    ShapesApplication(double maxArea)
    {
        this.list = new ArrayList<>();
        this.maxArea = maxArea;
    }

    public void addCanvas(Canvas c) throws IrregularCanvasException
    {
        if(c.maxArea() > maxArea) {
            throw new IrregularCanvasException(c.getCanvasId(), maxArea);
        }
        list.add(c);
    }

    public void readCanvases(InputStream inputStream) {
        Scanner input = new Scanner(inputStream);

        while(input.hasNextLine())
        {
            String canvas = input.nextLine();

            String canvasId = canvas.split(" ")[0];
            String [] str = canvas.split(" ");

            ArrayList<Shape> shapes = new ArrayList<>();

            for(int i = 1; i < str.length; i += 2)
            {
                int value = Integer.parseInt(str[i + 1]);

                if(Objects.equals(str[i], "C"))
                {
                    shapes.add(new Circle(value));
                } else if(Objects.equals(str[i], "S"))
                {
                    shapes.add(new Square(value));
                }
            }

            try
            {
                addCanvas(new Canvas(canvasId, shapes));
            } catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printCanvases(OutputStream outputStream)
    {
        PrintWriter pw = new PrintWriter(outputStream);
        list.stream().sorted(Collections.reverseOrder()).forEach(pw::println);
        pw.flush();
    }
}

public class Shapes2Test {
    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);
    }
}