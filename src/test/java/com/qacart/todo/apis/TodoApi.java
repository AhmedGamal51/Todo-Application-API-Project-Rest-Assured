package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {
    public static Response addTodo(Todo todo, String token){
        return given()
                .spec(Specs.getRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when().post(Route.TODOS_PATH)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getTodo(String taskID, String token){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODOS_PATH +"/" + taskID)
                .then()
                .log().all()
                .extract().response();

    }

    public static Response updateTodo(Todo todo, String token, String taskID){
        return given()
                .spec(Specs.getRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when()
                .put(Route.TODOS_PATH +"/" + taskID)
                .then()
                .log().all()
                .extract().response();

    }
    public static Response deleteTodo(String token, String taskID){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .delete(Route.TODOS_PATH +"/" + taskID)
                .then()
                .log().all()
                .extract().response();
    }
}
