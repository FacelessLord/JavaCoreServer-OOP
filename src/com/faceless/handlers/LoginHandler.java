package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;
import com.google.gson.JsonPrimitive;

import java.io.IOException;

public class LoginHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("POST", request, response))
			return;

        String login = request.getArgumentValue("login");
        String password = request.getArgumentValue("password");


        if(!checkValuesNotNull(response, login,password))
            return;


        String stmt =//if not here
                "INSERT IGNORE INTO users(login, password)\n" +
                        "    VALUE ('" + login + "', '" + password + "')\n" +
                        "ON DUPLICATE KEY UPDATE login=login;";
        Application.server.database.executeUpdate(stmt);
        propertyContainer.setProperty("logged_in", "true");
        propertyContainer.setProperty("login", login);
        propertyContainer.setProperty("password", password);
        response.setStatus("200");
        response.setDescription("OK");
        response.writeResponse(new JsonPrimitive(true).getAsString());
	}
}
