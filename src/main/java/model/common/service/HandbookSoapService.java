package model.common.service;// это аннотации, т.е. способ отметить наши классы и методы,
// как связанные с веб-сервисной технологией

import model.WinApiClass;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

// говорим, что наш интерфейс будет работать как веб-сервис
@WebService(wsdlLocation = "wsdl/handbook.wsdl")
// говорим, что веб-сервис будет использоваться для вызова методов
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HandbookSoapService {
    @WebMethod
    WinApiClass getTopic(long id);

    @WebMethod
    WinApiClass[] findTopics(String keyword);

    @WebMethod
    long createTopic(WinApiClass topic);

    @WebMethod
    void updateTopic(WinApiClass topic);

    @WebMethod
    void removeTopic(long id);
}