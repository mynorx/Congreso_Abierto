package com.redciudadana.congreso_abierto;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleComisionFragment extends Fragment{
	
	private View fragment = null;
	public static long codigo;
	private ProgressDialog pDialog= null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		try
		{
			ContenedorActivity.regreso = 5;
			PerfilDiputadoFragment.codigo_comision = codigo;
			PerfilDiputadoFragment.distritoFragment = true;
			//Codigo para llenar automaticamente el listView
			fragment = inflater.inflate(R.layout.detalle_comision_fragment, container, false);
			
			//Declaramos y configuramos nuestro ProgressDialgo
			pDialog = new ProgressDialog(getActivity());
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setMessage("Cargando...");
	        pDialog.setCancelable(true);
	        pDialog.setMax(100);
	      //Llenamos la lista con el listado de diputados del distrito seleccionado
	        DetalleComision miTarea = new DetalleComision();
			miTarea.execute();		        
			return fragment;
		}
		catch(Exception e)
		{
			return null;
		}		
	}
	
	//Clase AsincTask que recoge los datos de la api
	private class DetalleComision extends AsyncTask<String,Integer,JSONArray> 
	{
				@Override
				protected JSONArray doInBackground(String... params) 
				{
					// Creamos nuevo cliente Http
					DefaultHttpClient defaultClient = new DefaultHttpClient();
					// Hacemos el llamado al metodo get y damos la url JSON
					String cadena = Long.toString(codigo);
					String URL = "http://54.186.114.101:3000/comision_id.json?id="+cadena;
					// Hacemos el llamado al metodo get y damos la url JSON
					HttpGet httpGetRequest = new HttpGet(URL);
					//Variable Respuesta en Formato JSON
					JSONArray respuestaJSON = null;
					try
					{
						httpGetRequest.setHeader("content-type", "application/json");
						//Hacemos la ejecución para obtener los datos JSON
						HttpResponse respuesta = defaultClient.execute(httpGetRequest);
						//Ahora convertimos la respuesta a un String
						String respuestaStr = EntityUtils.toString(respuesta.getEntity());  		    
						//Ahora lo convertimos a un arreglo JSON
						respuestaJSON = new JSONArray(respuestaStr);
						//Creamos la cadena que vamos a devolver						
						return respuestaJSON;
					}
					catch(Exception e)
					{
						return respuestaJSON;
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
							DetalleComision.this.cancel(true);
				        }
				    });
					
					pDialog.setProgress(0);
			        pDialog.show();
			    }
					
				protected void onPostExecute(JSONArray result) 
				{	
					pDialog.dismiss();
				
					String nombre = "Error en la conexión";
					String descripcion = "S/D";
					String direccion = "";
					String telefono = "";
					
					//Obtenemos los parametros String
					for(int i=0;i<result.length();i++)
					{
						//Creamos un objeto JSON para almacenar cada item
						JSONObject objeto;
						try 
						{
							objeto = result.getJSONObject(i);
							
							nombre = objeto.getString("nombre");
							descripcion = objeto.getString("descripcion");
							direccion = objeto.getString("direccion");
							telefono = objeto.getString("telefono");
							//twitter_cuenta = objeto.getString("twitter");
							//facebook = objeto.getString("facebook");
							//url_foto = objeto.getString("url_foto");							
						}
						catch (JSONException e) 
						{
							e.printStackTrace();
						}
					}
					//Enviamos los parametros al layout
					TextView txtNombre = (TextView) fragment.findViewById(R.id.txtNombreComision);
					txtNombre.setText(nombre);
					TextView txtDescripcion = (TextView) fragment.findViewById(R.id.txtDescripcionComision);
					txtDescripcion.setText(descripcion);
					TextView txtDireccion = (TextView) fragment.findViewById(R.id.txtDireccionComision);
					txtDireccion.setText(direccion);
					TextView txtTelefono = (TextView) fragment.findViewById(R.id.txtTelefonoComision);
					txtTelefono.setText(telefono);
					
					TextView txtMiembros = (TextView) fragment.findViewById(R.id.txtMiembroComisionTitulo);
					txtMiembros.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ListaMiembrosComision miTarea = new ListaMiembrosComision();
							miTarea.execute();
						}
						
					});
				}
					
				@Override
			    protected void onCancelled() 
				{
					Toast.makeText(getActivity(), "Tarea cancelada!",Toast.LENGTH_SHORT).show();
			    }
					
			}
	
	//Clase AsincTask que recoge los datos de la api
		private class ListaMiembrosComision extends AsyncTask<String,Integer,ArrayList<ItemDiputados>> 
		{
			@Override
			protected ArrayList<ItemDiputados> doInBackground(String... params) 
			{
				// Creamos nuevo cliente Http
				DefaultHttpClient defaultClient = new DefaultHttpClient();
				// Hacemos el llamado al metodo get y damos la url JSON
				String cadena = Long.toString(codigo);
				String URL = "http://54.186.114.101:3000/miembros_comision.json?id="+cadena;
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
						ListaMiembrosComision.this.cancel(true);
			        }
			    });
				
				pDialog.setProgress(0);
		        pDialog.show();
		    }
				
			protected void onPostExecute(ArrayList<ItemDiputados> result) 
			{
				pDialog.dismiss();
				ListView lv = (ListView)fragment.findViewById(R.id.listMiembrosComision);
				ArrayList<ItemDiputados> itemsDiputados = result;					     
				ItemDiputadosAdapter adapter = new ItemDiputadosAdapter(DetalleComisionFragment.this, itemsDiputados);				   
				lv.setAdapter(adapter);			
			}
				
			@Override
		    protected void onCancelled() 
			{
				Toast.makeText(getActivity(), "Tarea cancelada!",Toast.LENGTH_SHORT).show();
		    }
				
		}

}
