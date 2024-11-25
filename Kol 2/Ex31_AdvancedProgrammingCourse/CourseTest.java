package Ex31_AdvancedProgrammingCourse;

//package mk.ukim.finki.midterm;

import java.util.*;
import java.util.stream.Collectors;


class Student
{

    String index;
    String name;
    int midterm1;
    int midterm2;
    int labs;

    public Student(String index, String name)
    {
        this.index = index;
        this.name = name;
        this.midterm1 = 0;
        this.midterm2 = 0;
        this.labs = 0;
    }

    public String getIndex()
    {

        return index;
    }

    public String getName()
    {

        return name;
    }

    public int getMidterm1()
    {

        return midterm1;
    }

    public int getMidterm2()
    {

        return midterm2;
    }

    public int getLabs()
    {

        return labs;
    }

    public void setMidterm1(int midterm1)
    {

        this.midterm1 = midterm1;
    }

    public void setMidterm2(int midterm2)
    {

        this.midterm2 = midterm2;
    }

    public void setLabs(int labs)
    {

        this.labs = labs;
    }


    public double summedPoints()
    {
        return getMidterm1()*0.45 + midterm2*0.45 +labs;
    }

    public int calculateGrade()
    {
        if(summedPoints()>=90)
        {
            return 10;
        }
        else if(summedPoints()>=80)
        {
            return 9;
        }
        else if(summedPoints()>=70)
        {
            return 8;
        }
        else if(summedPoints()>=60)
        {
            return 7;
        }
        else if(summedPoints()>=50)
        {
            return 6;
        }
        return 5;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                getIndex(),getName(),getMidterm1(),getMidterm2(),getLabs(),summedPoints(),calculateGrade());
    }
}




class AdvancedProgrammingCourse
{

    List<Student> students;

    public AdvancedProgrammingCourse()
    {
        students = new ArrayList<>();
    }

    public void addStudent (Student s)
    {

        students.add(s);
    }

    public void updateStudent (String idNumber, String activity, int points)
    {

       students.stream()
                .filter(student -> student.getIndex().equals(idNumber))
               .forEach(student ->
               {
                   if (activity.equals("midterm1") && points>=0 && points <=100)
                   {
                       student.setMidterm1(points);
                   }

                   else if (activity.equals("midterm2") && points>=0 && points <=100)
                   {
                       student.setMidterm2(points);
                   }

                   else if (activity.equals("labs") && points>=0 && points <=10)
                   {
                       student.setLabs(points);
                   }

               });

    }

    public List<Student> getFirstNStudents (int n)
    {


        students = students.stream().sorted(Comparator.comparing(Student::summedPoints)).collect(Collectors.toList());
        Collections.reverse(students);

        return students.stream().limit(n).collect(Collectors.toList());
    }

    public Map<Integer,Integer> getGradeDistribution()
    {

        Set<Integer> allGrades = new LinkedHashSet<>(Arrays.asList(5, 6, 7, 8, 9, 10));

        // Create the distribution map
//        Map<Integer, Integer> gradeDistribution = students.stream()
//                .map(Student::calculateGrade)
//                .collect(Collectors.groupingBy(
//                        grade -> grade,
//                        LinkedHashMap::new,
//                        Collectors.summingInt(e -> 1)
//                ));
//
//        // Ensure all possible grades are included in the map
//        allGrades.forEach(grade -> gradeDistribution.putIfAbsent(grade, 0));
//
//        // Create a sorted map that preserves insertion order from the set
//        return allGrades.stream()
//                .collect(Collectors.toMap(
//                        grade -> grade,
//                        grade -> gradeDistribution.getOrDefault(grade, 0),
//                        (existing, replacement) -> existing,
//                        LinkedHashMap::new
//                ));


        Map<Integer,Integer> map= students.stream()
                .collect(Collectors.groupingBy(
                        Student::calculateGrade,
                        TreeMap::new,
                        Collectors.summingInt(i->1)
                ));
        map.putIfAbsent(5,0);
        map.putIfAbsent(6,0);
        map.putIfAbsent(7,0);
        map.putIfAbsent(8,0);
        map.putIfAbsent(9,0);
        map.putIfAbsent(10,0);
        return map;


    }

    public void printStatistics()
    {
        int count =(int) students.stream().filter(student -> student.calculateGrade()>=6).count();
        double min = students.stream().filter(student -> student.calculateGrade()>=6).mapToDouble(Student::summedPoints).min().getAsDouble();
        double max = students.stream().filter(student -> student.calculateGrade()>=6).mapToDouble(Student::summedPoints).max().getAsDouble();
        double avg = students.stream().filter(student -> student.calculateGrade()>=6).mapToDouble(Student::summedPoints).average().getAsDouble();



        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f",count,min,avg,max);
    }

}


public class CourseTest
{

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}

