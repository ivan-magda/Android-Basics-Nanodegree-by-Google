package com.ivanmagda.quiz.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanmagda.quiz.R;
import com.ivanmagda.quiz.model.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {

    public final static String EXTRA_QUESTIONS = "com.ivanmagda.quiz.ui.QESTIONS";
    public final static String EXTRA_ANSWERS = "com.ivanmagda.quiz.ui.ANSWERS";

    private final static String LOG_TAG = QuizActivity.class.getSimpleName();

    private TextView questionTextView;

    // Question layout types.
    private ListView answersListView; // DEFAULT
    private RadioGroup radioGroup; // RADIO
    private EditText answerTextField; // TEXT
    private LinearLayout checkBoxLinearLayout; // CHECKBOX

    // Model variables.
    private Question[] questions;
    private Question currentQuestion;
    private int currentQuestionIdx;
    private HashMap<Integer, Boolean> questionsAnswerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configure();
        updateUI();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //region Actions.
    public void onSubmitButtonClick(View button) {
        if (processOnAnswer()) {
            nextQuestion();
        }
    }

    private void showResults() {
        Intent intent = new Intent(this, QuizResultsActivity.class);
        intent.putExtra(EXTRA_QUESTIONS, questions);
        intent.putExtra(EXTRA_ANSWERS, questionsAnswerList);
        startActivity(intent);
    }
    //endregion

    //region Configure.
    private void configure() {
        questionTextView = (TextView) findViewById(R.id.question_title);
        answersListView = (ListView) findViewById(R.id.answers_list_view);
        radioGroup = (RadioGroup) findViewById(R.id.answer_radio_group);
        answerTextField = (EditText) findViewById(R.id.answer_text_field);
        checkBoxLinearLayout = (LinearLayout) findViewById(R.id.checkbox_linear_layout);

        questions = Question.allQuestions(this);
        questionsAnswerList = new HashMap<>(questions.length);

        currentQuestionIdx = 0;
        currentQuestion = questions[currentQuestionIdx];
    }
    //endregion

    //region Quiz process.
    private void nextQuestion() {
        if (currentQuestionIdx == questions.length - 1) {
            showResults();
        } else {
            currentQuestion = questions[++currentQuestionIdx];
            updateUI();
        }
    }

    private boolean processOnAnswer() {
        switch (currentQuestion.getPresentationType()) {
            // DEFAULT case handles in the onItemClick of the answersListView adapter.
            case DEFAULT:
                showToastWithMessage(getString(R.string.select_answer_message));
                return false;
            case TEXT:
                String answerText = answerTextField.getText().toString();
                if (answerText.isEmpty() || answerText.length() == 0) {
                    showToastWithMessage("Please type your answer");
                    return false;
                }
                addAnswerToList(answerText);
                break;
            case CHECKBOX:
                CheckBox aCheckBox = (CheckBox) findViewById(R.id.a_checkbox);
                CheckBox bCheckBox = (CheckBox) findViewById(R.id.b_checkbox);
                CheckBox cCheckBox = (CheckBox) findViewById(R.id.c_checkbox);
                CheckBox dCheckBox = (CheckBox) findViewById(R.id.d_checkbox);

                if (!aCheckBox.isChecked() && !bCheckBox.isChecked() && !cCheckBox.isChecked()
                        && !dCheckBox.isChecked()) {
                    showToastWithMessage("Please select at least one answer");
                    return false;
                }

                ArrayList<String> selectedAnswers = new ArrayList<>();
                addCheckBoxAnswerIfSelected(selectedAnswers, aCheckBox);
                addCheckBoxAnswerIfSelected(selectedAnswers, bCheckBox);
                addCheckBoxAnswerIfSelected(selectedAnswers, cCheckBox);
                addCheckBoxAnswerIfSelected(selectedAnswers, dCheckBox);

                String joinedString = TextUtils.join(",", selectedAnswers);
                addAnswerToList(joinedString);

                break;
            case RADIO:
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.yes_radio_button:
                        addAnswerToList(getString(R.string.yes));
                        break;
                    case R.id.no_radio_button:
                        addAnswerToList(getString(R.string.no));
                        break;
                    case -1:
                        showToastWithMessage(getString(R.string.select_answer_message));
                        return false;
                }
                break;
        }

        return true;
    }

    private void addAnswerToList(String answer) {
        questionsAnswerList.put(currentQuestionIdx, currentQuestion.isCorrect(answer));
    }

    private void addCheckBoxAnswerIfSelected(ArrayList<String> list, CheckBox checkBox) {
        if (checkBox.isChecked()) list.add(checkBox.getText().toString());
    }
    //endregion

    //region UI methods.
    private void showToastWithMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void updateUI() {
        questionTextView.setText(currentQuestion.getTitle());
        answerTextField.setText(null);

        hideViews();

        switch (currentQuestion.getPresentationType()) {
            case DEFAULT:
                answersListView.setVisibility(View.VISIBLE);

                final String[] listItems = new String[currentQuestion.getResponses().length];
                for (int i = 0; i < currentQuestion.getResponses().length; ++i) {
                    listItems[i] = (i + 1) + ". " + currentQuestion.getResponses()[i];
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
                answersListView.setAdapter(arrayAdapter);
                answersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedAnswer = currentQuestion.getResponses()[position];
                        addAnswerToList(selectedAnswer);
                        nextQuestion();
                    }
                });

                break;
            case RADIO:
                radioGroup.setVisibility(View.VISIBLE);
                break;
            case TEXT:
                answerTextField.setVisibility(View.VISIBLE);
                break;
            case CHECKBOX:
                checkBoxLinearLayout.setVisibility(View.VISIBLE);
                ((CheckBox) findViewById(R.id.a_checkbox)).setText(currentQuestion.getResponses()[0]);
                ((CheckBox) findViewById(R.id.b_checkbox)).setText(currentQuestion.getResponses()[1]);
                ((CheckBox) findViewById(R.id.c_checkbox)).setText(currentQuestion.getResponses()[2]);
                ((CheckBox) findViewById(R.id.d_checkbox)).setText(currentQuestion.getResponses()[3]);
                break;
        }
    }

    private void hideViews() {
        switch (currentQuestion.getPresentationType()) {
            case DEFAULT:
                radioGroup.setVisibility(View.GONE);
                answerTextField.setVisibility(View.GONE);
                checkBoxLinearLayout.setVisibility(View.GONE);
                break;
            case TEXT:
                answersListView.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
                checkBoxLinearLayout.setVisibility(View.GONE);
                break;
            case CHECKBOX:
                answersListView.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
                answerTextField.setVisibility(View.GONE);
                break;
            case RADIO:
                answersListView.setVisibility(View.GONE);
                answerTextField.setVisibility(View.GONE);
                checkBoxLinearLayout.setVisibility(View.GONE);
                break;
        }
    }
    //endregion

}
