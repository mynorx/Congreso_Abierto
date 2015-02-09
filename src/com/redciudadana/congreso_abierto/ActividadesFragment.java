package com.redciudadana.congreso_abierto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActividadesFragment extends Fragment{

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
		View fragment = inflater.inflate(R.layout.actividades_fragment, container, false);
		ContenedorActivity.regreso = 1;
		return fragment;
	}	
}
