package eaict.luyksvancluysen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

/**
 * Created by CarlV on 1/8/2018.
 */

public class Editscreen extends MainActivity {
    MediaRecorder tempSound;
    int currentsound;
    String outputFile;
    int samplerate = 16000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editscreen);
        loadData();
        tempSound = new MediaRecorder();
        currentsound = getIntent().getIntExtra("sound", 0);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording" + currentsound + ".3gp";
        tempSound.setAudioSource(MediaRecorder.AudioSource.MIC);
        tempSound.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        tempSound.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        tempSound.setOutputFile(outputFile);
        tempSound.setAudioSamplingRate(16000);
        final EditText editText = findViewById(R.id.editNamechanger);
        TextView editName = findViewById(R.id.editName);
        final TextView editPlay = findViewById(R.id.editPlay);
        TextView editDelete = findViewById(R.id.editDelete);
        TextView defName = findViewById(R.id.editDefName);
        final TextView editPause = findViewById(R.id.editPause);
        final TextView editResume = findViewById(R.id.editResume);
        final TextView editRecord = findViewById(R.id.editRecord);
        final TextView editStopRecord = findViewById(R.id.editRecord);
        final TextView editDelYes = findViewById(R.id.editDeleteYes);
        final TextView editDelNo = findViewById(R.id.editDeleteNo);
        final SoundEffects editSound = soundEffects.get(currentsound);
        final TextView editDelCheck = findViewById(R.id.editDeleteChecker);
        editName.setText("Current name:");
        defName.setText(editSound.getName());
        editText.setText(editSound.getName());
        editDelCheck.setVisibility(View.GONE);
        editDelNo.setVisibility(View.GONE);
        editDelYes.setVisibility(View.GONE);
        editPause.setVisibility(View.GONE);
        editResume.setVisibility(View.GONE);
        editStopRecord.setVisibility(View.GONE);
        editPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(editSound.getOutput());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {

                }
            }

        });
        editRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    NewSound();
                    tempSound.prepare();
                    tempSound.start();
                } catch (IllegalStateException ise) {
                    Log.d("xD", "oops");
                } catch (IOException ioe) {

                }
                editRecord.setText("Recording");
                editStopRecord.setVisibility(View.VISIBLE);
                editRecord.setEnabled(false);
                editPlay.setEnabled(false);
                editText.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_SHORT).show();
            }
        });
        editStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRecord.setText("Record");
                if (null != tempSound) {
                    try {
                        tempSound.stop();
                    } catch (RuntimeException ex) {
                        //Ignore
                    }
                }
                tempSound.release();
                tempSound = null;
                editStopRecord.setVisibility(View.GONE);
                editRecord.setEnabled(true);
                editPlay.setEnabled(true);
                editText.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording Finished", Toast.LENGTH_SHORT).show();
            }
        });
        editPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != tempSound) {
                    try {
                        tempSound.pause();
                        editPause.setEnabled(false);
                        editResume.setEnabled(true);
                    } catch (RuntimeException ex) {
                        //Ignore
                    }
                }
            }
        });
        editResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != tempSound) {
                    try {
                        tempSound.resume();
                        editPause.setEnabled(true);
                        editResume.setEnabled(false);
                    } catch (RuntimeException ex) {
                        //Ignore
                    }
                }
            }
        });
        editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDelCheck.setVisibility(View.VISIBLE);
                editDelNo.setVisibility(View.VISIBLE);
                editDelYes.setVisibility(View.VISIBLE);
            }
        });
        editDelYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundEffects.remove(currentsound);
                saveData();
                Toast.makeText(getApplicationContext(), "Sound Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        editDelNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDelCheck.setVisibility(View.GONE);
                editDelNo.setVisibility(View.GONE);
                editDelYes.setVisibility(View.GONE);
            }
        });
    }
    private void NewSound() {
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording" + currentsound + ".3gp";
        tempSound = new MediaRecorder();
        tempSound.setAudioSource(MediaRecorder.AudioSource.MIC);
        tempSound.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        tempSound.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        tempSound.setOutputFile(outputFile);
        tempSound.setAudioSamplingRate(samplerate);
    }
}