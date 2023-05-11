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
    private String judgeName;
    private int[][] numScoresPerStudent = new int[7][1];
    private int[][] scores = new int[7][3];
    private boolean[][] scoresEntered = new boolean[7][1];

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

        Intent intent = getIntent();
        judgeName = intent.getStringExtra("judgeName");

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

                // Update scores for current student and judge
                numScoresPerStudent[currentStudentIndex][0]++;
                scores[currentStudentIndex][0] += score1 + score2 + score3;
                scoresEntered[currentStudentIndex][0] = true;

                // Move on to next student
                currentStudentIndex++;

                // Check if all judges except the last one have scored
                if (currentStudentIndex == 7) {
                    // Disable next button and enable submit and logout button
                    nextButton.setEnabled(false);
                    logoutButton.setEnabled(true);
                    submitButton.setEnabled(true);
                } else {
                    // Update student name and scores for the next student
                    studentNameTextView.setText(studentNames[currentStudentIndex]);
                    // Disable next button and enable logout button
                    nextButton.setEnabled(true);
                    logoutButton.setEnabled(false);
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate results
                int[] totalScores = new int[scores.length];
                for (int i = 0; i < scores.length; i++) {
                    int sum = 0;
                    for (int j = 0; j < scores[i].length; j++) {
                        sum += scores[i][j];
                    }
                    totalScores[i] = sum;
                }
// Initialize variables to store the top three scores and their corresponding student names
                int[] topThreeScores = new int[3];
                String[] topThreeNames = new String[3];

// Find the top three scores and their corresponding student names using selection sort
                for (int i = 0; i < 3; i++) {
                    int maxIndex = i;
                    for (int j = i + 1; j < totalScores.length; j++) {
                        if (totalScores[j] > totalScores[maxIndex]) {
                            maxIndex = j;
                        }
                    }
                    // Swap the maximum score with the score at the current index
                    int tempScore = totalScores[i];
                    totalScores[i] = totalScores[maxIndex];
                    totalScores[maxIndex] = tempScore;
                    // Swap the name of the student with the maximum score with the name of the student at the current index
                    String tempName = studentNames[i];
                    studentNames[i] = studentNames[maxIndex];
                    studentNames[maxIndex] = tempName;
                    // Store the top three scores and their corresponding student names
                    topThreeScores[i] = totalScores[i];
                    topThreeNames[i] = studentNames[i];
                }

// Create an intent to start the next activity and pass the top three grades and their names
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("firstGrade", topThreeScores[0]);
                intent.putExtra("secondGrade", topThreeScores[1]);
                intent.putExtra("thirdGrade", topThreeScores[2]);
                intent.putExtra("firstStudentName", topThreeNames[0]);
                intent.putExtra("secondStudentName", topThreeNames[1]);
                intent.putExtra("thirdStudentName", topThreeNames[2]);

// Start the next activity
                startActivity(intent);


            }
        });}}
