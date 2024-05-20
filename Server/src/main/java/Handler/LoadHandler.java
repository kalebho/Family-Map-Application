package Handler;

import DAO.DataAccessException;
import Request.LoadRequest;
import Request.RegisterRequest;
import Response.LoadResponse;
import Response.RegisterResponse;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            //if the request is a post then we will continue
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //Go from JSON string to RegisterRequest Object
                Gson gson = new Gson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                LoadRequest loadRequest = (LoadRequest) gson.fromJson(reqBody, LoadRequest.class);

                //Create a Load Service object and call load on it using the request
                LoadService loadService = new LoadService();
                LoadResponse loadResponse = null;
                loadResponse = loadService.load(loadRequest);

                //Once youve registered then start creating response body
                if (loadResponse.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                //Change the RegisterResponse object into JSON string using GSON
                String loadResponseJSON = gson.toJson(loadResponse);
                //Get the response body
                OutputStream respBody = exchange.getResponseBody();
                writeString(loadResponseJSON, respBody);
                //CLose the output stream to know we are done sending data
                respBody.close();
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
