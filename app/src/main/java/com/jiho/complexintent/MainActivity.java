package com.jiho.complexintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btnIntent);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification("title", "Message");
            }
        });
    }

    private void sendNotification(String title, String message){ // 알림 띄워주는 메서드
        Intent intent = new Intent(this, MainActivity.class);// 인텐트는 메인 액티비티로 넘어가게 해 줌
        intent.putExtra("name", "test"); // name이라는 태그의 test 문자열을 인텐트로 넘겨준다
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_CANCEL_CURRENT); // 팬딩 인텐트

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // 소리 받아옴

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String name = "chname";
        String channel_id = "chid";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // 안드로이드 버전 체크 후 oreo 버전 이상일 때 알림 채널 생성해주는 부분
            NotificationChannel mchannel = new NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mchannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id).setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_info)
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true) // 터치 시 알림 사라짐
                .setSound(defaultSoundUri) // 소리 가져옴
                .setContentIntent(pendingIntent); // pendingIntent 가져옴

        notificationManager.notify(0, notificationBuilder.build());
    }
}