import java.sql.Connection;
import java.util.Scanner;

public class MainApp {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection conn = DBConnection.getConnection()) {

            System.out.println("Hotel System Started Successfully!");

            while (true) {

                System.out.println("\n===== HOTEL MANAGEMENT MENU =====");
                System.out.println("1. Add Room");
                System.out.println("2. View Rooms");
                System.out.println("3. Check-in Customer");
                System.out.println("4. Check-out Customer");
                System.out.println("5. Exit");
                System.out.print("Choose option: ");    

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        RoomService.addRoom(conn);
                        break;

                    case 2:
                        RoomService.viewRooms(conn);
                        break;

                    case 3:
                        BookingService.checkIn(conn);
                        break;

                    case 4:
                        BookingService.checkOut(conn);
                        break;
                    case 5:
                        System.out.println("Thank you. Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");      
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
