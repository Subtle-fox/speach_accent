package andyanika.speechaccent;

/**
 * Created by kolpakov on 15/04/16.
 */
public interface PlayerCallback {
    void onStarted(int duration);
    void onPlaying(int position);
    void onFinished();
}
