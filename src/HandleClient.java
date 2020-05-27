
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Thread class allows multiple users to connect concurrently to the server.
 * It compares client's request and sends desirable data back to client.
 */
public class HandleClient implements Runnable {

    private Socket socket;
    public static int condition;

    static String msg;
    static String bookingId;
    static String memberId;
    static String PTid;
    static String date;
    static String timeframe;
    static String focus;

    public HandleClient(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        switch (condition) {
            case 1: //Start of client
                MyClient.connectToServer(); //Connecting to the server.
                GymDB.deletePastBookings(); //Deleting all past bookings on start of the client.
                break;
            default:
                try {
                    PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                    Scanner sc = new Scanner(socket.getInputStream());

                    if (sc.hasNext()) {
                        msg = sc.nextLine();
                        memberId = sc.nextLine();
                        PTid = sc.nextLine();
                        date = sc.nextLine();
                        timeframe = sc.nextLine();
                        focus = sc.nextLine();
                        bookingId = sc.nextLine();
                    }

                    if (null != msg) {
                        switch (msg) {
                            case "listAll": //requests all existing bookings
                                GymDB.listAll();
                                pw.println(GymDB.result);
                                pw.close();
                                break;
                            case "listDate": //requests bookings by date
                                GymDB.listGivenDate(date);
                                pw.println(GymDB.result);
                                pw.close();
                                break;
                            case "listMember": //requests single member bookings
                                GymDB.listGivenMember(memberId);
                                pw.println(GymDB.result);
                                pw.close();
                                break;
                            case "listPT": //requests single PT bookings
                                GymDB.listGivenPT(PTid);
                                pw.println(GymDB.result);
                                pw.close();
                                break;
                            case "delete": //deletes the booking
                                GymDB.deleteBooking(bookingId);
                                pw.println(GymDB.result);
                                pw.close();
                                break;
                            case "add": //adds new the booking
                                GymDB.addBooking(memberId, PTid, LocalDate.parse(date), timeframe, focus);
                                pw.println(GymDB.confirmationNo);
                                pw.close();
                                break;
                            case "openRecord": //opens record of existing booking
                                GymDB.openRecord(bookingId);
                                pw.println(GymDB.result);
                                pw.println(GymDB.id);
                                pw.println(GymDB.staffId);
                                pw.println(GymDB.date);
                                pw.println(GymDB.focus);
                                pw.close();
                                break;
                            case "modify": //modifies the record of existing booking
                                GymDB.modifyBooking(memberId, PTid, LocalDate.parse(date), timeframe, focus, bookingId);
                                pw.println(GymDB.result);
                                pw.close();
                                break;
                        }
                    }
                } catch (IOException ex) {
                    System.err.println("One of the clients has disconnected");
                }
                break;
        }
    }
}