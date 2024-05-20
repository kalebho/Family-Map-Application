package Handler;

import DAO.DataAccessException;
import Response.ClearResponse;
import Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            //if the request is a post then we will continue
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //Clear all of the data in each table
                ClearService clearService = new ClearService();
                ClearResponse clearResponse = clearService.clear();

                //Once everything has been cleared create the response body
                if (clearResponse.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                Gson gson = new Gson();
                String clearResponseJson = gson.toJson(clearResponse);
                OutputStream respBody = exchange.getResponseBody();
                writeString(clearResponseJson, respBody);
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
