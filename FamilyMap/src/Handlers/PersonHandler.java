package Handlers;

import Model.AuthToken;
import Model.User;
import Requests.PersonIDRequest;
import Requests.PersonRequest;
import Result.PersonIDResult;
import Result.PersonResult;
import Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;

import java.io.*;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class PersonHandler implements HttpHandler {
    private Database db;
    private Boolean personIDCheck;

    public PersonHandler(Database db) {
        this.db = db;
        personIDCheck = false;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        String token = null;
        String user = null;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    token = reqHeaders.getFirst("Authorization");
                    user = tokenAuthenticated(token);
                    if (user == null) {
                        throw new DataAccessException("Wrong Token Given");
                    }
                }
                else {
                    throw new DataAccessException("Wrong Token Given");
                }

                PersonService personService = new PersonService(db);
                PersonIDRequest personIDRequest = null;
                PersonRequest personRequest = null;
                Object request = createPersonRequest(exchange, user);

                if(personIDCheck) {
                    personIDRequest = (PersonIDRequest) request;
                }
                else {
                    personRequest = (PersonRequest) request;
                }
                PersonIDResult resultsID = null;
                PersonResult results = null;
                try {
                    if (personIDCheck) {
                        resultsID = personService.personID(personIDRequest);
                    }
                    else {
                        results = personService.person(personRequest);
                    }
                } catch (DataAccessException e) {
                    results = new PersonResult(null,false);
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                String respData = null;
                if (personIDCheck) {
                    respData = gson.toJson(resultsID);
                }
                else {
                    respData = gson.toJson(results);
                }

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
                exchange.getResponseBody().close();
                success = true;
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (DataAccessException e) {
            errorHandling(exchange);
        e.printStackTrace();
        }catch(IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private void errorHandling(HttpExchange exchange) {
        Gson gson = new Gson();
        if (personIDCheck) {
            try {
                String respData = gson.toJson(new PersonIDResult(null, null, null, null, null, null, null, null, false));
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
                exchange.getResponseBody().close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                String respData = gson.toJson(new PersonResult(null, false));
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
                exchange.getResponseBody().close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buff = new char[1024];
        int len;
        while ((len = sr.read(buff)) > 0) {
            sb.append(buff, 0, len);
        }
        return sb.toString();
    }

    private Object createPersonRequest(HttpExchange exchange, String user) throws IOException {
        int index = findPersonIndex(exchange.getRequestURI().toString());
        StringBuilder sb = new StringBuilder(exchange.getRequestURI().toString());
        sb.delete(0, 7);
        if (sb.length() > 0) {
            sb.delete(0,1);
            personIDCheck = true;
            PersonIDRequest personIDRequest = new PersonIDRequest(sb.toString());
            return personIDRequest;
        }
        personIDCheck = false;
        Gson gson = new Gson();
        PersonRequest personRequest = new PersonRequest(user);
        return personRequest;
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private String tokenAuthenticated(String token) {
        Connection conn = null;
        try {
            conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            String user = aDao.QueryAuthTokenByToken(token);
            if (user != null) {
                return user;
            }
            else {
                return null;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    private int findPersonIndex(String fullUrl) {
        StringBuilder sb = new StringBuilder(fullUrl);
        int index = sb.indexOf("/person");
        return index;
    }
}
