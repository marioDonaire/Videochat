package edu.uclm.esi.videochat.model;

import javax.json.JsonArray;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Ps {

	public static void main(String[] args) throws Exception {
		String url = "https://12345678901234567890123456789012@mcbro.es/api/products/?output_format=JSON";
		JSONObject jsonproducts = getJSON(url);
		System.out.println(jsonproducts);
		
		JSONArray jsoProduct = jsonproducts.getJSONArray("products");
		for(int i=0; i<jsoProduct.length();i++) {
			JSONObject jsaProduct = jsoProduct.getJSONObject(i);
			int id = jsaProduct.getInt("id");
			url = "https://12345678901234567890123456789012@mcbro.es/api/products/"+id+"/?output_format=JSON";
			JSONObject jsonDetalles = getJSON(url);
			System.out.println(jsonDetalles);
		}
	}
	
	private static JSONObject getJSON(String url) throws Exception {
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(request); HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			if (result.length()>0 && !result.equals("[]") && !result.equals("{}"))
				return new JSONObject(result); 
			return null;
		}
	}
}
