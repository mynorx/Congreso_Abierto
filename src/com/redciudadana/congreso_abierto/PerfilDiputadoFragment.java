package com.redciudadana.congreso_abierto;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilDiputadoFragment extends Fragment
{
	private View fragment = null;
	public static long codigo;
	public static long codigo_comision;
	public static CharSequence distrito;
	ProgressDialog pDialog= null;
	String telefono = null;
	String correo_diputado = null;
	String nombre = "Error en la conexión";
	long asistencia_diputado;
	public static boolean distritoFragment = false;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		try
		{
			if (distritoFragment == false)
			{
				DiputadosDistritoFragment.distrito = distrito;
				ContenedorActivity.regreso = 3;
			}
			
			else
			{
				DetalleComisionFragment.codigo = codigo_comision;
				ContenedorActivity.regreso = 6;
			}
			//Inflamos el fragment que se usará
			fragment = inflater.inflate(R.layout.perfil_diputado_fragment, container, false);
			//Declaramos y configuramos nuestro ProgressDialgo
			pDialog = new ProgressDialog(getActivity());
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setMessage("Cargando...");
	        pDialog.setCancelable(false);
	        pDialog.setMax(100);
			//Llenamos la lista con el listado de diputados del distrito seleccionado
			PerfilDiputado miTareados = new PerfilDiputado();
			miTareados.execute();
		}
		catch(Exception e)
		{Toast.makeText(getActivity(), "Error: " + e.toString() ,Toast.LENGTH_SHORT).show();}		
		return fragment;
	}
	
	//Clase AsyncTask que recoge los datos de la api
	private class PerfilDiputado extends AsyncTask<String,Integer,JSONArray>
	{
		@Override
		protected JSONArray doInBackground(String... params) 
		{
			// Creamos nuevo cliente Http
			DefaultHttpClient defaultClient = new DefaultHttpClient();
			// Hacemos el llamado al metodo get y damos la url JSON
			String cadena = Long.toString(codigo);
			String URL = "http://54.186.114.101:3000/id.json?id="+cadena;
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
			pDialog.setProgress(0);
			pDialog.show();
		}
		
		@Override
		protected void onPostExecute(JSONArray result) 
		{	
			pDialog.dismiss();		
			String distrito = "Listado Nacional";
			String partido = null;
			String id = null;
			String url_foto = "jpg";
			//String twitter_cuenta = "";
			//String facebook = "";
			
			Button llamar = (Button) fragment.findViewById(R.id.btnLlamarDiputado);
			Button correo = (Button) fragment.findViewById(R.id.btnCorreoDiputado);
			Button comisiones = (Button) fragment.findViewById(R.id.btnComisionesDiputado);
			Button asistencia = (Button) fragment.findViewById(R.id.btnAsistanciaDiputado);
			
			////Se revisa la conexión de internet
			ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			 
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			boolean isConnected = activeNetwork != null &&
			                      activeNetwork.isConnectedOrConnecting();
			
			if (isConnected)
			{				
				//Obtenemos los parametros String
				for(int i=0;i<result.length();i++)
				{
					//Creamos un objeto JSON para almacenar cada item
					JSONObject objeto;
					try 
					{
						
						objeto = result.getJSONObject(i);
						id = Long.toString(objeto.getLong("id")); 
						nombre = objeto.getString("nombre");
						distrito = objeto.getString("distrito");
						partido = objeto.getString("partido_actual");
						telefono = objeto.getString("telefono");
						correo_diputado = objeto.getString("correo");
						asistencia_diputado = objeto.getInt("asistencia");
						url_foto = objeto.getString("url_foto");
						//twitter_cuenta = objeto.getString("twitter");
						//facebook = objeto.getString("facebook");		
						
					}
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
				}
			}
			else{

				llamar.setEnabled(false);
				correo.setEnabled(false);
				comisiones.setEnabled(false);
				asistencia.setEnabled(false);
				Toast.makeText(getActivity(), "Necesita conexión a internet para ver la información",Toast.LENGTH_SHORT).show();
			}
			
			
			
			//Enviamos los parametros al layout			
			TextView txtNombre = (TextView) fragment.findViewById(R.id.txtNombreDiputado);
			txtNombre.setText(nombre);
			TextView txtDistrito = (TextView) fragment.findViewById(R.id.txtDistritoDiputado);
			if(distrito.equals("Diputado por Listado Nacional")) distrito = "Listado Nacional";
			txtDistrito.setText(distrito);
			TextView txtPartido = (TextView) fragment.findViewById(R.id.txtPartidoDiputado);
			txtPartido.setText(partido);
			
			
			llamar.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telefono)));						
			}});
				
			
			correo.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					
					enviar(correo_diputado);
				}});
	
			
			comisiones.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
				//Aquí se crea procedimiento para llamar al fragment de diputados al hacer clic
				FragmentManager fragmentManager = PerfilDiputadoFragment.this.getActivity().getSupportFragmentManager();
					
				ComisionesFragment.frag=true;
				ComisionesFragment.codigo = codigo;
					
				//Aquí se modifica el numero al textview dependiendo de su posicion
				Fragment fragment = new ComisionesFragment();
				fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
			}});
				
			
			asistencia.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog;
					alertDialog = new AlertDialog.Builder(getActivity()).create();
					alertDialog.setTitle(nombre);
					alertDialog.setMessage("Tiene: "+asistencia_diputado + " días de asistencia");
					alertDialog.show();
						
				}});
			
			ImageView fotoPerfil = (ImageView) fragment.findViewById(R.id.imgFotoDiputadoPerfil);
			
			String direccionUrl = "http://54.186.114.101:3000/photo_store/"+id+"."+url_foto;
			Picasso.with(getActivity())
			.load(direccionUrl)
			.error(R.drawable.error_detail)
			.into(fotoPerfil, new Callback() {
				 @Override public void onSuccess() { } 
				 @Override public void onError() { } 
			 });
			
			Picasso.with(getActivity())
			.load(direccionUrl)
			.into(target);
		
		}
			
		private void enviar(String Correo) {
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
		    emailIntent.setData(Uri.parse("mailto:"));
		    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Correo});
		    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Correo");
		    emailIntent.setType("message/rfc822");
		    startActivity(Intent.createChooser(emailIntent, "Email "));
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
			public void onBitmapFailed(Drawable arg0) {	}		
		};
		
	}
}
