package com.redciudadana.congreso_abierto;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDiputadosAdapter extends BaseAdapter
{
	protected Fragment Fragmento;
	protected Activity Actividad;
	protected ArrayList<ItemDiputados> items;
	private ImageView imagenLista = null;
	
	
	//Este es el constructor
	public ItemDiputadosAdapter(Fragment fragmento, ArrayList<ItemDiputados> items) 
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
	        vi = inflater.inflate(R.layout.item_diputados, null);
	    }
		
	    final ItemDiputados item = items.get(position);
	    TextView nombre = (TextView) vi.findViewById(R.id.txtNombreLista);
	    TextView partido_actual = (TextView) vi.findViewById(R.id.txtTwitterLista);
	    final Button btn_perfil = (Button) vi.findViewById(R.id.btn_Perfil);
	    //imagenLista = (ImageView) vi.findViewById(R.id.imgFotoLista);
	    
	    nombre.setText(item.getNombre());
	    partido_actual.setText(item.getPartidoActual());
	    
	    imagenLista = (ImageView) vi.findViewById(R.id.imgFotoLista);
	    
	    String id = Long.toString(item.getId()); 
	    
	    String direccionUrl = "http://54.186.114.101:3000/photo_store/"+id+".jpg";
		Picasso.with(Fragmento.getActivity())
		.load(direccionUrl)
		.error(R.drawable.error_detail)
		.into(imagenLista, new Callback() {
			 @Override public void onSuccess() { } 
			 @Override public void onError() { } 
		 });
		
		Picasso.with(Fragmento.getActivity())
		.load(direccionUrl)
		.into(target);
	    
	    
	    btn_perfil.setOnClickListener(new OnClickListener()
	    {
			@Override
			public void onClick(View v) 
			{
				//Se revisa la conexión de internet
				ConnectivityManager cm = (ConnectivityManager)Fragmento.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
				 
				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				boolean isConnected = activeNetwork != null &&
				                      activeNetwork.isConnectedOrConnecting();
				
				if (isConnected){
					
					//Aquí se crea procedimiento para llamar al fragment de diputados al hacer clic
					FragmentManager fragmentManager = Fragmento.getActivity().getSupportFragmentManager();
					PerfilDiputadoFragment.codigo= item.getId();
					//Aquí se modifica el numero al textview dependiendo de su posicion
					Fragment fragment = new PerfilDiputadoFragment();
					fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();

				}
				else Toast.makeText(Fragmento.getActivity(), "Necesita conexión a internet para continuar",Toast.LENGTH_SHORT).show();
			}
	    	
	    });
	    
	    return vi;
	}
	
	private Target target = new Target() 
	{
		@Override
		public void onBitmapLoaded(final Bitmap arg0, LoadedFrom arg1) {			
			new Thread(new Runnable() 
			{
				 @Override
				 public void run() {
					 File file = new File(Environment.getExternalStorageDirectory().getPath() +"/actress_wallpaper.jpg");
					 try 
					 {
						 file.createNewFile();
						 FileOutputStream ostream = new FileOutputStream(file);
						 arg0.compress(CompressFormat.JPEG, 75, ostream);
						 ostream.close();
					 } 
					 catch (Exception e) 
					 {
						 e.printStackTrace();
					 }				 
				 }
			}).start();	
		}
		
		@Override
		public void onPrepareLoad(Drawable arg0) {			
			if (arg0 != null) 
			{}
		}
		
		@Override
		public void onBitmapFailed(Drawable arg0) {
			
		}		
	};
}
