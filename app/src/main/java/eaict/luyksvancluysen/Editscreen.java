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

import java.io.IOException;

/**
 * Created by CarlV on 1/8/2018.
 */

public class Editscreen extends MainActivity {

    MediaRecorder tempSound;
    int currentSound;
    String outputFile;
    int sampleRate = 16000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editscreen);
        loadData();

        tempSound = new MediaRecorder();
        currentSound = getIntent().getIntExtra("sound", 0);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording" + currentSound + ".3gp";
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
        final TextView editStopRecord = findViewById(R.id.editStopRecord);
        final TextView editDelYes = findViewById(R.id.editDeleteYes);
        final TextView editDelNo = findViewById(R.id.editDeleteNo);
        final TextView editDelCheck = findViewById(R.id.editDeleteChecker);
        TextView editSave = findViewById(R.id.editSave);

        editName.setText("Current name:");
        defName.setText(soundEffects.get(currentSound).getName());
        editText.setText(soundEffects.get(currentSound).getName());
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
                        mediaPlayer.setDataSource(soundEffects.get(currentSound).getOutput());
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
                editPause.setVisibility(View.VISIBLE);
                editResume.setVisibility(View.VISIBLE);
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
                editPause.setVisibility(View.GONE);
                editResume.setVisibility(View.GONE);
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
                soundEffects.remove(currentSound);
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

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundEffects.get(currentSound).setName(editText.getText().toString());
                Toast.makeText(getApplicationContext(), "Changes applied", Toast.LENGTH_SHORT).show();
                saveData();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void NewSound() {
        tempSound = new MediaRecorder();
        tempSound.setAudioSource(MediaRecorder.AudioSource.MIC);
        tempSound.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        tempSound.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        tempSound.setOutputFile(soundEffects.get(currentSound).getOutput());
        tempSound.setAudioSamplingRate(sampleRate);
    }
}