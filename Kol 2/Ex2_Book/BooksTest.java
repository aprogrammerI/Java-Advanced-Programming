package Ex2_Book;




//MUST USE LAMBDA WITH COMPARATOR OTHERWISE IT DOES NOT WORK


// List<Book> newList = bookList.stream()
//                .filter(book -> book.getCategory().equals(category))
//                .sorted(Comparator.comparing(Book::getTitle).thenComparingDouble(Book::getPrice))
//                .collect(Collectors.toList());
//
//        newList.forEach(book ->
//        {
//           System.out.printf("%s \n",book.toString());
//        }
//        );

//List<Book> cheapestBooks = bookList.stream()
//                .sorted(Comparator.comparingDouble(Book::getPrice).thenComparing(Book::getTitle))
//                .collect(Collectors.toList());

// return cheapestBooks.stream().limit(n).collect(Collectors.toList());


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


class Book
{

    String title;
    String category;
    float price;

    public Book(String title, String category, float price)
    {
        this.title = title;
        this.category = category;
        this.price = price;
    }


    public String getTitle()
    {
        return title;
    }

    public String getCategory ()
    {
        return category;
    }

    public double getPrice()
    {
        return price;
    }

    @Override
    public String toString()
    {
        return String.format("%s (%s) %.2f",title,getCategory(),price);
    }
}

class BookCollection
{

    List<Book> bookList;

    public BookCollection()
    {

        bookList = new ArrayList<>();
    }

    public void addBook(Book book)
    {

        bookList.add(book);

    }

    public void printByCategory(String category)
    {
        List<Book> newList = bookList.stream()
                .filter(book -> book.getCategory().equals(category))
                .sorted(Comparator.comparing(Book::getTitle).thenComparingDouble(Book::getPrice))
                .collect(Collectors.toList());




        newList.forEach(book ->
        {
           System.out.printf("%s \n",book.toString());
        }
        );

    }

    public List<Book> getCheapestN(int n)
    {



        List<Book> cheapestBooks = bookList.stream()
                .sorted(Comparator.comparingDouble(Book::getPrice).thenComparing(Book::getTitle))
                .collect(Collectors.toList());

        if(n<bookList.size())
        {
            return cheapestBooks;
        }


        return cheapestBooks.stream().limit(n).collect(Collectors.toList());

    }


}

public class BooksTest
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories)
        {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books)
    {
        for (Book book : books) {

            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection)
    {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

