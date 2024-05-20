package Handler;

import DAO.DataAccessException;
import Request.RegisterRequest;
import Response.RegisterResponse;
import Services.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            //if the request is a post then we will continue
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //Go from JSON string to RegisterRequest Object
                Gson gson = new Gson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                RegisterRequest regRequest = gson.fromJson(reqBody, RegisterRequest.class);

                //Create a Register Service object and call register on it using the request
                RegisterService regService = new RegisterService();
                RegisterResponse registerResponse = null;
                registerResponse = regService.register(regRequest);

                //Once you've registered then start creating response body
                if (registerResponse.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                //Change the RegisterResponse object into JSON string using GSON
                String regResponseJSON = gson.toJson(registerResponse);
                System.out.println(regResponseJSON);
                //Get the response body
                OutputStream respBody = exchange.getResponseBody();
                writeString(regResponseJSON, respBody);
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

