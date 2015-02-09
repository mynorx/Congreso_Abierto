package com.redciudadana.congreso_abierto;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

public class ContenedorActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks 
{
	//Se crea una instancia de la clase NavigationDrawer que fue creada y que extiende a la fragment para que se incorpore al contenedor
	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	//Variable que indica la posision para el boton de regreso
	public static int regreso; 
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	
	private CharSequence mTitle; //Variable que contendrá Haruki-Murakamiel titulo de cada opción y que se irá cambiando. 
	
	//Funcion para controlar el boton regreso
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	
        	Fragment fragment;
        	FragmentManager fragmentManager = getSupportFragmentManager();
        	switch (regreso)
        	{
        	case 0:
        		ContenedorActivity.this.finish();
        		break;
        	case 1:
        		
        		fragment = new InicioFragment();
        		fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        		break;
        	case 2:
        		
        		fragment = new DiputadosFragment();
        		fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        		break;
        	case 3:
        		fragment = new DiputadosDistritoFragment();
        		fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        		break;
        	case 4:
        		fragment = new PerfilDiputadoFragment();
        		fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        		break;
        	case 5:
        		fragment = new ComisionesFragment();
        		fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        		break;
        	case 6:
        		fragment = new DetalleComisionFragment();
        		fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        	}
        	      
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override //Comienza funcion onCreate de la Actividad Contenedor
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contenedor);
		//Se asígna la instancia con el fragmente de NavigationDrawer
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);	
		mTitle = getTitle();
		
		//Se llama a la funcion setUp para la configuración del NavigationDrawer
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		
		
		
		
				
	}//Fin de funcion onCreate de la Actividad Contenedor 

	
	public void setActionBarTitle(String title)
	{
		   
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) 
	{	
		// actualizar el contenido principal, sustituyendo los fragmentos
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		//Aquí se modifica el numero al textview dependiendo de su posicion
		Fragment fragment = null;
	
		fragmentManager.beginTransaction().replace(R.id.container,PlaceholderFragment.newInstance(position + 1)).commit();
		//Creamos la instancia que nos servirá para agregar el fragment de cada opcion
		switch (position)
		{
		case 0:
			fragment = new InicioFragment();
			break;
			
		case 1:
			fragment = new DiputadosFragment();
			break;
			
		case 2:			
			fragment = new ComisionesFragment();
			break;
			
		case 3:
			fragment = new TuVozFragment();
			break;
			
		case 4:
			fragment = new ActividadesFragment();
			break;
			
		case 5:
			fragment = new RedCiudadanaFragment();
			break;
			
		case 6:
			fragment = new ContactoFragment();
			break;
		}
		fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
	}

	public void onSectionAttached(int number) {
		
		switch (number) { //Aquí se cambia el titulo en la action bar cuando se elecciona una opcion
		case 1:
			mTitle = getString(R.string.opcion0);//Inicio			
			break;
		case 2:
			mTitle = getString(R.string.opcion1);//Distritos			
			break;
		case 3:
			mTitle = getString(R.string.opcion2);//Comisiones
			break;
		case 4:
			mTitle = getString(R.string.opcion3);//Tu voz cuenta
			break;
		case 5:
			mTitle = getString(R.string.opcion4);//Actividades
			break;
		case 6:
			mTitle = getString(R.string.opcion5);//Red Ciudadana
			break;
		case 7:
			mTitle = getString(R.string.opcion6);//Contáctanos
			break;
		}
	}

	public void restoreActionBar() 
	{
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.contenedor, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		
		return super.onOptionsItemSelected(item);
	}	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment 
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) 
		{
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{ 
			View rootView = inflater.inflate(R.layout.fragment_contenedor, container, false);//Inflamos y llenamos con el nuevo fragmento 
			
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) 
		{
			super.onAttach(activity);
			((ContenedorActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

}
