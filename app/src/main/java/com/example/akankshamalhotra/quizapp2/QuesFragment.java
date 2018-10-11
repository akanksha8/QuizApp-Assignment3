package com.example.akankshamalhotra.quizapp2;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class QuesFragment extends Fragment {


    private MainActivity activity;
    private int quesId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final String ques=getArguments().getString("Ques");
        quesId= getArguments().getInt("QuesId");
        View view = inflater.inflate(R.layout.ques_fragment, container, false);
        TextView text =(TextView) view.findViewById(R.id.QuesText);
        text.setText(quesId+".  "+ques);
        activity=(MainActivity)getActivity();

        Button save = (Button) view.findViewById(R.id.saveButton);
      final RadioGroup grp=(RadioGroup)view.findViewById(R.id.Options);
       final RadioButton AnsTrue=(RadioButton)view.findViewById(R.id.AnsTrue);
       final RadioButton AnsFalse=(RadioButton)view.findViewById(R.id.AnsFalse);
        String savedAns=((MainActivity) getActivity()).getUserAnswer();
        if(savedAns.equalsIgnoreCase("True"))
        {
            AnsTrue.setChecked(true);
        }
        else if(savedAns.equalsIgnoreCase("False"))
        {
            AnsFalse.setChecked(true);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ans="";



                if (AnsTrue.isChecked())
                {
                    ans=String.valueOf(AnsTrue.getText());
                    // no radio buttons are checked
                    Log.d("RADIO BUTTON ID", "onClick:TRUE");
                }
                else if(AnsFalse.isChecked())
                {
                    ans=String.valueOf(AnsFalse.getText());
                    Log.d("RADIO BUTTON ID", "onClick:FALSE");
                    // one of the radio buttons is checked
                }

//
                if(ans.length()>0)
                {
                    ((MainActivity) getActivity()).saveUserAnswer(ans);
                }

                Toast.makeText(getActivity(),"Answer Saved",Toast.LENGTH_SHORT).show();
//                Log.d("Answer Selected", "onClick Save: "+answer);
//
//                if(currentQ.getANSWER().equals(answer.getText()))
//                {
//                    score++;
//                }
//                currentQ=quesList.get(qid);
               // setQuestionView();
            }
        });

//        Button next = (Button) view.findViewById(R.id.nextButton);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //mainActvity method call with quesId+1
////                ((MainActivity) getActivity()).showQues(quesId+1);
//            }
//            });

        return view;
    }


}
