package Ex23_LabExercises;

import java.util.*;
import java.util.stream.Collectors;

class Student
{
    String index;
    List<Integer> points;

    public Student(String index, List<Integer> points)
    {

        this.index = index;
        this.points = points;
    }

    public String getIndex()
    {

        return index;
    }

    public String getWhatSignature()
    {
        if(points.size()<8)
        {
            return "NO";
        }

        return "YES";
    }

    public boolean  getSignature()
    {
        return getWhatSignature().equals("YES");
    }

    public double getSummedSum()
    {

        return (points.stream().mapToInt(Integer::intValue).sum())/10.0;
    }

    public int getYear()
    {
        int y = Integer.parseInt(index.substring(0,2));
        return 20-y;
    }

    @Override
    public String toString()
    {

        return String.format("%s %s %.2f",getIndex(), getWhatSignature(),getSummedSum());
    }
}

class  LabExercises
{

    List<Student> students;

    public LabExercises()
    {

        students = new ArrayList<>();
    }

    public void addStudent (Student student)
    {

        students.add(student);

    }

    public void printByAveragePoints (boolean ascending, int n)
    {
        if(ascending)
        {

            students = students.stream()
                    .sorted(Comparator.comparingDouble(Student::getSummedSum)
                            .thenComparing(Student::getIndex))
                    .collect(Collectors.toList());

        }
        else
        {
            students = students.stream()
                    .sorted(Comparator.comparingDouble(Student::getSummedSum)
                            .thenComparing(Student::getIndex))
                    .collect(Collectors.toList());
            Collections.reverse(students);
        }

        students.stream().limit(n).forEach(student -> System.out.println(student.toString()));
    }

    public List<Student> failedStudents ()
    {

        return students.stream()
                .filter(student -> student.getWhatSignature().equals("NO"))
                .sorted(Comparator.comparing(Student::getIndex)
                        .thenComparingDouble(Student::getSummedSum))
                .collect(Collectors.toList());
    }

    public Map<Integer,Double> getStatisticsByYear()
    {



        return students.stream()
                .filter(Student::getSignature)
                .collect(Collectors.groupingBy(
                        Student::getYear,
                        Collectors.averagingDouble(Student::getSummedSum)
                ));
    }

}

public class LabExercisesTest
{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}
