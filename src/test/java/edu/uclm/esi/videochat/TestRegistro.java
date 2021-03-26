package edu.uclm.esi.videochat;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.uclm.esi.videochat.springdao.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestRegistro {
	private WebDriver chrome;

	ArrayList<WebDriver> drivers = new ArrayList<>();

	String[] nombres = { "Ana", "Pepe", "Lucas" };
	String[] contrasenyas = { "Ana123", "Pepe123", "Lucas123" };

	@Autowired
	UserRepository usersRepo;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "[RUTA DEL WEBDRIVER]");
//		System.setProperty("webdriver.gecko.driver", "/Users/macariopolousaola/Downloads/geckodriver");

		cargarCaras();

	}

	private void cargarCaras() throws Exception {
		String outputFolder = System.getProperty("java.io.tmpdir");
		if (!outputFolder.endsWith("/"))
			outputFolder += "/";

		CloseableHttpClient client = HttpClients.createDefault();
		for (int i = 0; i < this.nombres.length; i++) {
			System.out.println("Bajando foto " + (i + 1) + "/" + nombres.length);
			HttpGet get = new HttpGet("https://thispersondoesnotexist.com/image");
			CloseableHttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			byte[] image = EntityUtils.toByteArray(entity);
			try (FileOutputStream fos = new FileOutputStream(outputFolder + "cara" + i + ".jpeg")) {
				fos.write(image);
			}
		}
		client.close();
	}

	@After
	public void tearDown() {
		for (int i = 0; i < drivers.size(); i++)
			drivers.get(i).close();
	}

	@Test
	@Order(1)
	public void registrar() {
		chrome = new ChromeDriver();

		chrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		chrome.get("https://localhost:443/");
		chrome.manage().window().setSize(new Dimension(1161, 977));
		chrome.manage().window().setPosition(new Point(0, 0));

		try {
			chrome.findElement(By.id("details-button")).click();
			chrome.findElement(By.id("proceed-link")).click();
		} catch (NoSuchElementException e) {
			System.out.println(e);
		}

		String inputFolder = System.getProperty("java.io.tmpdir");
		if (!inputFolder.endsWith("/"))
			inputFolder += "/";

		String picturePath;
		String script = "window.scrollTo(0,1000)";
		JavascriptExecutor je = (JavascriptExecutor) chrome;

		for (int i = 0; i < nombres.length; i++) {
			chrome.findElement(By.linkText("Crear cuenta")).click();

			WebElement cajaNombre = chrome
					.findElement(By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/input[1]"));
			WebElement cajaEmail = chrome
					.findElement(By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/input[2]"));
			WebElement cajaPwd1 = chrome
					.findElement(By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/input[3]"));
			WebElement cajaPwd2 = chrome
					.findElement(By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/input[4]"));
			RemoteWebElement inputFile = (RemoteWebElement) chrome
					.findElement(By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/input[5]"));

			cajaNombre.sendKeys(nombres[i]);
			cajaEmail.sendKeys(nombres[i] + "@gmail.com");
			cajaPwd1.sendKeys(contrasenyas[i]);
			cajaPwd2.sendKeys(contrasenyas[i]);

			LocalFileDetector detector = new LocalFileDetector();
			picturePath = inputFolder + "cara" + i + ".jpeg";
			File file = detector.getLocalFile(picturePath);
			inputFile.setFileDetector(detector);
			inputFile.sendKeys(file.getAbsolutePath());

			je.executeScript(script);

			WebElement botonCrearCuenta = chrome.findElement(By.id("btnCrearCuenta"));
			botonCrearCuenta.click();

			new WebDriverWait(chrome, 60).ignoring(NoAlertPresentException.class)
					.until(ExpectedConditions.alertIsPresent());

			assertThat(chrome.switchTo().alert().getText(), is("Registrado correctamente"));
			chrome.switchTo().alert().accept();
		}

		chrome.quit();
	}

	@Test
	@Order(2)
	public void login() {

		for (int i = 0; i < nombres.length; i++) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--use-fake-ui-for-media-stream", "--use-fake-device-for-media-stream",
					"--disable-user-media-security", "--auto-open-devtools-for-tabs", "--incognito");

			ChromeDriver driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1920,1080));
			driver.manage().window().setPosition(new Point(0, 0));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.get("https://localhost:443");
			try {
				driver.findElement(By.id("details-button")).click();
				driver.findElement(By.id("proceed-link")).click();
			} catch (NoSuchElementException e) {
				System.out.println(e);
			}
			WebElement cajaNombre = driver.findElement(
					By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/div[1]/div[1]/input"));
			WebElement cajaPwd = driver.findElement(
					By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/div[1]/div[2]/input"));
			WebElement btnEntrar = driver.findElement(
					By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div/div[1]/div[3]/button"));

			cajaNombre.clear();
			cajaPwd.clear();
			cajaNombre.sendKeys(nombres[i]);
			cajaPwd.sendKeys(contrasenyas[i]);
			btnEntrar.click();
			drivers.add(driver);
		}
		
		
		for (WebDriver driverUsuario : drivers) {
			// Vídeo local 
			WebElement btn = driverUsuario
					.findElement(By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div[2]/button[1]"));
			btn.click();
			// Crear conexión 
			btn = driverUsuario
					.findElement(By.xpath("//*[@id=\"globalBody\"]/oj-module/div[1]/div[2]/div/div/div[2]/button[2]"));
			btn.click();
			
		}
		
		//Pepe busca a Ana y la llama
		List<WebElement> divUsuarios = drivers.get(1).findElement(By.id("usuarios")).findElements(By.tagName("div"));
		WebElement btn = null;
		for (WebElement divUsuario : divUsuarios) {
			WebElement anchor = divUsuario.findElement(By.tagName("a"));
			if (anchor.getText().equals("Ana")) {
				btn = divUsuario.findElements(By.tagName("button")).get(0);
				btn.click();
				break;
			}
		}
				
		try {
			Thread.sleep(500);//Espera de medio segundo
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		drivers.get(0).switchTo().alert().dismiss(); //Ana rechaza la llamada
		try {
			Thread.sleep(500);//Espera de medio segundo
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		drivers.get(1).switchTo().alert().accept(); //Pepe acepta el mensaje de rechazo
		
		
		//Ana busca a Lucas y le llama
		List<WebElement> divUsuarios2 = drivers.get(0).findElement(By.id("usuarios")).findElements(By.tagName("div"));
		for (WebElement divUsuario : divUsuarios2) {
			WebElement anchor = divUsuario.findElement(By.tagName("a"));
			if (anchor.getText().equals("Lucas")) {
				btn = divUsuario.findElements(By.tagName("button")).get(0);
				btn.click();
				break;
			}
		}
		
		try {
			Thread.sleep(500);//Espera de medio segundo
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		assertThat(drivers.get(2).switchTo().alert().getText(), is("Te llama Ana. ¿Contestar?\n"));
		drivers.get(2).switchTo().alert().accept();

	}
}