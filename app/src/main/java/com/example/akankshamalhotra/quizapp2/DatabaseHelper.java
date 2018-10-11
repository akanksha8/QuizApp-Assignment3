package com.example.akankshamalhotra.quizapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.Writer;
import static android.os.Build.ID;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "QuizApp.db"; // the name of database
    private static final int DB_VERSION = 1;
    private static final String TABLE_QUES="TABLE_QUESTIONS";
    private static final String Ques_ID = "ID";
    private static final String QUESTION = "QUESTION";
    private static final String ANSWER = "ANSWER"; //correct option
    private static final String USER_ANSWER= "USER_ANSWER";
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private LinkedHashMap<String,String> QuestionsHashMap;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
        QuestionsHashMap= new LinkedHashMap<String,String>();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase=sqLiteDatabase;


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_QUES+" ( "+Ques_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  QUESTION + " TEXT, "
                + ANSWER+" TEXT, "
                + USER_ANSWER+ " TEXT )");
        //updateDatabase(sqLiteDatabase,0,DB_VERSION);
        Log.d("DATABASE", "onCreate: ");
        addQues();

//        sqLiteDatabase.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

//        updateDatabase(sqLiteDatabase,oldVersion,DB_VERSION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUES);
        onCreate(db);
    }

    //    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
