package com.example.proy1_chantscarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView studentNameTextView, score1TextView, score2TextView, score3TextView, resultTextView;
    private EditText judgeNameEditText, score1EditText, score2EditText, score3EditText;
    private Button nextButton, submitButton;
    private int currentStudentIndex = 0;
    private int currentJudgeIndex = 0;
    private String[] studentNames = {"CHANTAL DE GRACIA", "CARLOS CAMPBELL", "DIEGO BURGOS", "LUIS CARREYÓ", "MAIKEL DOMÍNGUEZ", "ROMAS LESCURE", "EIVAR MORALES"};
    private String[] judgeNames = {"juez1", "juez2", "juez3"};

    private int[][] numScoresPerJudgePerStudent = new int[7][3];
    private int[][] scores = new int[7][9];
    private boolean[][] scoresEntered = new boolean[7][3];

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
        judgeNameEditText = findViewById(R.id.judgeNameEditText);
        nextButton = findViewById(R.id.nextButton);
        submitButton = findViewById(R.id.submitButton);

        // Initialize UI
        studentNameTextView.setText(studentNames[0]);
        score1TextView.setText("Puntuación 1");
        score2TextView.setText("Puntuación 2");
        score3TextView.setText("Puntuación 3");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate judge name
                String judgeName = judgeNameEditText.getText().toString();
                if (!Arrays.asList(judgeNames).contains(judgeName)) {
                    Toast.makeText(MainActivity.this, "Nombre del juez inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                numScoresPerJudgePerStudent[currentStudentIndex][currentJudgeIndex]++;
                scores[currentStudentIndex][currentJudgeIndex] += score1 + score2 + score3;

                // Move on to next judge or student
                if (currentJudgeIndex == 2) {
                    // Move on to next student if all scores have been entered for current student
                    boolean allScoresEntered = true;
                    for (int i = 0; i < 3; i++) {
                        if (numScoresPerJudgePerStudent[currentStudentIndex][i] == 0) {
                            allScoresEntered = false;
                            break;
                        }
                    }
                    if (allScoresEntered) {
                        currentStudentIndex++;
                        currentJudgeIndex = 0;
                    }
                } else {
                    // Move on to next judge
                    currentJudgeIndex++;
                }

                // Check if all students have been scored
                if (currentStudentIndex == 7) {
                    // Disable next button and enable submit button
                    nextButton.setEnabled(false);
                    submitButton.setEnabled(true);
                } else {
                    // Update student name and scores for the next student
                    studentNameTextView.setText(studentNames[currentStudentIndex]);
                    score1EditText.setText("");
                    score2EditText.setText("");
                    score3EditText.setText("");
                }
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
