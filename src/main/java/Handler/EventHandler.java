package Handler;

import DAO.DataAccessException;
import Response.EventResponse;
import Response.EventsResponse;
import Services.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {

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

                    EventService eSer = new EventService();
                    EventResponse eResponse = null;
                    EventsResponse esResponse = null;
                    boolean singleEvent = true;

                    if (urlArray.length == 3) {
                        eResponse = eSer.getSingleEvent(urlArray[2], authtoken);
                        if (eResponse.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                    } else {
                        esResponse = eSer.getEvents(authtoken);
                        singleEvent = false;
                        if (esResponse.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }
                    //Once we've gotten the response start creating the response body

                    //Change the EventResponse object into JSON string using GSON
                    Gson gson = new Gson();
                    String eResponseJSON = gson.toJson(eResponse);
                    String esResponseJSON = gson.toJson(esResponse);
                    //Get the response body
                    OutputStream respBody = exchange.getResponseBody();
                    if (singleEvent) {
                        //Write the EventResponse String to the response body
                        writeString(eResponseJSON, respBody);
                    }
                    else {
                        //Write the EventsResponse String to the response body
                        writeString(esResponseJSON, respBody);
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
