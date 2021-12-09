package com.websarva.wings.android.alram_1206;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import static java.lang.String.format;

public class Main_alarm_Activity extends AppCompatActivity {

    //設定時刻表示用
    String sdata;
    //初期値用 x:時　y:分
    int x=0;
    int y=0;

    //時間になったら起動するパラメータ
    PendingIntent pendingIntent;
    AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alarm);

        read();
    }

    //開始ボタンを押した時
    public void aet(View view) {
        write();

        //開始ボタンを押したら画面を隠す
        //finish();

        //タイマー起動
        //ベースコンテキストを取得
        Context context = getBaseContext();

        //アラームマネージャーの作成と設定
        manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //インテントの作成   指定時刻にSubActivityを起動
        Intent intent = new Intent(context, Sub_alarm_Activity.class);

        //ペンディングインテントの作成
        pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        //カレンダーの作成
        Calendar calendar = Calendar.getInstance();     //現在時間が取得される

        //指定時間をセット
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, x);
        calendar.set(Calendar.MINUTE, y);
        calendar.set(Calendar.SECOND, 0);

        //指定間隔を分単位でする場合
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 1, pendingIntent);
        System.out.println("開始時------------------>" + pendingIntent);


        //指定間隔を予め用意されている間隔でする場合
        //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //        AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);


    }

    //終了ボタンを押した時
    public void stop(View view) {

        System.out.println("終了時------------------>" + pendingIntent);

        //繰り返しアラームの停止
        if (manager!= null) {
            manager.cancel(pendingIntent);
        }

        //finish();
    }

    //アプリケーション設定値登録
    private void write(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //プレーンテキストから値取得
        TextView jikoku=findViewById(R.id.jikoku);
        sdata=jikoku.getText().toString();
        String ttt=(sdata.substring(0,2));
        String mmm=(sdata.substring(3));

        //TextView pct = findViewById(R.id.percent);
        //String text = pct.getText().toString();

        //数値に変換
        int tt = Integer.parseInt(ttt);
        int mm = Integer.parseInt(mmm);
        //int ps = Integer.parseInt(text);

        //SharedPreferencesに保存
        editor.putInt("SET_TIME", tt);
        editor.putInt("SET_Minutes", mm);
        //editor.putInt("percent", ps);

        //実際の保存
        editor.apply();

        read();

    }

    //アプリケーション設定値取得
    private void read() {

        //値を読み出す
        SharedPreferences pref  = PreferenceManager.getDefaultSharedPreferences(this);

        //SharedPreferencesからの呼び出し時刻
        int time = pref.getInt("SET_TIME", 0);
        int Minutes = pref.getInt("SET_Minutes", 0);

        //バッテリーボーダーラインの呼び出し
        //int percent = pref.getInt("percent", 40);


        x=time;
        y=Minutes;

        //テキストにセット
        String sjikoku = format(Locale.JAPANESE, "%02d:%02d", x, y);
        TextView textView = findViewById(R.id.jikoku);
        textView.setText(sjikoku);

        //バッテリーボーダーラインを文字列に変換
        //String pp = Integer.valueOf(percent).toString();
        //TextView spct = findViewById(R.id.percent);
        //spct.setText(pp);

    }

    //時刻表示テキストビューをタップしたらタイムピッカーを表示
    public void set2(View view) {
        TextView date = findViewById(R.id.jikoku);
        TimePickerDialog dialog = new TimePickerDialog(this,
                //ここで初期値          x:時　y:分   タイムピッカーに初期値をセット
                new DateSetHandler(date), x, y, true);
        dialog.show();
    }

    //タイムピッカーの日付をTextViewに表示するリスナー
    public class DateSetHandler implements TimePickerDialog.OnTimeSetListener {
        private TextView jikoku;

        public DateSetHandler(TextView date) {
            this.jikoku = date;
        }

        //タイムビーカーで設定した時分を取得
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            //実際に日付をTextViewにセット
            jikoku.setText(format("%tR", calendar));
            //タイムピッカーの時刻をsdataに退避
            sdata= format("%tR", calendar);
        }

    }


}