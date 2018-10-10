package com.tfgp2p.tfg_p2p_nsp.Modelo;

import android.content.Context;

import com.tfgp2p.tfg_p2p_nsp.AlertException;
import com.tfgp2p.tfg_p2p_nsp.MyAlert;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by Julio on 09/04/2018.
 */
public class Amigos {

	private static String myName;

	// TODO: ¿Cuáles son los pasos para añadir un amigo?
	// TODO: Implementar el guardado de amigos en un fichero o base de datos local.
	// TODO: Implementar la carga de amigos.

	// Colección de amigos que contiene nombres, direcciones y puertos remotos.
	// TODO: Quitar dirección de los amigos y poner alguna clave aleatoria.
	private HashMap<String, InetSocketAddress> friendsMap;

	private static Amigos amigos = null;

	// TODO: Debería ser final, pero en android no me permite dejarla en blanco e inicializarla en el constructor por ejemplo...
	private static InetSocketAddress udpServerInfo;
	private static InetSocketAddress tcpServerInfo;

	private Context context;



	public static Amigos getInstance(Context c){
		if (amigos == null)
			amigos = new Amigos(c);
		return amigos;
	}


	private Amigos(Context c){
		context = c;
		/* De momento ponemos nuestro nombre desde el código. Habría que poner una opción en la
		 * configuración de la aplicación. Tb deberíamos hacer que el usuario introduzca su nombre
		 * la primera vez que ejecuta el programa.
		 */
		myName = "Pedro";
		//myName = "Manolito";

		// Hasta 16 amigos por defecto.
		this.friendsMap = new HashMap<>();
		try {
			udpServerInfo = new InetSocketAddress(Inet4Address.getByName());
			tcpServerInfo = new InetSocketAddress(Inet4Address.getByName());
		} catch (UnknownHostException e){e.printStackTrace();}
	}


	public void addFriend(String name, InetAddress addr, int port) throws AlertException {
		//if (!this.friendsMap.containsKey(name)) {
			InetSocketAddress iaddr = new InetSocketAddress(addr, port);
			this.friendsMap.put(name, iaddr);
		//}
		//else throw new MyAlert(name + " ya existe, introduce otro nombre o modifica el antiguo amigo.", context);
	}


	public void updateFriendName(String name, String newName) throws AlertException {
		if (this.friendsMap.containsKey(name)){
			InetSocketAddress addr = this.friendsMap.get(name);
			this.friendsMap.remove(name);
			this.friendsMap.put(newName, addr);
		}
		else throw new AlertException(name + " no existe.", context);
	}


	public void updateFriendAddr(String name, InetSocketAddress newAddr) throws AlertException {
		if (this.friendsMap.containsKey(name)){
			this.friendsMap.remove(name);
			this.friendsMap.put(name, newAddr);
		}
		else throw new AlertException(name + " no existe.", context);
	}


	public void removeFriend(String name) throws AlertException {
		if (this.friendsMap.containsKey(name)){
			this.friendsMap.remove(name);
		}
		else throw new AlertException(name + " no existe.", context);
	}


	public HashMap<String, InetSocketAddress> getFriendsMap(){
		return this.friendsMap;
	}


	public InetSocketAddress getFriendAddr(String name) throws AlertException {
		if (this.friendsMap.containsKey(name)){
			return this.friendsMap.get(name);
		}
		else throw new AlertException(name + " no existe.", context);
	}


	public boolean isFriend(String name, InetAddress addr){
		try {
			InetAddress localAddr = friendsMap.get(name).getAddress();
			return localAddr.equals(addr);
		}
		catch (NullPointerException e){
			return false;
		}
	}


	public static String getMyName(){
		return myName;
	}


	public static void setMyName(String newname){
		myName = newname;
	}


	public static InetSocketAddress getUdpServerInfo(){
		return udpServerInfo;
	}
	public static InetSocketAddress getTcpServerInfo(){
		return tcpServerInfo;
	}


}
