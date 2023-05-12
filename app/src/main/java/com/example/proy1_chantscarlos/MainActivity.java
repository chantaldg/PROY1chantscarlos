package com.example.proy1_chantscarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private TextView studentNameTextView, score1TextView, score2TextView, score3TextView, resultTextView;
    private EditText score1EditText, score2EditText, score3EditText;
    private Button nextButton, submitButton, logoutButton;
    private int currentStudentIndex = 0;
    private String[] studentNames = {"CHANTAL DE GRACIA", "CARLOS CAMPBELL", "DIEGO BURGOS", "LUIS CARREYÓ", "MAIKEL DOMÍNGUEZ", "ROMAS LESCURE", "EIVAR MORALES"};
    private int[][] scores = new int[7][3];
    private int[] totalScores = new int[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentNameTextView = findViewById(R.id.studentNameTextView);
        score1TextView = findViewById(R.id.score1TextView);
        score1EditText = findViewById(R.id.score1EditText);
        score2TextView = findViewById(R.id.score2TextView);
        score2EditText = findViewById(R.id.score2EditText);
        score3TextView = findViewById(R.id.score3TextView);
        score3EditText = findViewById(R.id.score3EditText);
        nextButton = findViewById(R.id.nextButton);
        logoutButton = findViewById(R.id.logoutButton);
        submitButton = findViewById(R.id.submitButton);

        // Initialize UI
        studentNameTextView.setText(studentNames[0]);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get scores entered for current student
                int score1 = Integer.parseInt(score1EditText.getText().toString());
                int score2 = Integer.parseInt(score2EditText.getText().toString());
                int score3 = Integer.parseInt(score3EditText.getText().toString());

                // Check if scores are within valid range
                if (score1 < 1 || score1 > 10 || score2 < 1 || score2 > 10 || score3 < 1 || score3 > 10) {
                    Toast.makeText(MainActivity.this, "Puntuación debe estar entre 1 y 10", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update scores for current student
                scores[currentStudentIndex][0] = score1;
                scores[currentStudentIndex][1] = score2;
                scores[currentStudentIndex][2] = score3;

                // Move on to next student
                currentStudentIndex++;

                // Check if all students have been scored
                if (currentStudentIndex == 7) {
                    // Disable next button and enable submit and logout button
                    nextButton.setEnabled(false);
                    logoutButton.setEnabled(true);
                    submitButton.setEnabled(true);
                } else {
                    // Update student name for the next student
                    studentNameTextView.setText(studentNames[currentStudentIndex]);
                    nextButton.setEnabled(true);
                    logoutButton.setEnabled(false);
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the login activity
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);

                // Finish the MainActivity
                finish();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate results
                for (int i = 0; i < scores.length; i++) {
                    int sum = 0;
                    for (int j = 0; j < scores[i].length; j++) {
                        sum += scores[i][j];
                    }
                    totalScores[i] = sum;
                }

                // Create an intent to start the next activity and pass the scores and student names
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("scores", totalScores);
                intent.putExtra("studentNames", studentNames);

                // Start the next activity
                startActivity(intent);
            }
        });}}
