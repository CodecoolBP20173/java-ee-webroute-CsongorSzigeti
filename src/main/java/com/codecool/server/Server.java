package com.codecool.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
    static HttpServer server;

    public static void main(String[] args) throws Exception {

        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String requestedPath = exchange.getRequestURI().getPath();
            Class aClass = Routes.class;
            Method[] methods = aClass.getDeclaredMethods();

            for (Method method : methods) {
                if(method.getAnnotation(WebRoute.class) != null){
                    WebRoute annotation = method.getAnnotation(WebRoute.class);
                    if (annotation.path().equals(requestedPath)){
                        try {
                            method.invoke(new Routes(),exchange);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
