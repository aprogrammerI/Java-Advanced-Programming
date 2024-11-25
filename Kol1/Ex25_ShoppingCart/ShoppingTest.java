package Ex25_ShoppingCart;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


//public double totalPrice()
//    {
//        return  (getPrice()/1000.0)*getQuantity();
//    }

//sortedList.forEach( i -> pw.println(i.toString()));

// items.stream()
//                   .filter(item1 -> discountItems.stream()
//                           .anyMatch(item2 -> item2.equals(item1.getProductID())))
//                           .forEach(item1 ->
//                           {
//                               double saved = item1.totalPrice()*0.1;
//                               pw.format("%d - %.2f\n",item1.getProductID(),saved);
//
//                           });

enum Type
{
    WS,
    PS;
}


class InvalidOperationException extends Exception
{
    public InvalidOperationException(String message)
    {
        super(message);
    }
}


abstract class Item
{
    Type type;
    int productID;
    String productName;


    public Item(Type type, int productID, String productName)
    {

        this.type = type;
        this.productID = productID;
        this.productName = productName;

    }

    public Type getType()
    {

        return type;
    }

    public int getProductID()
    {

        return productID;
    }

    public String getProductName()
    {

        return productName;
    }

    public double getPrice()
    {

        return 0;
    }

    public double getQuantity()
    {

        return 0;
    }

    public double totalPrice()
    {
     return 0;
    }
}

class WS extends Item
{
    int price;
    int quantity;


    public WS(Type type, int productID, String productName, int price, int quantity)
    {

        super(type, productID, productName);
        this.price=price;
        this.quantity = quantity;
    }

    @Override
    public double getPrice()
    {

        return price;
    }

    @Override
    public double getQuantity()
    {

        return quantity;
    }

    @Override
    public double totalPrice()
    {
        return  getPrice()*getQuantity();
    }

    @Override
    public String toString()
    {
        return String.format("%d - %.2f",getProductID(),totalPrice());
    }
}

class PS extends Item
{
    int price;
    double quantity;

    public PS(Type type, int productID, String productName,int price,double quantity)
    {

        super(type, productID, productName);
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public double getPrice()
    {

        return price;
    }

    @Override
    public double getQuantity()
    {

        return quantity;
    }


    @Override
    public double totalPrice()
    {
        return  (getPrice()/1000.0)*getQuantity();
    }

    @Override
    public String toString()
    {
        return String.format("%d - %.2f",getProductID(),totalPrice());
    }
}

class ShoppingCart
{
    List<Item> items;
    public ShoppingCart()
    {
     items = new ArrayList<>();
    }

    void addItem(String itemData) throws
            InvalidOperationException
    {

        String [] parts = itemData.split(";");

        Type type = Type.valueOf(parts[0]);
        int id = Integer.parseInt(parts[1]);
        String name = parts[2];
        double price = Double.parseDouble(parts[3]);
        double quantity = Double.parseDouble(parts[4]);

        if(quantity == 0)
        {
            throw  new InvalidOperationException(String.format("The quantity of the product with id %s can not be 0.",id));
        }

        if (type == Type.WS)
        {
            items.add(new WS(type,id,name,Integer.parseInt(parts[3]),Integer.parseInt(parts[4])));
        }
        else if (type == Type.PS)
        {
            items.add(new PS(type,id,name,Integer.parseInt(parts[3]),Double.parseDouble(parts[4])));
        }



    }

    void printShoppingCart(OutputStream os)
    {
        PrintWriter pw = new PrintWriter(os);

        List<Item> sortedList = items.stream().sorted(
                (x,y)->Double.compare(y.totalPrice(), x.totalPrice())).collect(Collectors.toList());

        //List<Item> sortedList = items.stream().sorted(Comparator.comparingDouble(Item::totalPrice)).collect(Collectors.toList());

        sortedList.forEach( i -> pw.println(i.toString()));

        pw.flush();
        pw.close();


    }

    void blackFridayOffer(List<Integer> discountItems, OutputStream os)
            throws InvalidOperationException
    {

        if(discountItems.size() == 0)
        {
            throw  new InvalidOperationException("There are no products with discount.");
        }
        else
        {
            PrintWriter pw = new PrintWriter(os);


           items.stream()
                   .filter(item1 -> discountItems.stream()
                           .anyMatch(item2 -> item2.equals(item1.getProductID())))
                           .forEach(item1 ->
                           {
                               double saved = item1.totalPrice()*0.1;
                               pw.format("%d - %.2f\n",item1.getProductID(),saved);

                           });



            pw.flush();
            pw.close();


        }


    }

}


public class ShoppingTest
{

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++)
        {
            try
            {
                cart.addItem(sc.nextLine());
            }
            catch (InvalidOperationException e)
            {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++)
        {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1)
        {
            cart.printShoppingCart(System.out);
        }
        else if (testCase == 2)
        {
            try
            {
                cart.blackFridayOffer(discountItems, System.out);
            }
            catch (InvalidOperationException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("Invalid test case");
        }
    }
}
