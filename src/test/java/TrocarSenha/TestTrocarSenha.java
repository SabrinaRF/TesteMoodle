package TrocarSenha;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes trocar senha")
public class TestTrocarSenha {
    private static JSONObject jsonObject;
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException, ParseException {
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\Tomasia\\IdeaProjects\\Login\\src\\test\\java\\TrocarSenha\\dados.json"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Validar recuperação de senha pelo identificador do usuário")
    public void testValidarRecupSenhaIdentUsuario() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("login-form-forgotpassword")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("id_username")).sendKeys(jsonObject.get("matricula").toString());
        driver.findElement(By.id("id_submitbuttonusername")).click();
        String resultado = driver.findElement(By.id("notice")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));

    }
    @Test
    @DisplayName("Validar recuperação de senha pelo email")
    public void testValidarRecupSenhaEmail() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("login-form-forgotpassword")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("id_email")).sendKeys(jsonObject.get("email").toString());
        driver.findElement(By.id("id_submitbuttonemail")).click();
        String resultado = driver.findElement(By.id("notice")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado2").toString()));

    }

    @Test
    @DisplayName("Falha na recuperação de senha pelo identificador do usuário")
    public void testFalhaRecupSenhaIdentUsuario() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("login-form-forgotpassword")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("id_username")).sendKeys(jsonObject.get("matriculaIncorreta").toString());
        driver.findElement(By.id("id_submitbuttonusername")).click();
        String resultado = driver.findElement(By.id("notice")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado3").toString()));

    }

    @Test
    @DisplayName("Falha na recuperação de senha pelo email")
    public void testFalhaRecupSenhaEmail() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("login-form-forgotpassword")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("id_email")).sendKeys(jsonObject.get("emailIncorreto").toString());
        driver.findElement(By.id("id_submitbuttonemail")).click();
        String resultado = driver.findElement(By.id("id_error_email")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado4").toString()));

    }

    @Test
    @DisplayName("Falha na recuperação de senha pelo não preenchimento do campo identificador de usuário")
    public void testFalhaRecupSenhaCampoVazioMatricula() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("login-form-forgotpassword")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("id_username")).sendKeys(jsonObject.get("matriculaVazia").toString());
        driver.findElement(By.id("id_submitbuttonusername")).click();
        String resultado = driver.findElement(By.id("id_error_username")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado5").toString()));

    }

    @Test
    @DisplayName("Falha na recuperação de senha pelo não preenchimento do campo email")
    public void testFalhaRecupSenhaCampoVazioEmail() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("login-form-forgotpassword")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("id_email")).sendKeys(jsonObject.get("matriculaVazia").toString());
        driver.findElement(By.id("id_submitbuttonemail")).click();
        String resultado = driver.findElement(By.id("id_error_email")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado6").toString()));

    }

}
