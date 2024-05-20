package Handler;

import DAO.DataAccessException;
import Request.LoadRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.LoginResponse;
import Services.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //if the request is a post then we will continue
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                //Go from JSON string to RegisterRequest Object
                Gson gson = new Gson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                LoginRequest loginRequest = gson.fromJson(reqBody, LoginRequest.class);

                //Create a Login Service object and call login on it using the request
                LoginService loginService = new LoginService();
                LoginResponse logResponse = null;
                logResponse = loginService.login(loginRequest);

                //Once youve logged in then start creating response body
                if (logResponse.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                //Change the LoginResponse object into JSON string using GSON
                String logResponseJSON = gson.toJson(logResponse);
                //Get the response body
                OutputStream respBody = exchange.getResponseBody();
                writeString(logResponseJSON, respBody);
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
