package util;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

public class UtilJSP {
	public void generaOptions(File fichConfigIni, String keyList) {
		
	}
    public Vector<String[]> dameArraysLista(String temaLista) {
    	Vector<String[]> datosLista = new Vector<String[]>();
        ManejaInis mIniGroupBy = new ManejaInis("GroupBy.ini");
        String label = mIniGroupBy.getKeySeccion(temaLista, "label");
        String values= mIniGroupBy.getKeySeccion(temaLista, "value");
        String labelCentro [] =label.split(",");
        String valueCentro [] = values.split(",");        
        
        if (label.contains("#")){
        	datosLista=filtrarConsultasImplicitas(labelCentro,valueCentro);
        	
        }else {

        	datosLista.add(0, labelCentro);
        	datosLista.add(1, valueCentro);
        }

    	return datosLista;

    }
    
    public String[][] dameArrayDatosLista(String temaLista){
    	String[][] datos = null;
    	Vector<String[]> vectorDatos = dameArraysLista(temaLista);
    	int sizeArray = vectorDatos.get(0).length;
    	datos = new String[2][sizeArray];
    	datos[0]=vectorDatos.get(0);
    	datos[1]=vectorDatos.get(1);
    	return datos;
    }
    
    public Vector<String[]>filtrarConsultasImplicitas(String[] labels, String[] values){
    	Vector<String[]> datesList= new Vector<String[]>();
    	Vector<String> vectorLabels = new Vector<String>(Arrays.asList(labels));
    	Vector<String> vectorValues = new Vector<String>(Arrays.asList(values));
  

    	Vector<String> vectorLabelsFinal = new Vector<String>();
    	Vector<String> vectorValuesFinal =new Vector<String>();
    	
    	Iterator<String> itLabels= vectorLabels.iterator();
    	
    	int count=0;
    	UtilDatabase utilDatabase = new UtilDatabase();
    	while (itLabels.hasNext()) {
    		
    		String label = (String)itLabels.next();
    	
    		if(label.equals("#")) {
    			String query = vectorValues.get(count);
    			//La utilidad devuelve un vector con las filas devueltas por la base de datos
    			//un array por fila con el numero de campos ajustado (campos=1)
    			ConnectionManager conMng = new util.ConnectionManager();
    			Connection con = conMng.getConexionODBC("Mis", "admin", "");
    			Vector<String> insertVector = utilDatabase.dameVectorResultsSQL1Columna(query,con);
 
    			vectorLabelsFinal.addAll(insertVector);
    			vectorValuesFinal.addAll(insertVector);
    		}else {

    			vectorLabelsFinal.add(label);
    			vectorValuesFinal.add(count,vectorValues.elementAt(count));
    		}
    		count++;
    	}
    	String arrayL[] = new String[vectorLabels.size()];
    	String arrayV[] = new String[vectorLabels.size()];
    	datesList.add(0,vectorLabelsFinal.toArray(arrayL));
    	datesList.add(1,vectorValuesFinal.toArray(arrayV));
    	return datesList;
    }
}
