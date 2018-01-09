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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    List<SoundEffects> soundEffects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        TextView newsound = findViewById(R.id.newSound);
        newsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Newsound.class);
                startActivity(intent);
                saveData();
            }
        });
        TextView test2 = findViewById(R.id.textView4);
        ListView listView = findViewById(R.id.listview);
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
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
            listedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), Editscreen.class);
                    intent.putExtra("sound", current);
                    startActivity(intent);
                }
            });
            return view;
        }
    }


    public void saveData() {
        SharedPreferences sp = getSharedPreferences("SOUNDEFFECTS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String json = new Gson().toJson(soundEffects);
        String name = "soundlist";
        editor.putString(name, json);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sp = getSharedPreferences("SOUNDEFFECTS", MODE_PRIVATE);
        String name = "soundlist";
        String json = sp.getString(name, null);
        Type soundEffectListType = new TypeToken<ArrayList<SoundEffects>>(){}.getType();
        soundEffects = new Gson().fromJson(json, soundEffectListType);
        if (soundEffects == null){
            soundEffects = new ArrayList<>();
        }
    }

}