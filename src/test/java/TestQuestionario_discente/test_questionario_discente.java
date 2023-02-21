package TestQuestionario_discente;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Testes questionário perspectiva docente (múltipla escolha)")
public class test_questionario_discente {
    private static JSONObject jsonObject;
    private WebDriver driver;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException, ParseException {
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\sabri\\IdeaProjects\\TestesMoodle\\src\\test\\java\\TestQuestionario_discente\\dados.json"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

    }
    @AfterEach
    public void tearDown() {
        //driver.quit();
    }

    @Test
    @DisplayName("Teste múltipla escolha, v/f, associação, resposta curta e numérica")
    public void testeResponderQuestionarioTodasQuestoes() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricola

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        List<WebElement> listaTeste = driver.findElements(By.className("activity-instance"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                for (WebElement top : listaTeste) {
                    if (top.getText().contains("Questionário sobre plantação")) {
                        top.findElement(By.tagName("a")).click();
                        break;
                    }
                }
                break;
            }
        }


        Thread.sleep(3000);
        driver.findElement(By.xpath("//form/button")).click();

        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//div[2]/div/div/input[2]")).click();
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//div[2]/input[2]")).click();

        Thread.sleep(3000);
        driver.findElement(By.className("submitbtns")).findElement(By.tagName("input")).click();//botão próximo

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//div[2]/div/div/input")).click();

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[2]/input[2]")).click();//botão próximo

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//select")).findElements(By.tagName("option")).get(1).click();
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//tr[2]/td[2]/select")).findElements(By.tagName("option")).get(2).click();

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[2]/input[2]")).click();//botão próximo

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(0).findElement(By.className("form-control")).sendKeys(jsonObject.get("resp04").toString());

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[2]/input[2]")).click();//botão próximo

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(0).findElement(By.className("form-control")).sendKeys(jsonObject.get("resp5").toString());

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[2]/input[2]")).click();//botão próximo


        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[5]/div/div/form/button")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/button[2]")).click();


        String resultado = driver.findElement(By.xpath("//tr[2]/td")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));


    }

    @Test
    @DisplayName("Teste layout diferente")
    public void testeResponderQuestionariolayout() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricola

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        List<WebElement> listaTeste = driver.findElements(By.className("activity-instance"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                for (WebElement top : listaTeste) {
                    if (top.getText().contains("Questionário Mudando layout e comportamento")) {
                        top.findElement(By.tagName("a")).click();
                        break;
                    }
                }
                break;
            }
        }


        Thread.sleep(3000);
        driver.findElement(By.xpath("//form/button")).click();

        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//div[2]/div/div/input[2]")).click();
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//div[2]/input[2]")).click();
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//label[3]/input")).click();
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//form/div/div/div[2]/div/div[3]/input")).click();

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(1).findElement(By.xpath("//div[2]/div[2]/div/div[2]/div/div/input")).click();
        driver.findElements(By.className("que")).get(1).findElement(By.xpath("//div[2]/div[2]/div/div[3]/div/label/input")).click();
        driver.findElements(By.className("que")).get(1).findElement(By.xpath("//div[2]/div[2]/div/div[3]/input")).click();

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(2).findElement(By.xpath("//select")).findElements(By.tagName("option")).get(1).click();
        driver.findElements(By.className("que")).get(2).findElement(By.xpath("//tr[2]/td[2]/select")).findElements(By.tagName("option")).get(2).click();
        driver.findElements(By.className("que")).get(2).findElement(By.xpath("//div[3]/div[2]/div/div[3]/div/label[2]")).click();
        driver.findElements(By.className("que")).get(2).findElement(By.xpath("//div[3]/div[2]/div/div[3]/input")).click();

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(3).findElement(By.className("form-control")).sendKeys(jsonObject.get("resp04").toString());
        driver.findElements(By.className("que")).get(3).findElement(By.xpath("//div[4]/div[2]/div/div[3]/div/label[2]/input")).click();
        driver.findElements(By.className("que")).get(3).findElement(By.xpath("//div[4]/div[2]/div/div[3]/input")).click();

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(4).findElement(By.className("form-control")).sendKeys(jsonObject.get("resp5").toString());
        driver.findElements(By.className("que")).get(4).findElement(By.xpath("//div[5]/div[2]/div/div[3]/div/label/input")).click();
        driver.findElements(By.className("que")).get(4).findElement(By.xpath("//div[5]/div[2]/div/div[3]/input")).click();

        Thread.sleep(3000);
        driver.findElement(By.className("submitbtns")).findElement(By.tagName("input")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[5]/div/div/form/button")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/button[2]")).click();

        //tr[2]/td
        String resultado = driver.findElement(By.xpath("//tr[2]/td")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));


    }

    @Test
    @DisplayName("Teste multipla escolha (opção única escolha) e v/f (falso certo)")
    public void testeResponderQuestionarioME() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricola

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        List<WebElement> listaTeste = driver.findElements(By.className("activity-instance"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                for (WebElement top : listaTeste) {
                    if (top.getText().contains("Questionário sobre insetos")) {
                        top.findElement(By.tagName("a")).click();
                        break;
                    }
                }
                break;
            }
        }


        Thread.sleep(3000);
        driver.findElement(By.xpath("//form/button")).click();

        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//div[2]/div/div/input")).click();

        Thread.sleep(3000);
        driver.findElement(By.xpath("//form/div/div[2]/input")).click();//botão próximo

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//div[2]/input")).click();

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[2]/input[2]")).click();//botão próximo

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[5]/div/div/form/button")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/button[2]")).click();


        //String resultado = driver.findElement(By.xpath("//tr[2]/td")).getText();
        //System.out.println(resultado);
        //assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));


    }

    @Test
    @DisplayName("Teste associação")
    public void testeResponderQuestionarioASS() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricola

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        List<WebElement> listaTeste = driver.findElements(By.className("activity-instance"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                for (WebElement top : listaTeste) {
                    if (top.getText().contains("Questionário sobre Agrícola02")) {
                        top.findElement(By.tagName("a")).click();
                        break;
                    }
                }
                break;
            }
        }


        Thread.sleep(3000);
        driver.findElement(By.xpath("//form/button")).click();

        Thread.sleep(3000);
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//select")).findElements(By.tagName("option")).get(1).click();
        driver.findElements(By.className("que")).get(0).findElement(By.xpath("//tr[2]/td[2]/select")).findElements(By.tagName("option")).get(2).click();

        Thread.sleep(3000);
        driver.findElement(By.xpath("//form/div/div[2]/input")).click();//botão próximo

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[5]/div/div/form/button")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/button[2]")).click();


        String resultado = driver.findElement(By.xpath("//tr[2]/td")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));


    }

}
