package de.hackerspacebremen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public final class FreifunkRouterTest {

	private static JSONObject json;
	
	private static Map<String, JSONObject> nodes = new HashMap<>();
	
	@BeforeClass
	public static void init() throws ClientProtocolException, IOException{
		final HttpClient httpclient = HttpClientBuilder.create().build();
		final HttpGet httpget = new HttpGet(
				"http://downloads.bremen.freifunk.net/data/nodes.json");

		final HttpResponse response = httpclient.execute(httpget);

		final String jsonResponse = IOUtils.toString(response.getEntity()
				.getContent());

		json = new JSONObject(jsonResponse);
		
		final JSONArray nodesArray = json.getJSONArray("nodes");
		for(int i=0;i<nodesArray.length();i++){
			final JSONObject node = nodesArray.getJSONObject(i);
			nodes.put(node.getString("id"), node);
		}
	}
	
	public void checkMainroomFreifunkRouter(){
		final JSONObject node = nodes.get("90:f6:52:f2:5a:dc");
		
		Assert.assertNotNull(node);
	}
	
	@Test(enabled=false)
	public void checkEWerkstattFreifunkRouter(){
		final JSONObject node = nodes.get("???");
		
		Assert.assertNotNull(node);
	}
}
