package com.example.celebration;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

public class DownloadAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<Button> weakButton;
    private WeakReference<ProgressBar> weakProgressBar;
    private WeakReference<TextView> weakTextView;
    private WeakReference<String> weakPath;

    public DownloadAsyncTask(Button b, ProgressBar pb, TextView tv, String path) {  //weak references linked to strong references
        this.weakButton = new WeakReference<>(b);
        this.weakProgressBar = new WeakReference<>(pb);
        this.weakTextView = new WeakReference<>(tv);
        this.weakPath = new WeakReference<>(path);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        weakButton.get().setEnabled(false); //makes the download button disabled while downloading
    }

    @Override
    protected String doInBackground(Void... voids) {
        /*
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(10);
                publishProgress(i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Download Completed";

         */

        int count;
        try {
            URL url = new URL("https://arato.inf.unideb.hu/kocsis.gergely/song.mp3");
            URLConnection connection = url.openConnection();
            connection.connect();
            int lenghtOfFile = connection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),10*1024);
            // Output stream to write file
            OutputStream output = new FileOutputStream(weakPath.get() + "/files/song.mp3");
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress((int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return "Download completed";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        weakProgressBar.get().setProgress(values[0]);
        weakTextView.get().setText(""+ values[0] + "%");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        weakButton.get().setEnabled(true);  //Download button visible again
        weakTextView.get().setText(s);
    }
}
