package com.redciudadana.congreso_abierto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoticiasFragment extends Fragment {
	
	
	private View fragment = null;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		fragment = inflater.inflate(R.layout.noticias_fragment, container, false);
        
		return fragment;
		
	}
	
	
}
