package com.example.proy1_chantscarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.Arrays;
public class ResultsActivity extends AppCompatActivity {

    private TextView resultadoTextView;
    private TextView tieTextView;

    private String[] studentNames = {"CHANTAL DE GRACIA", "CARLOS CAMPBELL", "DIEGO BURGOS", "LUIS CARREYÓ", "MAIKEL DOMÍNGUEZ", "ROMAS LESCURE", "EIVAR MORALES"};
    private float[] averageScores = new float[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultadoTextView = findViewById(R.id.resultadoTextView);
        tieTextView = findViewById(R.id.tieTextView);

        // Get average scores and student names from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            averageScores = extras.getFloatArray("averageScores");
            studentNames = extras.getStringArray("studentNames");
        }

        // Sort averageScores in descending order
        Arrays.sort(averageScores);
        float[] sortedAverageScores = new float[7];
        for (int i = 0; i < 7; i++) {
            sortedAverageScores[i] = averageScores[6 - i];
        }

        // Display top three scores with corresponding student names
        String[] topThree = new String[3];
        float topScore = sortedAverageScores[0];
        for (int i = 0; i < 3; i++) {
            int index = -1;
            for (int j = 0; j < 7; j++) {
                if (averageScores[j] == sortedAverageScores[i]) {
                    index = j;
                    break;
                }
            }
            topThree[i] = studentNames[index] + ": " + String.format("%.2f", sortedAverageScores[i]);
            if (sortedAverageScores[i] != topScore) {
                break;
            }
        }
        resultadoTextView.setText(String.join("\n", topThree));

        // Check for ties in top three
        String tieMsg = "";
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 3; j++) {
                if (sortedAverageScores[i] == sortedAverageScores[j]) {
                    // There is a tie
                    String tieNames = studentNames[Arrays.binarySearch(averageScores, sortedAverageScores[i])];
                    for (int k = j; k < 3; k++) {
                        if (sortedAverageScores[i] == sortedAverageScores[k]) {
                            tieNames += ", " + studentNames[Arrays.binarySearch(averageScores, sortedAverageScores[k])];
                        }
                    }
                    tieMsg = "Empate entre " + tieNames + " en el puesto " + (i + 1) + ".";
                }
            }
        }
        tieTextView.setText(tieMsg);
    }
}