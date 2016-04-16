package andyanika.speechaccent;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class SoundRecorder {
    private final Context ctx;
    private MediaRecorder mediaRecorder;
    private final String fileName;
    private Timer playNotificator;
    private PlayerCallback playerCallback;

    public SoundRecorder(Context ctx, PlayerCallback playerCallback){
        this.ctx = ctx;
        this.playerCallback = playerCallback;
        fileName = Environment.getExternalStorageDirectory()
//        ctx.getCacheDir().getAbsolutePath()
                + File.separator + "audiorecord.3gp";
    }

    public void startRecording() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();

            AudioManager audioManager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_NORMAL);
            int result = audioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
                                                            @Override
                                                            public void onAudioFocusChange(int focusChange) {

                                                            }
                                                        }, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE);

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getFileName());
            mediaRecorder.setMaxDuration(2 * 60 * 1000);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

            try {
                mediaRecorder.prepare();

                playNotificator = new Timer();
                playNotificator.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        playerCallback.onPlaying(0);
                    }
                }, 1000, 1000);
                playerCallback.onStarted(2000);
            } catch (IOException e) {
                Log.e(SoundRecorder.class.getSimpleName(), "prepare() failed");
            }

            mediaRecorder.start();
        } catch (IOException e) {
            Toast.makeText(ctx, "Упс... :( Не удалось открыть звукозапись", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        if (playNotificator != null) {
            playNotificator.cancel();
        }
    }

    public String getFileName() {
        return fileName;
    }
}
