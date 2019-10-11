package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERS = "answers";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;

    private List<Question> mQuestionBank = new LinkedList<>();
    private ListIterator<Question> mListIterator;
    private int mCorrectAnswers;
    private int question;
    private int mIterator;
    private boolean mFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        mQuestionBank.add(new Question(R.string.question_australia, true));
        mQuestionBank.add(new Question(R.string.question_oceans, true));
        mQuestionBank.add(new Question(R.string.question_mideast, false));
        mQuestionBank.add(new Question(R.string.question_africa, false));
        mQuestionBank.add(new Question(R.string.question_americans, true));
        mQuestionBank.add(new Question(R.string.question_asia, true));

        if (savedInstanceState != null) {
            mIterator = savedInstanceState.getInt(KEY_INDEX);
            mCorrectAnswers = savedInstanceState.getInt(KEY_ANSWERS);
            mListIterator = mQuestionBank.listIterator(mIterator);
        } else mListIterator = mQuestionBank.listIterator();


        //Получение ссылок на виджеты
        mTrueButton = findViewById(R.id.true_button);
        //Создаем слушателей при помощи анонимных классов
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFalseButton.setClickable(false);
                checkAnswer(true);

            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkAnswer(false);
                mTrueButton.setClickable(false);
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mFlag = true;
                mTrueButton.setClickable(true);
                mFalseButton.setClickable(true);
                updateQuestion();
            }
        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListIterator.hasPrevious()) {
                    mIterator = mListIterator.previousIndex();
                    question = mListIterator.previous().getTextResId();
                    mQuestionTextView.setText(question);
                }

            }
        });

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
        savedInstanceState.putInt(KEY_INDEX, mIterator);
        savedInstanceState.putInt(KEY_ANSWERS, mCorrectAnswers);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    private void updateQuestion() {
        if (mListIterator.hasNext()) {
            question = mListIterator.next().getTextResId();
            mIterator = mListIterator.nextIndex() - 1;
            mQuestionTextView.setText(question);

        } else showNumberOfCorrectAnswers();

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank.get(mIterator).isAnswerTrue();
        int messageResId;
        if (answerIsTrue == userPressedTrue) {
            messageResId = R.string.correct_toast;
            mCorrectAnswers++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void showNumberOfCorrectAnswers() {
        Toast.makeText(MainActivity.this, "Number of correct answers: " + mCorrectAnswers, Toast.LENGTH_SHORT).show();
    }
}