//    {
//        db=this.getWritableDatabase();
//
//            if(oldVersion< 1)
//            {
//                db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_QUES+" ( "+Ques_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
//                        +  QUESTION + "TEXT,"
//                        + ANSWER+"TEXT,"
//                        + USER_ANSWER+ "TEXT)");
//
//            }
//            else if (oldVersion< 2)
//            {
//                db.execSQL("ALTER TABLE " + QUESTION + " ADD COLUMN "+USER_ANSWER+" TEXT");
//
//            }
//
//        addQues();
//    }

    private void addQues()
    {
        addQuesToDatabase(new Question("The Language that the computer can understand is called Machine Language.","True"));
        addQuesToDatabase(new Question("Magnetic Tape used random access method.","False"));
        addQuesToDatabase(new Question("Twitter is an online social networking and blogging service.","False"));
        addQuesToDatabase(new Question("Worms and trojan horses are easily detected and eliminated by antivirus software.","True"));
        addQuesToDatabase(new Question("Dot-matrix, Deskjet, Inkjet and Laser are all types of Printers.","True"));
        addQuesToDatabase(new Question("Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.","True"));
        addQuesToDatabase(new Question("Freeware is software that is available for use at no monetary cost.","True"));
        addQuesToDatabase(new Question("IPv6 Internet Protocol address is represented as eight groups of four Octal digits.","False"));
        addQuesToDatabase(new Question("The hexadecimal number system contains digits from 1 - 15.","False"));
        addQuesToDatabase(new Question("CPU controls only input data of computer.","False"));
        addQuesToDatabase(new Question("In an instance method or a constructor, \"this\" is a reference to the current object.","True"));
        addQuesToDatabase(new Question("The JRE deletes objects when it determines that they are no longer being used. This process is called Garbage Collection.","True"));
        addQuesToDatabase(new Question("Constructor overloading is not possible in Java.","False"));
        addQuesToDatabase(new Question("Assignment operator is evaluated Left to Right.","False"));
        addQuesToDatabase(new Question("Java programming is not statically-typed, means all variables should not first be declared before they can be used.","False"));
        addQuesToDatabase(new Question("Assignment operator is evaluated Left to Right.","False"));
        addQuesToDatabase(new Question("The modulus operator (%) in Java can be used only with variables of integer type.","False"));
        addQuesToDatabase(new Question("All bitwise operations are carried out with the same level of precedence in Java.","False"));
        addQuesToDatabase(new Question("The operations y >> 3 and y >>> 3 produce the same result when y > 0.","True"));
        addQuesToDatabase(new Question("The \"switch\" selection structure must end with the default case.","False"));
        addQuesToDatabase(new Question("For the expression (y >= z && a == b) to be true, at least one of (y >= z) and (a == b) must be true.","False"));
        addQuesToDatabase(new Question("The == operator can be used to compare two String objects. The result is always true if the two strings are identical.","False"));
        addQuesToDatabase(new Question("Objects of a super class can always be assigned to a subclass reference.","False"));
        addQuesToDatabase(new Question("Paging helps in reducing the number of holes in the memory.","True"));
        addQuesToDatabase(new Question("The ++ operator increments the operand by 1, whereas, the -- operator decrements it by 1.","true"));
        addQuesToDatabase(new Question("It is necessary that a loop counter must only be an int. It cannot be a float.","False"));
        addQuesToDatabase(new Question("A zero value is considered to be false and a non-zero value is considered to be true.","True"));
        addQuesToDatabase(new Question("for comparison = is used, whereas, == is used for assignment of two quantities.\n","False"));
        addQuesToDatabase(new Question("The keywords cannot be used as variable names.","True"));
        addQuesToDatabase(new Question("continue keyword skip one iteration of loop?","True"));




    }

    public void addQuesToDatabase(Question quest) {

        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUESTION, quest.getQues());
        values.put(ANSWER, quest.getAnswer());
        values.put(USER_ANSWER,quest.getUseranswer());
        sqLiteDatabase.insert(TABLE_QUES, null, values);
        Log.d("ADD TO DATABASE", "addQuesToDatabase: ");
       // sqLiteDatabase.close();
    }


    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> quesList = new ArrayList<Question>();

        String selectQuery = "SELECT  * FROM " + TABLE_QUES;
        sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) { // add to list
            do {
                Question quest = new Question();
                quest.setQuesID(cursor.getInt(0));
                quest.setQues(cursor.getString(1));
                quest.setanswer(cursor.getString(2));
                Log.d("DATABASE", "getAllQuestions: "+cursor.getInt(0));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        Log.d("DatabaseHelper", "getAllQuestions: "+quesList.size());
// return quest list
        cursor.close();
        return quesList;
    }


    public void addUserAnswerToDataBase(Question ques)
    {

        ContentValues cv= new ContentValues();
        cv.put(USER_ANSWER,ques.getUseranswer());
        sqLiteDatabase.update(TABLE_QUES,cv,"ID=?",new String[]{String.valueOf(ques.getQuesID())});

    }

    public void resetUserAnswer(Question ques)
    {

        ContentValues cv= new ContentValues();
        cv.put(USER_ANSWER,"Not Attempted");
        sqLiteDatabase.update(TABLE_QUES,cv,"ID=?",new String[]{String.valueOf(ques.getQuesID())});

    }
    public void exportDB()
    {
//        DBHelper dbhelper = new DBHelper(getApplicationContext());
//        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
//        if (!exportDir.exists())
//        {
//            exportDir.mkdirs();
//        }
//
//        File file = new File(exportDir, "csvname.csv");
//        try
//        {
//            file.createNewFile();
//            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
//            SQLiteDatabase db = dbhelper.getReadableDatabase();
//            Cursor curCSV = db.rawQuery("SELECT * FROM TABLE_QUESTIONS",null);
//            csvWrite.writeNext(curCSV.getColumnNames());
//            while(curCSV.moveToNext())
//            {
//                //Which column you want to exprort
//                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
//                csvWrite.writeNext(arrStr);
//            }
//            csvWrite.close();
//            curCSV.close();
//        }
//        catch(Exception sqlEx)
//        {
//            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
//        }
    }

//    private void writeToSD() throws IOException {
//        File sd = Environment.getExternalStorageDirectory();
//        String DB_PATH="";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            DB_PATH = context.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
//        }
//    else {
//            DB_PATH = context.getFilesDir().getPath() + context.getPackageName() + "/databases/";
//        }
//        if (sd.canWrite()) {
//            String currentDBPath = DB_NAME;
//            String backupDBPath = "backupname.db";
//            File currentDB = new File(DB_PATH, currentDBPath);
//            File backupDB = new File(sd, backupDBPath);
//
//            if (currentDB.exists()) {
//                FileChannel src = new FileInputStream(currentDB).getChannel();
//                FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                dst.transferFrom(src, 0, src.size());
//                src.close();
//                dst.close();
//            }
//        }
//    }

 //   public boolean exportDatabase() {
//        //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
//
//        /**First of all we check if the external storage of the device is available for writing.
//         * Remember that the external storage is not necessarily the sd card. Very often it is
//         * the device storage.
//         */
//        String state = Environment.getExternalStorageState();
//        if (!Environment.MEDIA_MOUNTED.equals(state)) {
//            return false;
//        }
//        else {
//            //We use the Download directory for saving our .csv file.
//            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            if (!exportDir.exists())
//            {
//                exportDir.mkdirs();
//            }
//
//            File file;
//            PrintWriter printWriter = null;
//            try
//            {
//                file = new File(exportDir, "MyCSVFile.csv");
//                file.createNewFile();
//                printWriter = new
//                (new FileWriter(file));
//
//                /**This is our database connector class that reads the data from the database.
//                 * The code of this class is omitted for brevity.
//                 */
//                DBCOurDatabaseConnector dbcOurDatabaseConnector = new DBCOurDatabaseConnector(myContext);
//                dbcOurDatabaseConnector.openForReading(); //open the database for reading
//
//                /**Let's read the first table of the database.
//                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
//                 * containing all records of the table (all fields).
//                 * The code of this class is omitted for brevity.
//                 */
//                Cursor curCSV = dbcOurDatabaseConnector.getFirstTable();
//                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
//                printWriter.println("FIRST TABLE OF THE DATABASE");
//                printWriter.println("DATE,ITEM,AMOUNT,CURRENCY");
//                while(curCSV.moveToNext())
//                {
//                    Long date = curCSV.getLong(curCSV.getColumnIndex("date"));
//                    String item = curCSV.getString(curCSV.getColumnIndex("item"));
//                    Double amount = curCSV.getDouble(curCSV.getColumnIndex("amount"));
//                    String currency = curCSV.getString(curCSV.getColumnIndex("currency"));
//
//                    /**Create the line to write in the .csv file.
//                     * We need a String where values are comma separated.
//                     * The field date (Long) is formatted in a readable text. The amount field
//                     * is converted into String.
//                     */
//                    String record = df.format(new Date(date)) + "," + item + "," + importo.toString() + "," + currency;
//                    printWriter.println(record); //write the record in the .csv file
//                }
//
//                curCSV.close();
//                dbcOurDatabaseConnector.close();
//            }
//
//            catch(Exception exc) {
//                //if there are any exceptions, return false
//                return false;
//            }
//            finally {
//                if(printWriter != null) printWriter.close();
//            }
//
//            //If there are no errors, return true.
//            return true;
//        }
//    }
}

