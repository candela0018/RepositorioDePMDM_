package com.example.prctica_reproductor_msica;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer;
    private Button btnPlay, btnPause, btnSonido1, btnSonido2, btnSonido3, btnSonido4;
    private SeekBar seekBar;
    private TextView tvTiempo, tvDuracion, tvTituloCancion;

    private Handler handler = new Handler();

    private SoundPool soundPool;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnPlay = findViewById(R.id.btnPlay); //Botón play
        btnPause = findViewById(R.id.btnPause); //Botón pause
        seekBar = findViewById(R.id.seekBar); //Barra de reproducción
        tvTiempo = findViewById(R.id.tvTiempo); //TextView que se actualizara mientras se reproduzca la canción
        tvDuracion = findViewById(R.id.tvDuracion); //TextView que mostrará la actualizacion total de la canción
        tvTituloCancion = findViewById(R.id.tvTituloCancion); //Para mostrar el titulo de la canción de la cancion que está sonando


        soundPool = new SoundPool.Builder().setMaxStreams(10).build();

        int sonido1 = soundPool.load(this, R.raw.disparar_con_una_mira_optica_en_un_rifle, 1);
        btnSonido1 = findViewById(R.id.btnSonido1);

        btnSonido1.setOnClickListener(v -> {

            soundPool.play(sonido1, 1, 1, 1, 0, 1);

        });


        int sonido2 = soundPool.load(this, R.raw.disparo_a_un_objetivo_entrenamiento, 1);
        btnSonido2 = findViewById(R.id.btnSonido2);


        btnSonido2.setOnClickListener(v -> {

            soundPool.play(sonido2, 1, 1, 1, 0, 1);

        });


        int sonido3 = soundPool.load(this, R.raw.golpe_aplastanete_de_sable, 1);
        btnSonido3 = findViewById(R.id.btnSonido3);


        btnSonido3.setOnClickListener(v -> {

            soundPool.play(sonido3, 1, 1, 1, 0, 1);

        });

        int sonido4 = soundPool.load(this, R.raw.golpe_magistral_con_la_espada_entrenamiento, 1);
        btnSonido4 = findViewById(R.id.btnSonido4);


        btnSonido4.setOnClickListener(v -> {

            soundPool.play(sonido4, 1, 1, 1, 0, 1);

        });






        mediaPlayer = MediaPlayer.create(this, R.raw.audio1); //Vincular la carpeta raw, que tendra las canciones
        tvTituloCancion.setText("Canción: Audio 1");

        //Duración total de la cancion
        int duracionTotalCancion = mediaPlayer.getDuration();
        tvDuracion.setText(formatoTiempo(duracionTotalCancion));

        //El tamaño máximo de la barra
        seekBar.setMax(duracionTotalCancion);

        //Método cuando se clickea el boton play
        btnPlay.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                actualizarSeekBar();
            }
        });

       //Método cuando se clickea el botón pause
        btnPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

       //Para que podamos arrastrar la barra de reproducción
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    //Actualizar la barra y el tiempo que irá aumentando mientras la canción esté sonando
    private void actualizarSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        tvTiempo.setText(formatoTiempo(mediaPlayer.getCurrentPosition()));

        handler.postDelayed(this::actualizarSeekBar, 500);
    }

   //Método de los minutos y los segundos
    private String formatoTiempo(int ms) {
        int minutos = (ms / 1000) / 60;
        int segundos = (ms / 1000) % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
