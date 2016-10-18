package br.eti.francisco.socket;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private String readStream(InputStream in) throws IOException {
        byte [] buffer = new byte[1024];
        int count = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while(count != -1){
            count = in.read(buffer);
            baos.write(buffer, 0, count);
        }
        return new String(baos.toByteArray());
    }

    public void ligar(View v){
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.i("btn", "Ligar");
                    URL url = new URL("http://192.168.90.68/gpio/5/1");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setReadTimeout(10000 /* milliseconds */);
                        urlConnection.setConnectTimeout(15000 /* milliseconds */);
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoInput(true);

                        urlConnection.connect();
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        in.close();
                        Log.i("btn", s);
                    } finally {
                        urlConnection.disconnect();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void desligar(View v){
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.i("btn", "Desligar");
                    URL url = new URL("http://192.168.90.68/gpio/5/0");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setReadTimeout(10000 /* milliseconds */);
                        urlConnection.setConnectTimeout(15000 /* milliseconds */);
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoInput(true);

                        urlConnection.connect();
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        in.close();
                        Log.i("btn", s);
                    } finally {
                        urlConnection.disconnect();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
