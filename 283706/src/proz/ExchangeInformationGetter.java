package proz;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exchangerates/rates")
public class ExchangeInformationGetter {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{table}/{code}/{topCount}")
	public String getText(@PathParam("table") String tableChar, @PathParam("code") String currencyCode,
			@PathParam("topCount") Integer topCount) {
		NBPConnector nbp = new NBPConnector();
		return nbp.getAverage(tableChar, currencyCode, topCount);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{table}/{code}/{topCount}")
	public String getHTML(@PathParam("table") String tableChar, @PathParam("code") String currencyCode,
			@PathParam("topCount") Integer topCount) {
		NBPConnector nbp = new NBPConnector();
		return "<html><body><h1>" + nbp.getAverage(tableChar, currencyCode, topCount) + "</h1></body></html>";
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{table}/{code}/{topCount}")
	public String getXML(@PathParam("table") String tableChar, @PathParam("code") String currencyCode,
			@PathParam("topCount") Integer topCount) {
		NBPConnector nbp = new NBPConnector();
		return "<?xml version=\"1.0\"?>" + "<average>" + nbp.getAverage(tableChar, currencyCode, topCount) + "</average>";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{table}/{code}/{topCount}")
	public String getJSON(@PathParam("table") String tableChar, @PathParam("code") String currencyCode,
			@PathParam("topCount") Integer topCount) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		NBPConnector nbp = new NBPConnector();
		builder.add("average", nbp.getAverage(tableChar, currencyCode, topCount));
		JsonObject jsonObj = builder.build();
		return jsonObj.toString();
	}

}