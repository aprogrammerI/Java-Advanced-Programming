package Ex7_TimeTable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


// extends RuntimeException instead of Exception
//everything in it is the same
//Comparable use Integer.compare()
//string.format() for the string
//  "\\s"  for 1 or more spaces
//  parts[i].split("[.:]");  split by : or .
// pw.flush();  not pw.close();


enum TimeFormat
{
    FORMAT_24, FORMAT_AMPM
}


class UnsupportedFormatException extends RuntimeException
{
    public UnsupportedFormatException(String message)
    {
        super(message);
    }
}

class InvalidTimeException extends RuntimeException
{
    public InvalidTimeException(String message)
    {
        super(message);
    }
}

class Time implements Comparable<Time>
{
    int hour;
    int min;

    public Time(int hour, int min)
    {
        this.hour = hour;
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String format_AMPM()
    {
        if(hour==0)
            return String.format("%2d:%02d AM",getHour()+12,getMin());
        if(hour>0 && hour<12)
            return String.format("%2d:%02d AM",getHour(),getMin());
        if(hour==12)
            return String.format("%2d:%02d PM",getHour(),getMin());

        return String.format("%2d:%02d PM",getHour()-12,getMin());
    }



    @Override
    public int compareTo(Time o)
    {
        if(this.hour==o.hour)
            return Integer.compare(this.min,o.min);

        return Integer.compare(this.hour,o.hour);
    }


    @Override
    public String toString()
    {
        return String.format("%2d:%02d",getHour(),getMin());
    }
}


class TimeTable
{

    List<Time> timeList;
    public TimeTable()
    {
        timeList = new ArrayList<>();
    }

    public void readTimes(InputStream in)
            throws UnsupportedFormatException,InvalidTimeException
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        br.lines().forEach(line ->
        {
            String [] parts = line.split("\\s");

//            Arrays.stream(parts).forEach(System.out::println);

            for(int i=0;i< parts.length;i++)
            {
                if(!parts[i].contains(":") && !parts[i].contains("."))
                {

                        throw new UnsupportedFormatException(parts[i]);

                }

                String [] t = parts[i].split("[.:]");
                int h = Integer.parseInt(t[0]);
                int m = Integer.parseInt(t[1]);

                if(h<0 || h>23 || m<0 || m>59)
                {

                        throw  new InvalidTimeException(parts[i]);

                }

                Time time = new Time(h,m);

                timeList.add(time);


            }
//            System.out.println(timeList);

        });

    }

    public void writeTimes(PrintStream out, TimeFormat format)
    {

        PrintWriter pw = new PrintWriter(out);
        Collections.sort(timeList);

        if(format==TimeFormat.FORMAT_24)
        {
            for(int i=0;i< timeList.size();i++)
            {
                pw.println(timeList.get(i));
            }
        }

       else
        {
            for(int i=0;i< timeList.size();i++)
            {
                pw.println(timeList.get(i).format_AMPM());
            }

        }




        pw.flush();




    }
}


public class TimesTest
{

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}


