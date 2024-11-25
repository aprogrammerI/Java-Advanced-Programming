package Ex16_MojDDV;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//return String.format("%10d\t%10d\t%10.5f",ID,totalAmount,totalTax);

//        pw.format("min:\t%5.3f\nmax:\t%5.3f\nsum:\t%5.3f\ncount:\t%d\navg:\t%5.3f",min,max,sum,count,avg);


class AmountNotAllowedException extends Exception
{
    public AmountNotAllowedException(String message)
    {

        super(message);
    }
}


class Item
{
    int item_price;
    String item_type;

    public Item(int item_price, String item_type)
    {
        this.item_price = item_price;
        this.item_type = item_type;
    }

    public int getItem_price()
    {
        return item_price;
    }

    public double calculateTax()
    {
        if(item_type.equals("A"))
        {
            return 0.18 * item_price;
        }
        if(item_type.equals("B"))
        {
            return 0.05 * item_price;
        }

        return 0;
    }

    public  double tax_Return()
    {
        return 0.15 * calculateTax();
    }

    @Override
    public String toString()
    {
        return null;
    }
}


class Receipt
{
    int ID;
    List<Item> itemList;

    int totalAmount;

    double totalTax;


    public Receipt(int ID, List<Item> itemList)
            throws AmountNotAllowedException
    {

        int a = calculateTotalAmount(itemList);

        if(a>30000)
        {
            throw new AmountNotAllowedException(String.format("Receipt with amount %d is not allowed to be scanned",a));
        }

        totalAmount = calculateTotalAmount(itemList);
        totalTax = calculateTaxReturn(itemList);

        this.ID = ID;
        this.itemList = itemList;
    }


    public int calculateTotalAmount(List<Item> itemList)
    {

        return itemList.stream().mapToInt(Item::getItem_price)
                .sum();
    }

    public  double calculateTaxReturn(List<Item> itemList)
    {

        return itemList.stream().mapToDouble(Item::tax_Return)
                .sum();

    }

    @Override
    public String toString()
    {

        return String.format("%10d\t%10d\t%10.5f",ID,totalAmount,totalTax);
    }


}

class MojDDV
{

    List<Receipt> receiptList;

    public MojDDV()
    {

        receiptList = new ArrayList<>();
    }

    public void readRecords(InputStream inputStream)
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        br.lines().forEach(line ->
        {
            String [] pieces = line.split("\\s+");
            List<Item> items = new ArrayList<>();

            int id = Integer.parseInt(pieces[0]);


            items = IntStream.range(1, pieces.length)
                    .filter(i -> i % 2 == 1)
                    .mapToObj(i -> new Item(Integer.parseInt(pieces[i]), pieces[i + 1]))
                    .collect(Collectors.toList());


            try
            {
                receiptList.add(new Receipt(id,items));
            }
            catch (AmountNotAllowedException e)
            {
                System.out.println(e.getMessage());
            }





        });



    }

    public void printTaxReturns(OutputStream outputStream)
    {

        PrintWriter pw = new PrintWriter(outputStream);

        receiptList.forEach(pw::println);

        pw.flush();


    }

    public void printStatistics(PrintStream out)
    {

        PrintWriter pw = new PrintWriter(out);

        double min = receiptList.stream().mapToDouble(i->i.totalTax).min().getAsDouble();
        double max = receiptList.stream().mapToDouble(i->i.totalTax).max().getAsDouble();
        double sum = receiptList.stream().mapToDouble(i->i.totalTax).sum();
        long count = receiptList.stream().mapToDouble(i->i.totalTax).count();
        double avg = receiptList.stream().mapToDouble(i->i.totalTax).average().getAsDouble();

//        pw.println(min);
//        pw.println(max);
//        pw.println(sum);
//        pw.println(count);
//        pw.println(avg);

        pw.format("min:\t%5.3f\nmax:\t%5.3f\nsum:\t%5.3f\ncount:\t%d\navg:\t%5.3f",min,max,sum,count,avg);

        pw.flush();

    }
}


public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}
