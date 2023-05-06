package com.example.proy1_chantscarlos;

        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView studentNameTextView, score1TextView, score2TextView, score3TextView, resultTextView;
    private EditText judgeNameEditText;
    private SeekBar score1SeekBar, score2SeekBar, score3SeekBar;
    private Button nextButton, submitButton;
    private int currentStudentIndex = 0;
    private int currentJudgeIndex = 0;
    private String[] studentNames = {"CHANTAL DE GRACIA", "CARLOS CAMPBELL", "DIEGO BURGOS", "LUIS CARREYÓ", "MAIKEL DOMÍNGUEZ", "ROMAS LESCURE", "EIVAR MORALES"};
    private String[] judgeNames = new String[3];
    private int[][] scores = new int[7][9];
    private boolean[][] scoresEntered = new boolean[7][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentNameTextView = findViewById(R.id.studentNameTextView);
        score1TextView = findViewById(R.id.score1TextView);
        score1SeekBar = findViewById(R.id.score1SeekBar);
        score2TextView = findViewById(R.id.score2TextView);
        score2SeekBar = findViewById(R.id.score2SeekBar);
        score3TextView = findViewById(R.id.score3TextView);
        score3SeekBar = findViewById(R.id.score3SeekBar);
        judgeNameEditText = findViewById(R.id.judgeNameEditText);
        nextButton = findViewById(R.id.nextButton);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Initialize UI
        studentNameTextView.setText(studentNames[0]);
        score1TextView.setText("Puntuación 1 (Juez 1)");
        score2TextView.setText("Puntuación 2 (Juez 1)");
        score3TextView.setText("Puntuación 3 (Juez 1)");

        // Set up seek bars
        score1SeekBar.setMax(9);
        score2SeekBar.setMax(9);
        score3SeekBar.setMax(9);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move on to next student
                currentStudentIndex++;
                // Check if all students have been scored
                if (currentStudentIndex == 7) {
                    // Disable next button and enable submit button
                    nextButton.setEnabled(false);
                    submitButton.setEnabled(true);  }
                else {
                    // Update student name for the next student
                    studentNameTextView.setText(studentNames[currentStudentIndex]);
                }

                // Validate judge name
                String judgeName = judgeNameEditText.getText().toString().trim();
                if (judgeName.isEmpty()) {
                    judgeNameEditText.setError("Ingrese el nombre del juez.");
                    return;
                }
                if (Arrays.asList(judgeNames).contains(judgeName)) {
                    judgeNameEditText.setError("Este juez ya ha evaluado.");
                    return;
                }
                if (currentStudentIndex == 0) {
                    // Add first judge without checking
                    judgeNames[currentJudgeIndex] = judgeName;
                } else {
                    // Check if the judge has evaluated all previous students
                    boolean hasEvaluatedAllPrevious = true;
                    for (int i = 0; i <= currentStudentIndex - 1; i++) {
                        if (!scoresEntered[i][currentJudgeIndex]) {
                            hasEvaluatedAllPrevious = false;
                            break;}
                    }
                    if (!hasEvaluatedAllPrevious) {
                        judgeNameEditText.setError("Este juez debe evaluar al estudiante anterior antes de evaluar a este estudiante.");
                        return;
                    } else {
                        judgeNames[currentJudgeIndex] = judgeName;
                    }
                }
                judgeNameEditText.setText("");
                // Get scores entered for current student and judge
                scoresEntered[currentStudentIndex][currentJudgeIndex] = true;
                if (currentJudgeIndex == 2) {
                    // Move on to next student if all scores have been entered
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
                        if (currentStudentIndex == 7) {
                            // All students have been scored, calculate results
                            int[] totalScores = new int[7];
                            for (int i = 0; i < 7; i++) {
                                for (int j = 0; j < 9; j++) {
                                    totalScores[i] += scores[i][j];
                                }
                            }
                            // Determine top three students based on total scores
                            int[] sortedScores = totalScores.clone();
                            Arrays.sort(sortedScores);
                            String[] topThree = new String[3];
                            for (int i = 0; i < 3; i++) {
                                int index = Arrays.binarySearch(totalScores, sortedScores[6 - i]);
                                topThree[i] = studentNames[index];
                            }
                            // Display results
                            String result = "Resultados:\n\n";
                            for (int i = 0; i < 7; i++) {
                                result += studentNames[i] + ": " + totalScores[i] + "\n";
                            }
                            result += "\nPrimer lugar: " + topThree[0] + "\nSegundo lugar: " + topThree[1] + "\nTercer lugar: " + topThree[2];
                            resultTextView.setText(result);
                            submitButton.setEnabled(false);
                        }
                    }
                } else {
                    currentJudgeIndex++;
                }
            }
        }
        );
    }
}