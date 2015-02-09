package com.redciudadana.congreso_abierto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class Banner1Fragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View fragment = inflater.inflate(R.layout.banner1_fragment, container, false);
		//Detectamos el estado de la rotacion
		WindowManager wm = getActivity().getWindowManager();
		Display d = wm.getDefaultDisplay();
				
		if ((d.getRotation() == Surface.ROTATION_90) || (d.getRotation() == Surface.ROTATION_270))
		{fragment = inflater.inflate(R.layout.banner1_fragment_h, container, false);}
		
		return fragment;
	}
}
