package kaleb.familyMap.AppLogic;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;

import Request.LoginRequest;
import Request.RegisterRequest;
import Response.ClearResponse;
import Response.EventsResponse;
import Response.LoginResponse;
import Response.PersonsResponse;
import Response.RegisterResponse;

public class ServerProxy {

    public RegisterResponse register(RegisterRequest registerRequest, String serverHost, String serverPort) {

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            //Tell the server its a post
            http.setRequestMethod("POST");

            //Tell the sever there is a request body
            http.setDoOutput(true);

            // Connect to the server and send the HTTP request
            http.connect();

            //Go from register request to Json String
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(registerRequest);

            // Finish sending the request body
            OutputStream reqBody = http.getOutputStream();

            // Write the JSON data to the request body
            writeString(jsonRequest, reqBody);

            //Close the request body and send it over
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();

                // Extract JSON data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                RegisterResponse registerResponse = gson.fromJson(jsonResponse, RegisterResponse.class);

                //Return back the register response object
                return registerResponse;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());

                // Get the error stream containing the HTTP response body (if any)
                InputStream respBody = http.getErrorStream();

                // Extract data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                RegisterResponse registerResponse = gson.fromJson(jsonResponse, RegisterResponse.class);

                //return
                return registerResponse;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new RegisterResponse("Hello", false);
    }



    public LoginResponse login(LoginRequest loginRequest, String serverHost, String serverPort) {

        try {

            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            //Tell the server its a post
            http.setRequestMethod("POST");

            //Tell the sever there is a request body
            http.setDoOutput(true);


            // Connect to the server and send the HTTP request
            http.connect();

            //Go from login request to Json String
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(loginRequest);

            // Finish sending the request body
            OutputStream reqBody = http.getOutputStream();

            // Write the JSON data to the request body
            writeString(jsonRequest, reqBody);

            //Close the request body and send it over
            reqBody.close();

            //Check to see if connection was a success or fail
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("HAPPY!\n");

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();

                // Extract JSON data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                LoginResponse loginResponse = gson.fromJson(jsonResponse, LoginResponse.class);

                //Return back the register response object
                return loginResponse;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());

                // Get the error stream containing the HTTP response body (if any)
                InputStream respBody = http.getErrorStream();

                // Extract data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                LoginResponse loginResponse = gson.fromJson(jsonResponse, LoginResponse.class);

                //return
                return loginResponse;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new LoginResponse("Hello", false);
    }

    public PersonsResponse getPersons(String authtoken, String serverHost, String serverPort) {
        try {

            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            //Tell the server its a get
            http.setRequestMethod("GET");

            //Tell the sever there is a request body
            http.setDoOutput(false);

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", authtoken);

            // Connect to the server and send the HTTP request
            http.connect();

            //Check to see if connection was a success or fail
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();

                // Extract JSON data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                Gson gson = new Gson();
                PersonsResponse personsResponse = gson.fromJson(jsonResponse, PersonsResponse.class);

                //Return back the register response object
                return personsResponse;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());

                // Get the error stream containing the HTTP response body (if any)
                InputStream respBody = http.getErrorStream();

                // Extract data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                Gson gson = new Gson();
                PersonsResponse personsResponse = gson.fromJson(jsonResponse, PersonsResponse.class);

                //return
                return personsResponse;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public EventsResponse getEvents(String authtoken, String serverHost, String serverPort) {
        try {

            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            //Tell the server its a get
            http.setRequestMethod("GET");

            //Tell the sever there is a request body
            http.setDoOutput(false);

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", authtoken);

            // Connect to the server and send the HTTP request
            http.connect();

            //Check to see if connection was a success or fail
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();

                // Extract JSON data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                Gson gson = new Gson();
                EventsResponse eventsResponse = gson.fromJson(jsonResponse, EventsResponse.class);

                //Return back the register response object
                return eventsResponse;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());

                // Get the error stream containing the HTTP response body (if any)
                InputStream respBody = http.getErrorStream();

                // Extract data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                Gson gson = new Gson();
                EventsResponse eventsResponse = gson.fromJson(jsonResponse, EventsResponse.class);

                //return
                return eventsResponse;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClearResponse clear(String serverHost, String serverPort) {

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/clear");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            //Tell the server its a post
            http.setRequestMethod("POST");

            //Tell the sever there is a request body
            http.setDoOutput(true);

            // Finish sending the request body
            OutputStream reqBody = http.getOutputStream();

            //Close the request body and send it over
            reqBody.close();

            //Check to see if connection was a success or fail
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();

                // Extract JSON data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                Gson gson = new Gson();
                ClearResponse clearResponse = gson.fromJson(jsonResponse, ClearResponse.class);

                //Return back the register response object
                return clearResponse;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());

                // Get the error stream containing the HTTP response body (if any)
                InputStream respBody = http.getErrorStream();

                // Extract data from the HTTP response body
                String jsonResponse = readString(respBody);

                //Turn Json string back into object
                Gson gson = new Gson();
                ClearResponse clearResponse = gson.fromJson(jsonResponse, ClearResponse.class);

                //return
                return clearResponse;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
		The readString method shows how to read a String from an InputStream.
	*/
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
		The writeString method shows how to write a String to an OutputStream.
	*/
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
