package Handlers;

import Requests.EventIDRequest;
import Requests.EventRequest;
import Result.EventIDResult;
import Result.EventResult;
import Services.EventService;
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

public class EventHandler implements HttpHandler {
    private Database db;
    private Boolean eventIDCheck;

    public EventHandler(Database db) {
        this.db = db;
        eventIDCheck = false;
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

                EventService eventService = new EventService(db);
                EventIDRequest eventIDRequest = null;
                EventRequest eventRequest = null;
                Object request = createEventRequest(exchange, user);

                if(eventIDCheck) {
                    eventIDRequest = (EventIDRequest) request;
                }
                else {
                    eventRequest = (EventRequest) request;
                }
                EventIDResult resultsID = null;
                EventResult results = null;
                try {
                    if (eventIDCheck) {
                        resultsID = eventService.eventID(eventIDRequest);
                    }
                    else {
                        results = eventService.event(eventRequest);
                    }
                } catch (DataAccessException e) {
                    results = new EventResult(null,false);
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                String respData = null;
                if (eventIDCheck) {
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
        if (eventIDCheck) {
            try {
                String respData = gson.toJson(new EventIDResult(null, false));
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
                String respData = gson.toJson(new EventResult(null, false));
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

    private Object createEventRequest(HttpExchange exchange, String user) throws IOException {
        int index = findPersonIndex(exchange.getRequestURI().toString());
        StringBuilder sb = new StringBuilder(exchange.getRequestURI().toString());
        sb.delete(0, 6);
        if (sb.length() > 0) {
            sb.delete(0,1);
            eventIDCheck = true;
            EventIDRequest eventIDRequest = new EventIDRequest(sb.toString(), user);
            return eventIDRequest;
        }
        eventIDCheck = false;
        Gson gson = new Gson();
        EventRequest eventRequest = new EventRequest(user);
        return eventRequest;
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
        int index = sb.indexOf("/event");
        return index;
    }
}
