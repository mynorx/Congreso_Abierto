package com.redciudadana.congreso_abierto;

public class ItemDiputados 
{
	//Creamos las variables
	protected long id;
	protected String nombre;
	protected String partido_actual;
	protected String url_foto;
	
	//Constructor
	public ItemDiputados()
	{//Constructor 1 -- no pide parametros
	    this.nombre = "";
	    this.partido_actual = "";
	    this.url_foto = "";
	}
	     
	public ItemDiputados(long id, String nombre, String twitter_cuenta, String url_foto) 
	{ //Constructor 2 -- parametros
	    this.id = id;
	    this.nombre = nombre;
	    this.partido_actual = twitter_cuenta;
	    this.url_foto = url_foto;
	}
	
	//Métodos y funciones
	public long getId() 
	{//Metodo para mostrar parametro Id
		return id;
	}
	     
	public void setId(long id) 
	{//Metodo para registrar parametro Id
	    this.id = id;
	}
	     
	public String getNombre() 
	{//Metodo para mostra parametro nombre
	    return nombre;
	}
	     
	public void setNombre(String nombre) 
	{//Metodo para registrar parametro nombre
	    this.nombre = nombre;
	}
	
	public String getPartidoActual()
	{//Metodo para mostrar parametro partido_actual
		return partido_actual;
	}
	
	public void setPartidoActual(String partido_actual)
	{//Metodo para registrar parametro partido_actual
		this.partido_actual = partido_actual;
	}
	
	public String getUrl()
	{//Metodo para mostrar parametro url_foto
		return url_foto;
	}
	
	public void setUrl(String url)
	{//Metodo para registra parametro url_foto
		this.url_foto = url;
	}
}
