package andyanika.speechaccent;

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

import java.io.IOException;

/**
 * Created by Andrey Kolpakov on 14.04.2016
 * for It-Atlantic
 */
public class MediaPlayback extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnCompletionListener {

    MediaPlayer mediaPlayer;

    private static final int NOTIFICATION_ID = 5050;

    static final String ACTION_PLAY = "com.example.action.PLAY";
    static final String ACTION_PAUSE = "com.example.action.PAUSE";
    static final String ACTION_STOP = "com.example.action.STOP";
    static final String ACTION_RESUME = "com.example.action.RESUME";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_PLAY:
                if (mediaPlayer != null) {
                    mediaPlayer.reset();
                }

                play(this, R.raw.china_china_dekun_0_0_1);
                break;
            case ACTION_PAUSE:
                pause();
                break;
            case ACTION_STOP:
                stop();
                break;
            case ACTION_RESUME:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    void play(Context ctx, @RawRes int rawFileId) {
        try {
            AssetFileDescriptor afd = ctx.getResources().openRawResourceFd(rawFileId);
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

    void stop() {
        mediaPlayer.stop();
        cancelNotification();
    }

    void pause() {
        mediaPlayer.pause();
        pauseNotification();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();
            showNotification();
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

    void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setContentTitle("Проигрывается")
                        .setContentText("название")
                        .setContentIntent(pi)
                        .setOngoing(true)
                        .setCategory(NotificationCompat.CATEGORY_PROGRESS);

        startForeground(NOTIFICATION_ID, builder.build());
    }

    void pauseNotification() {
        stopForeground(false);
    }

    void cancelNotification() {
        stopForeground(true);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        cancelNotification();
    }
}
