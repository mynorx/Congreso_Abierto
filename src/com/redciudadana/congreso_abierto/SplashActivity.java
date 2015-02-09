package com.redciudadana.congreso_abierto;


import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Eliminamos el encabezado
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //Se inicia la actividad
		setContentView(R.layout.activity_splash);
		
		//Llamado a la función para cambio de tiempos
		Handler Clauch1 = new Handler();
		Clauch1.postDelayed(Cambio1(), 2000);
				
		Handler Clauch2 = new Handler();
		Clauch2.postDelayed(Cambio2(), 3000);
				
		Handler Clauch3 = new Handler();
		Clauch3.postDelayed(Cambio3(), 4000);
				
		//Llamado a la función para cambio de tiempos
		Handler handler = new Handler();
		handler.postDelayed(IniciarApp(), 7000);
	}
	
	//Estas son las funciones que se usan para hacer el cambio de imagen
	private Runnable Cambio1()
	{
		final ImageView lauch1 = (ImageView) findViewById(R.id.imgsplash1);
		final ImageView lauch2 = (ImageView) findViewById(R.id.imgsplash2);
		final ImageView lauch3 = (ImageView) findViewById(R.id.imgsplash3);
		final ImageView lauch4 = (ImageView) findViewById(R.id.imgsplash4);
		
		Runnable res = new Runnable(){
			@Override
			public void run() {
				lauch1.setVisibility(View.INVISIBLE);
				lauch2.setVisibility(View.VISIBLE);
				lauch3.setVisibility(View.INVISIBLE);
				lauch4.setVisibility(View.INVISIBLE);
			}	
		};
		return res;
	}
	
	private Runnable Cambio2()
	{
		final ImageView lauch1 = (ImageView) findViewById(R.id.imgsplash1);
		final ImageView lauch2 = (ImageView) findViewById(R.id.imgsplash2);
		final ImageView lauch3 = (ImageView) findViewById(R.id.imgsplash3);
		final ImageView lauch4 = (ImageView) findViewById(R.id.imgsplash4);
		
		Runnable res = new Runnable(){
			@Override
			public void run() {
				lauch1.setVisibility(View.INVISIBLE);
				lauch2.setVisibility(View.INVISIBLE);
				lauch3.setVisibility(View.VISIBLE);
				lauch4.setVisibility(View.INVISIBLE);
			}	
		};
		return res;
	}
	
	private Runnable Cambio3()
	{
		final ImageView lauch1 = (ImageView) findViewById(R.id.imgsplash1);
		final ImageView lauch2 = (ImageView) findViewById(R.id.imgsplash2);
		final ImageView lauch3 = (ImageView) findViewById(R.id.imgsplash3);
		final ImageView lauch4 = (ImageView) findViewById(R.id.imgsplash4);
		
		Runnable res = new Runnable(){
			@Override
			public void run() {
				lauch1.setVisibility(View.INVISIBLE);
				lauch2.setVisibility(View.INVISIBLE);
				lauch3.setVisibility(View.INVISIBLE);
				lauch4.setVisibility(View.VISIBLE);
			}	
		};
		return res;
	}
	
	private Runnable IniciarApp()
	{
		Runnable res = new Runnable(){
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, ContenedorActivity.class);
				startActivity(intent);
				finish();
			}	
		};
		return res;
	}
}
