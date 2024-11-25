package Ex27_Risk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;


// the intStream.range()
//the  Arrays.stream(results).forEach(i -> System.out.print(i + " "));


class Risk
{

    public void processAttacksData(InputStream in)
    {


        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        br .lines().forEach(line ->
        {

            int [] results = {0,0};

            String [] parts = line.split(";");

            int [] attack = Arrays.stream(parts[0].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int [] defence = Arrays.stream(parts[1].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();


            Arrays.sort(attack);
            Arrays.sort(defence);


//            for(int i=0;i<3;i++)
//            {
//                if(attack[i]>defence[i])
//                {
//                    results[0]++;
//                }
//                else
//                {
//                    results[1]++;
//                }
//            }

            IntStream.range(0, 3)
                    .forEach(i -> {
                        if (attack[i] > defence[i]) {
                            results[0]++;
                        } else {
                            results[1]++;
                        }
                    });


                        Arrays.stream(results).forEach(i -> System.out.print(i + " "));
            System.out.println();



        });

    }
}


public class RiskTester
{
    public static void main(String[] args) {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}