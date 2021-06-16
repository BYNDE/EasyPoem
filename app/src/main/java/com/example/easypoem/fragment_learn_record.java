package com.example.easypoem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class fragment_learn_record extends Fragment implements View.OnClickListener{
    private static final int PERMISSION_CODE = 1;
    private boolean is_recording = false;
    private ImageButton record_button,record_again_button,correct_button;
    private MediaRecorder mediaRecorder;
    private String record_file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_learn_record, container, false);


        record_button = view.findViewById(R.id.start_or_stop_recording);
        record_again_button = view.findViewById(R.id.record_again);
        correct_button = view.findViewById(R.id.correct);
        TextView tv_text = view.findViewById(R.id.TV_text);


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


        record_button.setOnClickListener(this);
        record_again_button.setOnClickListener(this);
        correct_button.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_or_stop_recording:
                if(is_recording){
                    stopRecording();
                    record_button.setVisibility(View.INVISIBLE);
                    record_again_button.setVisibility(View.VISIBLE);
                    correct_button.setVisibility(View.VISIBLE);
                    is_recording = !is_recording;
                }
                else{
                    if(check_permission()){
                        startRecording();
                        record_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_stop_40));
                        is_recording = !is_recording;
                    }
                }
                break;

            case R.id.record_again:
                startRecording();
                record_again_button.setVisibility(View.INVISIBLE);
                correct_button.setVisibility(View.INVISIBLE);
                record_button.setVisibility(View.VISIBLE);
                is_recording = !is_recording;
                break;

            case R.id.correct:
                poem_learn_main poem_learn_main= new poem_learn_main();
                fragment_learn_listening fragment_learn_listening = new fragment_learn_listening();
                poem_learn_main.getInstance().replace_fragment(fragment_learn_listening);
                poem_learn_main.getInstance().set_progress(100);
                break;
        }
    }

    private void startRecording() {
        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        record_file = "filename.3gp";



        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(recordPath + "/" + record_file);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            mediaRecorder.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private boolean check_permission() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},PERMISSION_CODE);
            return false;
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (is_recording){
            stopRecording();
        }
    }
}