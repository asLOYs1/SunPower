package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class consejos extends AppCompatActivity {

    private ImageButton registroButton, estadisticasButton;
    private TextView consejo;
    private Button siguiente, anterior, exitButton;
    private TableLayout tableLayout;

    private String[] tips = {
            "Ahorra energía apagando las luces que no necesitas.",
            "Desconecta los electrodomésticos que no estás usando.",
            "Utiliza bombillas LED para reducir el consumo de energía.",
            "Mira cuál es tu excedente de energía solar.",
            "Aprovecha la luz natural durante el día.",
            "Incluye baterías a tu instalación fotovoltaica.",
            "Programa tus electrodomésticos y dispositivos para que funcionen durante las horas de mayor generación solar.",
            "Posición y orientación adecuadas. La ubicación y orientación de tus paneles solares son fundamentales para su rendimiento óptimo. ",
            "Revisa tus aparatos eléctricos para evitar fugas de energía.",
            "Limpieza regular. Una buena limpieza permite un panel optimo.",
            "Busca un buen ángulo para que la luz llegue directamente",
            "una mala instalación, configuración incorrecta, fallo eléctrico o mecánico puede disminuir la eficiencia y rendimiento de este sistema.",
            "Al limpiar los paneles solares, no se conseja usar productos quimicos.",
            "Limpia los paneles con agua tibia de forma cuidadosa, si utilizas  algun productos que sea uno que no deje restos"
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos);
        exitButton = findViewById(R.id.button5);
        registroButton = findViewById(R.id.imageButton2);
        estadisticasButton = findViewById(R.id.imageButton);
        siguiente = findViewById(R.id.button7);
        anterior = findViewById(R.id.button6);
        consejo = findViewById(R.id.textView);
        // Declarar el TableLayout
        tableLayout = findViewById(R.id.tableLayoutConsejos);

        consejo.setText(tips[currentIndex]);



        // Listener para salir a la pantalla de login
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consejos.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        // Listener para ir a la pantalla de registro
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consejos.this, pantallaPrincipalCategorias.class);
                startActivity(intent);
            }
        });
        // Listener para ir a la pantalla de monitoreo
        estadisticasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consejos.this, estadisticas.class);
                startActivity(intent);
            }
        });
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex < tips.length - 1) {
                    currentIndex++;
                    consejo.setText(tips[currentIndex]);
                }
            }
        });
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex > 0) {
                    currentIndex--;
                    consejo.setText(tips[currentIndex]);
                }
            }
        });
    }

}