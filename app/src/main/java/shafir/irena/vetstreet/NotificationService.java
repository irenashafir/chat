package shafir.irena.vetstreet;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    public NotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        showNotification();
    }


    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("VetStreet");
        builder.setContentText("Long time no See. Come see what's new on VetStreet");
        builder.setSmallIcon(R.drawable.ic_pets);
        builder.setAutoCancel(true);
        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        Notification notification = builder.build();
        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(1, notification);

    }


}
