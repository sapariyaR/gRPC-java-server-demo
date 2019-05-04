package com.anand.grpc;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.anand.grpc.service.UserServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/** main class to start grpc server on port 8080 */
public class App {

  private static Logger logger = Logger.getLogger(App.class.getName());

  public static void main(String[] args) throws IOException, InterruptedException {

    // Create a new server to listen on port 8080
    Server server = ServerBuilder.forPort(8080).addService(new UserServiceImpl()).build();

    // Start the server
    server.start();

    // Server threads are running in the background.
    logger.log(Level.INFO, "Server started on port 8080");
    // Don't exit the main thread. Wait until server is terminated.
    server.awaitTermination();
  }
}
