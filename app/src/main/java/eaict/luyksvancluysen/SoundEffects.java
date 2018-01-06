package eaict.luyksvancluysen;

import android.media.MediaRecorder;

/**
 * Created by CarlV on 1/6/2018.
 */

public class SoundEffects {
    private MediaRecorder mediaRecorder = new MediaRecorder();
    private String name;
    public SoundEffects(MediaRecorder mediaRecorder) {
        this.mediaRecorder = mediaRecorder;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public MediaRecorder getMediaRecorder() {
        return  mediaRecorder;
    }
    public void setMediaRecorder(MediaRecorder mediaRecorder) {
        this.mediaRecorder = mediaRecorder;
    }
}
