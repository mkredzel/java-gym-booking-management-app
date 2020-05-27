
import java.time.LocalDate;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javafx.collections.FXCollections;

public class NewBooking {

    static LinkedHashSet<String> setOfSpecialities;

    static ArrayList<String> specialities = new ArrayList<>();
    static ArrayList<String> availableHours = new ArrayList<>();
    static ArrayList<String> stillAvailableHours = new ArrayList<>();

    static TextField memberIdText = new TextField("");
    static TextField PTidText = new TextField("");

    static Label endTimeText = new Label("1 hour");

    static DatePicker datePicker = new DatePicker();

    static ChoiceBox startTime;
    static ChoiceBox specialityChoice;

    static String userInputStart;

    /**
     * Method displays a window with all the text fields needed for the booking
     * to be created or modified.
     * 
     * @param buttonName 
     */
    public static void displayWindow(String buttonName) {

        Stage window = new Stage();

        window.setTitle("New Booking");

        Label memberIdLabel = new Label("Member's id");
        Label PTidLabel = new Label("Personal Trainer's id");
        Label dateLabel = new Label("Select a date");
        Label startTimeLabel = new Label("Start Time");
        Label endTimeLabel = new Label("Length of the session");
        Label specialityLabel = new Label("Session's Focus");
        Label showavailabilityLabel = new Label("Show start time availability");

        Button showavailabilityBtn = new Button("Update Available Hours");
        Button addBtn = new Button(buttonName);
        Button backBtn = new Button("Back");

        startTime.getSelectionModel().selectFirst();
        specialityChoice.getSelectionModel().selectFirst();

        //Lambda method to disable past dates from date picker
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        addBtn.setOnAction(e -> {

            String userInputMember = memberIdText.getText();
            String userInputPT = PTidText.getText();
            userInputStart = startTime.getValue().toString();
            LocalDate date = datePicker.getValue();
            int userInputEnd = Integer.parseInt(userInputStart) + 1;
            String timeFrame = userInputStart + "-" + userInputEnd;
            String focus = specialityChoice.getValue().toString();

            //Validation of the users input.
            if ("".equals(userInputMember) || "".equals(userInputPT) || "".equals(userInputStart)) {
                Alert.display("Alert", "All fields must be filled out.");
            } else if (!userInputMember.matches("[0-9]+") || !userInputPT.matches("[0-9]+") || !userInputStart.matches("[0-9]+")) {
                Alert.display("Alert", "All values must be digits.");
            } else if (userInputMember.length() < 4 || userInputPT.length() < 4) {
                Alert.display("Alert", "Member's id or PT's id are to short");
            } else if (GymDB.isTimeClash(date, userInputStart)) {
                Alert.display("Alert", "Slot is already taken. Please try with different time.");
            } else if ("Modify".equals(addBtn.getText()) && !GymDB.isTimeClash(date, userInputStart)) {
                MyClient.sendRequestForModification(userInputMember, userInputPT, date, timeFrame, focus, DeleteOrModifyBooking.confNoText.getText());
            } else if ("Add".equals(addBtn.getText()) && !GymDB.isTimeClash(date, userInputStart)) {
                MyClient.sendRequestForNewBooking(userInputMember, userInputPT, date, timeFrame, focus);
            }
        });

        //Combo box updates its values on click.
        showavailabilityBtn.setOnAction(e -> {

            userInputStart = startTime.getValue().toString();
            LocalDate date = datePicker.getValue();

            availableHours.clear();

            for (int i = 6; i < 22; i++) {
                NewBooking.availableHours.add(Integer.toString(i));
            }
            GymDB.addBookedTimeToArray(date);

            for (int i = 0; i < GymDB.bookedHours.size(); i++) {

                if (NewBooking.availableHours.contains(GymDB.bookedHours.get(i))) {
                    NewBooking.availableHours.remove(GymDB.bookedHours.get(i));
                }
            }
            startTime.setItems(FXCollections.observableArrayList(availableHours));
            startTime.getSelectionModel().selectFirst();
        });

        backBtn.setOnAction(e -> {
            window.close();
        });

        GridPane addWindow = new GridPane();
        addWindow.add(memberIdLabel, 0, 1);
        addWindow.add(memberIdText, 1, 1);
        addWindow.add(PTidLabel, 0, 2);
        addWindow.add(PTidText, 1, 2);
        addWindow.add(dateLabel, 0, 3);
        addWindow.add(datePicker, 1, 3);
        addWindow.add(showavailabilityLabel, 0, 4);
        addWindow.add(showavailabilityBtn, 1, 4);
        addWindow.add(startTimeLabel, 0, 5);
        addWindow.add(startTime, 1, 5);
        addWindow.add(endTimeLabel, 0, 6);
        addWindow.add(endTimeText, 1, 6);
        addWindow.add(specialityLabel, 0, 7);
        addWindow.add(specialityChoice, 1, 7);

        HBox submit = new HBox(10, addBtn, backBtn);

        VBox pane = new VBox(10, addWindow, submit);

        addBtn.setMinWidth(100);
        backBtn.setMinWidth(100);

        VBox.setMargin(addWindow, new Insets(0, 0, 0, 20));

        addWindow.setVgap(5);
        addWindow.setHgap(20);

        submit.setStyle("-fx-font-size: 1.2em; ");

        memberIdText.setMaxWidth(150);
        PTidText.setMaxWidth(150);
        startTime.setMaxWidth(150);
        endTimeText.setMaxWidth(150);
        datePicker.setMaxWidth(150);
        specialityChoice.setMaxWidth(150);

        submit.setAlignment(Pos.CENTER);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 350, 280);
        window.setScene(scene);
        window.showAndWait();
    }
}