package proz;

import java.io.StringReader;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class NBPConnector {

	private Client client;
	private WebTarget webTarget;
	private URI uri;
	
	NBPConnector() {
		client = ClientBuilder.newClient();
		uri = UriBuilder.fromUri("http://api.nbp.pl/api/exchangerates/rates").build();
	}

	private String getXMLAnswer() {
		return webTarget.request().accept(MediaType.TEXT_XML).get(String.class);
	}

	public String getAverage(String table, String code, Integer topCount) {
		NBPConnector nbp = new NBPConnector();
		nbp.webTarget = nbp.client.target(nbp.uri).path(table).path(code).path("last").path(topCount.toString());

		String ans = nbp.getXMLAnswer();

		try {
			JAXBContext context = JAXBContext.newInstance(ExchangeRatesSeries.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(ans);
			ExchangeRatesSeries example = (ExchangeRatesSeries) unmarshaller.unmarshal(reader);
			return String.valueOf(example.getAvg());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return "0";
	}

}