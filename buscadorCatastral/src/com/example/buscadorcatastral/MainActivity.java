package com.example.buscadorcatastral;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	private LinkedList<Provincia> provincias;
	private LinkedList<Municipio> municipios;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//spiner es el select desplegable
		Spinner spinnerProvincia = (Spinner) findViewById(R.id.spinnerProvincias);
		//Creamos la lista
		provincias = new LinkedList<Provincia>();
		//La poblamos con las provincias
		provincias.add(new Provincia("15","A CORUÑA"));
		provincias.add(new Provincia("03","ALACANT"));
		provincias.add(new Provincia("02","ALBACETE"));
		provincias.add(new Provincia("04","ALMERIA"));
		provincias.add(new Provincia("33","ASTURIAS"));
		provincias.add(new Provincia("05","AVILA"));
		provincias.add(new Provincia("06","BADAJOZ"));
		provincias.add(new Provincia("08","BARCELONA"));
		provincias.add(new Provincia("09","BURGOS"));
		provincias.add(new Provincia("10","CACERES"));
		provincias.add(new Provincia("11","CADIZ"));
		provincias.add(new Provincia("39","CANTABRIA"));
		provincias.add(new Provincia("12","CASTELLO"));
		provincias.add(new Provincia("51","CEUTA"));
		provincias.add(new Provincia("13","CIUDAD REAL"));
		provincias.add(new Provincia("14","CORDOBA"));
		provincias.add(new Provincia("16","CUENCA"));
		provincias.add(new Provincia("17","GIRONA"));
		provincias.add(new Provincia("18","GRANADA"));
		provincias.add(new Provincia("19","GUADALAJARA"));
		provincias.add(new Provincia("21","HUELVA"));
		provincias.add(new Provincia("22","HUESCA"));
		provincias.add(new Provincia("07","ILLES BALEARS"));
		provincias.add(new Provincia("23","JAEN"));
		provincias.add(new Provincia("26","LA RIOJA"));
		provincias.add(new Provincia("35","LAS PALMAS"));
		provincias.add(new Provincia("24","LEON"));
		provincias.add(new Provincia("25","LLEIDA"));
		provincias.add(new Provincia("27","LUGO"));
		provincias.add(new Provincia("28","MADRID"));
		provincias.add(new Provincia("29","MALAGA"));
		provincias.add(new Provincia("52","MELILLA"));
		provincias.add(new Provincia("30","MURCIA"));
		provincias.add(new Provincia("32","OURENSE"));
		provincias.add(new Provincia("34","PALENCIA"));
		provincias.add(new Provincia("36","PONTEVEDRA"));
		provincias.add(new Provincia("38","S.C. TENERIFE"));
		provincias.add(new Provincia("37","SALAMANCA"));
		provincias.add(new Provincia("40","SEGOVIA"));
		provincias.add(new Provincia("41","SEVILLA"));
		provincias.add(new Provincia("42","SORIA"));
		provincias.add(new Provincia("43","TARRAGONA"));
		provincias.add(new Provincia("44","TERUEL"));
		provincias.add(new Provincia("45","TOLEDO"));
		provincias.add(new Provincia("46","VALENCIA"));
		provincias.add(new Provincia("47","VALLADOLID"));
		provincias.add(new Provincia("49","ZAMORA"));
		provincias.add(new Provincia("50","ZARAGOZA"));
		//Creamos el adaptador
		ArrayAdapter<Provincia> spinnerAdapterProvincias = new ArrayAdapter<Provincia>(this, android.R.layout.simple_spinner_item, provincias);
		//Añadimos el layout para el menú y se lo damos al spinner
		spinnerAdapterProvincias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerProvincia.setAdapter(spinnerAdapterProvincias);
		//Se le añade el evento on onItemSelected y onNothingSelected
		spinnerProvincia.setOnItemSelectedListener(this);//*/
	}//Fin onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}//Fin onCreateOptionsMenu

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,long arg3) {
		// TODO Auto-generated method stub
		if (parent.getId() == R.id.spinnerProvincias) {
			String temp = ((Provincia) parent.getItemAtPosition(pos)).toString();
			getMunicipios( temp );
		}
	}//Fin onItemSelected

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}//Fin onNothingSelected
	
	public void getMunicipios(String provincia){
		//"http://ovc.catastro.meh.es/ovcservweb/OVCSWLocalizacionRC/OVCCallejeroCodigos.asmx/ConsultaProvincia"
		try {
			provincia = URLEncoder.encode(provincia, "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error al construrir la URL");
			e1.printStackTrace();
		}
		String url = "http://ovc.catastro.meh.es/ovcservweb/OVCSWLocalizacionRC/OVCCallejero.asmx/ConsultaMunicipio?Provincia="+provincia+"&Municipio=";
		XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
		    InputStream in_s = new URL(url).openStream();
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            municipios = parseXML(parser);
            
            //spiner municipios
    		Spinner spinnerMunicipios = (Spinner) findViewById(R.id.spinnerMunicipios);
    		//Creamos la lista
    		//municipios = new LinkedList<Municipio>();
    		//municipios.add(new Municipio("12","Agramunt"));
    		//municipios.add(new Municipio("12","puigvred d'Agramunt"));
    		//Creamos el adaptador
    		ArrayAdapter<Municipio> spinnerAdapterMunicipios = new ArrayAdapter<Municipio>(this, android.R.layout.simple_spinner_item, municipios);
    		//Añadimos el layout para el menú y se lo damos al spinner
    		spinnerAdapterMunicipios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		spinnerMunicipios.setAdapter(spinnerAdapterMunicipios);
    		
    		//Se le añade el evento on onItemSelected y onNothingSelected
    		spinnerMunicipios.setOnItemSelectedListener(this);//*/
            
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Importante");
			alertDialog.setMessage("Connección a internet impossible.");
			alertDialog.setPositiveButton("OK",null);
			alertDialog.create();
			alertDialog.show(); 
			e.printStackTrace();
		}
		
	}//Fin getMunicipios
	
	public LinkedList<Municipio> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException{
		LinkedList<Municipio> municipios = null;
        int eventType = parser.getEventType();
        Municipio currentmunicipio = null;
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	municipios = new LinkedList<Municipio>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("muni") ){
                    	//System.out.println("Dentro muni");
                        currentmunicipio = new Municipio("","");
                    	}
                    else{
                		 if (name.equals("nm") ){
                             currentmunicipio.nombre = parser.nextText();
                             //System.out.println(currentmunicipio.nombre);
                		 	}
                		 if (name.equals("cmc") ){
                			 currentmunicipio.id = parser.nextText();
                             //System.out.println(currentmunicipio.id);
                		 	}
                		}
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("muni")){
                    	municipios.add(currentmunicipio);
                    } 
            	}
            eventType = parser.next();
        }
        //printMunicipios(municipios);
        return municipios;
	}//Fin parseXML

	private void printMunicipios(LinkedList<Municipio> municipios){
		String content = "";
		Iterator<Municipio> it = municipios.iterator();
		while(it.hasNext()){
			Municipio currMunicipio  = it.next();
			content = content+currMunicipio.nombre + "\n";
		}
		System.out.println(content);
	}//Fin printMunicipios
	
	public void getCordenadasRC(View view) {
		//Municipio	//Provincia //RC //SRS	
		/*$_REQUEST['Municipio'] = "agramunt";
		$_REQUEST['Provincia'] = "Lleida"; 
		$_REQUEST['RC'] = "25003A01100011";
		$_REQUEST['SRS'] = "EPSG:4326"; //*/ 
		String SRS = "EPSG:4326";
		
		//Preparamos la provincia
		Spinner spinnerProvincia = (Spinner) findViewById(R.id.spinnerProvincias);
		String nombreProvincia = spinnerProvincia.getSelectedItem().toString();
		Provincia lProvincia = null;
		for ( Provincia item : provincias){
			if (item.nombre == nombreProvincia){
				lProvincia = item;
				break;	
			}	
		}
		
		
		try {
			nombreProvincia = URLEncoder.encode(nombreProvincia,"ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error al construrir la nombreProvincia");
			e1.printStackTrace();
		}
		
		//Preparamos el municipio
		Spinner spinnerMunicipio = (Spinner) findViewById(R.id.spinnerMunicipios);
		String nombreMunicipio = spinnerMunicipio.getSelectedItem().toString();
		Municipio lMunicipio = null;
		for ( Municipio item : municipios){
			if (item.nombre == nombreMunicipio){
				lMunicipio = item;
				break;	
			}	
		}
		try {
			//nombreMunicipio = nombreMunicipio.replace("Ñ", "%D1");
			nombreMunicipio = URLEncoder.encode( nombreMunicipio ,"ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			System.out.println("Error al construrir la URL: parámetro lMunicipio");
			e1.printStackTrace();
		}
		
		while( lMunicipio.id.length() < 3 ){
			lMunicipio.id = "0" + lMunicipio.id;
		}
		//Preparamos poligono
		EditText lEditTextPoligono = (EditText) findViewById(R.id.editTextPoligono);
		String lPoligono = lEditTextPoligono.getText().toString();
		while( lPoligono.length() < 3 ){
			lPoligono = "0" + lPoligono;
		}
		//Preparamos poligono
		EditText lEditTextParcela = (EditText) findViewById(R.id.editTextParcela);
		String lParcela = lEditTextParcela .getText().toString();
		while( lParcela .length() < 5 ){
			lParcela = "0" + lParcela ;
		}
		
		String RC = lProvincia.id + lMunicipio.id + "A" + lPoligono + lParcela;
		
		
		String url = "http://ovc.catastro.meh.es/ovcservweb/OVCSWLocalizacionRC/OVCCoordenadas.asmx/Consulta_CPMRC?Municipio="+nombreMunicipio+"&Provincia="+nombreProvincia +"&RC="+RC+"&SRS="+SRS;
		
		
		System.out.println( url );
		
		String temp="";
		XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
		    InputStream in_s = new URL(url).openStream();
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            
            Cordenadas cordenadas;
            cordenadas = parseXMLConsultaRC(parser);
            temp = cordenadas.x+" "+cordenadas.y;
            
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			temp = "Error en la red";
			e.printStackTrace();
		} catch (Exception e) {
			temp = e.getMessage();
			e.printStackTrace();
		}
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Importante");
		alertDialog.setMessage(temp);
		alertDialog.setPositiveButton("OK",null);
		alertDialog.create();
		alertDialog.show();        
		return ;
	}//Fin getCoordenadasR
	 
	public Cordenadas parseXMLConsultaRC(XmlPullParser parser) throws XmlPullParserException,Exception{
        int eventType = parser.getEventType();
        Cordenadas cordenadas = new Cordenadas();
        
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("des") ){
                    	//descripción del error
                    	String text = parser.nextText();
                    	System.out.println(text);
                    	throw new Exception(text);
                    	}
                    if (name.equals("xcen") ){
                    	String text = parser.nextText();
                    	System.out.println(text);
                    	cordenadas.x = text;
                    	}
                    if (name.equals("ycen") ){
                    	String text = parser.nextText();
                    	System.out.println(text);
                    	cordenadas.y = text;
                    	}
                   
                //case XmlPullParser.END_TAG:
                    //    name = parser.getName();
                    //if (name.equalsIgnoreCase("muni")){
                    //} 
            	}
            eventType = parser.next();
        }
        //printMunicipios(municipios);
        return cordenadas;
	}//Fin parseXMLConsultaRC
	
	public class Provincia{
		String id;
		String nombre;
		//Constructor
		public Provincia(String id, String nombre) {
			super();
			this.id = id;
			this.nombre = nombre;
		}
		@Override
		public String toString() {
			return nombre;
		}
		public String getId() {
			return id;
		}
	}//Fin clase Provincia
	
	public class Municipio{
		String id;
		String nombre;
		//Constructor
		public Municipio(String id, String nombre) {
			super();
			this.id = id;
			this.nombre = nombre;
		}
		@Override
		public String toString() {
			return nombre;
		}
		public String getId() {
			return id;
		}
		public void setNombre(String l_nombre){
			this.nombre = l_nombre;
		}//Fin setNombre
	}//Fin clase Municipio
	
	public class Cordenadas{
		public String x;
		public String y;
	}//Fin Cordenadas
	
}

