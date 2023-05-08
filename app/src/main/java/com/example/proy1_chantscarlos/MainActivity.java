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
        resultTextView = findViewById(R.id.resultTextView);

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

                /* Check if current judge has already scored the current student
                for (int i = 0; i < 3; i++) {
                    if (scoresEntered[currentStudentIndex][i] && judgeNames[i].equals(judgeName)) {
                        Toast.makeText(MainActivity.this, "Este juez ya ha calificado a este estudiante", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
*/
                // Update scores for current student and judge
                scores[currentStudentIndex][currentJudgeIndex * 3] = score1;
                scores[currentStudentIndex][currentJudgeIndex * 3 + 1] = score2;
                scores[currentStudentIndex][currentJudgeIndex * 3 + 2] = score3;
                scoresEntered[currentStudentIndex][currentJudgeIndex] = true;

                // Move on to next judge or student
                if (currentJudgeIndex == 2) {
                    // Move on to next student if all scores have been entered for current student
                    boolean allScoresEntered = true;
                    for (int i = 0; i < 3; i++) {
                        if (!scoresEntered[currentStudentIndex][i]) {
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
                float[] averageScores = new float[7];
                for (int i = 0; i < 7; i++) {
                    float totalScore = 0;
                    int scoresEntered = 0;
                    for (int j = 0; j < 9; j++) {
                        if (scores[i][j] != 0) {
                            totalScore += scores[i][j];
                            scoresEntered++;
                        }
                    }
                    if (scoresEntered != 0) {
                        averageScores[i] = totalScore / scoresEntered;
                    }
                }
                // Disable submit button
                submitButton.setEnabled(false);
                // Start ResultsActivity and pass averageScores
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("averageScores", averageScores);
                startActivity(intent);
            }
        });}}
