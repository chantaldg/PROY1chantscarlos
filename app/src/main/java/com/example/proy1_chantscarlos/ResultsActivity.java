package com.example.proy1_chantscarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private TextView firstGradeTextView, secondGradeTextView, thirdGradeTextView;
    private TextView firstStudentNameTextView, secondStudentNameTextView, thirdStudentNameTextView;
    private TextView tieTextView, studentGradeTextViews, studentNameTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get the data passed from the MainActivity
        Intent intent = getIntent();
        int[] scores = intent.getIntArrayExtra("scores");
        String[] studentNames = intent.getStringArrayExtra("studentNames");

        // Find the TextViews in the layout
        firstGradeTextView = findViewById(R.id.firstGradeTextView);
        secondGradeTextView = findViewById(R.id.secondGradeTextView);
        thirdGradeTextView = findViewById(R.id.thirdGradeTextView);
        firstStudentNameTextView = findViewById(R.id.firstStudentNameTextView);
        secondStudentNameTextView = findViewById(R.id.secondStudentNameTextView);
        thirdStudentNameTextView = findViewById(R.id.thirdStudentNameTextView);
        tieTextView = findViewById(R.id.tieTextView);
        studentGradeTextViews = findViewById(R.id.studentGradeTextViews);
        /*studentNameTextViews = findViewById(R.id.studentNameTextViews);*/



        // Sort the grades in descending order
        int n = scores.length;
        for (int i = 0; i < n-1; i++) { //i es el index del score actual en el array scores
            for (int j = 0; j < n-i-1; j++) { //"j" es el índice del elemento actual del array "scores" en el bucle interno
                if (scores[j] < scores[j+1]) { //comprueba que las calificaciones estén intercambiadas para estar descendentes
                    // Swap grades
                    int tempGrade = scores[j]; //
                    scores[j] = scores[j+1];
                    scores[j+1] = tempGrade; //tempGrade variable temporal pa la calificacion
                    // Swap student names
                    String tempName = studentNames[j];
                    studentNames[j] = studentNames[j+1];
                    studentNames[j+1] = tempName;
                }
            }
        }

        // Check for ties
        boolean isTie = false;
        String tiedPositions = ""; //string vacio declarado para luego almacenar las posiciones
        String tiedStudents = "";//string vacio declarado para luego almacenar los nombres
        for (int i = 0; i < n-1; i++) { //pa recorrer scores, es n- porque hay empate hasta sexto lugar porque son solo 7 estudiantes
            if (scores[i] == scores[i+1]) {//se verifica si la calificacion del estudiante es igual a la del siguiente
                if (!isTie) {
                    isTie = true;
                    tiedPositions = (i+1) + "°"; //se almacena el primer empate en tiedPositions
                    tiedStudents = studentNames[i] + " y " + studentNames[i+1]; //same thing as before but for students
                } else { //whe additional ties are detected tiedpositios y tiedstudents se van concatenando (appending)
                    tiedPositions += ", " + (i+1) + "°";
                    tiedStudents += " y " + studentNames[i+1];
                }
            }
        }
        if (isTie) {
            firstGradeTextView.setText(String.valueOf(scores[0]));
            secondGradeTextView.setText(String.valueOf(scores[1]));
            thirdGradeTextView.setText(String.valueOf(scores[2]));
            firstStudentNameTextView.setText(studentNames[0]);
            secondStudentNameTextView.setText(studentNames[1]);
            thirdStudentNameTextView.setText(studentNames[2]);
            tieTextView.setText(tiedStudents + " están empatados en: " + tiedPositions + ".");
        } else {
            // Display the top 3 scores and their corresponding students
            firstGradeTextView.setText(String.valueOf(scores[0]));
            secondGradeTextView.setText(String.valueOf(scores[1]));
            thirdGradeTextView.setText(String.valueOf(scores[2]));
            firstStudentNameTextView.setText(studentNames[0]);
            secondStudentNameTextView.setText(studentNames[1]);
            thirdStudentNameTextView.setText(studentNames[2]);
        }
        /* Display all the students and their scores
        String allStudents = "";
        for (int i = 0; i < n; i++) {
            allStudents += studentNames[i] + ": " + scores[i] + "\n";
        }
        studentGradeTextViews.setText(allStudents); */
    }
}