package andyanika.speechaccent;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Andrey Kolpakov on 14.04.2016
 * for It-Atlantic
 */
public class MediaPlayback implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private Context ctx;

    public MediaPlayback(Context ctx) {
        this.ctx = ctx;
    }

    public void play(String languageId, String accentFileNameId) {
        try {
            String fn = languageId + File.separator + accentFileNameId;
            AssetFileDescriptor afd = ctx.getAssets().openFd(fn);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Упс... :( Не удалось открыть звукозапись", Toast.LENGTH_SHORT).show();

            if (mediaPlayer != null) {
                mediaPlayer.reset();
            }
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();
        } else {
            mediaPlayer.reset();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        Log.w(getClass().getSimpleName(), "media player failed t init with error: " + what);
        return true;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            mediaPlayer.start();
        } else {
            mediaPlayer.reset();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
