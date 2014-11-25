package util;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class OsCache extends GeneralCacheAdministrator {
	
	private static OsCache osCache=null;	     
    
    private static final long serialVersionUID = -4397192926052141162L;        
    
    public synchronized static OsCache getInstance(){
    	if(osCache==null){
    		osCache=new OsCache();
    	}
    	return osCache;
    }
    
    private OsCache(){    
        super();             
    }   
//    public OsCache(int refreshPeriod){    
//        super();               
//        this.refreshPeriod = refreshPeriod;    
//    }  
    
    
    //添加被缓存的对象;    
    public void put(String key,Object value){
    	
        this.putInCache(key,value);    
    }    
    //删除被缓存的对象;    
    public void remove(String key){    
        this.flushEntry(key);    
    }    
    //删除所有被缓存的对象;    
    public void removeAll(Date date){    
        this.flushAll(date);    
    }    
        
    public void removeAll(){    
        this.flushAll();    
    }    
    /**
     * 获取被缓存的对象    
     * @param key
     * @param refreshPeriod 单位：秒
     * @return
     * @throws Exception
     * @author mengdz
     * 2014年11月24日
     */
    public Object get(String key,int refreshPeriod) throws Exception{    
        try{    
            return this.getFromCache(key,refreshPeriod);    
        } catch (NeedsRefreshException e) {    
            this.cancelUpdate(key);    
            throw e;    
        }  
    }    
}
