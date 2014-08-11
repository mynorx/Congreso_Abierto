package com.redciudadana.congreso_abierto;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DiputadosFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		//Codigo para llenar automaticamente el listView
		View fragment = inflater.inflate(R.layout.diputados_fragment, container, false);
		ListView list_distritos = (ListView)fragment.findViewById(R.id.listDistritos);
				        
		ArrayList<ItemLista> datos = obtenerItems();

		//Creamos el nuevo adapter con el fragmento y la lista creados
		ItemListaAdapter adapter = new ItemListaAdapter(this, datos);
		        
		list_distritos.setAdapter(adapter);  
		
		return fragment;
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
