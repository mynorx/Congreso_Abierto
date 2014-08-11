package com.redciudadana.congreso_abierto;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListaAdapter extends BaseAdapter
{
	protected Fragment Fragmento;
	protected Activity Actividad;
	protected ArrayList<ItemLista> items;
	
	//Este es el constructor
	public ItemListaAdapter(Fragment fragmento, ArrayList<ItemLista> items) 
	{
		this.Fragmento = fragmento;
	    this.items = items;
	}

	@Override
	public int getCount() 
	{
		return items.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return items.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi= convertView;
	    
		if(convertView == null) 
	    {	        
	        LayoutInflater inflater = (LayoutInflater) Fragmento.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        vi = inflater.inflate(R.layout.item_lista, null);
	    }
		
	    ItemLista item = items.get(position);
	    TextView nombre = (TextView) vi.findViewById(R.id.item_nombre);
	    nombre.setText(item.getNombre());
	    
	    return vi;
	}
	
}
