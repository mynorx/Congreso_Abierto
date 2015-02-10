package com.redciudadana.congreso_abierto;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DiputadosDistritoFragment extends Fragment
{
	ListView lv = null;
	private View fragment = null;
	private ProgressDialog pDialog= null;
	public static CharSequence distrito;
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
		ft.hide(miBanner2);
		ft.show(miBanner3);
		ft.commit();
		
		try
		{
			ContenedorActivity.regreso = 2;
			PerfilDiputadoFragment.distritoFragment = false;
			
			
			
			//Inflamos el fragment que se usará
			fragment = inflater.inflate(R.layout.diputados_distrito_fragment, container, false); 
			TextView t = (TextView) fragment.findViewById(R.id.txtDiputadosDistrito);
			
			t.setText(distrito);
			
			//Valor que damos para la busqueda de la api
			if(distrito == "Listado Nacional") distrito = "Diputado por Listado Nacional";
			//Enviamos el distrito al perfil para usarlo con el boton de regreso
			PerfilDiputadoFragment.distrito = distrito;
			//Declaramos y configuramos nuestro ProgressDialgo
			pDialog = new ProgressDialog(getActivity());
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setMessage("Cargando...");
	        pDialog.setCancelable(false);
	        pDialog.setMax(100);
	        
	        //Llenamos la lista con el listado de diputados del distrito seleccionado
	        ListaDiputadosComision miTarea = new ListaDiputadosComision();
			miTarea.execute();
			
		}
		catch(Exception e)
		{
			Toast.makeText(getActivity(), "Error: " + e.toString() ,Toast.LENGTH_SHORT).show();
		}		
		return fragment;
	}
	
	//Clase AsincTask que recoge los datos de la api
	private class ListaDiputadosComision extends AsyncTask<String,Integer,ArrayList<ItemDiputados>> 
	{
		@Override
		protected ArrayList<ItemDiputados> doInBackground(String... params) 
		{
			// Creamos nuevo cliente Http
			DefaultHttpClient defaultClient = new DefaultHttpClient();
			// Hacemos el llamado al metodo get y damos la url JSON
			String cadena = distrito.toString(); 
			cadena = cadena.replace(" ", "%20");
			String URL = "http://54.186.114.101:3000/distrito.json?distrito="+cadena;
			// Hacemos el llamado al metodo get y damos la url JSON
			HttpGet httpGetRequest = new HttpGet(URL);
			try
			{
				httpGetRequest.setHeader("content-type", "application/json");
				//Hacemos la ejecución para obtener los datos JSON
				HttpResponse respuesta = defaultClient.execute(httpGetRequest);
				//Ahora convertimos la respuesta a un String
				String respuestaStr = EntityUtils.toString(respuesta.getEntity());  		    
				//Ahora lo convertimos a un arreglo JSON
				JSONArray respuestaJSON = new JSONArray(respuestaStr);
				//Creamos la cadena que vamos a devolver
				ArrayList<ItemDiputados> items = new ArrayList<ItemDiputados>();				
					    				    
				//Llenar arreglo String
				for(int i=0;i<respuestaJSON.length();i++)
				{
					//Creamos un objeto JSON para almacenar cada item
					JSONObject objeto = respuestaJSON.getJSONObject(i);
					long id = objeto.getLong("id");
					String nombre_diputado = objeto.getString("nombre");
					String partido_actual = objeto.getString("partido_actual");
					String url_foto = objeto.getString("url_foto");
					items.add(new ItemDiputados(id,nombre_diputado,partido_actual,url_foto));
				}
					
				return items;
			}
			
			catch(Exception e)
			{
				//Ahora creamos el arreglo String que vamos a devolver
				ArrayList<ItemDiputados> items = new ArrayList<ItemDiputados>();			
				items.add(new ItemDiputados(0,"Error en la conexión","",""));
				return items;
			}			 
		}
			
		@Override
	    protected void onProgressUpdate(Integer... values) 
		{
			int progreso = values[0].intValue();
	        pDialog.setProgress(progreso);
	    }
			
		@Override
	    protected void onPreExecute() 
		{
			pDialog.setOnCancelListener(new OnCancelListener() 
			{
				public void onCancel(DialogInterface dialog) 
				{
					ListaDiputadosComision.this.cancel(true);
		        }
		    });
			pDialog.setProgress(0);
	        pDialog.show();
	    }
			
		protected void onPostExecute(ArrayList<ItemDiputados> result) 
		{
			pDialog.dismiss();
			lv = (ListView)fragment.findViewById(R.id.listDiputadosDistrito);
			ArrayList<ItemDiputados> itemsDiputados = result;					     
			ItemDiputadosAdapter adapter = new ItemDiputadosAdapter(DiputadosDistritoFragment.this, itemsDiputados);				   
			lv.setAdapter(adapter);
			
			//Se revisa la conexión de internet
			ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			 
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			boolean isConnected = activeNetwork != null &&
			                      activeNetwork.isConnectedOrConnecting();
			
			if (isConnected == false)
			{
				lv.setVisibility(View.INVISIBLE);
				Toast.makeText(getActivity(), "Necesita conexión a internet para continuar",Toast.LENGTH_SHORT).show();
			}
		}
			
		@Override
	    protected void onCancelled() 
		{
			Toast.makeText(getActivity(), "Tarea cancelada!",Toast.LENGTH_SHORT).show();
	    }			
	}
}
