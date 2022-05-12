package br.com.icostalima.tasks.apitest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		given()
		.when()
			.get("/todo")
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		given()
			.contentType(ContentType.JSON)
			.body("{\r\n"
					+ "\"task\": \"teste3\",\r\n"
					+ "\"dueDate\": \"2022-05-12\"\r\n"
					+ "}")
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201);
	}	  
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		given()
			.contentType(ContentType.JSON)
			.body("{\r\n"
					+ "\"task\": \"teste3\",\r\n"
					+ "\"dueDate\": \"2010-05-12\"\r\n"
					+ "}")
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", Matchers.is("Due date must not be in past"));
	}
}


