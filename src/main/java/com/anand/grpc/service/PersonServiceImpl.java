package com.anand.grpc.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.anand.grpc.PersonServiceGrpc.PersonServiceImplBase;
import com.anand.grpc.PersonServiceOuterClass.AddPersonRequest;
import com.anand.grpc.PersonServiceOuterClass.Person;

import io.grpc.stub.StreamObserver;

public class PersonServiceImpl extends PersonServiceImplBase {

	private Map<String, Person> personHolder = new HashMap<>();
	@Override
	public void addPerson(AddPersonRequest request, StreamObserver<AddPersonRequest.Response> responseObserver) {
		Person person = Person.newBuilder().setName(request.getName()).setUser(request.getUser()).build();
		personHolder.put(UUID.randomUUID().toString(), person);
		AddPersonRequest.Response response = AddPersonRequest.Response.newBuilder().setPerson(person).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
