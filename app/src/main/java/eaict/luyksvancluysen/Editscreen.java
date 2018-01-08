package eaict.luyksvancluysen;

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
    String numberofrecordings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tempSound = new MediaRecorder();
        String outputFile;
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording"+ numberofrecordings+ ".3gp";
        tempSound.setAudioSource(MediaRecorder.AudioSource.MIC);
        tempSound.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        tempSound.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        tempSound.setOutputFile(outputFile);
        tempSound.setAudioSamplingRate(16000);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editscreen);
        final EditText editText = findViewById(R.id.editNamechanger);
        TextView editName = findViewById(R.id.editName);
        final TextView editPlay = findViewById(R.id.editPlay);
        TextView editDelete = findViewById(R.id.editDelete);
        final TextView editRecord = findViewById(R.id.editRecord);
        final TextView editStopRecord = findViewById(R.id.editRecord);
        String name = getIntent().getStringExtra("name");
        final String Output = getIntent().getStringExtra("output");
        SoundEffects currentSound = new SoundEffects(Output, name);
        editName.setText("Current name:");
        editText.setText(name);
        editPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(Output);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {

                }
            }

        });
        SoundEffects newSound = new SoundEffects();
        editRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tempSound.prepare();
                    tempSound.start();
                } catch (IllegalStateException ise) {
                    Log.d("xD", "oops");
                } catch (IOException ioe) {

                }
                editRecord.setText("Recording");
                editStopRecord.setVisibility(View.VISIBLE);
                editRecord.setEnabled(false);
                //newsoundboard.setEnabled(false);
                editPlay.setEnabled(false);
                editText.setEnabled(false);
                //newcancel.setEnabled(false);
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
              //  newsoundboard.setEnabled(true);
                editPlay.setEnabled(true);
                editText.setEnabled(true);
               // newcancel.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording Finished", Toast.LENGTH_SHORT).show();
            }
        });
        editDelete.setText("Test");
    }
}