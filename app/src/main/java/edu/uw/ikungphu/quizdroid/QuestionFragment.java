package edu.uw.ikungphu.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class QuestionFragment extends Fragment {

    //private String topic, topicRes;
    //private int numQuestions;
    private int questionNum;
    private int correct;
    private int position;
    private int indexSelected;
    private Topic topic;
    private Question question;

    RadioGroup answers;
    private Activity hostActivity;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //topic = getArguments().getString("topic");
            //topicRes = getArguments().getString("topicRes");
            //numQuestions = getArguments().getInt("numQuestions");
            questionNum = getArguments().getInt("questionNum");
            correct = getArguments().getInt("correct");
            position = getArguments().getInt("position");
            topic = ((QuizApp) getActivity().getApplication()).getTopics().get(position);
            question = topic.questions.get(questionNum);
        }
        if (savedInstanceState != null) {
            indexSelected = savedInstanceState.getInt("indexSelected");
        } else {
            indexSelected = -1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_quiz_question, container, false);

        answers = (RadioGroup) view.findViewById(R.id.answers);
        TextView correctText = (TextView) view.findViewById(R.id.correctText);
        correctText.setText("You have " + correct + "/" + questionNum + " correct.");

        //int idQuestion = getResources().getIdentifier(topicRes + "_q" + questionNum, "string", getActivity().getPackageName());
        //String question = getResources().getString(idQuestion);
        TextView questionText = (TextView) view.findViewById(R.id.questionText);
        questionText.setText(question.question);

        Button btn = (Button) view.findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(indexSelected >= 0) {
                    //int idCorrect = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a", "integer", getActivity().getPackageName());
                    //int indexCorrect = getResources().getInteger(idCorrect);

                    Bundle bundle = new Bundle();
                    //bundle.putString("topic", topic);
                    //bundle.putString("topicRes", topicRes);
                    bundle.putInt("indexSelected", indexSelected);
                    //bundle.putInt("indexCorrect", indexCorrect);
                    //bundle.putInt("numQuestions", numQuestions);
                    bundle.putInt("questionNum", questionNum);
                    bundle.putInt("position", position);
                    if (indexSelected == question.correctIndex) {
                        bundle.putInt("correct", correct + 1);
                    } else {
                        bundle.putInt("correct", correct);
                    }

                    if (hostActivity instanceof SecondActivity) {
                        ((SecondActivity) hostActivity).loadAnswerFrag(bundle);
                    }
                }
            }
        });

        for (int i = 0; i < getResources().getInteger(R.integer.questions); i++) {
            int idAnswer = getResources().getIdentifier("answer" + (i+1), "id", getActivity().getPackageName());
            //String answer = getResources().getString(idAnswer);
            //int idAnswerButton = getResources().getIdentifier("answer" + i, "id", getActivity().getPackageName());
            Button answerButton = (Button) view.findViewById(idAnswer);
            answerButton.setText(question.answers.get(i));
            answerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    indexSelected = answers.indexOfChild(v);
                }
            });
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
