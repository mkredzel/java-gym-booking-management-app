
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * All methods that use JDBC to connect to MySQL database are located here.
 */
public class GymDB {

    static String dburl = "jdbc:mysql://localhost/gym";
    static String user = "root";
    static String pwd = "";
    static String result;
    static String id;
    static String staffId;
    static String date;
    static String focus;
    static String confirmationNo;

    static int counter = 1;

    static ArrayList<String> bookedHours = new ArrayList<>();
    static ArrayList<String> alreadyBookedHours = new ArrayList<>();

    /**
     * Method allows user to retrieve all existing bookings.
     */
    public static void listAll() {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            Statement stat = conn.createStatement();

            String query = "SELECT * FROM Booking WHERE date >= CURDATE()";

            ResultSet rs = stat.executeQuery(query);

            result = "Displaying all existing bookings: \n \n";

            while (rs.next()) {
                String confirmation = rs.getString(1);
                id = rs.getString(2);
                staffId = rs.getString(3);
                date = rs.getString(4);
                String timeframe = rs.getString(5);
                focus = rs.getString(6);
                result += counter + ") Confirmation: " + confirmation
                        + ", Member: " + id + ", PT: " + staffId
                        + ", Date: " + date + ", Time: " + timeframe
                        + ", Focus: " + focus + "\n";
                counter++;
            }
            counter = 1;
            conn.close();
            
            if (result.equals("Displaying all existing bookings: \n \n")) {
                result = "There is not even a single booking.";
            }
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method allows user to retrieve all existing bookings for the given date.
     * 
     * @param chosenDate 
     */
    public static void listGivenDate(String chosenDate) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String query = "SELECT * FROM Booking WHERE date = ?";

            PreparedStatement stat = conn.prepareStatement(query);

            stat.setString(1, chosenDate);

            ResultSet rs = stat.executeQuery();

            result = "Displaying all the bookings on the " + chosenDate + "\n \n";

            while (rs.next()) {
                String confirmation = rs.getString(1);
                id = rs.getString(2);
                staffId = rs.getString(3);
                date = rs.getString(4);
                String timeframe = rs.getString(5);
                focus = rs.getString(6);
                result += counter + ") Confirmation: " + confirmation
                        + ", Member: " + id + ", PT: " + staffId
                        + ", Time: " + timeframe
                        + ", Focus: " + focus + "\n";
                counter++;
            }
            counter = 1;
            conn.close();

            if (result.equals("Displaying all the bookings on the " + chosenDate + "\n \n")) {
                result = "There is not even a single booking for the given date.";
            }
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method allows user to retrieve all existing bookings for the given member.
     * 
     * @param membersId 
     */
    public static void listGivenMember(String membersId) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String query = "SELECT * FROM Booking WHERE id = ? && date >= CURDATE()";

            PreparedStatement stat = conn.prepareStatement(query);

            stat.setString(1, membersId);

            result = "Displaying all the bookings made for member: " + membersId + "\n \n";

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                String confirmation = rs.getString(1);
                id = rs.getString(2);
                staffId = rs.getString(3);
                date = rs.getString(4);
                String timeframe = rs.getString(5);
                focus = rs.getString(6);
                result += counter + ") Confirmation: " + confirmation
                        + ", PT: " + staffId + ", Date: " + date
                        + ", Time: " + timeframe
                        + ", Focus: " + focus + "\n";
                counter++;
            }
            counter = 1;
            conn.close();

            if (result.equals("Displaying all the bookings made for member: " + membersId + "\n \n")) {
                result = "There is not even a single booking for the given member.";
            }
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method allows user to retrieve all existing bookings for the given PT.
     * 
     * @param PTid 
     */
    public static void listGivenPT(String PTid) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String query = "SELECT * FROM Booking WHERE staffId = ? && date >= CURDATE()";

            PreparedStatement stat = conn.prepareStatement(query);

            stat.setString(1, PTid);

