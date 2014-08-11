package com.redciudadana.congreso_abierto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BannerFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View fragment = inflater.inflate(R.layout.banner_fragment, container, false);
        ImageView img_Banner = (ImageView)fragment.findViewById(R.id.img_Banner);

        img_Banner.setOnClickListener(new OnClickListener()
        {
			@Override
		    public void onClick(View v) 
			{
				//Aquí se crea procedimiento para llamar al fragment de diputados al hacer clic
				FragmentManager fragmentManager = getFragmentManager();
				
				//Aquí se modifica el numero al textview dependiendo de su posicion
				Fragment fragment = null;
				fragment = new DiputadosFragment();
				fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
			}
		});
        
		return fragment;
	}	
}
