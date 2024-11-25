package Ex38_QuizProcessor;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DifferentNumberOfAnswers extends Exception
{
    public DifferentNumberOfAnswers(String message)
    {
        super(message);
    }
}


class Quiz
{
    String id;
    List<String> correctAnswers;
    List<String> givenAnswers;

    public Quiz(String id, List<String> correctAnswers, List<String> givenAnswers)
    {
        this.id = id;
        this.correctAnswers = correctAnswers;
        this.givenAnswers = givenAnswers;
    }

    public static Quiz addQuiz(String line)
            throws DifferentNumberOfAnswers
    {
        String [] strings = line.split(";");
        String id = strings[0].trim();


        List<String> correct =
                Arrays.stream(strings[1]
                                .split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

        List<String> given =
                Arrays.stream(strings[2]
                                .split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

        if(correct.size()!= given.size())
        {
            throw new DifferentNumberOfAnswers("A quiz must have same number of correct and selected answers");
        }

        return new Quiz(id,correct,given);

    }

    public String getId()
    {
        return id;
    }

    public double calculatePoints()
    {
        return IntStream.range(0,correctAnswers.size())
                .mapToDouble(i->correctAnswers.get(i).equals(givenAnswers.get(i))? 1: -0.25)
                .sum();
    }




}

class QuizProcessor
{
    static List<Quiz> quizList;

    public QuizProcessor()
    {
        quizList = new ArrayList<>();
    }

    public static Map<String,Double> processAnswers(InputStream is)
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        quizList = br.lines().map(line ->
                        {
                            try
                            {
                                return Quiz.addQuiz(line);
                            }
                            catch (DifferentNumberOfAnswers ex)
                            {
                                System.out.println(ex.getMessage());
                            }

                            return  null;
                        }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        return quizList.stream()
                .collect(Collectors
                        .toMap(
                                Quiz::getId,
                                Quiz::calculatePoints,
                                (oldValue, newValue)-> oldValue,
                                LinkedHashMap::new
                        )
                );

    }

}


public class QuizProcessorTest
{
    public static void main(String[] args)
    {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}
