import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        LocalDate l2 = LocalDate.parse("2024-12-28");
        LocalDate l1 = LocalDate.now();
        System.out.println(l2.isAfter(l1));
    }
}
