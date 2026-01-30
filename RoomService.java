import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class RoomService {

    static Scanner sc = new Scanner(System.in);

    // Add Room
    public static void addRoom(Connection conn) {

        try {

            System.out.print("Enter Room Number: ");
            String number = sc.next();

            System.out.print("Enter Room Type: ");
            String type = sc.next();

            System.out.print("Price per Day: ");
            double price = sc.nextDouble();

            String sql =
                "INSERT INTO rooms (room_number, type, price_per_day, status) " +
                "VALUES (?, ?, ?, 'Available')";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, number);
            ps.setString(2, type);
            ps.setDouble(3, price);

            ps.executeUpdate();

            System.out.println("Room added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding room!");
            e.printStackTrace();
        }
    }

    // View Rooms
    public static void viewRooms(Connection conn) {

        try {

            String sql = "SELECT * FROM rooms";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Room List ---");

            while (rs.next()) {

                System.out.println(
                    "ID: " + rs.getInt("room_id") +
                    " | Number: " + rs.getString("room_number") +
                    " | Type: " + rs.getString("type") +
                    " | Price: " + rs.getDouble("price_per_day") +
                    " | Status: " + rs.getString("status")
                );
            }

        } catch (Exception e) {
            System.out.println("Error fetching rooms!");
            e.printStackTrace();
        }
    }
}
