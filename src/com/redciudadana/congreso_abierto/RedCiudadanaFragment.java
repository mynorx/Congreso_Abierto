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
import android.widget.ImageView;

public class RedCiudadanaFragment extends Fragment {

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
		
		View fragment = inflater.inflate(R.layout.red_ciudadana_fragment, container, false);
		ImageView logo = (ImageView) fragment.findViewById(R.id.imgLogoRedCiudadana);
		logo.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent redireccion = new Intent();
				redireccion.setAction(Intent.ACTION_VIEW);
				redireccion.addCategory(Intent.CATEGORY_BROWSABLE);
				redireccion.setData(Uri.parse("http://www.redciudadana.org"));
				startActivity(redireccion);				
			}
		});
		
		ContenedorActivity.regreso = 1;
		return fragment;
	}	
}
