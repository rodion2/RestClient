package client.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import model.WinApiClass;
import model.WinApiFunction;
import model.WinApiParameter;
import model.common.URLS;
import model.common.exception.HandbookException;
import model.common.service.WinApiHandbookService;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;


/**
 * Created by rodya on 28.4.17.
 */
public class WinApiHandbookRestClient implements WinApiHandbookService {
    static private Logger logger = Logger.getLogger(WinApiHandbookRestClient.class);
    private WebResource service;
    private Client client;
    private Gson gsonService;
    ClientResponse response;

    public WinApiHandbookRestClient(){
        this.client = Client.create();
        this.service = client.resource(getBaseURI());
        logger.debug("Connected to REST-service : "+service.getURI());
         gsonService= new Gson();
    }

    @Override
    public WinApiClass getWinApiClass(long id) throws HandbookException {
        WinApiClass winApiClass=null;
        try {
             response = service.path(URLS.GET_WIN_API_CLASS).queryParam("id", Long.toString(id))
                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
             gsonService.fromJson(response.getEntity(String.class), WinApiClass.class);
        }catch (ClientHandlerException e){
            logger.debug(e);
            throw new HandbookException("Failed to connect : "+e.getMessage(),e);
        }catch(JsonSyntaxException e) {
            logger.debug(e);
            throw new HandbookException(e);
        }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+response.getStatus());
            return winApiClass;
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    @Override
    public List<WinApiClass> findClasses(String keyword) throws HandbookException {
        List<WinApiClass> classes=null;
       try{
            response = service.path(URLS.FIND_WIN_API_CLASS).queryParam("keyword", keyword)
                   .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            classes = gsonService.fromJson(response.getEntity(String.class), new TypeToken<List<WinApiClass>>() {
           }.getType());
       }catch (ClientHandlerException e){
           logger.debug(e);
           throw new HandbookException("Failed to connect : "+e.getMessage(),e);
       }catch (JsonSyntaxException e){
           logger.debug(e);
           throw new HandbookException(e);
       }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+response.getStatus());
            return classes;
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    @Override
    public WinApiClass saveOrUpdate(WinApiClass winApiClass) throws HandbookException {
        WinApiClass winApiClassResponse=null;
        try{
           String winClass = gsonService.toJson(winApiClass);
           response = service.path(URLS.WIN_API_CLASS).post(ClientResponse.class,winClass);
           winApiClassResponse=gsonService.fromJson(response.getEntity(String.class), WinApiClass.class);
       }catch (ClientHandlerException e){
           throw new HandbookException("Failed to connect : "+e.getMessage(),e);
       }catch (JsonSyntaxException e){
            throw new HandbookException("Incorrect values : "+e.getMessage(),e);
        }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+response.getStatus());
            return winApiClassResponse;
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    @Override
    public void removeClass(long id) throws HandbookException {
       try {
            response = service.path(URLS.FUNCTION).queryParam("id",Long.toString(id))
                    .delete(ClientResponse.class);
       }catch (ClientHandlerException e){
           throw new HandbookException("Failed to connect : "+e.getMessage(),e);
       }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+200);
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    @Override
    public void updateFunction(WinApiFunction function) throws HandbookException {
        try{
             response = service.path(URLS.FUNCTION)
                    .put(ClientResponse.class, gsonService.toJson(function));
        }catch (ClientHandlerException e){
            throw new HandbookException("Failed to connect : "+e.getMessage(),e);
        }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+200);
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    @Override
    public void removeWinApiFunction(long id) throws HandbookException {
        try{
             response = service.path(URLS.FUNCTION).path(Long.toString(id)).delete(ClientResponse.class, Long.toString(id));
        }catch (ClientHandlerException e){
            throw new HandbookException("Failed to connect : "+e.getMessage());
        }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+response.getStatus());
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    @Override
    public void updateParam(WinApiParameter parameter) throws HandbookException {
       try{
            response = service.path(URLS.PARAMETER).put(ClientResponse.class, gsonService.toJson(parameter));
       }catch (ClientHandlerException e){
           throw new HandbookException("Failed to connect : "+e.getMessage(),e);
       }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+200);
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    @Override
    public void removeWinApiParameter(long id) throws HandbookException {
       try{
            response = service.path(URLS.PARAMETER).delete(ClientResponse.class, Long.toString(id));
       }catch (ClientHandlerException e){
           throw new HandbookException("Failed to connect : "+e.getMessage(),e);
       }
        if (response.getStatus() == 200) {
            logger.debug("HTTP request executed with status : "+200);
        } else {
            throw new HandbookException("Failed : HTTP error code : "+response.getStatus());
        }
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/RestService_war_exploded/rest_service").build();
    }
}
