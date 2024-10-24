package com.estefanoquiriconi.proyecto021;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Pais> paises;
    ViewPager2 viewPager;

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

        paises = new ArrayList<>();

        paises.add(new Pais("Argentina", R.drawable.argentina));
        paises.add(new Pais("Brasil", R.drawable.brasil));
        paises.add(new Pais("Colombia", R.drawable.colombia));

        viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(new PaisAdapter(this, paises));
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    }
}