package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class HandleProperties {
	Properties props;
	public HandleProperties() {
 
    	URL pathConfig = null;
		try {
			pathConfig = new URL("http://localhost/Test/configuracion/config.properties");

		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	props = new Properties();
 
    	try {
    		System.out.println("Loading .."+pathConfig);
			props.load(pathConfig.openConnection().getInputStream());
			System.out.println("properties loaded");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public String leeProperties(String propertie) {

    	return props.getProperty(propertie);
    	
    }
}
