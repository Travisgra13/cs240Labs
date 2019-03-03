package Handlers;

import Requests.FillRequest;
import Result.FillResult;
import Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import dataAccess.Database;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillHandler implements HttpHandler {
    private Database db;

    public FillHandler(Database db) {
        this.db = db;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                FillService fillService = new FillService(db);

                FillRequest fillRequest = createFillRequest(exchange);
                FillResult results;
                results = fillService.fill(fillRequest.getUserName());
                Gson gson = new Gson();
                String respData = gson.toJson(results);
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
        }catch(IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
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

    private FillRequest createFillRequest(HttpExchange exchange) throws IOException {
        String reqBody = exchange.getRequestURI().toString();

        int index = findFillIndex(reqBody);
        StringBuilder sb = new StringBuilder(reqBody);
        sb.delete(index, 6);
        //if / then do something else
        FillRequest fillRequest = new FillRequest(sb.toString(), 4);
        return fillRequest;
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private int findFillIndex(String fullUrl) {
        StringBuilder sb = new StringBuilder(fullUrl);
        int index = sb.indexOf("/fill/");
        return index;
    }
}
