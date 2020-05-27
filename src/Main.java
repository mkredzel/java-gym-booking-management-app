
import java.time.LocalDate;
import java.util.LinkedHashSet;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

/**
 * The Main program extends an application that starts a new client.
 * It opens up a JavaFX GUI and allows user to Create, Read, Update and Delete 
 * data from the database. Java is communicating with a MySQL database by JDBC.
 */
public class Main extends Application {

    static Button disconnectBtn = new Button("Disconnect");
    static Label disconnectLabel = new Label("Disconnect from the server");

    @Override
    public void start(Stage primaryStage) {

        Label welcomeMsg = new Label("Welcome to PT bookings");

        Label addLabel = new Label("Add new booking");
        Label modifyLabel = new Label("Modify existing booking");
        Label deleteLabel = new Label("Delete existing booking");
        Label searchLabel = new Label("Search for existing booking");

        Button addBtn = new Button("Add");
        Button modifyBtn = new Button("Modify");
        Button deleteBtn = new Button("Delete");
        Button searchBtn = new Button("Search");

        //Adds specialities to an array and creates a set out of them. 
        GymDB.addSpecialitiesToArray();
        NewBooking.setOfSpecialities = new LinkedHashSet<>(NewBooking.specialities);
        
        //Set of specialities is being used to create a simple choice box.
        NewBooking.specialityChoice = new ChoiceBox(FXCollections.observableArrayList(
                NewBooking.setOfSpecialities)
        );

        // Gym working hours added to array.
        for (int i = 6; i < 22; i++) {
            NewBooking.availableHours.add(Integer.toString(i));
        }

        // Choice box with available booking hours.
        NewBooking.startTime = new ChoiceBox(FXCollections.observableArrayList(
                NewBooking.availableHours));

        GymDB.addBookedTimeToArray(LocalDate.now());

        for (int i = 0; i < GymDB.bookedHours.size(); i++) {
            if (NewBooking.availableHours.contains(GymDB.bookedHours.get(i))) {
                NewBooking.availableHours.remove(GymDB.bookedHours.get(i));
            }
        }

        NewBooking.startTime.setItems(FXCollections.observableArrayList(NewBooking.availableHours));

        addBtn.setOnAction((e) -> {
            NewBooking.memberIdText.setText("");
            NewBooking.PTidText.setText("");
            NewBooking.datePicker.setValue(LocalDate.now());
            NewBooking.displayWindow("Add");
        });

        modifyBtn.setOnAction((e) -> {
            DeleteOrModifyBooking.confirmationRequest("Modify Booking", "Modify");
        });

        deleteBtn.setOnAction((e) -> {
            DeleteOrModifyBooking.confirmationRequest("Delete Booking", "Delete");
        });

        searchBtn.setOnAction((e) -> {
            SearchBooking.displaySearch();
        });

        //Connecting and disconnecting from the server on click.
        disconnectBtn.setOnAction((e) -> {

            if ("Disconnect".equals(Main.disconnectBtn.getText())) {
                MyClient.disconnectFromServer();
                disconnectBtn.setText("Connect");
                disconnectLabel.setText("Connect to the server");
            } else {
                MyClient.connectToServer();
                disconnectBtn.setText("Disconnect");
                disconnectLabel.setText("Disconnect from the server");
            }
        });

        GridPane menu = new GridPane();
        menu.add(addLabel, 0, 1);
        menu.add(addBtn, 1, 1);
        menu.add(modifyLabel, 0, 2);
        menu.add(modifyBtn, 1, 2);
        menu.add(deleteLabel, 0, 3);
        menu.add(deleteBtn, 1, 3);
        menu.add(searchLabel, 0, 4);
        menu.add(searchBtn, 1, 4);
        menu.add(disconnectLabel, 0, 5);
        menu.add(disconnectBtn, 1, 5);

        VBox pane = new VBox(5, welcomeMsg, menu);

        VBox.setMargin(welcomeMsg, new Insets(20, 15, 0, 0));
        VBox.setMargin(menu, new Insets(15, 0, 0, 10));
        pane.setAlignment(Pos.TOP_CENTER);

        welcomeMsg.setStyle("-fx-font-size: 1.7em; ");

        addBtn.setMinWidth(100);
        modifyBtn.setMinWidth(100);
        deleteBtn.setMinWidth(100);
        searchBtn.setMinWidth(100);
        disconnectBtn.setMinWidth(100);
        addBtn.setStyle("-fx-font-size: 1.2em; ");
        modifyBtn.setStyle("-fx-font-size: 1.2em; ");
        deleteBtn.setStyle("-fx-font-size: 1.2em; ");
        searchBtn.setStyle("-fx-font-size: 1.2em; ");
        disconnectBtn.setStyle("-fx-font-size: 1.2em; ");

        addLabel.setStyle("-fx-font-size: 1.2em; ");
        modifyLabel.setStyle("-fx-font-size: 1.2em; ");
        deleteLabel.setStyle("-fx-font-size: 1.2em; ");
        searchLabel.setStyle("-fx-font-size: 1.2em; ");
        disconnectLabel.setStyle("-fx-font-size: 1.2em; ");

        menu.setVgap(5);
        menu.setHgap(15);

        Scene scene = new Scene(pane, 350, 310);
        primaryStage.setTitle("PT Software");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Starting a new thread.
        HandleClient.condition = 1;
        new Thread(new HandleClient(MyClient.socket)).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
