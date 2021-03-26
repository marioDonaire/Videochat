package edu.uclm.esi.videochat;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class Conversora {

	public static void main(String[] args) throws Exception {
		cargarCaras();
		for(int i=1;i<=10;i++) {
			String outputFolder = System.getProperty("java.io.tmpdir");
			if (!outputFolder.endsWith("/"))
				outputFolder+="/";
			FileInputStream fi= new FileInputStream(outputFolder+"cara" + i + ".jpeg");
			byte[] b = new byte[fi.available()];
			fi.read(b);
			System.out.println("convirtiendo foto " + i + "/");
			String imagen ="data:image/png;base64," + Base64.getEncoder().encodeToString(b); 
			System.out.println(imagen);
			fi.close();
		}
		
		
	}
	
	 private static void cargarCaras() throws Exception {
		String outputFolder = System.getProperty("java.io.tmpdir");
		if (!outputFolder.endsWith("/"))
			outputFolder+="/";
		
		CloseableHttpClient client = HttpClients.createDefault();
		for (int i=1; i<=10; i++) {
			System.out.println("Bajando foto " + i + "/");
			HttpGet get = new HttpGet("https://thispersondoesnotexist.com/image");
			CloseableHttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			byte[] image = EntityUtils.toByteArray(entity);
			try(FileOutputStream fos = new  FileOutputStream(outputFolder + "cara" + i + ".jpeg")) {
				fos.write(image);
			}
		}
		client.close();
	}
	
}
