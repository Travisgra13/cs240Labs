package Handlers;

import Requests.LoadRequest;
import Result.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import dataAccess.Database;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
private Database db;

public LoadHandler(Database db) {
    this.db = db;
}
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                LoadService loadService = new LoadService(db);

                LoadRequest loadRequest = createLoadRequest(exchange);
                LoadResult results;
                try {
                    results = loadService.load(loadRequest);
                } catch (DataAccessException e) {
                    results = new LoadResult(false, 0, 0, 0);
                    e.printStackTrace();
                }
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

    private LoadRequest createLoadRequest(HttpExchange exchange) throws IOException {
        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);

        Gson gson = new Gson();
        LoadRequest loadRequest = gson.fromJson(reqData, LoadRequest.class);
        return loadRequest;
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
