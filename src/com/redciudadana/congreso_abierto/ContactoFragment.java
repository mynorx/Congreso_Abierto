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

public class ContactoFragment extends Fragment {
	
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
		
		final View fragment = inflater.inflate(R.layout.contacto_fragment, container, false);
		
		Button enviar = (Button) fragment.findViewById(R.id.btnEnviar);
		
		enviar.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				
				TextView nombre = (TextView) fragment.findViewById(R.id.txtNombre);
				TextView correo = (TextView) fragment.findViewById(R.id.txtCorreo);
				TextView comentario = (TextView) fragment.findViewById(R.id.txtComentario);
				
				String Mensaje = "Enviado por: "+ nombre.getText() + 
						         ", Correo Electrónico: " + correo.getText() +
						         ", Comentario: " + comentario.getText();
				enviar(Mensaje);
				
			}
			
		});
		ContenedorActivity.regreso = 1;
		return fragment;
	}
	
	
	private void enviar(String mensaje) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"congreso@redciudadana.org"});		
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Comentario");
		emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Email "));
	}
}
