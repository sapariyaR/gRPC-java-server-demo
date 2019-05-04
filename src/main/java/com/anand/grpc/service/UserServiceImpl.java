package com.anand.grpc.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.anand.grpc.UserServiceGrpc.UserServiceImplBase;
import com.anand.grpc.UserServiceOuterClass.AddUserRequest;
import com.anand.grpc.UserServiceOuterClass.GetAllUserRequest;
import com.anand.grpc.UserServiceOuterClass.GetAllUserRequest.Response;
import com.anand.grpc.UserServiceOuterClass.User;

import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceImplBase {

  private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
  private static Map<Integer, User> userData = new HashMap<>();
  Integer userIdCount = 1;

  @Override
  public void addUser(
      AddUserRequest request, StreamObserver<AddUserRequest.Response> responseObserver) {
    try {
      // Fill request data into Object.
      User user =
          User.newBuilder()
              .setId(userIdCount.toString())
              .setName(request.getName())
              .addAllHobbies(request.getHobbiesList())
              .build();

      // insert data in database from here.
      userData.put(userIdCount, user);
      userIdCount++;
      logger.log(Level.INFO, "User added at server");

      // You must use a builder to construct a new Protobuffer object
      AddUserRequest.Response response = AddUserRequest.Response.newBuilder().setUser(user).build();

      // Use responseObserver to send a single response back
      responseObserver.onNext(response);

      // When you are done, you must call onCompleted.
      responseObserver.onCompleted();
    } catch (Exception ex) {
      ex.printStackTrace();
      responseObserver.onError(ex);
    }
  }

  @Override
  public void getAllUser(GetAllUserRequest request, StreamObserver<Response> responseObserver) {
    try {
      // You must use a builder to construct a new Protobuffer object
      GetAllUserRequest.Response response =
          GetAllUserRequest.Response.newBuilder().addAllUsers(userData.values()).build();

      // Use responseObserver to send a single response back
      responseObserver.onNext(response);

      // When you are done, you must call onCompleted.
      responseObserver.onCompleted();
    } catch (Exception ex) {
      ex.printStackTrace();
      responseObserver.onError(ex);
    }
  }

  @Override
  public void getAllUserWithStream(
      GetAllUserRequest request, StreamObserver<Response> responseObserver) {
    try {
      logger.log(Level.INFO, "Enter in getAllUserWithStream");
      for (int count = 1; count < userIdCount; count++) {
        // You must use a builder to construct a new Protobuffer object
        GetAllUserRequest.Response response =
            GetAllUserRequest.Response.newBuilder().setUser(userData.get(count)).build();
        logger.log(Level.INFO, "Loop index : {0}", count);
        // Use responseObserver to send a single response back
        responseObserver.onNext(response);
      }

      // When you are done, you must call onCompleted.
      responseObserver.onCompleted();
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.log(Level.WARNING, ex.getMessage());
      responseObserver.onError(ex);
    }
  }
}
