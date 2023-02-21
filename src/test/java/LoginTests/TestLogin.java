package LoginTests;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@DisplayName("Testes do Login")
public class TestLogin {
    private static  JSONObject jsonObject;
    private WebDriver driver;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException, ParseException {
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\Tomasia\\IdeaProjects\\Login\\src\\test\\java\\LoginTests\\dados.json"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

    }
    @AfterEach
    public void tearDown() {
       driver.quit();
    }

    @Test
    @DisplayName("Validar login")
    public void testLoginValidar() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());
        driver.findElement(By.id("loginbtn")).click();
        String resultado = driver.findElement(By.tagName("h2")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));
    }

    @Test
    @DisplayName("Falha no login pela matrícula")
    public void testLoginErroMatricula() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaErrada").toString());
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());
        driver.findElement(By.id("loginbtn")).click();
        String resultado = driver.findElement(By.id("loginerrormessage")).getText();

        assertEquals(jsonObject.get("resultado2").toString(), resultado);
        //assertTrue(resultado2.contains("Nome de usuário ou senha errados. Por favor tente outra vez."));
    }

    @Test
    @DisplayName("Falha no login pela senha")
    public void testLoginErroSenha() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaErrada").toString());
        driver.findElement(By.id("loginbtn")).click();
        String resultado = driver.findElement(By.id("loginerrormessage")).getText();

        assertEquals(jsonObject.get("resultado3").toString(), resultado);

    }

    @Test
    @DisplayName("Falha no login pela matricula e senha")
    public void testLoginErroMatriculaSenha() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaErrada").toString());
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaErrada").toString());
        driver.findElement(By.id("loginbtn")).click();
        String resultado = driver.findElement(By.id("loginerrormessage")).getText();

        assertEquals(jsonObject.get("resultado4").toString(), resultado);

    }

    @Test
    @DisplayName("Falha no login pelos campos sem preencher")
    public void testLoginErroSemDados() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaZero").toString());
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaZero").toString());
        driver.findElement(By.id("loginbtn")).click();
        String resultado = driver.findElement(By.id("loginerrormessage")).getText();

        assertEquals(jsonObject.get("resultado5").toString(), resultado);

    }

    @Test
    @DisplayName("Falha no login como visitante")
    public void testLoginErroVisitante() {

        driver.get(jsonObject.get("url").toString());
        driver.findElement(By.id("loginguestbtn")).click();
        List<WebElement> listaCursos = driver.findElements(By.className("coursebox"));

        for(WebElement curso : listaCursos) {
            if(curso.getText().contains("Engenharia Agrícola (Grupo 4)")) {
                curso.findElement(By.tagName("h3")).click();
                break;
            }
        }
        String resultado = driver.findElement(By.id("notice")).getText();
        assertTrue(resultado.contains(jsonObject.get("resultado6").toString()));
    }


}

