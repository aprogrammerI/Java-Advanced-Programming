package Ex23_Quiz;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;


//can add try nad catch in main
//when in inheritance, the exception comes after super in constructor
//in else is the rest of the code so that it does not continue


//String answer;
//    public Multiple_Choice(Type type, String text, int points, String answer)
//            throws InvalidOperationException
//    {
//
//
//        super(type, text, points);
//
//        if(!answer.equals("A") &&!answer.equals("B") &&!answer.equals("C") && !answer.equals("D")&&!answer.equals("E"))
//        {
//            throw new InvalidOperationException(String.format("%s is not allowed option for this question",answer));
//        }
//
//        this.answer = answer;
//
//    }
//
//    @Override
//    public String getAnswer()
//    {
//        return answer;
//    }
//
//    @Override
//    public double wrongAnswer()
//    {
//        return -(0.2*getPoints());
//    }
//
//    @Override
//    public String toString()
//    {
//
//        return String.format("Multiple Choice Question: %s Points %d Answer: %s",text,points,answer);
//    }


//else if (type == Type.MC)
//        {
//            try
//            {
//                questions.add(new Multiple_Choice(type,text,points,answer)); //exception
//            }
//            catch (InvalidOperationException e)
//            {
//                System.out.println(e.getMessage());
//            }
//
//        }


//PrintWriter pw = new PrintWriter(os);
//
//
//        List<Question> sortedQuestions = questions.stream().sorted((x,y) -> Double.compare(y.getPoints(), x.getPoints())).collect(Collectors.toList());
//        //questions.stream().sorted(Comparator.comparingInt(Question::getPoints));
//
//
//
//        for(Question q : sortedQuestions)
//        {
//            pw.println(q.toString());
//        }
//        pw.flush();
//        pw.close();


//void answerQuiz (List<String> answers, OutputStream os)
//            throws InvalidOperationException
//    {
//        PrintWriter pw = new PrintWriter(os);
//
//        double totalPoints = 0;
//
//        if(answers.size() != questions.size())
//        {
//            throw new InvalidOperationException("Answers and questions must be of same length!");
//        }
//        else
//        {
//            for(int i=0;i< questions.size();i++)
//            {
//                if(answers.get(i).equals(questions.get(i).getAnswer()))
//                {
//                    totalPoints+=questions.get(i).getPoints();
//                    pw.format("%d. %.2f\n",i+1,questions.get(i).getPoints());
//                }
//                else
//                {
//                    totalPoints+=questions.get(i).wrongAnswer();
//                    pw.format("%d. %.2f\n",i+1,questions.get(i).wrongAnswer());
//                }
//            }
//
//            pw.format("Total points: %.2f",totalPoints);
//        }

//try {
//                quiz.answerQuiz(answers, System.out);
//            } catch (InvalidOperationException e) {
//                System.out.println(e.getMessage());
//            }



class InvalidOperationException extends  Exception
{
    public InvalidOperationException(String message)
    {
        super(message);
    }

}


enum Type
{
    TF,
    MC;
}

abstract class Question
{
    Type type;
    String text;
    int points;


    public Question(Type type, String text, int points)
    {

        this.type = type;
        this.text = text;
        this.points = points;

    }

    public Type getType()
    {
        return type;
    }

    public String getText()
    {
        return text;
    }

    public double getPoints()
    {
        return points;
    }


    public double wrongAnswer()
    {
        return 0;
    }

    public String getAnswer()
    {
        return null;
    }


}

class True_False extends Question
{
    String answer;
    public True_False(Type type, String text, int points, String answer)
    {

        super(type, text, points);
        this.answer=answer;
    }

    @Override
    public String getAnswer()
    {
        return answer;
    }

    @Override
    public String toString() {
        return String.format("True/False Question: %s Points: %d Answer: %s",text,points,answer);
    }
}

class Multiple_Choice extends Question
{
    String answer;
    public Multiple_Choice(Type type, String text, int points, String answer)
            throws InvalidOperationException
    {


        super(type, text, points);

        if(!answer.equals("A") &&!answer.equals("B") &&!answer.equals("C") && !answer.equals("D")&&!answer.equals("E"))
        {
            throw new InvalidOperationException(String.format("%s is not allowed option for this question",answer));
        }

        this.answer = answer;

    }

    @Override
    public String getAnswer()
    {
        return answer;
    }

    @Override
    public double wrongAnswer()
    {
        return -(0.2*getPoints());
    }

    @Override
    public String toString()
    {

        return String.format("Multiple Choice Question: %s Points %d Answer: %s",text,points,answer);
    }
}

class Quiz
{

    List<Question> questions;

    public Quiz()
    {

        questions = new ArrayList<>();
    }

    void addQuestion(String questionData)
    {

        String [] parts = questionData.split(";");

        Type type     = Type.valueOf(parts[0]);
        String text   = parts[1];
        int points    = Integer.parseInt(parts[2]);
        String answer = parts[3];

        if(type == Type.TF)
        {
             questions.add(new True_False(type,text,points,answer));
        }
        else if (type == Type.MC)
        {
            try
            {
                questions.add(new Multiple_Choice(type,text,points,answer)); //exception
            }
            catch (InvalidOperationException e)
            {
                System.out.println(e.getMessage());
            }

        }

    }

    void printQuiz(OutputStream os)
    {

        PrintWriter pw = new PrintWriter(os);


        List<Question> sortedQuestions = questions.stream().sorted((x,y) -> Double.compare(y.getPoints(), x.getPoints())).collect(Collectors.toList());
        //questions.stream().sorted(Comparator.comparingInt(Question::getPoints));



        for(Question q : sortedQuestions)
        {
            pw.println(q.toString());
        }
        pw.flush();
        pw.close();


    }

    void answerQuiz (List<String> answers, OutputStream os)
            throws InvalidOperationException
    {
        PrintWriter pw = new PrintWriter(os);

        double totalPoints = 0;

        if(answers.size() != questions.size())
        {
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        else
        {
            for(int i=0;i< questions.size();i++)
            {
                if(answers.get(i).equals(questions.get(i).getAnswer()))
                {
                    totalPoints+=questions.get(i).getPoints();
                    pw.format("%d. %.2f\n",i+1,questions.get(i).getPoints());
                }
                else
                {
                    totalPoints+=questions.get(i).wrongAnswer();
                    pw.format("%d. %.2f\n",i+1,questions.get(i).wrongAnswer());
                }
            }

            pw.format("Total points: %.2f",totalPoints);
        }



        pw.flush();
        pw.close();




    }


}


public class QuizTest
{
    public static void main(String[] args)
            throws InvalidOperationException
    {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}
