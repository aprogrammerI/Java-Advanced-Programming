package Ex9_Triple;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//class Triple<T extends Number>

//public double max()
//    {
//        return Math.max(Math.max(a.doubleValue(),b.doubleValue()),c.doubleValue());
//    }
//
//    public double average()
//    {
//        return (a.doubleValue() + b.doubleValue() + c.doubleValue())/3.0;
//    }

//List<T> sortedList = Arrays.asList(a, b, c).stream()
//                .sorted((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()))
//                .collect(Collectors.toList());
//
//
//        a = sortedList.get(0);
//        b = sortedList.get(1);
//        c = sortedList.get(2);

//or


//List<T> list = new ArrayList<>();
//        list.add(a);
//        list.add(b);
//        list.add(c);
//
//        list.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));
//        a = list.get(0);
//        b = list.get(1);
//        c = list.get(2);

class Triple<T extends Number>
{


    T a;
    T b;
    T c;

    public Triple(T a, T b, T c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double max()
    {
        return Math.max(Math.max(a.doubleValue(),b.doubleValue()),c.doubleValue());
    }

    public double avarage()
    {
        return (a.doubleValue() + b.doubleValue() + c.doubleValue())/3.0;
    }

    public void sort()
    {
//
//        List<T> sortedList = Arrays.asList(a, b, c).stream()
//                .sorted((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()))
//                .collect(Collectors.toList());
//
//
//        a = sortedList.get(0);
//        b = sortedList.get(1);
//        c = sortedList.get(2);


        List<T> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);

        list.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));
        a = list.get(0);
        b = list.get(1);
        c = list.get(2);


    }

    @Override
    public String toString()
    {
        return String.format("%.2f %.2f %.2f", a.doubleValue(), b.doubleValue(), c.doubleValue());

    }
}

public class TripleTest
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}




