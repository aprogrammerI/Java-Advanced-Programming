package Ex17_News;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


//abstract class NewsItem
// abstract String getTeaser();

//class TextNewsItem extends NewsItem
//public TextNewsItem(String title, Date date, Category category,String text)
//    {
//        super(title, date, category);
//        this.placeForText=text;
//    }
//@Override
//    String getTeaser()
//    {
//        long duration = Calendar.getInstance().getTime().getTime() - date.getTime();
//        String first80Characters = placeForText.substring(0, Math.min(placeForText.length(), 80));
//
//        return getTitle() + "\n"
//                + TimeUnit.MILLISECONDS.toMinutes(duration)
//                + "\n"
//                + first80Characters
//                + "\n";
//    }
//@Override
//    public String toString() {
//        return getTeaser();
//    }


// return newsItems.stream().filter(newsItem -> newsItem.getCategory().getCategoryName().equals(category.getCategoryName())).collect(Collectors.toList());


// boolean flag = Arrays.stream(categories).anyMatch(value -> value.getCategoryName().equals(category));
//
//        if(!flag)
//        {
//            throw  new CategoryNotFoundException(String.format("Category %s was not found",category));
//        }


//StringBuilder result = new StringBuilder();
//
//        for (NewsItem newsItem : newsItems) {
//            result.append(newsItem.getTeaser());
//        }
//
//        return result.toString();

class CategoryNotFoundException extends Exception
{
    public CategoryNotFoundException(String message)
    {
        super(message);
    }
}

class Category
{
    String categoryName;

    public Category(String categoryName)
    {

        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    //2 cat are equal if their names are the same
}



abstract class NewsItem
{

    String title;
    Date date;
    Category category;

    public NewsItem(String title, Date date, Category category)
    {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public Category getCategory()
    {

        return category;
    }

    public String getTitle()
    {

        return title;
    }

    public Date getDate()
    {

        return date;
    }

    abstract String getTeaser();
}

class TextNewsItem extends NewsItem
{
    String placeForText;

    public TextNewsItem(String title, Date date, Category category,String text)
    {
        super(title, date, category);
        this.placeForText=text;
    }


    @Override
    String getTeaser()
    {
        long duration = Calendar.getInstance().getTime().getTime() - date.getTime();
        String first80Characters = placeForText.substring(0, Math.min(placeForText.length(), 80));

        return getTitle() + "\n"
                + TimeUnit.MILLISECONDS.toMinutes(duration)
                + "\n"
                + first80Characters
                + "\n";
    }

    @Override
    public String toString() {
        return getTeaser();
    }
}

class MediaNewsItem extends NewsItem
{
    String url;
    int numOfViews;

    public MediaNewsItem(String title, Date date, Category category,String url,int numOfViews)
    {
        super(title, date, category);
        this.url=url;
        this.numOfViews=numOfViews;
    }

    public String getUrl()
    {
        return url;
    }

    public int getNumOfViews()
    {
        return numOfViews;
    }

    @Override
    String getTeaser()
    {
        long duration = Calendar.getInstance().getTime().getTime() - date.getTime();

        return getTitle() + "\n" +
                TimeUnit.MILLISECONDS.toMinutes(duration) + "\n" +
                getUrl() + "\n" +
                getNumOfViews() + "\n";
    }

    @Override
    public String toString() {
        return getTeaser();
    }
}

class FrontPage
{

    List<NewsItem> newsItems;
    Category [] categories;

    public FrontPage(Category[] categories)
    {
        newsItems = new ArrayList<>();
        this.categories = categories;

    }

    public void addNewsItem(NewsItem newsItem)
    {
        newsItems.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category)
    {

        return newsItems.stream().filter(newsItem -> newsItem.getCategory().getCategoryName().equals(category.getCategoryName())).collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category)
            throws CategoryNotFoundException
    {

       boolean flag = Arrays.stream(categories).anyMatch(value -> value.getCategoryName().equals(category));

        if(!flag)
        {
            throw  new CategoryNotFoundException(String.format("Category %s was not found",category));
        }

         return newsItems.stream().filter(newsItem -> newsItem.getCategory().getCategoryName().equals(category)).collect(Collectors.toList());
    }

    @Override
    public String toString()

    {
        StringBuilder result = new StringBuilder();

        for (NewsItem newsItem : newsItems) {
            result.append(newsItem.getTeaser());
        }

        return result.toString();
    }
}


public class FrontPageTest
{
    public static void main(String[] args)
    {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i)
        {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i)
        {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i)
        {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories)
        {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}



