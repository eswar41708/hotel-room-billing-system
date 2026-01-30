import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class BookingService {

    static Scanner sc = new Scanner(System.in);

    // Check-in
    public static void checkIn(Connection conn) {

        try {

            System.out.print("Customer Name: ");
            String name = sc.next();

            System.out.print("Phone: ");
            String phone = sc.next();

            System.out.print("Room ID: ");
            int roomId = sc.nextInt();

            // Check if room is available
            String checkRoom =
                "SELECT status FROM rooms WHERE room_id = ?";

            PreparedStatement ps1 =
                conn.prepareStatement(checkRoom);

            ps1.setInt(1, roomId);

            ResultSet rs = ps1.executeQuery();

            if (!rs.next() ||
                !rs.getString("status").equals("Available")) {

                System.out.println("Room not available!");
                return;
            }

            // Insert customer
            String custSql =
                "INSERT INTO customers(name, phone) VALUES(?, ?) RETURNING customer_id";

            PreparedStatement ps2 =
                conn.prepareStatement(custSql);

            ps2.setString(1, name);
            ps2.setString(2, phone);

            ResultSet crs = ps2.executeQuery();

            crs.next();
            int custId = crs.getInt(1);

            // Create booking
            String bookSql =
                "INSERT INTO bookings(room_id, customer_id, check_in) VALUES(?, ?, ?)";

            PreparedStatement ps3 =
                conn.prepareStatement(bookSql);

            ps3.setInt(1, roomId);
            ps3.setInt(2, custId);
            ps3.setTimestamp(3,
                Timestamp.valueOf(LocalDateTime.now()));

            ps3.executeUpdate();

            // Update room status
            String upd =
                "UPDATE rooms SET status='Occupied' WHERE room_id=?";

            PreparedStatement ps4 =
                conn.prepareStatement(upd);

            ps4.setInt(1, roomId);
            ps4.executeUpdate();

            System.out.println("Check-in successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check-out
    public static void checkOut(Connection conn) {

        try {

            System.out.print("Booking ID: ");
            int bookingId = sc.nextInt();

            // Get booking info
            String sql =
                "SELECT b.*, r.price_per_day " +
                "FROM bookings b " +
                "JOIN rooms r ON b.room_id = r.room_id " +
                "WHERE b.booking_id=? AND b.check_out IS NULL";

            PreparedStatement ps =
                conn.prepareStatement(sql);

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid booking!");
                return;
            }

            Timestamp in = rs.getTimestamp("check_in");
            double price = rs.getDouble("price_per_day");
            int roomId = rs.getInt("room_id");

            Timestamp out =
                Timestamp.valueOf(LocalDateTime.now());

            long millis =
                out.getTime() - in.getTime();

            long days =
                Math.max(1, millis / (1000 * 60 * 60 * 24));

            double bill = days * price;

            // Update booking
            String upd =
                "UPDATE bookings SET check_out=?, total_amount=? WHERE booking_id=?";

            PreparedStatement ps2 =
                conn.prepareStatement(upd);

            ps2.setTimestamp(1, out);
            ps2.setDouble(2, bill);
            ps2.setInt(3, bookingId);

            ps2.executeUpdate();

            // Free room
            String free =
                "UPDATE rooms SET status='Available' WHERE room_id=?";

            PreparedStatement ps3 =
                conn.prepareStatement(free);

            ps3.setInt(1, roomId);
            ps3.executeUpdate();

            System.out.println("Check-out complete!");
            System.out.println("Total Bill: ₹" + bill);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
