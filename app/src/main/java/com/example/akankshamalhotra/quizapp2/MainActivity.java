package com.example.akankshamalhotra.quizapp2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Question ques;
    private  DatabaseHelper dbHelper;
    private ArrayList<Question>QuesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper= new DatabaseHelper(this);
        QuesList=dbHelper.getAllQuestions();
        //Log.d("QuesList", "QuesList size: "+QuesList.size());
        QuesList=dbHelper.getAllQuestions();
        for(int i=0;i<QuesList.size();i++)
        {
            dbHelper.resetUserAnswer(QuesList.get(i));
        }

          Button  start = (Button) findViewById(R.id.StartButton);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(MainActivity.this,"start clicked",Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getSupportFragmentManager();
                    Bundle b= new Bundle();
                    b.putSerializable("Ques",dbHelper.getAllQuestions());
                    Fragment frag;
                    frag = new QuizFragment();
                    frag.setArguments(b);
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.add(R.id.MainActivity, frag).commit();
                    ft.addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                }
            });


    }


    public void MakequesFragment(Question ques)
    {
        this.ques=ques;
        Bundle b= new Bundle();
        b.putSerializable("Ques",ques.getQues());
        Log.d("MAIN ACTIVITY", "MakequesFragment: "+ques.getQuesID());
        b.putInt("QuesId", ques.getQuesID());
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag;
        frag = new QuesFragment();
        frag.setArguments(b);

        FragmentTransaction ft= fm.beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.MainActivity, frag).commit();


    }

    public void showQues(int quesId){
        Question q=QuesList.get(quesId-1);
        MakequesFragment(q);
    }

    public void saveUserAnswer(String ans)
    {
        ques.setUseranswer(ans);
        dbHelper.addUserAnswerToDataBase(ques);
    }

    public String getUserAnswer()
    {
        return ques.getUseranswer();
    }


    public void exportDatabase()
    {
        dbHelper.exportDB();
    }
}
