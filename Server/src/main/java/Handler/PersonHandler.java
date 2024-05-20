package Handler;

import DAO.DataAccessException;
import Response.PersonResponse;
import Response.PersonsResponse;
import Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    private Object gson;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            //if method is a "get" then continue
            //********
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                // Get the HTTP request headers
                //These are like maps with key value pairs
                Headers reqHeaders = exchange.getRequestHeaders();

                //if there is an authtoken then continue
                //********
                if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    String authtoken = reqHeaders.getFirst("Authorization");
                    String url = exchange.getRequestURI().toString();
                    String[] urlArray = url.split("/");

                    PersonService pServ = new PersonService();
                    PersonResponse pResponse = null;
                    PersonsResponse psResponse = null;
                    boolean singlePerson = true;

                    if (urlArray.length == 3) {
                        pResponse = pServ.getSinglePerson(urlArray[2], authtoken);
                        if (pResponse.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                    } else {
                        psResponse = pServ.getPersons(authtoken);
                        singlePerson = false;
                        if (psResponse.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }
                    //Once we've gotten the response start creating the response body

                    Gson gson = new Gson();
                    //Change the PersonResponse object into JSON string using GSON
                    String pResponseJSON = gson.toJson(pResponse);
                    String psResponseJSON = gson.toJson(psResponse);
                    //Get the response body
                    OutputStream respBody = exchange.getResponseBody();
                    if (singlePerson) {
                        //Write the PersonResponse String to the response body
                        writeString(pResponseJSON, respBody);
                    }
                    else {
                        //Write the PersonsResponse String to the response body
                        writeString(psResponseJSON, respBody);
                    }
                    //CLose the output stream to know we are done sending data
                    respBody.close();
                }
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
