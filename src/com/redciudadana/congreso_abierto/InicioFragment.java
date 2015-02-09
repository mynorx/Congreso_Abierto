package com.redciudadana.congreso_abierto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class InicioFragment extends Fragment {
	
	private String mTitle; //Variable que contendrá el titulo de cada opción y que se irá cambiando.
	//Variables para los fragment de los banner
	private Fragment miBanner1, miBanner2, miBanner3;
	private FragmentTransaction ft;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View fragment = inflater.inflate(R.layout.inicio_fragment, container, false);
		
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
		//Detectamos el estado de la rotacion
		WindowManager wm = getActivity().getWindowManager();
		Display d = wm.getDefaultDisplay();
				
		if ((d.getRotation() == Surface.ROTATION_90) || (d.getRotation() == Surface.ROTATION_270))
		{fragment = inflater.inflate(R.layout.inicio_fagment_h, container, false);}

		
		ContenedorActivity.regreso = 0;
				
		//Se llaman las imágenes del fragment_layout
		ImageView imgDiputados = (ImageView)fragment.findViewById(R.id.imgInicioDiputados);//Imágen ¿Quién es mi representante?
		ImageView imgComisiones = (ImageView)fragment.findViewById(R.id.imgInicioComisiones);//Imágen Comisiones
		ImageView imgTuVoz = (ImageView)fragment.findViewById(R.id.imgInicioTuVoz);//Imágen Tu Voz Cuenta
		ImageView imgContacto = (ImageView)fragment.findViewById(R.id.imgInicioContacto);//Imágen Contáctanos
		
		//Reaccion al presionarlos
		imgDiputados.setOnClickListener(new OnClickListener ()
		{
			@Override
			public void onClick(View v) 
			{
				//Se cambia la imagen del banner a banner_distrito
				
				
				//Se busca el titulo a mostrar
				mTitle = getString(R.string.opcion1);//Diputados
				((ContenedorActivity) getActivity()).setActionBarTitle(mTitle);//Agregamos el titulo al activity
				//Aquí se crea procedimiento para llamar al fragment de diputados al seleccionarlo
				FragmentManager fragmentManager = getFragmentManager();
				//Aquí se modifica el numero al textview dependiendo de su posicion
				Fragment fragment = new DiputadosFragment();
				fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
			}			
		});
		
		imgComisiones.setOnClickListener(new OnClickListener ()
		{
			@Override
			public void onClick(View v) 
			{
				mTitle = getString(R.string.opcion2);//Diputados
				((ContenedorActivity) getActivity()).setActionBarTitle(mTitle);//Agregamos el titulo al activity
				//Aquí se crea procedimiento para llamar al fragment de diputados al seleccionarlo
				FragmentManager fragmentManager = getFragmentManager();
				//Aquí se modifica el numero al textview dependiendo de su posicion
				Fragment fragment = new ComisionesFragment();
				fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
			}			
		});
		
		imgTuVoz.setOnClickListener(new OnClickListener ()
		{
			@Override
			public void onClick(View v) 
			{
				mTitle = getString(R.string.opcion3);//Diputados
				((ContenedorActivity) getActivity()).setActionBarTitle(mTitle);//Agregamos el titulo al activity
				//Aquí se crea procedimiento para llamar al fragment de diputados al seleccionarlo
				FragmentManager fragmentManager = getFragmentManager();
				//Aquí se modifica el numero al textview dependiendo de su posicion
				Fragment fragment = new TuVozFragment();
				fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
			}			
		});
		
		imgContacto.setOnClickListener(new OnClickListener ()
		{
			@Override
			public void onClick(View v) 
			{
				mTitle = getString(R.string.opcion6);//Diputados
				((ContenedorActivity) getActivity()).setActionBarTitle(mTitle);//Agregamos el titulo al activity
				//Aquí se crea procedimiento para llamar al fragment de diputados al seleccionarlo
				FragmentManager fragmentManager = getFragmentManager();
				
				//Aquí se modifica el numero al textview dependiendo de su posicion
				Fragment fragment = new ContactoFragment();
				fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
			}			
		});
		
		return fragment;
	}	

}
