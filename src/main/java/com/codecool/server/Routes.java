package com.codecool.server;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

class Routes {

    private void responseWriter(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(path = "/")
    void mainPage(HttpExchange exchange) throws IOException {
        String response = "Main page!";
        responseWriter(exchange, response);
    }

    @WebRoute(path = "/test")
    void onTest(HttpExchange exchange) throws IOException {
        String response = "First test!";
        responseWriter(exchange, response);
    }

    @WebRoute(path = "/second-test")
    void onSecondTest(HttpExchange exchange) throws IOException {
        String response = "Second test!";
        responseWriter(exchange, response);
    }
}
