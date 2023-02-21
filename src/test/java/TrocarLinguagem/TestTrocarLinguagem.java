package TrocarLinguagem;

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

@DisplayName("Testes trocar linguagem")
public class TestTrocarLinguagem {
    private static JSONObject jsonObject;
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException, ParseException {
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\Tomasia\\IdeaProjects\\Login\\src\\test\\java\\TrocarLinguagem\\dados.json"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @AfterEach
    public void tearDown() {
        //driver.quit();
    }

    @Test
    @DisplayName("Validar troca de linguagem para o English")
    public void testValidarTrarLingEN() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("dropdown-toggle")).click();
        driver.findElement(By.id("actionmenuaction-1")).click();
        String resultado = driver.findElement(By.className("loginform")).findElement(By.tagName("h1")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultadoEsperadoEN").toString()));

    }

    @Test
    @DisplayName("Validar troca de linguagem  do English para o Português")
    public void testValidarTrocaLingEnPt() {

        driver.get (jsonObject.get("url").toString());
        driver.findElement(By.className("dropdown-toggle")).click();
        driver.findElement(By.id("actionmenuaction-1")).click();
        driver.findElement(By.className("dropdown-toggle")).click();
        driver.findElement(By.id("actionmenuaction-2")).click();
        String resultado = driver.findElement(By.className("loginform")).findElement(By.tagName("h1")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultadoEsperadoPT").toString()));

    }
}
