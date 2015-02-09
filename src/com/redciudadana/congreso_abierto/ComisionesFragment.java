package com.redciudadana.congreso_abierto;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ComisionesFragment extends Fragment {
	
	public View fragment = null;
	public ProgressDialog pDialog= null;
	public static boolean  frag = false;
	public static long codigo;
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
		fragment = inflater.inflate(R.layout.comisiones_fragment, container, false);
		if(frag == false)ContenedorActivity.regreso = 1;
		else
		{
			PerfilDiputadoFragment.codigo = codigo;
			ContenedorActivity.regreso = 4;
		}
		try
		{
			pDialog = new ProgressDialog(getActivity());
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setMessage("Cargando...");
	        pDialog.setCancelable(true);
	        pDialog.setMax(100);
	        
	      //Pruebas para listview en estas pruebas
			ListaComisiones miTarea = new ListaComisiones();
			miTarea.execute();
		}
		catch(Exception e)
		{
		    // In your production code handle any errors and catch the individual exceptions
			Toast.makeText(getActivity(), "Error: " + e.toString() ,Toast.LENGTH_SHORT).show();
		}
		
		return fragment;
	}
	
	//Clase que toma los datos de la api del listado de Comisiones
	private class ListaComisiones extends AsyncTask<String,Integer,ArrayList<ItemLista>> 
	{
			@Override
			protected ArrayList<ItemLista> doInBackground(String... params) 
			{	
				// TODO Auto-generated method stub
				
				// Creamos nuevo cliente Http
				DefaultHttpClient defaultClient = new DefaultHttpClient();
				String URL = "http://54.186.114.101:3000/listado_comisiones.json";
				if(frag == true)
				{
					String cadena = Long.toString(codigo);
					URL = "http://54.186.114.101:3000/comisiones_diputado.json?id="+cadena;
					frag = false;
				}
				
				// Hacemos el llamado al metodo get y damos la url JSON
				 HttpGet httpGetRequest = new HttpGet(URL);
				 
				 try{
				
					httpGetRequest.setHeader("content-type", "application/json");
					    						
					//Hacemos la ejecución para obtener los datos JSON
					HttpResponse respuesta = defaultClient.execute(httpGetRequest);
					// Ahora convertimos la respuesta a un String
					String respuestaStr = EntityUtils.toString(respuesta.getEntity());
					    		    
					//Ahora lo convertimos a un arreglo JSON
					JSONArray respuestaJSON = new JSONArray(respuestaStr);
					//Creamos la cadena que vamos a devolver
					ArrayList<ItemLista> items = new ArrayList<ItemLista>();				
					    				    
					//Llenar arreglo String
					for(int i=0;i<respuestaJSON.length();i++)
					{
						//Creamos un objeto JSON para almacenar cada item 
					    JSONObject objeto = respuestaJSON.getJSONObject(i);
					    String nombreComision = objeto.getString("nombre");
					    long codigo = objeto.getLong("id");
					    items.add(new ItemLista(codigo,nombreComision));
					}
					
					return items;
				}
					    
				catch(Exception e){
					//Ahora creamos el arreglo String que vamos a devolver
					ArrayList<ItemLista> items = new ArrayList<ItemLista>();			
					items.add(new ItemLista(0,"Error en la conexión"));
					 return items;
				}			 
			}
			
			@Override
	        protected void onProgressUpdate(Integer... values) {
	            int progreso = values[0].intValue();
	 
	            pDialog.setProgress(progreso);
	        }
			
			@Override
	        protected void onPreExecute() 
			{
				pDialog.setOnCancelListener(new OnCancelListener() {
		            
		            public void onCancel(DialogInterface dialog) {
		                ListaComisiones.this.cancel(true);
		            }
		        });
	            pDialog.setProgress(0);
	            pDialog.show();
	        }
			
			protected void onPostExecute(ArrayList<ItemLista> result) 
			{
				
				pDialog.dismiss();
				
				ListView lv = (ListView)fragment.findViewById(R.id.listComisiones);
		        
				ArrayList<ItemLista> itemsCompra = result;
					         
				ItemListaAdapter adapter = new ItemListaAdapter(ComisionesFragment.this, itemsCompra);
				        
				lv.setAdapter(adapter);
				
				lv.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
					{
						//Aquí obtenemos el distrito que seleccionamos
						ItemLista elegido = (ItemLista) parent.getItemAtPosition(position);
						long codigo = elegido.getId();
						
						//Aquí se crea procedimiento para llamar al fragment de diputados al hacer clic
						DetalleComisionFragment.codigo =codigo;
										
						FragmentManager fragmentManager = getFragmentManager();
						Fragment fragmento = new DetalleComisionFragment();
						fragmentManager.beginTransaction().replace(R.id.container,fragmento).commit();					
					}
				});
				
		    }
			
			@Override
	        protected void onCancelled() {
				pDialog.dismiss();
	            Toast.makeText(getActivity(), "Tarea cancelada!",Toast.LENGTH_SHORT).show();
	        }
			
		}

}
