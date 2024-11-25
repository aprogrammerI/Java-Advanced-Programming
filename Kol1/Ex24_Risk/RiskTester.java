package Ex24_Risk;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


//the array with one element
//mapping arrays elements into integers
//returning count in return (int) br.lines
//reverse(array);   so that we reverse the array



class Risk
{

    public int processAttacksData(InputStream in)
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        int [] sum = {0};

        br.lines().forEach(line ->
        {

            String [] parts = line.split(";");
            int [] attack = Arrays.stream(parts[0].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int [] defence = Arrays.stream(parts[1].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();


            Arrays.sort(attack);
            Arrays.sort(defence);

            if(attack[0]>defence[0] && attack[1]>defence[1] && attack[2]>defence[2])
            {
                sum[0]++;
            }

        });


        return sum[0];
    }
}


public class RiskTester
{
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}
