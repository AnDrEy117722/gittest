package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animation.Shake;

class ButtonOFF extends Thread {
    public static int nbThreads =  Thread.getAllStackTraces().keySet().size();
    Label label;
    ButtonOFF(Label label) {
        this.label = label;
    }
    public String nbThreads1;
    @Override
    public void run() {
        if (!label.isVisible()) {
            label.setVisible(true);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            label.setVisible(false);
        }
    }

}

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button authSignInButton;

    @FXML
    private Label error;

    @FXML
    private Button loginSignUpButton;

    @FXML
    void initialize() {

        authSignInButton.setOnAction(event -> {
            String loginText = login.getText().trim();
            String PasswordText = password.getText().trim();

            if (!loginText.equals("") && !PasswordText.equals("")) {
                loginUser(loginText, PasswordText);
            } else {
                ButtonOFF off = new ButtonOFF(error);
                off.start();
            }
        });

        loginSignUpButton.setOnAction(event -> {
            //Спрятать окно
            loginSignUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/signUp.fxml"));

            try {
                //Загрузка файла
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Путь к файлу
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            //Отображение окна
            stage.showAndWait();
        });
    }

    private void loginUser(String loginText, String passwordText) {
        DataBaseHandler dbHandler = new DataBaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(passwordText);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        try {
            while(result.next()) {
                counter++;
            }
        } catch (Exception e){}

        System.out.println(counter);
        if (counter >= 1) {
            authSignInButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/application.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } else {
            Shake userLoginAnim = new Shake(login);
            Shake userPassAnim = new Shake(password);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();
        }
    }
}

