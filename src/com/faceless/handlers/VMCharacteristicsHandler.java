package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.containers.VirtualMachine;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class VMCharacteristicsHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("GET", request, response))
			return;

		String         login = request.getArgumentValue("login");
		int    index=-1;
		try {
			index = Integer.parseInt(request.getArgumentValue("index"));
		}catch (NumberFormatException e)
		{
			throwForbidden(response);
		}
		if(!checkValuesNotNull(response, login))
			return;
		VirtualMachine vm    = VirtualMachine.getVmByUserAndId(login, index, Application.server.database);

		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(vm.getHttpRequestArgumentString());
	}
}
