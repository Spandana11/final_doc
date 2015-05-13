package profile.auto.com.autoprofile_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by spandana on 4/27/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Alarm received", Toast.LENGTH_SHORT).show();
        MediaPlayer mp = new MediaPlayer();

        try {
            mp.reset();
            AssetFileDescriptor afd = context.getAssets().openFd("dexter.mp3");
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
