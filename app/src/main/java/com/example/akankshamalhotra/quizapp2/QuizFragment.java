package com.example.akankshamalhotra.quizapp2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class QuizFragment extends Fragment {

    private RecyclerView settingsRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // getActivity().setVisible(false);
    }

    @Override

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);

        ArrayList<String> itemList = new ArrayList<String>();
        ArrayList<Question> QuesList=new ArrayList<Question>();
        populateList(itemList);

        QuesList= (ArrayList<Question>) getArguments().getSerializable("Ques");

        QuizAdapter itemAdapter = new QuizAdapter(itemList, (MainActivity) getActivity(),QuesList);
        settingsRecyclerView = (RecyclerView) view.findViewById(R.id.quiz_recycler_view);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        settingsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        settingsRecyclerView.setAdapter(itemAdapter);



        //Submit button
        Button submit=(Button)view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Submitted",Toast.LENGTH_SHORT).show();

                //source :internet
                DatabaseHelper dbhelper = new DatabaseHelper(getContext());
                File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!exportDir.exists())
                {
                    exportDir.mkdirs();
                }

                File file = new File(exportDir, "QuestionsDatabase.csv");
                try
                {
                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    SQLiteDatabase sqliteDatabase = dbhelper.getReadableDatabase();
                    Cursor curCSV = sqliteDatabase.rawQuery("SELECT * FROM TABLE_QUESTIONS",null);
                    csvWrite.writeNext(curCSV.getColumnNames());
                    while(curCSV.moveToNext())
                    {
                        //Which column you want to exprort
                        String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2),curCSV.getString(3)};
                        csvWrite.writeNext(arrStr);
                    }
                    csvWrite.close();
                    curCSV.close();

                    //export ends
                }
                catch(Exception sqlEx)
                {
                    Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                }

                checkConnection();
            }
            });
        return view;
    }


    public void populateList(ArrayList<String>itemList)
    {
        for(int i=1;i<31;i++)
        {
            itemList.add("Question "+ i);
        }
    }

    public void checkConnection()
    {
        Toast.makeText(getActivity(),"checking connection",Toast.LENGTH_SHORT).show();
        //String stringUrl = urlText.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new UploadFile(getActivity()).execute();
        } else {
            Toast.makeText(getActivity(),"No Network Connection Available",Toast.LENGTH_LONG).show();
            //textView.setText("No network connection               available.");
        }
    }
}
