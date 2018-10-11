package com.example.akankshamalhotra.quizapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UploadFile extends AsyncTask<String,Integer,String> {

    private Context context;
//    private final ProgressDialog mDialog=null;

    public UploadFile(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {

        try {
            //Source: https://stackoverflow.com/questions/25398200/uploading-file-in-php-server-from-android-device/37953351
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(exportDir, "QuestionsDatabase.csv");
            String path = file.toString();
            String sourceFileUri = path;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (sourceFile.isFile()) {

                try {
                    //String upLoadServerUri = "https://aayush18122.000webhostapp.com/UploadToServer.php";
                    String upLoadServerUri="https://quizapp-mt18062.000webhostapp.com/upload.php";

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE",
                            "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("quiz", sourceFileUri);
                    System.out.println("getting error");
                    dos = new DataOutputStream(conn.getOutputStream());
                    System.out.println("after getting");
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"fileToUpload\";filename=\""
                            + sourceFileUri + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math
                                .min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,
                                bufferSize);
                        Log.d("While loop", "doInBackground: "+bytesRead);

                    }

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);

                    // Responses from the server (code and message)
                   int  serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn
                            .getResponseMessage();

                    if (serverResponseCode == 200) {
                        System.out.println("here"+serverResponseCode);
                        // messageText.setText(msg);
                        //Toast.makeText(ctx, "File Upload Complete.",
                        //      Toast.LENGTH_SHORT).show();

                        // recursiveDelete(mDirectory1);

                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                    return "Successful";
                } catch (Exception e) {

                    // dialog.dismiss();
                    e.printStackTrace();

                }
                // dialog.dismiss();

            } // End else block


        } catch (Exception ex) {
            // dialog.dismiss();

            ex.printStackTrace();
        }
        return "Error";
    }

    @Override
    protected void onPostExecute(String v) {

        if(v.equalsIgnoreCase("Error"))
             Toast.makeText(context,"Error in Upload",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context,"Successful Upload",Toast.LENGTH_SHORT).show();
    }

//    private void UploadURL(String myurl) throws IOException {
//
//        HttpURLConnection conn = null;
//        InputStream stream = null;
//        DataOutputStream dos = null;
//        String sourceFileUri = "/mnt/sdcard/Download/QuestionsDatabase.csv";
//        int maxBufferSize = 1 * 1024 * 1024;
//        // URL url = new URL(myurl);
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int response;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//
//        File sourceFile = new File(sourceFileUri);
//
//        if (sourceFile.isFile()) {
//
//            try {
////https://aayush18122.000webhostapp.com/upload.php
//                String upLoadServerUri = "https://aayush18122.000webhostapp.com/upload.php";
//
//                // open a URL connection to the Servlet
//                FileInputStream fileInputStream = new FileInputStream(
//                        sourceFile);
//                URL url = new URL(upLoadServerUri);
//
//                // Open a HTTP connection to the URL
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setDoInput(true); // Allow Inputs
//                conn.setDoOutput(true); // Allow Outputs
//                conn.setUseCaches(false); // Don't use a Cached Copy
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Connection", "Keep-Alive");
//                conn.setRequestProperty("ENCTYPE",
//                        "multipart/form-data");
//                conn.setRequestProperty("Content-Type",
//                        "multipart/form-data;boundary=" + boundary);
//                conn.setRequestProperty("quiz", sourceFileUri);
//
//                dos = new DataOutputStream(conn.getOutputStream());
//
//                dos.writeBytes(twoHyphens + boundary + lineEnd);
//                dos.writeBytes("Content-Disposition: form-data; name=\"quiz\";filename=\""
//                        + sourceFileUri + "\"" + lineEnd);
//
//                dos.writeBytes(lineEnd);
//
//                // create a buffer of maximum size
//                bytesAvailable = fileInputStream.available();
//
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                buffer = new byte[bufferSize];
//
//                // read file and write it into form...
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                while (bytesRead > 0) {
//
//                    dos.write(buffer, 0, bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math
//                            .min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0,
//                            bufferSize);
//
//                }
//
//                // send multipart form data necesssary after file
//                // data...
//                dos.writeBytes(lineEnd);
//                dos.writeBytes(twoHyphens + boundary + twoHyphens
//                        + lineEnd);
//
//                // Responses from the server (code and message)
//                int serverResponseCode = conn.getResponseCode();
//                String serverResponseMessage = conn
//                        .getResponseMessage();
//
//                if (serverResponseCode == 200) {
//
//                    // messageText.setText(msg);
//                    //Toast.makeText(ctx, "File Upload Complete.",
//                    //      Toast.LENGTH_SHORT).show();
//
//                    // recursiveDelete(mDirectory1);
//
//                }
//
//                // close the streams //
//                fileInputStream.close();
//                dos.flush();
//                dos.close();
//            } catch (Exception e) {
//
//                // dialog.dismiss();
//                e.printStackTrace();
//
//            }
//
//
//            // Starts the query
////        conn.connect();
////        stream=conn.getInputStream();
////       // int response = conn.getResponseCode();
////
////        if(response== HttpsURLConnection.HTTP_OK)
////        {
////            // Toast.makeText(context,"helloooo",Toast.LENGTH_LONG).show();
////            //OutputStream output = new FileOutputStream(path);
////            OutputStream output = context.openFileOutput("audio.mp3", Context.MODE_PRIVATE);
////            int totalLength = conn.getContentLength();
////            byte data[] = new byte[1024 ];
////            long total = 0;
////            int count;
////            while ((count = stream.read(data)) != -1)
////            {
////                total += count;
////                // to show how much data is downloaded
////                if (totalLength > 0) // only if total length is known
////                    publishProgress((int) (total * 100 / totalLength));
////                Log.d("downloader","Downloaded "+ (total * 100 / totalLength) +" %");
////                output.write(data, 0, count);
////            }
////            output.close();
//        }
//    }
}