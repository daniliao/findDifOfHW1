package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class firstUser extends Application {
    private static HashMap<String, String> userDatabase = new HashMap<>();
    private static HashMap<String, Set<String>> userRoles = new HashMap<>();
    private static boolean firstUserCreated = false;

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showFirstUserSetupScreen();
    }

    private void showFirstUserSetupScreen() {
        Label label = new Label("Create First User (Admin)");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button createButton = new Button("Create Account");

        createButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                userDatabase.put(username, password);
                Set<String> roles = new HashSet<>();
                roles.add("Admin");
                userRoles.put(username, roles);
                firstUserCreated = true;
                showLoginScreen();
            }
        });

        VBox layout = new VBox(10, label, usernameField, passwordField, createButton);
        layout.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(layout, 400, 300));
        primaryStage.show();
    }

    private void showLoginScreen() {
        Label label = new Label("Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                Set<String> roles = userRoles.get(username);
                if (roles.size() > 1) {
                    showRoleSelectionScreen(username, roles);
                } else {
                    showRoleHomeScreen(roles.iterator().next());
                }
            }
        });

        VBox layout = new VBox(10, label, usernameField, passwordField, loginButton);
        layout.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(layout, 400, 300));
        primaryStage.show();
    }

    private void showRoleSelectionScreen(String username, Set<String> roles) {
        Label label = new Label("Select Your Role");
        ComboBox<String> roleDropdown = new ComboBox<>();
        roleDropdown.getItems().addAll(roles);
        Button selectButton = new Button("Select");

        selectButton.setOnAction(e -> {
            String selectedRole = roleDropdown.getValue();
            if (selectedRole != null) {
                showRoleHomeScreen(selectedRole);
            }
        });

        VBox layout = new VBox(10, label, roleDropdown, selectButton);
        layout.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(layout, 400, 300));
    }

    private void showRoleHomeScreen(String role) {
        Label label = new Label("Welcome to " + role + " Home Screen");
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> showLoginScreen());

        VBox layout = new VBox(10, label, logoutButton);
        layout.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(layout, 400, 300));
    }
}
