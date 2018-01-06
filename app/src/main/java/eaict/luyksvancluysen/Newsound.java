package eaict.luyksvancluysen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CarlV on 1/5/2018.
 */
public class Newsound extends MainActivity {
    MediaRecorder tempSound;
    String outputFile;
    String name;
    String numberofrecordings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsound);
        numberofrecordings = getIntent().getStringExtra("listsize");
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording"+numberofrecordings + ".3gp";
        tempSound = new MediaRecorder();
        tempSound.setAudioSource(MediaRecorder.AudioSource.MIC);
        tempSound.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        tempSound.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        tempSound.setOutputFile(outputFile);
        tempSound.setAudioSamplingRate(16000);
        final TextView newrecord = findViewById(R.id.newrecord);
        final TextView newstoprecord = findViewById(R.id.newstoprecord);
        final TextView newsoundboard = findViewById(R.id.newaddsoundboard);
        final TextView newplay = findViewById(R.id.newplay);
        final EditText newnamechanger = findViewById(R.id.newnamechanger);
        final TextView newcancel = findViewById(R.id.newcancel);
        newrecord.setText("Record");
        newstoprecord.setVisibility(View.GONE);
        newrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tempSound.prepare();
                    tempSound.start();
                } catch (IllegalStateException ise) {
                    Log.d("xD", "oops");
                } catch (IOException ioe) {

                }
                newrecord.setText("Recording");
                newstoprecord.setVisibility(View.VISIBLE);
                newrecord.setEnabled(false);
                newsoundboard.setEnabled(false);
                newplay.setEnabled(false);
                newnamechanger.setEnabled(false);
                newcancel.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_SHORT).show();
            }
        });

        newstoprecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newrecord.setText("Record");
                if (null != tempSound) {
                    try {
                        tempSound.stop();
                    } catch (RuntimeException ex) {
                        //Ignore
                    }
                }
                tempSound.release();
                tempSound = null;
                newstoprecord.setVisibility(View.GONE);
                newrecord.setEnabled(true);
                newsoundboard.setEnabled(true);
                newplay.setEnabled(true);
                newnamechanger.setEnabled(true);
                newcancel.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording Finished", Toast.LENGTH_SHORT).show();
            }
        });
        newplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {

                }
            }

        });
        newsoundboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = newnamechanger.getText().toString();
                Log.d("Name", name);
                Toast.makeText(getApplicationContext(), "New sound added!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("output", outputFile);
                startActivity(intent);
                }
            }
        );
        newcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
