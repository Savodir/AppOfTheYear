package eaict.luyksvancluysen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public List<SoundEffects>         soundEffects = new ArrayList<>();

    String name;
    String Output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        String name = getIntent().getStringExtra("name");
        String Output = getIntent().getStringExtra("output");
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        TextView newsound = findViewById(R.id.newSound);
        newsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Newsound.class);
                intent.putExtra("name", soundEffects.size());
                startActivity(intent);
                saveData();
            }
        });
        soundEffects.add(new SoundEffects("t", "t"));
        soundEffects.add(new SoundEffects("t", "t"));
        soundEffects.add(new SoundEffects("t", "t"));
        soundEffects.add(new SoundEffects("t", "t"));

        TextView test2 = findViewById(R.id.textView4);
        ListView listView = findViewById(R.id.listview);
        if (name != null && Output != null) {
            soundEffects.add(new SoundEffects(Output, name));
        }
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        saveData();
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return soundEffects.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int current = i;
            view = getLayoutInflater().inflate(R.layout.soundlist, null);
            TextView Listname = view.findViewById(R.id.txtname);
            Button listplay = view.findViewById(R.id.listplay);
            Button listedit = view.findViewById(R.id.listedit);
            Listname.setText(soundEffects.get(i).getName());
            listplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(soundEffects.get(current).getOutput());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (Exception e) {

                    }
                }
            });
            return view;
        }
    }

    public void saveData() {
        super.onStop();
        SharedPreferences sp = getSharedPreferences("SOUNDEFFECTS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        for (int i = 0; i < soundEffects.size(); i++){
            String json = gson.toJson(soundEffects.get(i));
            String name = "soundlist" + i;
            editor.putString(name, json);
        }
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sp = getSharedPreferences("SOUNDEFFECTS", MODE_PRIVATE);
        Gson gson = new Gson();
        List<SoundEffects> tempsoundEffects = new ArrayList<>();
            for (int i = 0; i < soundEffects.size(); i++){
                String name = "soundlist" + i;
                String json = sp.getString(name, "");
                SoundEffects soundeffect = gson.fromJson(json, SoundEffects.class);
                tempsoundEffects.add(soundeffect);
            }
        soundEffects = tempsoundEffects;
    }

}