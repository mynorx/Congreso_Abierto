package com.redciudadana.congreso_abierto;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemNoticiaAdapter extends BaseAdapter 
{
	protected Fragment Fragmento;
	protected Activity Actividad;
	protected ArrayList<ItemNoticia> items;
	
	//Este es el constructor
	public ItemNoticiaAdapter(Fragment fragmento, ArrayList<ItemNoticia> items) 
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
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View vi= convertView;
	    
		if(convertView == null) 
	    {	        
	        LayoutInflater inflater = (LayoutInflater) Fragmento.getActivity().getLayoutInflater();
	        vi = inflater.inflate(R.layout.item_noticia, null);
	    }
		
	    ItemNoticia item = items.get(position);
	    //TextView fecha = (TextView) vi.findViewById(R.id.txtFechaNoticia);
	    TextView descripcion = (TextView) vi.findViewById(R.id.txtDescripcionNoticia);
	    TextView titulo = (TextView) vi.findViewById(R.id.txtTituloNoticia);
	    //ImageView foto = (ImageView) vi.findViewById(R.id.imgFotoNoticia);
	    
	    //fecha.setText(item.getFecha());
	    descripcion.setText(item.getDescipcion());
	    titulo.setText(item.getTitulo());
	    //foto.setImageBitmap(item.getImagen());
	    
	    return vi;
	}

}
