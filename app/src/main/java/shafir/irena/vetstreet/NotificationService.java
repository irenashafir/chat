package shafir.irena.vetstreet;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.firebase.jobdispatcher.JobParameters;

public class NotificationService extends com.firebase.jobdispatcher.JobService  {

    @Override
    public boolean onStartJob(final JobParameters job) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                showNotification();
                jobFinished(job, true);
            }
        });
        t.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


    public void showNotification() {

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("VetStreet");
        builder.setContentText("Long time no See. Come see what's new on VetStreet");
        builder.setSmallIcon(R.drawable.ic_pets);
        builder.setAutoCancel(true);

        builder.setContentIntent(pi);
        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(1, notification);
    }

//
//    private void setupChannel(String id){
//        String channelName = getResources().getString(R.string.channel1_name);
//
//        NotificationChannel channel = new NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_HIGH);
//
//        channel.setDescription(getResources().getString(R.string.channel_description));
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//        channel.enableVibration(true);
//        channel.enableLights(true);
//        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), Notification.AUDIO_ATTRIBUTES_DEFAULT);
//
//        channel.setShowBadge(true);
//
//        NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        mgr.createNotificationChannel(channel);
//    }



}
