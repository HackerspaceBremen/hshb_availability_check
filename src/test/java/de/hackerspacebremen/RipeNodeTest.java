package de.hackerspacebremen;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public final class RipeNodeTest {

	@BeforeClass
	public void init() {
		System.setProperty("jsse.enableSNIExtension", "false");
	}

	public void checkRipeNode() throws ClientProtocolException,
			IOException {
		final HttpClient httpclient = HttpClientBuilder.create().build();
		final HttpGet httpget = new HttpGet(
				"https://atlas.ripe.net/api/v1/probe/?id=4575");

		final HttpResponse response = httpclient.execute(httpget);

		final String jsonResponse = IOUtils.toString(response.getEntity()
				.getContent());

		final JSONObject json = new JSONObject(jsonResponse);

		final JSONArray objects = json.getJSONArray("objects");
		Assert.assertNotNull(objects);
		Assert.assertEquals("More than one ripe node found for id #4575", 1,
				objects.length());

		final JSONObject ripeNode = objects.getJSONObject(0);
		Assert.assertNotNull(ripeNode);
		Assert.assertEquals("Ripe Node #4575 is not connected!", 1,
				ripeNode.getInt("status"));
		Assert.assertEquals("Ripe Node #4575 is not connected!", "Connected",
				ripeNode.getString("status_name"));
	}
}
