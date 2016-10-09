package com.ivanmagda.quiz.model;

import android.content.Context;

import com.ivanmagda.quiz.R;

import java.io.Serializable;
import java.util.Random;

public class Question implements Serializable {

    public enum PresentationType {
        DEFAULT, TEXT, CHECKBOX, RADIO
    }

    private String title;
    private String[] responseOptions;
    private String answer;
    private PresentationType presentationType;

    public Question(String title, String[] responseOptions, String answer, PresentationType presentationType) {
        this.title = title;
        this.responseOptions = responseOptions;
        this.answer = answer;
        this.presentationType = presentationType;
    }

    public String getTitle() {
        return title;
    }

    public String getAnswer() {
        return answer;
    }

    public String[] getResponses() {
        return responseOptions;
    }

    public PresentationType getPresentationType() {
        return presentationType;
    }

    public boolean isCorrect(String answer) {
        return answer.equalsIgnoreCase(this.answer);
    }

    private static void shuffleArray(Question[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            Question a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static Question[] allQuestions(Context context) {
        Question[] questions = new Question[11];

        questions[0] = new Question(
                context.getString(R.string.question_1),
                new String[]{
                        context.getString(R.string.pl1), context.getString(R.string.fortran),
                        context.getString(R.string.basic), context.getString(R.string.pascal)
                },
                context.getString(R.string.pascal),
                PresentationType.DEFAULT
        );
        questions[1] = new Question(
                context.getString(R.string.question_2),
                new String[]{
                        context.getString(R.string.d_transmission), context.getString(R.string.d_flow),
                        context.getString(R.string.d_capture), context.getString(R.string.d_processing)
                },
                context.getString(R.string.d_flow),
                PresentationType.DEFAULT
        );
        questions[2] = new Question(
                context.getString(R.string.question_3),
                new String[]{
                        context.getString(R.string.alu), context.getString(R.string.memory),
                        context.getString(R.string.cpu), context.getString(R.string.control_unit)
                },
                context.getString(R.string.cpu),
                PresentationType.DEFAULT
        );
        questions[3] = new Question(
                context.getString(R.string.question_4),
                new String[]{
                        context.getString(R.string.tech_advancement), context.getString(R.string.scientific_code),
                        context.getString(R.string.oop),
                        context.getString(R.string.all_above)
                },
                context.getString(R.string.tech_advancement),
                PresentationType.DEFAULT
        );
        questions[4] = new Question(
                context.getString(R.string.question_5),
                new String[]{
                        context.getString(R.string.fortran), context.getString(R.string.prolog),
                        context.getString(R.string.c), context.getString(R.string.cobol)
                },
                context.getString(R.string.prolog),
                PresentationType.DEFAULT
        );
        questions[5] = new Question(
                context.getString(R.string.question_6),
                new String[]{"01", "110", "11", "10"},
                "01",
                PresentationType.DEFAULT
        );
        questions[6] = new Question(
                context.getString(R.string.question_7),
                new String[]{
                        context.getString(R.string.input), context.getString(R.string.storage_unit),
                        context.getString(R.string.logic_unit), context.getString(R.string.control_unit)
                },
                context.getString(R.string.control_unit),
                PresentationType.DEFAULT
        );
        questions[7] = new Question(
                context.getString(R.string.question_8),
                new String[]{context.getString(R.string.yes), context.getString(R.string.no)},
                context.getString(R.string.yes),
                PresentationType.RADIO
        );
        questions[8] = new Question(
                context.getString(R.string.question_9),
                null,
                context.getString(R.string.assembler),
                PresentationType.TEXT
        );
        questions[9] = new Question(
                context.getString(R.string.question_10),
                null,
                context.getString(R.string.mask),
                PresentationType.TEXT
        );
        questions[10] = new Question(
                context.getString(R.string.question_11),
                new String[]{
                        context.getString(R.string.linux), context.getString(R.string.gnu),
                        context.getString(R.string.open_solaris), context.getString(R.string.windows)
                },
                context.getString(R.string.linux) + context.getString(R.string.gnu) +
                        context.getString(R.string.open_solaris),
                PresentationType.CHECKBOX
        );

        shuffleArray(questions);

        return questions;
    }

}
