package com.example.akankshamalhotra.quizapp2;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LinearLayout layout;
    TextView quesNumber;
    View view;
    ArrayList<String> itemList = new ArrayList<String>();
    int row_index=-1;
//    ArrayList<Integer>Visitedno;
    MainActivity activity;
    ArrayList<Question> QuesList;
    QuizAdapter(ArrayList<String> itemList,MainActivity activity,ArrayList<Question>QuesList)
    {
        this.itemList=itemList;
//        Visitedno= new ArrayList<Integer>();
        this.activity = activity;
//        DatabaseHelper dbHelper= new DatabaseHelper(this.activity);
//        QuesList=dbHelper.getAllQuestions();
        this.QuesList=QuesList;
//        Log.d("QuesList", "QuesList size: "+QuesList.size());

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_view_layout, viewGroup, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("QuizAdapter", "onBindViewHolder:  called");
        initLayoutOne((ViewHolderOne) viewHolder, i);

    }

    private void initLayoutOne(  ViewHolderOne holder, final int pos) {
       final ViewHolderOne v=holder;
        holder.item.setText(itemList.get(pos));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SettingsAdapter", "onClick: "+ itemList.get(pos));
                row_index=pos;

               // Visitedno.add(pos);
                notifyDataSetChanged();

                activity.MakequesFragment(QuesList.get(pos));

            }
        });
        Log.d("initLayoutOne", "pos:"+pos+" row_index"+row_index);
        if(row_index==pos){
            Log.d("initLayoutOne", "initLayoutOne: "+pos);
            holder.item.setBackgroundColor(Color.parseColor("#343434"));
            holder.item.setTextColor(Color.parseColor("#ffffff"));
//            holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            Log.d("initLayoutOne", "initLayoutOne_row_index: "+pos);
            holder.item.setBackgroundColor(Color.parseColor("#e6b31e"));
            holder.item.setTextColor(Color.parseColor("#ffffff"));
           // holder.tv1.setTextColor(Color.parseColor("#000000"));
        }

        if(!QuesList.get(pos).getUseranswer().equalsIgnoreCase("Not Attempted"))
        {
            Log.d("ATTEMPTED", "initLayoutOne: "+pos);
            holder.item.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.item.setTextColor(Color.parseColor("#000000"));
        }



//            if(Visitedno.contains(pos))
//            {
//                holder.layout.setBackgroundColor(Color.parseColor("#343434"));
//                Visitedno.remove(pos);
//            }


    }
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView item;
        //                itemManagePrefrences,itemJoinNewCourse,itemLogout;
        public LinearLayout layout;
        public ViewHolderOne(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.QuesNumber);
            layout= (LinearLayout) itemView.findViewById(R.id.parentView);
        }
    }
}
