
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

public class MyClient {

    static Socket socket;
    static int clientNo;
    static String msg;
    static String response = "";
    static String id = "";
    static String staffId = "";
    static String date = "";
    static String focus = "";
    static String bookingId = "";

    /**
     * Method allows client to connect with a Server.
     */
    public static void connectToServer() {
        try {
            socket = new Socket("localhost", 8888);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Method allows client to disconnect from the Server.
     */
    public static void disconnectFromServer() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Method requests information to be retrieved in a new window.
     * 
     * @param title         
     * @param date
     * @param membersId
     * @param PTid 
     */
    public static void sendRequestForMessage(String title, String date, String membersId, String PTid) {
        try {
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(SearchBooking.request);
            pw.println(membersId);
            pw.println(PTid);
            pw.println(date);
            pw.println("");
            pw.println("");
            pw.println("");

            while (sc.hasNext()) {
                response += sc.nextLine() + "\n";
            }

            DisplayInfo.display(title, response);
            response = "";

            disconnectFromServer();
            connectToServer();

        } catch (IOException ex) {
            Alert.display("Alert", "You are disconnected from the server."
                    + " \n Please connect from the main menu");
        }
    }

    /**
     * Method requests a new booking to be created.
     * 
     * @param id
     * @param staffId
     * @param date
     * @param timeFrame
     * @param focus 
     */
    public static void sendRequestForNewBooking(String id, String staffId, LocalDate date, String timeFrame, String focus) {

        try {
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("add");
            pw.println(id);
            pw.println(staffId);
            pw.println(date);
            pw.println(timeFrame);
            pw.println(focus);
            pw.println("");

            while (sc.hasNext()) {
                response = sc.nextLine();
            }

            NewBooking.availableHours.clear();

            for (int i = 6; i < 22; i++) {
                NewBooking.availableHours.add(Integer.toString(i));
            }

            if ("Booking has not been created. Member's id or PT's id do not exist.".equals(response)) {
                Alert.display("Alert", "Booking has not been created. Member's id or PT's id do not exist.");
                response = "";
            } else {
                Alert.display("Alert", "Booking has been created. Confirmation no: " + response);
                response = "";
                disconnectFromServer();
                connectToServer();
            }
        } catch (IOException ex) {
            Alert.display("Alert", "You are disconnected from the server."
                    + " \n Please connect from the main menu");
        }
    }

    /**
     * Method requests a modification to be made for the given booking.
     * 
     * @param id
     * @param staffId
     * @param date
     * @param timeFrame
     * @param focus
     * @param givenConfNo 
     */
    public static void sendRequestForModification(String id, String staffId, LocalDate date, String timeFrame, String focus, String givenConfNo) {
        disconnectFromServer();
        connectToServer();
        try {
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("modify");
            pw.println(id);
            pw.println(staffId);
            pw.println(date);
            pw.println(timeFrame);
            pw.println(focus);
            pw.println(givenConfNo);

            while (sc.hasNext()) {
                response = sc.nextLine();
                Alert.display("Alert", response);
            }
            response = "";
            disconnectFromServer();
            connectToServer();
        } catch (IOException ex) {
            Alert.display("Alert", "You are disconnected from the server."
                    + " \n Please connect from the main menu");
        }
    }

    /**
     * Method requests a pull out of the record of the existing booking.
     * 
     * @param givenConfNo 
     */
    public static void sendRequestForRecord(String givenConfNo) {
        try {
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("openRecord");
            pw.println("");
            pw.println("");
            pw.println("");
            pw.println("");
            pw.println("");
            pw.println(givenConfNo);

            while (sc.hasNext()) {
                response = sc.nextLine();
                id = sc.nextLine();
                staffId = sc.nextLine();
                date = sc.nextLine();
                focus = sc.nextLine();
                NewBooking.memberIdText.setText(id);
                NewBooking.PTidText.setText(staffId);
                NewBooking.datePicker.setValue(LocalDate.parse(date));
                NewBooking.startTime.getSelectionModel().select(focus);
                NewBooking.specialityChoice.getSelectionModel().select(focus);
            }

            if ("No upcoming booking found. Please try again with a different confirmation number.".equals(response)) {
                Alert.display("Alert", "No upcoming booking found. Please try again with a different confirmation number.");

                response = "";
                disconnectFromServer();
                connectToServer();

            } else {
                NewBooking.displayWindow("Modify");
                response = "";
                disconnectFromServer();
                connectToServer();
            }

        } catch (IOException ex) {
            Alert.display("Alert", "You are disconnected from the server."
                    + " \n Please connect from the main menu");
        }
    }

    /**
     * Method requests a deletion of a given confirmation.
     * 
     * @param givenConfNo 
     */
    public static void sendRequestForDeletion(String givenConfNo) {

        try {
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("delete");
            pw.println("");
            pw.println("");
            pw.println("");
            pw.println("");
            pw.println("");
            pw.println(givenConfNo);

            while (sc.hasNext()) {
                response = sc.nextLine();
            }

            if ("No booking found. Please try again with a different confirmation number.".equals(response)) {
                Alert.display("Alert", "No booking found. Please try again with a different confirmation number.");
                DeleteOrModifyBooking.confNoText.setText("");
                DeleteOrModifyBooking.confirmationRequest("Delete Booking", "Delete");
                response = "";
            } else {
                Alert.display("Alert", response);
                response = "";

                disconnectFromServer();
                connectToServer();
            }
        } catch (IOException ex) {
            Alert.display("Alert", "You are disconnected from the server."
                    + " \n Please connect from the main menu");
        }
    }
}