            result = "Displaying all the bookings made with personal trainer: " + PTid + "\n \n";

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                String confirmation = rs.getString(1);
                id = rs.getString(2);
                staffId = rs.getString(3);
                date = rs.getString(4);
                String timeframe = rs.getString(5);
                focus = rs.getString(6);
                result += counter + ") Confirmation: " + confirmation
                        + ", Member: " + id + ", Date: " + date
                        + ", Time: " + timeframe + ", Focus: " + focus + "\n";
                counter++;
            }
            counter = 1;
            conn.close();

            if (result.equals("Displaying all the bookings made with personal trainer: " + PTid + "\n \n")) {
                result = "There is not even a single booking for the given Personal Trainer.";
            }
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method allows user to create a new booking.
     * 
     * @param id
     * @param staffId
     * @param date
     * @param timeFrame
     * @param focus 
     */
    public static void addBooking(String id, String staffId, LocalDate date, String timeFrame, String focus) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String record = "INSERT INTO Booking (id, staffId, date, timeFrame, focus) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stat = conn.prepareStatement(record);

            stat.setString(1, id);
            stat.setString(2, staffId);
            stat.setDate(3, Date.valueOf(date));
            stat.setString(4, timeFrame);
            stat.setString(5, focus);

            stat.executeUpdate();
            getConfirmationOfNewBooking(id, staffId, date, timeFrame, focus);
            conn.close();
        } catch (SQLException ex) {
            confirmationNo = "Booking has not been created. Member's id or PT's id do not exist.";
        }
    }

    /**
     * Method allows user to modify/update an existing booking.
     * 
     * @param id
     * @param staffId
     * @param date
     * @param timeFrame
     * @param focus
     * @param givenConfNo 
     */
    public static void modifyBooking(String id, String staffId, LocalDate date, String timeFrame, String focus, String givenConfNo) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String record = "UPDATE Booking SET id = ?, staffId = ?, date = ?, timeFrame = ?, focus = ? WHERE confirmation = ?";
            PreparedStatement stat = conn.prepareStatement(record);
            stat.setString(1, id);
            stat.setString(2, staffId);
            stat.setDate(3, Date.valueOf(date));
            stat.setString(4, timeFrame);
            stat.setString(5, focus);
            stat.setString(6, givenConfNo);
            stat.executeUpdate();
            int i = stat.executeUpdate();

            if (i > 0) {
                result = "Booking has been modified.";
            } else {
                result = "Booking has not been modified. Member's id or PT's id do not exist.";
            }
            conn.close();
        } catch (SQLException ex) {
            result = "Booking has not been created. Member's id or PT's id do not exist.";
        }
    }

    /**
     * Method allows user to delete a booking.
     * 
     * @param givenConfNo 
     */
    public static void deleteBooking(String givenConfNo) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String record = "DELETE FROM Booking WHERE confirmation = ?";

            PreparedStatement stat = conn.prepareStatement(record);

            stat.setString(1, givenConfNo);

            getDeletionConfirmation(givenConfNo);
            stat.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }
    
    /**
     * Method removes all past bookings from the database.
     */
    public static void deletePastBookings() {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {
            String query = "DELETE FROM Booking WHERE date < CURDATE()";
            PreparedStatement stat = conn.prepareStatement(query);
            stat.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method takes all PT's specialities and adds them to array.
     * So they can be used later on for user to choose from combo box.
     */
    public static void addSpecialitiesToArray() {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            Statement stat = conn.createStatement();

            String query = "SELECT focus FROM Speciality";

            ResultSet rs = stat.executeQuery(query);

            while (rs.next()) {
                focus = rs.getString(1);
                NewBooking.specialities.add(focus);
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method adds all booked times to an array. 
     * 
     * @param date 
     */
    public static void addBookedTimeToArray(LocalDate date) {

        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String query = "SELECT timeframe FROM Booking WHERE date = ?";

            PreparedStatement stat = conn.prepareStatement(query);

            stat.setDate(1, Date.valueOf(date));

            ResultSet rs = stat.executeQuery();

            String timeFrame;
            String startTime;

            while (rs.next()) {
                timeFrame = rs.getString(1);

                if (timeFrame.length() == 4) {
                    startTime = timeFrame.substring(0, 1);
                } else {
                    startTime = timeFrame.substring(0, timeFrame.length() / 2);
                }
                bookedHours.clear();
                bookedHours.add(startTime);
                if (NewBooking.availableHours.contains(bookedHours.get(0))) {
                    NewBooking.availableHours.remove(bookedHours.get(0));
                }
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method checks if the chosen booking already exists or it collides with
     * PT's time schedule.
     * 
     * @param date
     * @param startTimeUserInput
     * @return boolean
     */
    public static boolean isTimeClash(LocalDate date, String startTimeUserInput) {

        boolean bool = false;
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String query = "SELECT timeframe FROM Booking WHERE date = ? && staffId = ?";

            PreparedStatement stat = conn.prepareStatement(query);

            stat.setDate(1, Date.valueOf(date));
            stat.setString(2, NewBooking.PTidText.getText());

            ResultSet rs = stat.executeQuery();

            String timeFrame;
            String startTime;

            while (rs.next()) {
                timeFrame = rs.getString(1);

                if (timeFrame.length() == 4) {
                    startTime = timeFrame.substring(0, 1);
                } else {
                    startTime = timeFrame.substring(0, timeFrame.length() / 2);
                }
                alreadyBookedHours.add(startTime);
            }
            if (alreadyBookedHours.contains(startTimeUserInput)) {
                bool = true;
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
        return bool;
    }

    /**
     * Method finds member name, and a date for the deleted booking.
     * 
     * @param givenConfNo 
     */
    public static void getDeletionConfirmation(String givenConfNo) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String query = "SELECT fName, lName, date FROM Member m, Booking b WHERE m.id = b.id && confirmation = ?";

            PreparedStatement stat = conn.prepareStatement(query);

            stat.setString(1, givenConfNo);

            ResultSet rs = stat.executeQuery();

            if (rs.next()) {
                String fName = rs.getString(1);
                String lName = rs.getString(2);
                date = rs.getString(3);
                String fullName = fName + " " + lName;
                result = "Booking of " + fullName + " for " + date + " has been deleted.";
            } else {
                result = "No booking found. Please try again with a different confirmation number";
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method retrieves booking details, so that they can be amended by the user.
     * 
     * @param givenConfNo 
     */
    public static void openRecord(String givenConfNo) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String query = "SELECT * FROM Booking WHERE confirmation = ? && date >= CURDATE()";

            PreparedStatement stat = conn.prepareStatement(query);

            stat.setString(1, givenConfNo);

            ResultSet rs = stat.executeQuery();

            if (rs.next()) {
                id = rs.getString(2);
                staffId = rs.getString(3);
                date = rs.getString(4);
                focus = rs.getString(6);
                result = "Record has been open.";
            } else {
                result = "No upcoming booking found. Please try again with a different confirmation number.";
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }

    /**
     * Method gets a new booking confirmation number.
     * 
     * @param id
     * @param staffId
     * @param date
     * @param timeFrame
     * @param focus 
     */
    public static void getConfirmationOfNewBooking(String id, String staffId, LocalDate date, String timeFrame, String focus) {
        try (Connection conn = DriverManager.getConnection(dburl, user, pwd)) {

            String confirmation = "SELECT confirmation FROM Booking WHERE id = ? && staffId = ? && date = ? && timeFrame = ? && focus = ?";

            PreparedStatement stat = conn.prepareStatement(confirmation);

            stat.setString(1, id);
            stat.setString(2, staffId);
            stat.setDate(3, Date.valueOf(date));
            stat.setString(4, timeFrame);
            stat.setString(5, focus);

            ResultSet rs = stat.executeQuery();

            if (rs.next()) {
                confirmationNo = rs.getString(1);
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
        }
    }
}