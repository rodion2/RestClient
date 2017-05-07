/**
 * Created by rodya on 29.4.17.
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class HelloWorld {
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/RestService_war_exploded/rest_service").build();
    }

    public static void main(String[] args) {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource(getBaseURI());
            //ClientResponse response = webResource.path("get_win_api_class/").path(Long.toString(1))
             //       .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//            ClientResponse response = webResource.path("find_win_api_class").queryParam("keyword","LIST")
//                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            ClientResponse response= webResource.path("win_api_class").queryParam("id",Long.toString(0)).delete(ClientResponse.class);
            // ClientResponse response = webResource.accept("application/json")
                  //  .get(ClientResponse.class);
            System.out.println(webResource.getURI());
            if (response.getStatus() != 200) {
                System.out.println("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);

            System.out.println("Output from Server .... \n");
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}