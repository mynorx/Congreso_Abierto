package com.redciudadana.congreso_abierto;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DiputadosFragment extends Fragment {	
	
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
		ft.hide(miBanner1);
		ft.show(miBanner2);
		ft.hide(miBanner3);
		ft.commit();
		try
		{	
			//Codigo para llenar automaticamente el listView
			View fragment = inflater.inflate(R.layout.diputados_fragment, container, false);
			//Detectamos el estado de la rotacion
			WindowManager wm = getActivity().getWindowManager();
			Display d = wm.getDefaultDisplay();
					
			if (d.getRotation() == Surface.ROTATION_90)
			{fragment = inflater.inflate(R.layout.diputados_fragment_h, container, false);}
			
			final ListView list_distritos = (ListView)fragment.findViewById(R.id.listDistritos);
					        
			ArrayList<ItemLista> datos = obtenerItems();

			//Creamos el nuevo adapter con el fragmento y la lista creados
			ItemListaAdapter adapter = new ItemListaAdapter(this, datos);
			list_distritos.setAdapter(adapter);
			
			list_distritos.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
				{
					//Aquí obtenemos el distrito que seleccionamos
					ItemLista elegido = (ItemLista) parent.getItemAtPosition(position);
					CharSequence texto = elegido.getNombre();
					
					//Aquí se crea procedimiento para llamar al fragment de diputados al hacer clic
					DiputadosDistritoFragment.distrito=texto;
									
					FragmentManager fragmentManager = getFragmentManager();
					Fragment fragmento = new DiputadosDistritoFragment();
					fragmentManager.beginTransaction().replace(R.id.container,fragmento).commit();					
				}
			});
			ContenedorActivity.regreso = 1;
			return fragment;
		}
		catch(Exception e)
		{
			return null;
		}		
	}
	
	//Funcion para llenar el listview
	private ArrayList<ItemLista> obtenerItems() 
	{
		ArrayList<ItemLista> distritos = new ArrayList<ItemLista>();
		
		//Se agregan manualmente los distritos a la lista
		distritos.add(new ItemLista(0,"Alta Verapaz"));
		distritos.add(new ItemLista(1,"Baja Verapaz"));
		distritos.add(new ItemLista(2,"Chimaltenango"));
		distritos.add(new ItemLista(3,"Distrito Central"));
		distritos.add(new ItemLista(4,"Distrito Guatemala"));
		distritos.add(new ItemLista(5,"El Progreso"));
		distritos.add(new ItemLista(6,"Escuintla"));
		distritos.add(new ItemLista(7,"Huehuetenango"));
		distritos.add(new ItemLista(8,"Izabal"));
		distritos.add(new ItemLista(9,"Jalapa"));
		distritos.add(new ItemLista(10,"Jutiapa"));
		distritos.add(new ItemLista(11,"Petén"));
		distritos.add(new ItemLista(12,"Quetzaltenango"));
		distritos.add(new ItemLista(13,"Quiché"));
		distritos.add(new ItemLista(14,"Retalhuleu"));
		distritos.add(new ItemLista(15,"Sacatepéquez"));
		distritos.add(new ItemLista(16,"San Marcos"));
		distritos.add(new ItemLista(17,"Santa Rosa"));
		distritos.add(new ItemLista(18,"Sololá"));
		distritos.add(new ItemLista(19,"Totonicapán"));
		distritos.add(new ItemLista(20,"Zacapa"));
		distritos.add(new ItemLista(21,"Listado Nacional"));
		
		return distritos;
	}
	
}
