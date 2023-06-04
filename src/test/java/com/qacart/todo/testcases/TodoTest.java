package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("Todo Feature")
public class TodoTest {
    @Story("Should Be Able To Add Todo")
    @Test(description = "Should Be Able To Add Todo")
    public void shouldBeAbleToAddTodo(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        Response response = TodoApi.addTodo(todo, token);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getCompleted(), equalTo(todo.getCompleted()));

    }

    @Story("Should Not Be Able To Add To do If IsCompleted Is Missing")
    @Test(description = "Should Not Be Able To Add To do If IsCompleted Is Missing")
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing(){
        String token = UserSteps.getUserToken();
        Todo todo = new Todo( "Learn Appium");
        Response response = TodoApi.addTodo(todo, token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));

    }

    @Story("Should Be Able To Update A Todo By ID")
    @Test(description = "Should Be Able To Update A Todo By ID")
    public void shouldBeAbleToUpdateATodoByID(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        String todoID = TodoSteps.getTodoID(todo, token);
        todo.setCompleted(true);
        Response response = TodoApi.updateTodo(todo,token,todoID);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getCompleted(), equalTo(todo.getCompleted()));

    }

    @Story("Should Be Able To Get A Todo By ID")
    @Test(description = "Should Be Able To Get A Todo By ID")
    public void shouldBeAbleToGetATodoByID(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        String todoID = TodoSteps.getTodoID(todo, token);
        Response response = TodoApi.getTodo(todoID,token);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getCompleted(), equalTo(false));
    }

    @Story("Should Be Able To Delete A Todo By ID")
    @Test(description = "Should Be Able To Delete A Todo By ID")
    public void shouldBeAbleToDeleteATodoByID(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        String todoID = TodoSteps.getTodoID(todo, token);
        Response response = TodoApi.deleteTodo(token,todoID);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getCompleted(), equalTo(false));
    }
}
