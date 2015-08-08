package org.restapplication.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.restapplication.messenger.model.Message;
import org.restapplication.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();

	// for query parameters we cannot have different methods that is the reason
	// we use query parameters.
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean,@Context UriInfo uriInfo) {
		System.out.println("hii"+uriInfo.getAbsolutePath());
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		} else if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) { // pagination
																				// is
																				// done
																				// with
																				// zero
																				// indexing
			return messageService.getAllMessagesPaginated(
					filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId) {
		return messageService.getMessage(messageId);
	}

	@POST
	public Response addMessage(@Context UriInfo uriInfo, Message message) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();

	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId,
			Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);

	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long messageId) {
		messageService.removeMessage(messageId);
	}

}
