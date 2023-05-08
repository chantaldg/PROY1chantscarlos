package com.example.proy1_chantscarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private TextView firstGradeTextView, secondGradeTextView, thirdGradeTextView;
    private TextView firstStudentNameTextView, secondStudentNameTextView, thirdStudentNameTextView;
    private TextView tieTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get the data passed from the MainActivity
        Intent intent = getIntent();
        int firstGrade = intent.getIntExtra("firstGrade", 0);
        int secondGrade = intent.getIntExtra("secondGrade", 0);
        int thirdGrade = intent.getIntExtra("thirdGrade", 0);
        String firstStudentName = intent.getStringExtra("firstStudentName");
        String secondStudentName = intent.getStringExtra("secondStudentName");
        String thirdStudentName = intent.getStringExtra("thirdStudentName");

        // Find the TextViews in the layout
        firstGradeTextView = findViewById(R.id.firstGradeTextView);
        secondGradeTextView = findViewById(R.id.secondGradeTextView);
        thirdGradeTextView = findViewById(R.id.thirdGradeTextView);
        firstStudentNameTextView = findViewById(R.id.firstStudentNameTextView);
        secondStudentNameTextView = findViewById(R.id.secondStudentNameTextView);
        thirdStudentNameTextView = findViewById(R.id.thirdStudentNameTextView);
        tieTextView = findViewById(R.id.tieTextView);

        // Set the text of the TextViews to display the data passed from MainActivity
        firstGradeTextView.setText(String.valueOf(firstGrade));
        secondGradeTextView.setText(String.valueOf(secondGrade));
        thirdGradeTextView.setText(String.valueOf(thirdGrade));
        firstStudentNameTextView.setText(firstStudentName);
        secondStudentNameTextView.setText(secondStudentName);
        thirdStudentNameTextView.setText(thirdStudentName);

        // Check for ties
        if (firstGrade == secondGrade || secondGrade == thirdGrade) {
            String tiedStudents = "";
            if (firstGrade == secondGrade) {
                tiedStudents = firstStudentName + " and " + secondStudentName;
            } else if (secondGrade == thirdGrade) {
                tiedStudents = secondStudentName + " and " + thirdStudentName;
            }
            String tiedPositions = "";
            if (firstGrade == secondGrade && secondGrade == thirdGrade) {
                tiedPositions = "1st, 2nd, and 3rd";
            } else if (firstGrade == secondGrade) {
                tiedPositions = "1st and 2nd";
            } else if (secondGrade == thirdGrade) {
                tiedPositions = "2nd and 3rd";
            }
            tieTextView.setText(tiedStudents + " are tied for " + tiedPositions + " place.");
        }
    }
}
