package com.example.proy1_chantscarlos;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import java.util.Arrays;

public class login extends AppCompatActivity {

    // Array to store the usernames of the judges
    private String[] usernames = {"judge1", "judge2", "judge3"};

    // Array to store the passwords of the judges
    private String[] passwords = {"password1", "password2", "password3"};

    // UI elements
    private EditText userEditText;
    private EditText passwordEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize UI elements
        userEditText = findViewById(R.id.userEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.submitButtonlog);

        // Set click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and password
                String enteredUsername = userEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                // Check if the entered username and password are valid
                if (isValidUser(enteredUsername, enteredPassword)) {
                    // Create Intent to start MainActivity and pass the username
                    Intent intent = new Intent(login.this, MainActivity.class);
                    intent.putExtra("username", enteredUsername);
                    startActivity(intent);
                } else {
                    // Show an error message if the username or password is incorrect
                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Check if the entered username and password are valid
    private boolean isValidUser(String enteredUsername, String enteredPassword) {
        int index = Arrays.asList(usernames).indexOf(enteredUsername);
        if (index != -1 && passwords[index].equals(enteredPassword)) {
            return true;
        }
        return false;
    }
}
