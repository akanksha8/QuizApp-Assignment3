package com.example.akankshamalhotra.quizapp2;

import java.io.Serializable;

public class Question implements Serializable {
    private int quesID;
    private String ques;
    private String answer;
    private String useranswer;

    Question()
    {
        ques="";
        answer="";
        useranswer="Not Attempted";
    }

    Question(String ques,String answer)
    {
        this.ques=ques;
        this.answer=answer;
        useranswer="Not Attempted";
    }

    public int getQuesID()
    {
        return  quesID;
    }

    public String getQues()
    {
        return ques;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setQuesID(int quesID)
    {
        this.quesID=quesID;
    }

    public void setQues(String ques)
    {
        this.ques=ques;
    }

    public void setanswer(String answer)
    {
        this.answer=answer;
    }

    public void setUseranswer(String useranswer)
    {
        this.useranswer=useranswer;
    }

    public String getUseranswer()
    {return useranswer;}

}
