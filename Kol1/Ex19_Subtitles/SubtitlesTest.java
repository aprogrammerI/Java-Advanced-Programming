package Ex19_Subtitles;

//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.time.Duration;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//
//
//
//class Subtitles
//{
//
//    private static final DateTimeFormatter TIME_FORMATTER
//            = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
//
//    private List<SubtitleEntry> entries;
//
//    public Subtitles()
//    {
//        this.entries = new ArrayList<>();
//    }
//
//    public int loadSubtitles(InputStream inputStream)
//    {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
//        {
//            String line;
//            List<String> block = new ArrayList<>();
//            while ((line = reader.readLine()) != null)
//            {
//                if (line.trim().isEmpty()) {
//                    if (!block.isEmpty()) {
//                        entries.add(parseSubtitleEntry(block));
//                        block.clear();
//                    }
//                } else {
//                    block.add(line);
//                }
//            }
//            if (!block.isEmpty())
//            {
//                entries.add(parseSubtitleEntry(block));
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return entries.size();
//    }
//
//    public void print() {
//        entries.forEach(e -> System.out.printf("%s\n\n",e));
//    }
//
//    public void shift(int ms) {
//        entries.forEach(entry -> entry.shift(ms));
//    }
//
//    private SubtitleEntry parseSubtitleEntry(List<String> block)
//    {
//        int id = Integer.parseInt(block.get(0));
//        String[] times = block.get(1).split(" --> ");
//        LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);
//        LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);
//        String text = block.stream().skip(2).collect(Collectors.joining("\n"));
//        return new SubtitleEntry(id, startTime, endTime, text);
//    }
//
//    static class SubtitleEntry
//    {
//        private int id;
//        private LocalTime startTime;
//        private LocalTime endTime;
//        private String text;
//
//        public SubtitleEntry(int id, LocalTime startTime, LocalTime endTime, String text) {
//            this.id = id;
//            this.startTime = startTime;
//            this.endTime = endTime;
//            this.text = text;
//        }
//
//        public void shift(int ms)
//        {
//            startTime = startTime.plus(Duration.ofMillis(ms));
//            endTime = endTime.plus(Duration.ofMillis(ms));
//        }
//
//        @Override
//        public String toString()
//        {
//            return String.format("%d%n%s --> %s%n%s", id, startTime.format(TIME_FORMATTER), endTime.format(TIME_FORMATTER), text);
//        }
//    }
//}
//
//
//public class SubtitlesTest
//{
//    public static void main(String[] args)
//    {
//        Subtitles subtitles = new Subtitles();
//        int n = subtitles.loadSubtitles(System.in);
//        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
//        subtitles.print();
//        int shift = n * 37;
//        shift = (shift % 2 == 1) ? -shift : shift;
//        System.out.println(String.format("SHIFT FOR %d ms", shift));
//        subtitles.shift(shift);
//        System.out.println("+++++ SHIFTED SUBTITLES +++++");
//        subtitles.print();
//    }
//}
//
//// Вашиот код овде


// LOOK AGAIN


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.LocalTime;




// private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");

// public void shift(int ms)
//    {
//        Duration shiftDuration = Duration.ofMillis(ms);
//        startTime = startTime.plus(shiftDuration);
//        endTime = endTime.plus(shiftDuration);
//    }

//@Override
//    public String toString() {
//        return String.format("%d%n%s --> %s%n%s", id, startTime.format(TIME_FORMATTER),
//                endTime.format(TIME_FORMATTER), text);
//    }


//try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)))
//        {
//            // we are basically looking for white space to end the block
//
//
//            List<String> block = new ArrayList<>();
//            br.lines().forEach(line ->
//            {
//                if (line.trim().isEmpty())
//                {
//                    if (!block.isEmpty())
//                    {
//                        entries.add(parseSubtitleEntry(block));
//                        block.clear();
//                    }
//                }
//                else
//                {
//                    block.add(line);
//                }
//            });
//
//            //this is probably for the last block
//
//            if (!block.isEmpty())
//            {
//                entries.add(parseSubtitleEntry(block));
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        return entries.size();

// entries.forEach(entry -> System.out.println(entry + "\n"));

//entries.forEach(entry -> entry.shift(ms));


//private SubtitleEntry parseSubtitleEntry(List<String> block)
//    {
//
//        int id = Integer.parseInt(block.get(0));
//
//        String[] times = block.get(1).split(" --> ");
//
//        LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);
//
//        LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);
//
//        String text = block.stream().skip(2).collect(Collectors.joining("\n"));
//
//
//        return new SubtitleEntry(id, startTime, endTime, text);
//    }

 class SubtitleEntry
 {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");

    private final int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private final String text;

    public SubtitleEntry(int id, LocalTime startTime, LocalTime endTime, String text)
    {

        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }

    public void shift(int ms)
    {
        Duration shiftDuration = Duration.ofMillis(ms);
        startTime = startTime.plus(shiftDuration);
        endTime = endTime.plus(shiftDuration);
    }

    @Override
    public String toString() {
        return String.format("%d%n%s --> %s%n%s", id, startTime.format(TIME_FORMATTER),
                endTime.format(TIME_FORMATTER), text);
    }
}


 class Subtitles
 {

    private static final DateTimeFormatter TIME_FORMATTER
            = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");

    private final List<SubtitleEntry> entries;

    public Subtitles() {
        this.entries = new ArrayList<>();
    }

    public int loadSubtitles(InputStream inputStream)
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)))
        {
            // we are basically looking for white space to end the block


            List<String> block = new ArrayList<>();
            br.lines().forEach(line ->
            {
                if (line.trim().isEmpty())
                {
                    if (!block.isEmpty())
                    {
                        entries.add(parseSubtitleEntry(block));
                        block.clear();
                    }
                }
                else
                {
                    block.add(line);
                }
            });

            //this is probably for the last block

            if (!block.isEmpty())
            {
                entries.add(parseSubtitleEntry(block));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return entries.size();



    }

    public void print()
    {
        entries.forEach(entry -> System.out.println(entry + "\n"));
    }

    public void shift(int ms)
    {
        entries.forEach(entry -> entry.shift(ms));
    }

    private SubtitleEntry parseSubtitleEntry(List<String> block)
    {

        int id = Integer.parseInt(block.get(0));

        String[] times = block.get(1).split(" --> ");

        LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);

        LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);

        String text = block.stream().skip(2).collect(Collectors.joining("\n"));


        return new SubtitleEntry(id, startTime, endTime, text);
    }
}


public class SubtitlesTest
{

    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int subtitleCount = subtitles.loadSubtitles(System.in);

        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();

        int shiftAmount = subtitleCount * 37;
        if (shiftAmount % 2 == 1) {
            shiftAmount = -shiftAmount;
        }
        System.out.printf("SHIFT FOR %d ms%n", shiftAmount);

        subtitles.shift(shiftAmount);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

