package Ex1_ShapesApplication;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//can use  Collections.reverse(shapesList); to reverse it or
//return Integer.compare(this.total_squares_perimeter,o.total_squares_perimeter); have the places changed (I think)



class Shapes implements Comparable<Shapes>
{
    String canvas_id;
    int squares_count;
    int total_squares_perimeter;

    public Shapes(String canvas_id, int squares_count, int total_squares_perimeter)
    {
        this.canvas_id = canvas_id;
        this.squares_count = squares_count;
        this.total_squares_perimeter = total_squares_perimeter;
    }

    public String getCanvas_id()
    {
        return canvas_id;
    }

    public int getSquares_count()
    {
        return squares_count;
    }

    public int getTotal_squares_perimeter()
    {
        return total_squares_perimeter;
    }

    @Override
    public String toString()
    {
        return getCanvas_id() + " " + getSquares_count() + " " + getTotal_squares_perimeter();
    }

    @Override
    public int compareTo(Shapes o)
    {
        return Integer.compare(this.total_squares_perimeter,o.total_squares_perimeter);
    }
}



class ShapesApplication
{

    List<Shapes> shapesList;
    public ShapesApplication()
    {
       shapesList= new ArrayList<>();
    }

    public int readCanvases(InputStream inputStream)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        int [] total ={0};

        br.lines().forEach(line ->
        {

            int per = 0;
            int count =0;

            String [] parts = line.split("\\s+");
             String id = parts[0];

             for (int i=1;i< parts.length;i++)
             {
                 int n = Integer.parseInt(parts[i]);
                 per = per +4*n;
                 count++;
                 total[0]++;
             }

             Shapes shapes = new Shapes(id,count,per);

             shapesList.add(shapes);


        });

        return total[0];
    }

    public void printLargestCanvasTo(OutputStream outputStream)
    {

        PrintWriter pw = new PrintWriter(outputStream);

        Collections.sort(shapesList);
        Collections.reverse(shapesList);

        pw.println(shapesList.get(0));

        pw.flush();

    }
}



public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
