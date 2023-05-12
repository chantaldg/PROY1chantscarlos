package com.example.proy1_chantscarlos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

public class login extends AppCompatActivity {

    // Constants for the valid username and password
    private static final String VALID_USERNAME = "juez1";
    private static final String VALID_PASSWORD = "123";

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
        return enteredUsername.equals(VALID_USERNAME) && enteredPassword.equals(VALID_PASSWORD);
    }
}
