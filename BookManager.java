import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class BookManager {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Book> books = new ArrayList<>();
    private static int nextId = 1; // mã sách tự tăng

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = readInt("Chọn chức năng: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> updateBook();
                case 4 -> listAllBooks();
                case 5 -> findProgrammingBooks();
                case 6 -> takeBooksByPrice();
                case 7 -> findBooksByAuthors();
                case 0 -> {
                    System.out.println("Thoát chương trình.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("===== QUẢN LÝ SÁCH =====");
        System.out.println("1. Thêm 1 cuốn sách");
        System.out.println("2. Xóa 1 cuốn sách");
        System.out.println("3. Thay đổi cuốn sách");
        System.out.println("4. Xuất thông tin tất cả các cuốn sách");
        System.out.println("5. Tìm sách có tựa đề chứa chữ \"Lập trình\" (không phân biệt hoa thường)");
        System.out.println("6. Lấy sách theo giá (<= P) tối đa K cuốn");
        System.out.println("7. Nhập danh sách tác giả, liệt kê sách của các tác giả này");
        System.out.println("0. Thoát");
    }

    private static int readInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ.");
            }
        }
    }

    private static double readDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số thực hợp lệ.");
            }
        }
    }

    private static String readNonEmpty(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Không được để trống.");
        }
    }

    // 1. Thêm 1 cuốn sách
    private static void addBook() {
        System.out.println("--- Thêm sách ---");
        int id = nextId++;
        System.out.println("Mã sách được gán tự động: " + id);

        String title = readNonEmpty("Nhập tên sách: ");
        String author = readNonEmpty("Nhập tác giả: ");
        double price = readDouble("Nhập đơn giá: ");

        books.add(new Book(id, title, author, price));
        System.out.println("Đã thêm sách.");
    }

    // 2. Xóa 1 cuốn sách
    private static void removeBook() {
        System.out.println("--- Xóa sách ---");
        int id = readInt("Nhập mã sách cần xóa: ");
        Book b = findBookById(id);
        if (b == null) {
            System.out.println("Không tìm thấy sách có mã " + id);
            return;
        }
        books.remove(b);
        System.out.println("Đã xóa sách.");
    }

    // 3. Thay đổi cuốn sách
    private static void updateBook() {
        System.out.println("--- Cập nhật sách ---");
        int id = readInt("Nhập mã sách cần cập nhật: ");
        Book b = findBookById(id);
        if (b == null) {
            System.out.println("Không tìm thấy sách có mã " + id);
            return;
        }

        System.out.println("Thông tin hiện tại: " + b);
        String newTitle = readNonEmpty("Nhập tên sách mới (không rỗng): ");
        String newAuthor = readNonEmpty("Nhập tác giả mới (không rỗng): ");
        double newPrice = readDouble("Nhập giá mới: ");

        b.setTitle(newTitle);
        b.setAuthor(newAuthor);
        b.setPrice(newPrice);

        System.out.println("Đã cập nhật sách.");
    }

    // 4. Xuất thông tin tất cả các cuốn sách
    private static void listAllBooks() {
        System.out.println("--- Danh sách sách ---");
        if (books.isEmpty()) {
            System.out.println("Chưa có sách nào.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    // 5. Tìm sách có tựa đề chứa chữ "Lập trình" (không phân biệt hoa thường)
    private static void findProgrammingBooks() {
        System.out.println("--- Tìm sách có tựa đề chứa \"Lập trình\" ---");
        String keyword = "lập trình";
        boolean found = false;
        for (Book b : books) {
            if (b.getTitle().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không có sách nào thỏa mãn.");
        }
    }

    // 6. Lấy sách: nhập K và P, lấy tối đa K cuốn có giá <= P
    private static void takeBooksByPrice() {
        System.out.println("--- Lấy sách theo giá ---");
        int K = readInt("Nhập số lượng tối đa K: ");
        double P = readDouble("Nhập giá tối đa P: ");

        int count = 0;
        for (Book b : books) {
            if (b.getPrice() <= P) {
                System.out.println(b);
                count++;
                if (count >= K) break;
            }
        }

        if (count == 0) {
            System.out.println("Không có sách nào có giá <= " + P);
        } else {
            System.out.println("Đã liệt kê " + count + " cuốn sách (tối đa K = " + K + ").");
        }
    }

    // 7. Nhập danh sách tác giả, in tất cả sách của những tác giả này
    private static void findBooksByAuthors() {
        System.out.println("--- Tìm sách theo danh sách tác giả ---");
        System.out.println("Nhập danh sách tác giả, phân tách bởi dấu phẩy (,)");
        String line = readNonEmpty("Tác giả: ");

        String[] parts = line.split(",");
        Set<String> authorSet = new HashSet<>();
        for (String p : parts) {
            String a = p.trim();
            if (!a.isEmpty()) {
                authorSet.add(a.toLowerCase(Locale.ROOT));
            }
        }

        if (authorSet.isEmpty()) {
            System.out.println("Không có tác giả hợp lệ.");
            return;
        }

        boolean found = false;
        for (Book b : books) {
            if (authorSet.contains(b.getAuthor().toLowerCase(Locale.ROOT))) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy sách nào của các tác giả đã nhập.");
        }
    }

    private static Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }
}

