package andyanika.speechaccent;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class SoundRecorder {
    private final Context ctx;
    private MediaRecorder mediaRecorder;
    private final String fileName;

    public SoundRecorder(Context ctx){
        this.ctx = ctx;
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

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getFileName());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mediaRecorder.prepare();
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
    }

    public String getFileName() {
        return fileName;
    }
}
