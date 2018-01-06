package eaict.luyksvancluysen;

import android.media.MediaRecorder;

/**
 * Created by CarlV on 1/6/2018.
 */

public class SoundEffects {
    private MediaRecorder mediaRecorder = new MediaRecorder();
    private String name;
    private String output;
    public SoundEffects(String output, String name) {
        this.output = output;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOutput() {
        return  output;
    }
    public void setOutput(String output) {
        this.output = output;
    }
}
