package com.redciudadana.congreso_abierto;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ItemNoticia 
{
	private long id;
	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    private String titulo;
    private String link;
    private Bitmap imagen;
    private String descripcion;
    private Date fecha;
    
    public ItemNoticia()
	{//Constructor 1 -- no pide parametros
	    this.titulo= "";
	    this.link = "";
	    this.descripcion= "";
	    this.fecha = null;
	}
	     
	public ItemNoticia(long id, String titulo, String link, String descripcion) 
	{ //Constructor 2 -- parametros
	    this.id = id;
	    this.titulo = titulo;
	    this.link = link;
	    //this.imagen = imagen;
	    this.descripcion = descripcion;
	    //this.fecha = fecha;
	}
	
	public long getId() 
	{//Metodo para mostrar parametro Id
		return id;
	}
	     
	public void setId(long id) 
	{//Metodo para registrar parametro Id
	    this.id = id;
	}
	
	public String getTitulo()
	{//Metodo para mostrar parametro titulo
		return titulo;
	}
	
	public void setTitulo(String titulo)
	{//Metodo para registrar parametro Titulo
		this.titulo = titulo;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
    
	public Bitmap getImagen()
	{
		return imagen;
	}
	
	public void setImagen(String imagen){
        if (imagen.contains("autor")) {imagen = "http://abs.twimg.com/sticky/default_profile_images/default_profile_4_200x200.png";}
        try {this.imagen = loadFromUrl(new URL(imagen));} 
        catch (Exception e) 
        {
        	try {this.imagen = loadFromUrl(new URL("http://abs.twimg.com/sticky/default_profile_images/default_profile_4_200x200.png"));}
        	catch (MalformedURLException e1) {}
        }
    }
	
	public String getDescipcion()
	{
		return descripcion;
	}
	
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	
	public String getFecha()
	{
		return FORMATTER.format(this.fecha);
	}
    
	public void setFecha(String fecha)
	{
		try {this.fecha = FORMATTER.parse(fecha);}
		catch (java.text.ParseException e) {e.printStackTrace();}
	}
	
	private Bitmap loadFromUrl(URL link) {
        Bitmap bitmap = null;
        InputStream in = null;
        try 
        {
            in = link.openConnection().getInputStream();
            bitmap = BitmapFactory.decodeStream(in, null, null);
            in.close();
        }
        catch (IOException e) {}
        return bitmap;
    }
	
}
