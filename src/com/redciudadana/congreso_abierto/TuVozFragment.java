package com.redciudadana.congreso_abierto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TuVozFragment extends Fragment{
	//Variables para los fragment de los banner
	private Fragment miBanner1, miBanner2, miBanner3;
	private FragmentTransaction ft;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		//Asignamos los fragments de los banner creados en el layout activity
		miBanner1 = (Fragment) getFragmentManager().findFragmentById(R.id.FragmentBienvenida);
		miBanner2 = (Fragment) getFragmentManager().findFragmentById(R.id.FragmentDistritos);
		miBanner3 = (Fragment) getFragmentManager().findFragmentById(R.id.FragmentRepresentante);
		//Cargamos el fragmentManager
		ft = getFragmentManager().beginTransaction();
		//Aquí se gestiona la visibilidad de los fragmentos 
		ft.show(miBanner1);
		ft.hide(miBanner2);
		ft.hide(miBanner3);
		ft.commit();
		
		final View fragment = inflater.inflate(R.layout.tu_voz_fragment, container, false);
		
		Button enviar_voz = (Button) fragment.findViewById(R.id.btnEnviarTuVoz);
		
		enviar_voz.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				TextView pregunta1 = (TextView) fragment.findViewById(R.id.txtPregunta1);
				TextView pregunta2 = (TextView) fragment.findViewById(R.id.txtPregunta2);
				TextView pregunta3 = (TextView) fragment.findViewById(R.id.txtPregunta3);
				
				String Mensaje = "¿Qué no conoces del congreso y te gustaría conocer? "+ pregunta1.getText() + 
						         ",  ¿Qué no te gusta del congreso? " + pregunta2.getText() +
						         ", ¿Qué propones para mejorar lo que no te gusta del congreso " + pregunta3.getText();
				enviar(Mensaje);
			}
		});
		ContenedorActivity.regreso = 1;
		return fragment;
	}
	
	private void enviar(String mensaje) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"congreso.voz@redciudadana.org"});
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tu Voz Cuenta");
		emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Email "));
	}
}
