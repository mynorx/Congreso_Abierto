package com.redciudadana.congreso_abierto;

public class ItemLista 
{
	protected long id; //Guarda Id del item
	protected String nombre; //Guarda atributo nombre de itemo
	
	public ItemLista()
	{//Constructor 1 -- no pide parametros
	    this.nombre = "";
	}
	     
	public ItemLista(long id, String nombre) 
	{ //Constructor 2 -- parametros
	    this.id = id;
	    this.nombre = nombre;
	}
	     
	public long getId() 
	{//Metodo para mostrar parametro Id
		return id;
	}
	     
	public void setId(long id) 
	{//Metodo para registrar parametro Id
	    this.id = id;
	}
	     
	public String getNombre() 
	{//Metodo para mostra parametro Nombre
	    return nombre;
	}
	     
	public void setNombre(String nombre) 
	{//Metodo para registrar parametro Nombre
	    this.nombre = nombre;
	}
}
