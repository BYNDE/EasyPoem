package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

public class listen_activity extends AppCompatActivity  implements View.OnClickListener{
    private File file;
    private ImageButton play_button;
    private SeekBar seekBar;
    private Handler seekBarHandler;
    private Runnable updateSeekBar;
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private boolean audio_is_launched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_activity);
        getSupportActionBar().hide();

        String path = this.getExternalFilesDir("/").getAbsolutePath();
        file = new File(path+"/filename.3gp");

        play_button = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekBar);

        play_button.setOnClickListener(this);

        TextView tv_text = findViewById(R.id.TV_text);

        tv_text.setText("— Скажи-ка, дядя, ведь не даром\n" +
                "Москва, спаленная пожаром,\n" +
                "Французу отдана?\n" +
                "Ведь были ж схватки боевые,\n" +
                "Да, говорят, еще какие!\n" +
                "Недаром помнит вся Россия\n" +
                "Про день Бородина!\n" +
                "\n" +
                "— Да, были люди в наше время,\n" +
                "Не то, что нынешнее племя:\n" +
                "Богатыри — не вы!\n" +
                "Плохая им досталась доля:\n" +
                "Немногие вернулись с поля…\n" +
                "Не будь на то господня воля,\n" +
                "Не отдали б Москвы!\n" +
                "\n" +
                "Мы долго молча отступали,\n" +
                "Досадно было, боя ждали,\n" +
                "Ворчали старики:\n" +
                "«Что ж мы? на зимние квартиры?\n" +
                "Не смеют, что ли, командиры\n" +
                "Чужие изорвать мундиры\n" +
                "О русские штыки?»\n" +
                "\n" +
                "И вот нашли большое поле:\n" +
                "Есть разгуляться где на воле!\n" +
                "Построили редут.\n" +
                "У наших ушки на макушке!\n" +
                "Чуть утро осветило пушки\n" +
                "И леса синие верхушки —\n" +
                "Французы тут как тут.\n" +
                "\n" +
                "Забил заряд я в пушку туго\n" +
                "И думал: угощу я друга!\n" +
                "Постой-ка, брат мусью!\n" +
                "Что тут хитрить, пожалуй к бою;\n" +
                "Уж мы пойдем ломить стеною,\n" +
                "Уж постоим мы головою\n" +
                "За родину свою!\n");
    }

    @Override
    public void onClick(View v) {
        if (isPlaying){
            play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_play_40));
            pauseAudio();
        }
        else {
            play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_pause_40));
            if (audio_is_launched){
                resumeAudio();
            }
            else {
                playAudio();
                audio_is_launched = true;
            }
        }
        isPlaying = !isPlaying;
    }

    private void pauseAudio(){
        mediaPlayer.pause();
        seekBarHandler.removeCallbacks(updateSeekBar);
    }

    private void resumeAudio(){
        mediaPlayer.start();
        updateRunnable();
        seekBar.postDelayed(updateSeekBar,0);
    }

    private void stopAudio() {
        mediaPlayer.stop();
        seekBarHandler.removeCallbacks(updateSeekBar);
    }

    public void playAudio(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(50,100);
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){

        }

        seekBar.setMax(mediaPlayer.getDuration());
        seekBarHandler = new Handler();
        updateRunnable();
        seekBar.postDelayed(updateSeekBar,0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAudio();
                play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_play_40));

                isPlaying = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayer.seekTo(progress);
                resumeAudio();
                play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_pause_40));
                isPlaying = true;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_play_40));
                isPlaying = false;
                stopAudio();
                audio_is_launched = false;
            }
        });
    }

    private void updateRunnable() {
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                seekBarHandler.postDelayed(this,0);
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPlaying){
            mediaPlayer.stop();
        }
    }
}