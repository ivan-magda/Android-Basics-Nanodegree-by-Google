package com.ivanmagda.quiz.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanmagda.quiz.R;
import com.ivanmagda.quiz.model.Question;
import com.ivanmagda.quiz.ui.adapter.QuizResultsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuizResultsActivity extends AppCompatActivity {

    private ListView answersListView;

    private Question[] questions;
    private HashMap<Integer, Boolean> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        configure();
        updateUI();
    }

    private void configure() {
        Intent intent = getIntent();
        questions = (Question[]) intent.getSerializableExtra(QuizActivity.EXTRA_QUESTIONS);
        answers = (HashMap<Integer, Boolean>) intent.getSerializableExtra(QuizActivity.EXTRA_ANSWERS);

        answersListView = (ListView) findViewById(R.id.answers_list_view);

        ArrayList<Question> items = new ArrayList<>(Arrays.asList(questions));
        QuizResultsAdapter adapter = new QuizResultsAdapter(this, items, answers);
        answersListView.setAdapter(adapter);
    }

    private void updateUI() {
        int numberOfQuestions = questions.length;
        int numberOfCorrectAnswers = 0;

        for (Object o : answers.entrySet()) {
            Map.Entry me = (Map.Entry) o;
            Boolean value = (Boolean) me.getValue();
            if (value) numberOfCorrectAnswers++;
        }

        String titleText = "You answered " + numberOfCorrectAnswers + " of "
                + numberOfQuestions + " questions";
        ((TextView) findViewById(R.id.quiz_result_title)).setText(titleText);

        Toast.makeText(this, "Good job! " + titleText, Toast.LENGTH_LONG).show();
    }

}
