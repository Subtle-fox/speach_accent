package andyanika.speechaccent;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Andrey Kolpakov on 14.04.2016
 * for It-Atlantic
 */
public class MediaPlayback implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnCompletionListener {

    private final Context ctx;
    private final PlayerCallback playerCallback;

    private MediaPlayer mediaPlayer;
    private Timer playNotificator;
    private boolean isPaused;

    public MediaPlayback(Context ctx, PlayerCallback playerCallback) {
        this.ctx = ctx;
        this.playerCallback = playerCallback;
    }

    public void playRecorded(String fileName) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileName); //(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Toast.makeText(ctx, "Упс... :( Не удалось открыть звукозапись", Toast.LENGTH_SHORT).show();
            reset();
            e.printStackTrace();
        }
    }

    public void playAssets(String fileName) {
        isPaused = false;
        try {
            AssetFileDescriptor afd = ctx.getAssets().openFd(fileName);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Toast.makeText(ctx, "Упс... :( Не удалось открыть звукозапись", Toast.LENGTH_SHORT).show();
            reset();
            e.printStackTrace();
        }
    }

    public void stop() {
        reset();
    }

    public void pause() {
        isPaused = true;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resume() {
        isPaused = false;
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        audioManager.setMode(AudioManager.MODE_NORMAL);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();
            playerCallback.onStarted(mp.getDuration());
            playNotificator = new Timer();
            playNotificator.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    playerCallback.onPlaying(mediaPlayer.getCurrentPosition());
                }
            }, 1000, 1000);
        } else {
            reset();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        reset();
        Log.w(getClass().getSimpleName(), "media player failed t init with error: " + what);
        return true;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            mediaPlayer.start();
            playerCallback.onStarted(mediaPlayer.getDuration());
        } else {
            reset();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playerCallback.onFinished();
    }

    private void reset() {
        isPaused = false;

        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }

        if (playNotificator != null) {
            playNotificator.cancel();
            playNotificator = null;
        }

        playerCallback.onFinished();
    }

    public boolean isPaused() {
        return isPaused;
    }
}
