import java.util.Locale;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;

    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title == null ? "" : title.trim();
        this.author = author == null ? "" : author.trim();
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title.trim();
    }

    public void setAuthor(String author) {
        this.author = author == null ? "" : author.trim();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "Mã: %d | Tên: %s | Tác giả: %s | Giá: %.2f",
                id, title, author, price);
    }
}

