package com.websarva.wings.android.alram_1206;

import static android.media.AudioAttributes.CONTENT_TYPE_MUSIC;
import static android.media.AudioAttributes.FLAG_AUDIBILITY_ENFORCED;
import static android.media.AudioAttributes.USAGE_ALARM;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sub_alarm_Activity extends AppCompatActivity {

    MediaPlayer mediaPlayer; //メディアプレイヤー
    Uri uri; //音声ファイルのURI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_alarm);

        TextView textView = findViewById(R.id.textView);
        textView.setText("停止ボタンを押してアラームを止めてください");
        //アラーム起動
        Alarm();
    }

    //アラームを鳴らす
    private void Alarm() {
        //uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);        //着信音
        //uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);    //通知音
        //uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);             //アラーム音

        //mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this,R.raw.morning2);
        //ミュートでなければこれはいらない　つまりミュート対策-------
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setUsage(USAGE_ALARM)                //アラーム音
                        //.setUsage(USAGE_ASSISTANCE_SONIFICATION)            //着信音と通知音
                        .setContentType(CONTENT_TYPE_MUSIC)   //アラーム・メディアの音量
                        .setFlags(FLAG_AUDIBILITY_ENFORCED)   //ミュートでも鳴らす
                        .build());
        //mediaPlayer.setDataSource(this, uri);
        //mediaPlayer.prepare();
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    //終了ボタンが押された時
    public void syuryou(View view) {
        //音の停止
        mediaPlayer.stop();
        finish();
    }
}