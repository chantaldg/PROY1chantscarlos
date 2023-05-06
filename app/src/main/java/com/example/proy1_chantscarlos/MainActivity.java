package com.example.proy1_chantscarlos;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNombreEstudiante, etUsuarioJuez, etCalificacion1, etCalificacion2, etCalificacion3;
    private TextView tvPromedio, tvPosicion;

    private static final String[] usuariosJueces = {"juez1", "juez2", "juez3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombreEstudiante = findViewById(R.id.etNombreEstudiante);
        etUsuarioJuez = findViewById(R.id.etUsuarioJuez);
        etCalificacion1 = findViewById(R.id.etCalificacion1);
        etCalificacion2 = findViewById(R.id.etCalificacion2);
        etCalificacion3 = findViewById(R.id.etCalificacion3);
        tvPromedio = findViewById(R.id.tvPromedio);
        tvPosicion = findViewById(R.id.tvPosicion);

        findViewById(R.id.btnCalcularPromedio).setOnClickListener(view -> {
            String nombreEstudiante = etNombreEstudiante.getText().toString();
            String usuarioJuez = etUsuarioJuez.getText().toString();
            String calificacion1Str = etCalificacion1.getText().toString();
            String calificacion2Str = etCalificacion2.getText().toString();
            String calificacion3Str = etCalificacion3.getText().toString();

            if (usuarioJuez.isEmpty()) {
                etUsuarioJuez.setError(getString(R.string.error_juez_requerido));
                return;
            }

            if (nombreEstudiante.isEmpty()) {
                etNombreEstudiante.setError(getString(R.string.error_nombre_requerido));
                return;
            }

            if (!isValidCalificacion(calificacion1Str) || !isValidCalificacion(calificacion2Str) || !isValidCalificacion(calificacion3Str)) {
                etCalificacion1.setError(getString(R.string.error_calificacion_invalida));
                etCalificacion2.setError(getString(R.string.error_calificacion_invalida));
                etCalificacion3.setError(getString(R.string.error_calificacion_invalida));
                return;
            }

            if (!isValidJuez(usuarioJuez)) {
                etUsuarioJuez.setError(getString(R.string.error_juez_invalido));
                return;
            }

            int calificacion1 = Integer.parseInt(calificacion1Str);
            int calificacion2 = Integer.parseInt(calificacion2Str);
            int calificacion3 = Integer.parseInt(calificacion3Str);

            if (calificacion1 > 10 || calificacion2 > 10 || calificacion3 > 10) {
                etCalificacion1.setError(getString(R.string.error_calificacion_invalida));
                etCalificacion2.setError(getString(R.string.error_calificacion_invalida));
                etCalificacion3.setError(getString(R.string.error_calificacion_invalida));
                return;
            }

            double promedio = (double) (calificacion1 + calificacion2 + calificacion3) / 3;
            String promedioStr = String.format("%.2f", promedio);
            String[] registro = new String[] { nombreEstudiante, promedioStr };
            tvPromedio.setText(getString(R.string.promedio_estudiante, nombreEstudiante, promedioStr));

            String posicion = getPosition(promedio);
            tvPosicion.setText(getString(R.string.posicion_estudiante, nombreEstudiante, posicion));
        });
    }

    private boolean isValidJuez(String usuarioJuez) {
        for (String usuario : usuariosJueces) {
            if (usuario.equals(usuarioJuez)) {
                return true;
            }
        }
        return false;
    }


