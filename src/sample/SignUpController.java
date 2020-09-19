package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpCountry;

    @FXML
    private RadioButton signUpRadioMale;

    @FXML
    private ToggleGroup src;

    @FXML
    private RadioButton signUpRadioFemale;

    @FXML
    void initialize() {

        SignUpButton.setOnAction(event -> {
            signUpNewUser();
        });
    }

    private void signUpNewUser() {
        DataBaseHandler dbHandler = new DataBaseHandler();

        String firstName = signUpName.getText();
        String lastName = signUpLastName.getText();
        String userName = login.getText();
        String passwordText = password.getText();
        String location = signUpCountry.getText();
        String gender = "";
        if (signUpRadioMale.isSelected())
            gender = "Мужской";
        else if (signUpRadioFemale.isSelected())
            gender = "Женский";

        User user = new User(firstName, lastName, userName, passwordText, location, gender);

        dbHandler.signUpUser(user);
    }
}
