package kr.co.n3n.smartcity.common.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommMap extends HashMap<String, Object> {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	public CommMap() {
		
	}
	
	public String getString(String key) {
		
		String retVal="";
		try {
			if(this.get(key)!=null) {
				retVal=this.get(key).toString();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	
	public int getInt(String key) {
		int retVal=0;
		try {
			if(this.get(key)!=null) {
				retVal=Integer.parseInt(this.get(key).toString());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	public int getInt(String key, int defaultValue) {
		int retVal=defaultValue;
		try {
			if(this.get(key)!=null) {
				retVal=Integer.parseInt(this.get(key).toString());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	public Double getDouble(String key) {
		
		double retVal=0;
		try {
			if(this.get(key)!=null) {
				retVal=Double.parseDouble((String)this.get(key));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public Double getDouble(String key, double defaultValue) {
		
		double retVal=defaultValue;
		try {
			if(this.get(key)!=null) {
				retVal=Double.parseDouble((String)this.get(key));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public CommMap getCommMap(String key) {
		
		CommMap retVal=null;
		try {
			if(this.get(key)!=null) {
				retVal=(CommMap)this.get(key);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public List<Map> getListMap(String key) {
		
		List<Map> retVal=null;
		try {
			if(this.get(key)!=null) {
				retVal=(List<Map>)this.get(key);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	
}
