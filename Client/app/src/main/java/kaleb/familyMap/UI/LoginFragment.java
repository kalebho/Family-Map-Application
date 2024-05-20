package kaleb.familyMap.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import kaleb.familyMap.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kaleb.familyMap.AppLogic.DataCache;
import kaleb.familyMap.AppLogic.ServerProxy;
import Model.Person;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.EventsResponse;
import Response.LoginResponse;
import Response.PersonsResponse;
import Response.RegisterResponse;

public class LoginFragment extends Fragment {

    //Listener Variable
    private Listener listener;
    private String server_host;
    private String server_port;
    private EditText serverHost;
    private EditText serverPort;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioGroup gender;
    private Button registerButton;
    private Button loginButton;

    public interface Listener {
        void notifyDone();
    }

    //Function that sets the listener
    public void registerListener(Listener listener) {
        this.listener = listener;
    }


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        // Set the Menu off
        setHasOptionsMenu(false);


        RegisterRequest registerRequest = new RegisterRequest("k", "h", "n", "j", "j", "g");
        LoginRequest loginRequest = new LoginRequest("empty", "empty");
        server_host = "";
        server_port = "";

        //SERVER HOST INSTANCE******
        serverHost = view.findViewById(R.id.serverHostField);       //connect the edit txt to this variable "serverHost"
        serverHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                server_host = serverHost.getText().toString();
                checkForEnableLogin();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //SERVER PORT INSTANCE******
        serverPort = view.findViewById(R.id.serverPortField);       //connect the edit txt to this variable "serverHost"
        serverPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                server_port= serverPort.getText().toString();
                checkForEnableLogin();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // USERNAME INSTANCE*****
        username = view.findViewById(R.id.usernameField);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerRequest.setUsername(username.getText().toString());
                loginRequest.setUsername(username.getText().toString());
                checkForEnableLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //PASSWORD INSTANCE*******
        password = view.findViewById(R.id.passwordField);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerRequest.setPassword(password.getText().toString());
                loginRequest.setPassword(password.getText().toString());
                checkForEnableLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //FIRST NAME INSTANCE*********
        firstName = view.findViewById(R.id.firstNameField);
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerRequest.setFirstName(firstName.getText().toString());
                checkForEnableLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //LAST NAME INSTANCE******
        lastName = view.findViewById(R.id.lastNameField);       //connect the edit txt to this variable "serverHost"
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerRequest.setLastName(lastName.getText().toString());
                checkForEnableLogin();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //EMAIL INSTANCE******
        email = view.findViewById(R.id.emailField);       //connect the edit txt to this variable "serverHost"
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerRequest.setEmail(email.getText().toString());
                checkForEnableLogin();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //GENDER INSTANCE******
        gender = view.findViewById(R.id.buttonGroup);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                if (checkedID == R.id.maleButton) {
                    Button genderButton = view.findViewById(checkedID);
                    registerRequest.setGender("m");
                    checkForEnableLogin();
                }
                else if (checkedID == R.id.femaleButton) {
                    Button genderButton = view.findViewById(checkedID);
                    registerRequest.setGender("f");
                    checkForEnableLogin();
                }
            }
        });




        //REGISTER BUTTON INSTANCE*******
        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //WHEN THE BUTTON IS PRESSED
                Handler registerHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message message) {        //THIS IS THE UI THREAD
                        Bundle bundle = message.getData();
                        if (bundle.containsKey("first_name")) {
                            String firstName = bundle.getString("first_name");
                            String lastName = bundle.getString("last_name");
                            Toast toast = Toast.makeText(view.getContext(), "Successful Register!\n" + firstName + " " + lastName , Toast.LENGTH_SHORT);
                            toast.show();
                            listener.notifyDone();
                        }
                        else {
                            String errorMessage = bundle.getString("message");
                            Toast toast = Toast.makeText(view.getContext(), errorMessage , Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                };

                RegisterTask registerTask = new RegisterTask(registerHandler, registerRequest, server_host, server_port);
                //executor
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(registerTask);

            }
        });

        //LOGIN BUTTON INSTANCE*******
        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler loginHandler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        Bundle bundle = message.getData();
                        if (bundle.containsKey("first_name")) {
                            String firstName = bundle.getString("first_name");
                            String lastName = bundle.getString("last_name");
                            Toast toast = Toast.makeText(view.getContext(), "Successful Login!\n" + firstName + " " + lastName , Toast.LENGTH_SHORT);
                            toast.show();
                            listener.notifyDone();
                        }
                        else {
                            String errorMessage = bundle.getString("message");
                            Toast toast = Toast.makeText(view.getContext(), errorMessage , Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                };

                LoginTask loginTask = new LoginTask(loginHandler, loginRequest, server_host, server_port);
                //executor
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(loginTask);

            }
        });

        return view;
    }

    //REGISTER TASK CLASS********
    private static class RegisterTask implements Runnable {     //THIS IS THE BACKGROUND TASK

        //Handler object used to talk to the main thread
        private Handler handler;
        private RegisterRequest registerRequest;
        private String serverHost;
        private String serverPort;

        //Constructor
        public RegisterTask(Handler handler, RegisterRequest registerRequest, String serverHost, String serverPort) {
            this.handler = handler;
            this.registerRequest = registerRequest;
            this.serverHost = serverHost;
            this.serverPort = serverPort;
        }

        //Used to connect to the server with serverProxy and do the registration
        @Override
        public void run() {
            //run the servery proxy
            ServerProxy serverProxy = new ServerProxy();
            RegisterResponse registerResponse = serverProxy.register(registerRequest,serverHost,serverPort);
            if (registerResponse.isSuccess()) {
                System.out.println("Successfull reg\n");
                //Get people from the data for that specific authtoken
                String authtoken = registerResponse.getAuthtoken();
                PersonsResponse personsResponse = serverProxy.getPersons(authtoken, serverHost, serverPort);
                EventsResponse eventsResponse = serverProxy.getEvents(authtoken, serverHost, serverPort);
                //put data in dataCache
                DataCache dc = DataCache.getInstance();
                dc.clearCache();
                dc.insertPersons(personsResponse.getData());
                dc.insertEvents(eventsResponse.getData());
                //Find first name and last name to send as message
                String userPersonID = registerResponse.getPersonID();
                Person theUser = dc.getPerson(userPersonID);
                //Send successful message back to main thread
                System.out.println(theUser.getFirstName());
                System.out.println(theUser.getLastName());
                sendMessageSuccess(theUser.getFirstName(), theUser.getLastName());
            }
            else {
                //Send error message to main thread
                sendMessageFail();
            }
        }

        public void sendMessageSuccess(String firstName, String lastName) {
            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            messageBundle.putString("first_name", firstName);
            messageBundle.putString("last_name", lastName);
            message.setData(messageBundle);

            handler.sendMessage(message);
        }

        public void sendMessageFail() {
            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            messageBundle.putString("message", "Register Unsuccessful");
            message.setData(messageBundle);

            handler.sendMessage(message);
        }

    }

    //LOGIN TASK CLASS********
    private static class LoginTask implements Runnable {        //THIS IS THE BACKGROUND TASK

        private Handler handler;
        private LoginRequest loginRequest;
        private String serverHost;
        private String serverPort;

        //Constructor
        public LoginTask(Handler handler, LoginRequest loginRequest, String serverHost, String serverPort) {
            this.handler = handler;
            this.loginRequest = loginRequest;
            this.serverHost = serverHost;
            this.serverPort = serverPort;
        }

        @Override
        public void run() {
            //run the servery proxy
            ServerProxy serverProxy = new ServerProxy();
            LoginResponse loginResponse = serverProxy.login(loginRequest,serverHost, serverPort);
            if (loginResponse.isSuccess()) {
                System.out.println("Successfull login\n");
                //Get people from the data for that specific authtoken
                String authtoken = loginResponse.getAuthtoken();
                PersonsResponse personsResponse = serverProxy.getPersons(authtoken, serverHost, serverPort);
                EventsResponse eventsResponse = serverProxy.getEvents(authtoken, serverHost, serverPort);
                //put data in dataCache
                DataCache dc = DataCache.getInstance();
                dc.clearCache();
                dc.insertPersons(personsResponse.getData());
                dc.insertEvents(eventsResponse.getData());
                //Find first name and last name to send as message
                String userPersonID = loginResponse.getPersonID();
                dc.setUserID(userPersonID);
                Person theUser = dc.getPerson(userPersonID);
                //Send successful message back to main thread
                sendMessageSuccess(theUser.getFirstName(), theUser.getLastName());
            }
            else {
                //Send error message to main thread
                sendMessageFail();
            }
        }

        public void sendMessageSuccess(String firstName, String lastName) {
            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            messageBundle.putString("first_name", firstName);
            messageBundle.putString("last_name", lastName);
            message.setData(messageBundle);

            handler.sendMessage(message);
        }

        public void sendMessageFail() {
            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            messageBundle.putString("message", "Login Unsuccessful");
            message.setData(messageBundle);

            handler.sendMessage(message);
        }
    }

    private void checkForEnableLogin() {

        loginButton.setEnabled(false);
        registerButton.setEnabled(false);

        if (!server_host.isEmpty() && !server_port.isEmpty() &&
                !username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            loginButton.setEnabled(true);
        }

        //To check for radio group
        int genderHelp = 0;
        int selectedRadioButtonId = gender.getCheckedRadioButtonId();

        if (selectedRadioButtonId < 0) {
            genderHelp = 0;
        }
        else {
            genderHelp = 1;
        }

        if(!server_host.isEmpty() && !server_port.isEmpty() && !username.getText().toString().isEmpty()
                && !password.getText().toString().isEmpty() && !firstName.getText().toString().isEmpty()
                && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && (genderHelp == 1)) {
            registerButton.setEnabled(true);
        }

    }
}