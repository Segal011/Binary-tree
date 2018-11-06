import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class WindowFX extends Application {

    private TextArea textArea = new TextArea();
    private TextArea exceptionArea = new TextArea();
    private TextArea name = new TextArea();
    private TextArea surname = new TextArea();
    private TextArea setCame = new TextArea();
    private TextArea country = new TextArea();
    private ComboBox age = new ComboBox();
    private ComboBox gender = new ComboBox();
    private ComboBox came = new ComboBox();
    private static final TextField roomsCount = new TextField();
    private final TextField removedCode = new TextField();
    FileChooser chooser = new FileChooser();


    private BST<Participant> particSet;
    Stage stage;

    public  WindowFX(){
    }

    public static void main(String[] args) {
        launch( args );
    }

    public void writeMessage(String text) {
        SysOut.setFormatStartOfLine(true);
        SysOut.oun(exceptionArea, text);
        SysOut.setFormatStartOfLine(false);
    }

    @Override
    public void start(Stage primaryStage){
        particSet = new BST<>(  );
        exceptionArea.clear();
        textArea.clear();
        Region spacer = new Region();
        spacer.setPrefSize( 10,10 );
        textArea.setPrefSize( 100, 600 );
        exceptionArea.setPrefSize( 100, 100 );
        name.setPromptText("Name");
        name.setPrefSize( 20, 10 );
        surname.setPromptText("Surname");
        surname.setPrefSize( 20, 10 );
        country.setPrefSize( 10, 10 );
        country.setPromptText("Country");
        age.getItems().addAll(  18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30);
        age.resize( 40, 10 );
        age.setPromptText( "Age" );
        gender.getItems().addAll( "m", "v" );
        gender.resize( 40, 10 );
        gender.setPromptText( "Gender" );
        came.getItems().addAll( "true", "false" );
        came.resize( 40, 10 );
        came.setPromptText( "Arrival" );
        setCame.setPromptText("Code");
        setCame.setPrefSize( 20, 10 );
        roomsCount.setPromptText("Number of rooms");
        removedCode.setPromptText("Code");
        chooser.setTitle("Open Resource File");
        File defaultDirectory = new File("D:/Java/Lab2/files");
        chooser.setInitialDirectory( defaultDirectory );
        stage = primaryStage;
        stage.setTitle( "Participants Accommodation");

        GridPane grid = new GridPane();
        grid.setPadding( new Insets(10, 10, 10, 10) );
        grid.setHgap( 10 );
        grid.setVgap( 8 );

        HBox topTitle = new HBox();
        Label label = new Label("Participants accommodation");
        label.setStyle("-fx-font-size:40px;-fx-text-fill: #FFFFFF");
        topTitle.getChildren().addAll( label);

        VBox leftMenu = new VBox(  );
        leftMenu.setSpacing( 5 );
        Button buttonGenerate = new Button("Set participants");
        buttonGenerate.setLineSpacing( 10.0 );
        buttonGenerate.setOnAction( e -> {
            buttonGenerate.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            try {
                File file = chooser.showOpenDialog( stage );
                participantsGeneration(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } );

        Button buttonAdd = new Button("Add new participant");
        buttonAdd.setOnAction( e-> {
            if( name.getText() == null || surname.getText() == null ||
                    country.getText() == null || age.getValue() == null
                    || gender.getValue() == null || came.getValue() == null){
                SysOut.setFormatStartOfLine(true);
                SysOut.oun(exceptionArea,"Wrong Data");
                SysOut.setFormatStartOfLine(false);
                return;
            }
            addParticipant( new Participant(  name.getText(  ),  surname.getText(  ),  country.getText(  ),  age.getValue().toString(),  gender.getValue().toString(),  came.getValue().toString() ));
            buttonAdd.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));});
        Button buttonRemove = new Button("Remove participant");

        buttonRemove.setOnAction( e-> {removeParticipant( );
            buttonRemove.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));});
        Button buttonCountries  = new Button("Show countries list");

        buttonCountries.setOnAction( e-> {
            showCountries();
            buttonCountries.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));});
        Button setCameButton  = new Button("Change state of Arriving");

        setCameButton.setOnAction( e-> {
            setParticipantsArrival();
            setCameButton.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));});
        Button buttonCame = new Button("Show participants who is coming");

        buttonCame.setOnAction( e-> {showWhoCame();
            buttonCame.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));});
        Button buttonRooms = new Button("Show rooms list");

        buttonRooms.setOnAction( e-> {setRooms();
            buttonRooms.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));});
        Button buttonClear = new Button("Clear everything");

        buttonClear.setOnAction( e-> {ParticipantsAccounting.clearEverything();
            start( stage ); });


        leftMenu.getChildren().addAll( buttonGenerate, buttonAdd, name, surname,
                country, age, gender, came, spacer, buttonRemove, removedCode,
                buttonCountries, buttonCame, setCameButton, setCame, buttonRooms, roomsCount, buttonClear);

        VBox centreField = new VBox(  );
        centreField.getChildren().addAll(textArea);

        BorderPane borderPane = new BorderPane(  );
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setBackground( new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setTop( topTitle );
        borderPane.setLeft( leftMenu );
        borderPane.setCenter( centreField );
        borderPane.setBottom( exceptionArea );

        Scene scene = new Scene( borderPane, 800,800 );
        stage.setScene( scene );
        stage.show();
    }


    private void participantsGeneration(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File is not existing");
        }
        else{
           // ParticipantsAccounting.newList();
            ParticipantsAccounting.readingFromFile( file );
            particSet = ParticipantsAccounting.getParticipants();
            SysOut.setFormatStartOfLine( true );
            SysOut.oun(textArea, particSet.toVisualizedString(),
                   "Participants data");
            SysOut.setFormatStartOfLine(false);
        }

    }


    private void addParticipant(Participant partic) {
        SysOut.setFormatStartOfLine(true);

        particSet.add(partic);
        SysOut.oun(exceptionArea, partic, "Participants is added to list");
        SysOut.oun(textArea, particSet.toVisualizedString(), "Participant list after added new participant");
        SysOut.setFormatStartOfLine(false);
    }


    private void removeParticipant() {
        SysOut.setFormatStartOfLine(true);
        if (particSet.isEmpty()) {
            SysOut.oun(exceptionArea,"List is empty");
           //SysOut.oun(textArea, particSet.toVisualizedString());
        } else {
            Participant partic = ParticipantsAccounting.getParticipantByCode( particSet.root, removedCode.getText());
            if(partic == null){
                SysOut.oun( exceptionArea, "The participant is not in the list");
                SysOut.setFormatStartOfLine(false);
                return;
            }
            particSet.remove(partic);
            SysOut.oun(exceptionArea, partic, "Removed participant");
            SysOut.oun(textArea, particSet.toVisualizedString(), "Participants list");
        }
        SysOut.setFormatStartOfLine(false);
    }

    private void showWhoCame() {
        ParticipantsAccounting.newList();
        ParticipantsAccounting.findCameParticipants( particSet.root );
        BST<Participant> camePartic = ParticipantsAccounting.getParticipantsFromList();
        SysOut.setFormatStartOfLine(true);
        SysOut.oun(textArea, camePartic.toVisualizedString(),"Participants who is coming to project");
        SysOut.setFormatStartOfLine(false);
    }

    private void setParticipantsArrival() {
        Participant partic = ParticipantsAccounting.getParticipantByCode( particSet.root, setCame.getText());
        partic.setCame();
        SysOut.setFormatStartOfLine(true);
        SysOut.oun(exceptionArea, partic.toString(),"Participants is/ is not coming");
        SysOut.oun(textArea, particSet.toVisualizedString(),"Participants list");
        SysOut.setFormatStartOfLine(false);
    }

    private void showCountries(){
        ParticipantsAccounting.newList();
        ParticipantsAccounting.participantsCountries( particSet.root );
        ArrayList<String> countries = ParticipantsAccounting.getString();
        SysOut.setFormatStartOfLine( true );
        SysOut.oun( textArea, countries, "Countries List" );
        SysOut.setFormatStartOfLine( false );

    }

    private void setRooms(){
        try
        {
            ParticipantsAccounting.newList();
            ParticipantsAccounting.setRooms( particSet.root, new Integer( roomsCount.getText() ) );
            BST<Room> rooms = ParticipantsAccounting.getRooms();
            SysOut.setFormatStartOfLine(true);
            SysOut.oun(textArea, rooms.toVisualizedString(), "Rooms list");
            SysOut.setFormatStartOfLine(false);

        }
        catch (NumberFormatException e)
        {
            SysOut.oun( exceptionArea, "Rooms number is not a valid number");
        }
    }

}
