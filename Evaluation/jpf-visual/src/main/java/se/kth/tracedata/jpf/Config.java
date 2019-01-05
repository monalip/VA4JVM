package se.kth.tracedata.jpf;

public class Config {
	gov.nasa.jpf.Config jpfconfig;
	public Config(gov.nasa.jpf.Config jpfconfig) {
	  	
	    this.jpfconfig = jpfconfig; 
	  }
	public Object put (Object keyObject, Object valueObject){

	    return jpfconfig.put(keyObject, valueObject);
	  }
	public String getProperty(String key, String defaultValue) {
		return jpfconfig.getProperty(key, defaultValue);
	}

}
