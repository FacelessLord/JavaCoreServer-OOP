package com.faceless.vmservice.handlers;

import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;
import com.faceless.vmservice.containers.PropertyContainer;

import java.io.IOException;

public class SetValueHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("POST", request, response))
			return;

		String valueName = request.getArguments().keys().nextElement();
		String value     = request.getArgumentValue(valueName);
		if(!checkValuesNotNull(response, value,valueName))
			return;
		if (propertyContainer.hasProperty(valueName)) {
			propertyContainer.setProperty(valueName, value);
			response.setStatus("200");
			response.setDescription("OK");
			response.setJsonResponse();
			response.writeResponse("{\n\t\"" + valueName + "\" : \"" +
					propertyContainer.getProperty(valueName) + "\"\n}");
		}
		else
		{
			response.setStatus("404");
			response.setDescription("Value is not registered");
		}
	}
}
