package eaict.luyksvancluysen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by CarlV on 1/5/2018.
 */
public class Newsound extends MainActivity {

    MediaRecorder tempSound;
    String outputFile;
    String name;
    boolean hasBeenRecorded = false;
    int sampleRate = 16000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsound);
        loadData();

        final TextView newrecord = findViewById(R.id.newrecord);
        final TextView newstoprecord = findViewById(R.id.newstoprecord);
        final TextView newsoundboard = findViewById(R.id.newaddsoundboard);
        final TextView newplay = findViewById(R.id.newplay);
        final EditText newnamechanger = findViewById(R.id.newnamechanger);
        final TextView newcancel = findViewById(R.id.newcancel);
        final TextView newpause = findViewById(R.id.newPause);
        final TextView newResume = findViewById(R.id.newResume);

        newrecord.setText("Record");
        newstoprecord.setVisibility(View.GONE);
        newpause.setVisibility(View.GONE);
        newResume.setVisibility(View.GONE);

        newrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    newSound();
                    tempSound.prepare();
                    tempSound.start();
                } catch (IllegalStateException ise) {
                    Log.d("xD", "oops");
                } catch (IOException ioe) {

                }

                newrecord.setText("Recording");
                newstoprecord.setVisibility(View.VISIBLE);
                newpause.setVisibility(View.VISIBLE);
                newResume.setVisibility(View.VISIBLE);
                newrecord.setEnabled(false);
                newsoundboard.setEnabled(false);
                newplay.setEnabled(false);
                newnamechanger.setEnabled(false);
                newcancel.setEnabled(false);
                newpause.setEnabled(true);
                newResume.setEnabled(false);
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
                hasBeenRecorded = true;
                newstoprecord.setVisibility(View.GONE);
                newResume.setVisibility(View.GONE);
                newpause.setVisibility(View.GONE);
                newrecord.setEnabled(true);
                newrecord.setText("Re-record");
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
                                                 if (hasBeenRecorded){
                                                     name = newnamechanger.getText().toString();
                                                     soundEffects.add(new SoundEffects(outputFile, name));
                                                     Log.d("Name", name);
                                                     saveData();
                                                     Toast.makeText(getApplicationContext(), "New sound added!", Toast.LENGTH_SHORT).show();
                                                     Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                                     startActivity(intent);
                                                 } else if(!hasBeenRecorded) {
                                                     Toast.makeText(getApplicationContext(), "No sound recorded!", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
        });

        newcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_SHORT).show();

            }
        });

        newpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != tempSound) {
                    try {
                        tempSound.pause();
                        newpause.setEnabled(false);
                        newResume.setEnabled(true);
                    } catch (RuntimeException ex) {
                        //Ignore
                    }
                }
            }
        });

        newResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != tempSound) {
                    try {
                        tempSound.resume();
                        newpause.setEnabled(true);
                        newResume.setEnabled(false);
                    } catch (RuntimeException ex) {
                        //Ignore
                    }
                }
            }
        });
    }

    private void newSound() {
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording"+ soundEffects.size()+ ".3gp";
        tempSound = new MediaRecorder();
        tempSound.setAudioSource(MediaRecorder.AudioSource.MIC);
        tempSound.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        tempSound.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        tempSound.setOutputFile(outputFile);
        tempSound.setAudioSamplingRate(sampleRate);
    }
}
