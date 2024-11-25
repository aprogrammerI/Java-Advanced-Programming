package Ex21_F1Race;



import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


// in Lap, we have the Integer.compare()
//but in driver  return this.getBestTime().compareTo(o.getBestTime());

//  10 места за името на возачот (порамнето од лево) и 10 места за времето на најдобриот круг порамнето од десно
//   return String.format("%-10s%10s",name,getBestTime().toString());


class Lap implements Comparable<Lap>
{
    int mm;
    int ss;
    int nn;

    public Lap(int mm, int ss, int nn)
    {
        this.mm = mm;
        this.ss = ss;
        this.nn = nn;
    }


    @Override
    public int compareTo(Lap o)
    {
        if (this.mm == o.mm)
        {
            if (this.ss == o.ss)
            {
                return Integer.compare(this.nn, o.nn);
            } else
            {
                return Integer.compare(this.ss, o.ss);
            }
        }
        else
        {
            return Integer.compare(this.mm, o.mm);
        }
    }


    @Override
    public String toString() {
        return String.format("%d:%02d:%03d",mm,ss,nn);
    }
}

class Driver implements Comparable<Driver>
{
    String name;
    List<Lap> lapsList;

    public Driver(String name, List<Lap> lapsList)
    {
        this.name = name;
        this.lapsList = lapsList;
    }

    public Lap getBestTime()
    {
        Collections.sort(lapsList);
        return lapsList.get(0);
    }


    @Override
    public int compareTo(Driver o) {
        return this.getBestTime().compareTo(o.getBestTime());
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s",name,getBestTime().toString());
    }
}

class F1Race
{

    List<Driver> driverList;

    public F1Race() {
        driverList = new ArrayList<>();
    }

    void readResults(InputStream inputStream) {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        br.lines().forEach(line ->
        {
            String[] parts = line.split(" ");
            String name = parts[0];
            List<Lap> lapList = new ArrayList<>();

            for(int i=1;i< parts.length;i++)
            {
                String[] time = parts[i].split(":");

                int mm = Integer.parseInt(time[0]);
                int ss = Integer.parseInt(time[1]);
                int nn = Integer.parseInt(time[2]);

                Lap lap = new Lap(mm, ss, nn);
                lapList.add(lap);

            };

            Driver driver = new Driver(name, lapList);
            driverList.add(driver);

        });

    }

    void printSorted(OutputStream outputStream) {

        PrintWriter pw = new PrintWriter(outputStream);

        Collections.sort(driverList);


        for (int i=0;i<driverList.size();i++)
        {
            pw.println(i+1 + ". " + driverList.get(i));

        }
        pw.flush();
    }
}



public class F1Test
{

    public static void main(String[] args)
    {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}