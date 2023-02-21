package TestQuestionario_docente;
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
public class test_questionario_docente {
    private static JSONObject jsonObject;
    private WebDriver driver;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException, ParseException {
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\sabri\\IdeaProjects\\TestesMoodle\\src\\test\\java\\TestQuestionario_docente\\dados.json"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

    }
    @AfterEach
    public void tearDown() {
        //driver.quit();
    }

    @Test
    @DisplayName("Teste múltipla escolha, v/f, associação, resposta curta e numérica")
    public void testeCriarQuestionarioTodasQuestoes() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for(WebElement topico : listaTopicos) {
            if(topico.getText().contains("Biologia")){
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP =driver.findElements(By.className("option"));
        for (WebElement item : listaOP){
            if (item.getText().contains("Questionário")){
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//label/input")).click();//múltipla escolha
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestao").toString());// adiciona o nome da questão
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestao").toString());// adiciona a texto questão
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackQuestao").toString());// adiciona a feedback
        driver.findElement(By.id("id_single")).findElements(By.tagName("option")).get(0).click();

        //RESPOSTAS
        driver.findElement(By.id("id_answer_0editable")).sendKeys(jsonObject.get("opicao1").toString());// adiciona a opção 1
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(9).click();
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackOp1").toString());// adiciona o feedback opção 1

        //RESPOSTAS
        driver.findElement(By.id("id_answer_1editable")).sendKeys(jsonObject.get("opicao2").toString());// adiciona a opção 2
        driver.findElement(By.id("id_fraction_1")).findElements(By.tagName("option")).get(9).click();
        driver.findElement(By.id("id_feedback_1editable")).sendKeys(jsonObject.get("feedbackOp2").toString());// adiciona o feedback opção 2

        //RESPOSTAS
        driver.findElement(By.id("id_answer_2editable")).sendKeys(jsonObject.get("opicao3").toString());// adiciona a opção 3
        driver.findElement(By.id("id_fraction_2")).findElements(By.tagName("option")).get(0).click();
        driver.findElement(By.id("id_feedback_2editable")).sendKeys(jsonObject.get("feedbackOp3").toString());// adiciona o feedback opção 3

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP2 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP2){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }
        //driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        //driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão

        driver.findElement(By.xpath("//div[2]/label/input")).click();//V/F
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoVF").toString());// adiciona o nome da questão v/f
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoVF").toString());// adiciona a texto questão v/f
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackVF").toString());// adiciona a feedback v/f

        driver.findElement(By.id("id_correctanswer")).findElements(By.tagName("option")).get(1).click();//

        driver.findElement(By.id("id_feedbacktrueeditable")).sendKeys(jsonObject.get("feedbackVerdadeiro").toString());// adiciona a feedback verdadeiro v/f
        driver.findElement(By.id("id_feedbackfalseeditable")).sendKeys(jsonObject.get("feedbackFalso").toString());// adiciona a feedback falso v/f

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO V/F

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP3 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP3){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }
        Thread.sleep(4000);
        driver.findElement(By.xpath("//div[3]/label/input")).click();//Associação
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoASS").toString());// adiciona o nome da questão associação
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoASS").toString());// adiciona a texto questão associação
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackASS").toString());// adiciona a feedback associação

        //QUESTÃO 1
        driver.findElement(By.id("id_subquestions_0editable")).sendKeys(jsonObject.get("questao1").toString());// adiciona quastão 1
        driver.findElement(By.id("id_subanswers_0")).sendKeys(jsonObject.get("resp1").toString());// adiciona resposta questão 1

        //QUESTÃO 2
        driver.findElement(By.id("id_subquestions_1editable")).sendKeys(jsonObject.get("questao2").toString());// adiciona quastão 2
        driver.findElement(By.id("id_subanswers_1")).sendKeys(jsonObject.get("resp2").toString());// adiciona resposta questão 2

        //QUESTÃO 3
       // driver.findElement(By.id("id_subquestions_2editable")).sendKeys(jsonObject.get("questao2").toString());// adiciona quastão 3
        driver.findElement(By.id("id_subanswers_2")).sendKeys(jsonObject.get("resp2").toString());// adiciona resposta questão 3

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO ASSOCIAÇÃO

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP4 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP4){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }

        driver.findElement(By.xpath("//div[4]/label/input")).click();//Respopsta curta
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoResp").toString());// adiciona o nome da questão resposta curta
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoResp").toString());// adiciona a texto questão resposta curta
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackResp").toString());// adiciona a feedback resposta curta

        //RESPOSTA 1
        driver.findElement(By.id("id_answer_0")).sendKeys(jsonObject.get("respCurta").toString());// adiciona resposta curta
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackResposta1").toString());// adiciona feedback resposta
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(1).click();

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO RESPOSTA CURTA

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP5 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP5){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }

        driver.findElement(By.xpath("//div[5]/label/input")).click();//Numerico
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoNum").toString());// adiciona o nome da questão resposta curta
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoNum").toString());// adiciona a texto questão resposta curta
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackNum").toString());// adiciona a feedback resposta curta

        driver.findElement(By.id("id_answer_0")).sendKeys(jsonObject.get("respNum1").toString());
        driver.findElement(By.id("id_tolerance_0")).sendKeys(jsonObject.get("erroNum1").toString());
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(1).click();
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackNum01").toString());

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO NUMERICO

        //VOLTAR PARA O QUESTIONÁRIO
        Thread.sleep(4000);
        driver.findElement(By.xpath("//div[2]/nav/ul/li/a")).click();
        driver.findElement(By.xpath("//form/button")).click();

    }

    @Test
    @DisplayName("Teste múltipla escolha (nome inexistente)")
    public void testeCriarQuestME01() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome01").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao01").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen01").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose01").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose01").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose01").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//label/input")).click();//múltipla escolha
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoME1").toString());// não adiciona o nome da questão

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();

        String erro = driver.findElement(By.id("id_error_name")).getText();
        System.out.println(erro);
        assertTrue(erro.contains(jsonObject.get("resultadoERRO01").toString()));
    }

    @Test
    @DisplayName("Teste múltipla escolha (texto da questão inexistente)")
    public void testeCriarQuestME02() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome01").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao01").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen01").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose01").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose01").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose01").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//label/input")).click();//múltipla escolha
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoME02").toString());// não adiciona o nome da questão

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();

        String erro = driver.findElement(By.id("id_error_questiontext")).getText();
        System.out.println(erro);
        assertTrue(erro.contains(jsonObject.get("resultadoERRO02").toString()));
    }

    @Test
    @DisplayName("Teste múltipla escolha (opção única escolha) e v/f (falso certo)")
    public void testeCriarQuestME03VF01() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome03").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao03").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen03").toString());
        Thread.sleep(2000);
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose03").toString());
        Thread.sleep(2000);
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose03").toString());
        Thread.sleep(2000);
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose03").toString());
        Thread.sleep(2000);

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa03").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//label/input")).click();//múltipla escolha
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestao03").toString());// adiciona o nome da questão
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestao03").toString());// adiciona a texto questão
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackQuestao03").toString());// adiciona a feedback
        driver.findElement(By.id("id_single")).findElements(By.tagName("option")).get(1).click();

        //RESPOSTAS
        driver.findElement(By.id("id_answer_0editable")).sendKeys(jsonObject.get("opicao103").toString());// adiciona a opção 1
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(1).click();
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackOp103").toString());// adiciona o feedback opção 1

        //RESPOSTAS
        driver.findElement(By.id("id_answer_1editable")).sendKeys(jsonObject.get("opicao203").toString());// adiciona a opção 2
        driver.findElement(By.id("id_fraction_1")).findElements(By.tagName("option")).get(0).click();
        driver.findElement(By.id("id_feedback_1editable")).sendKeys(jsonObject.get("feedbackOp203").toString());// adiciona o feedback opção 2

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP2 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP2){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }
        driver.findElement(By.xpath("//div[2]/label/input")).click();//V/F
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoVF01").toString());// adiciona o nome da questão v/f
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoVF01").toString());// adiciona a texto questão v/f
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackVF01").toString());// adiciona a feedback v/f

        driver.findElement(By.id("id_correctanswer")).findElements(By.tagName("option")).get(0).click();//

        driver.findElement(By.id("id_feedbacktrueeditable")).sendKeys(jsonObject.get("feedbackVerdadeiro01").toString());// adiciona a feedback verdadeiro v/f
        driver.findElement(By.id("id_feedbackfalseeditable")).sendKeys(jsonObject.get("feedbackFalso01").toString());// adiciona a feedback falso v/f

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO V/F

    }

    @Test
    @DisplayName("Teste V/F (nome inexistente)")
    public void testeCriarQuestVF02() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome01").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao01").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen01").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose01").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose01").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose01").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//div[2]/label/input")).click();//V/F
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoVF02").toString());// adiciona o nome da questão v/f

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO V/F

        String erro = driver.findElement(By.id("id_error_name")).getText();
        System.out.println(erro);
        assertTrue(erro.contains(jsonObject.get("resultadoERRO03").toString()));
    }

    @Test
    @DisplayName("Teste  V/F (texto da questão inexistente)")
    public void testeCriarQuestVF03() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome01").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao01").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen01").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose01").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose01").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose01").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//div[2]/label/input")).click();//V/F
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoVF03").toString());// não adiciona o nome da questão

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();

        String erro = driver.findElement(By.id("id_error_questiontext")).getText();
        System.out.println(erro);
        assertTrue(erro.contains(jsonObject.get("resultadoERRO04").toString()));
    }

    @Test
    @DisplayName("Teste associação (2 questões 3 respostas)")
    public void testeCriarQuestASS01() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome01").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao01").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen01").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose01").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose01").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose01").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//div[3]/label/input")).click();//Associação
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoASS01").toString());// adiciona o nome da questão associação
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoASS01").toString());// adiciona a texto questão associação
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackASS01").toString());// adiciona a feedback associação

        //QUESTÃO 1
        driver.findElement(By.id("id_subquestions_0editable")).sendKeys(jsonObject.get("questao101").toString());// adiciona quastão 1
        driver.findElement(By.id("id_subanswers_0")).sendKeys(jsonObject.get("resp101").toString());// adiciona resposta questão 1

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO ASSOCIAÇÃO

        String erro = driver.findElement(By.id("id_error_subquestions_1")).getText();
        System.out.println(erro);
        assertTrue(erro.contains(jsonObject.get("resultadoERRO05").toString()));
    }

    @Test
    @DisplayName("Teste associação (2 questões 3 respostas) 02")
    public void testeCriarQuestASS02() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome011").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao01").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen01").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose01").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose01").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose01").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//div[3]/label/input")).click();//Associação
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoASS01").toString());// adiciona o nome da questão associação
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoASS01").toString());// adiciona a texto questão associação
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackASS01").toString());// adiciona a feedback associação

        //QUESTÃO 1 Questionário sobre Agrícola
        driver.findElement(By.id("id_subquestions_0editable")).sendKeys(jsonObject.get("questao101").toString());// adiciona quastão 1
        driver.findElement(By.id("id_subanswers_0")).sendKeys(jsonObject.get("resp101").toString());// adiciona resposta questão 1

        //QUESTÃO 2
        driver.findElement(By.id("id_subquestions_1editable")).sendKeys(jsonObject.get("questao2").toString());// adiciona quastão 2
        driver.findElement(By.id("id_subanswers_1")).sendKeys(jsonObject.get("resp2").toString());// adiciona resposta questão 2

        //QUESTÃO 3
        // driver.findElement(By.id("id_subquestions_2editable")).sendKeys(jsonObject.get("questao2").toString());// adiciona quastão 3
        driver.findElement(By.id("id_subanswers_2")).sendKeys(jsonObject.get("resp2").toString());// adiciona resposta questão 3

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO ASSOCIAÇÃO

    }

    @Test
    @DisplayName("Teste numerico (100%)")
    public void testeCriarQuestNUM01() throws InterruptedException {
        driver.get(jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for (WebElement topico : listaTopicos) {
            if (topico.getText().contains("Biologia")) {
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP = driver.findElements(By.className("option"));
        for (WebElement item : listaOP) {
            if (item.getText().contains("Questionário")) {
                item.findElement(By.tagName("a")).click();
                //item.findElement(By.xpath("//div[2]/div/div/div[2]/div/a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome05").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao05").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen01").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose01").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose01").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose01").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(1).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//div[5]/label/input")).click();//Numerico
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoNum").toString());// adiciona o nome da questão resposta curta
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoNum02").toString());// adiciona a texto questão resposta curta
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackNum02").toString());// adiciona a feedback resposta curta

        driver.findElement(By.id("id_answer_0")).sendKeys(jsonObject.get("respNum2").toString());
        driver.findElement(By.id("id_tolerance_0")).sendKeys(jsonObject.get("erroNum2").toString());
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(1).click();
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackNum01").toString());


        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO NUMERICO

        //String erro = driver.findElement(By.id("id_error_subquestions_1")).getText();
       // System.out.println(erro);
        //assertTrue(erro.contains(jsonObject.get("resultadoERRO05").toString()));
    }

    @Test
    @DisplayName("Teste (layout)")
    public void testeCriarQuestLayout() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matricula").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senha").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula

        //Cria uma lista com os topicos do curso e seleciona o que tiver biologia e clica nem adicionar atividade ou recurso
        List<WebElement> listaTopicos = driver.findElements(By.className("section"));
        for(WebElement topico : listaTopicos) {
            if(topico.getText().contains("Biologia")){
                topico.findElement(By.className("activity-add-text")).click();
                break;
            }
        }
        Thread.sleep(6000);

        driver.findElement(By.id("searchinput")).sendKeys(jsonObject.get("pesquisa").toString());// pesquisa "tarefa
        Thread.sleep(4000);//espera um pouquinho

        //Cria uma lista com as opções das atividades do topico e seleciona o que tiver tarefa e clica no link do form da tarefa
        List<WebElement> listaOP =driver.findElements(By.className("option"));
        for (WebElement item : listaOP){
            if (item.getText().contains("Questionário")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nome06").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricao06").toString());// adiciona a descrição

        driver.findElement(By.id("id_timing")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(3000);
        driver.findElement(By.id("id_timeopen_enabled")).click();
        driver.findElement(By.id("id_timeclose_enabled")).click();
        driver.findElement(By.id("id_timeopen_day")).sendKeys(jsonObject.get("diaOpen").toString());
        driver.findElement(By.id("id_timeclose_day")).sendKeys(jsonObject.get("diaClose").toString());
        driver.findElement(By.id("id_timeclose_month")).sendKeys(jsonObject.get("mesClose").toString());
        driver.findElement(By.id("id_timeclose_year")).sendKeys(jsonObject.get("anoClose").toString());

        driver.findElement(By.id("id_modstandardgrade")).findElement(By.className("position-relative")).click();//expande a nota
        driver.findElement(By.id("id_attempts")).sendKeys(jsonObject.get("tentativa").toString());

        driver.findElement(By.id("id_layouthdr")).findElement(By.className("position-relative")).click();//expande a layout
        driver.findElement(By.id("id_questionsperpage")).findElements(By.tagName("option")).get(5).click();

        driver.findElement(By.id("id_interactionhdr")).findElement(By.className("position-relative")).click();//expande a comportamento da questão
        driver.findElement(By.id("id_shuffleanswers")).findElements(By.tagName("option")).get(0).click();
        driver.findElement(By.id("id_preferredbehaviour")).findElements(By.tagName("option")).get(3).click();

        driver.findElement(By.id("id_submitbutton")).click();//salvar questionário

        driver.findElement(By.className("row")).findElement(By.tagName("a")).click();
        driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão
        driver.findElement(By.xpath("//label/input")).click();//múltipla escolha
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestao").toString());// adiciona o nome da questão
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestao").toString());// adiciona a texto questão
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackQuestao").toString());// adiciona a feedback
        driver.findElement(By.id("id_single")).findElements(By.tagName("option")).get(0).click();

        //RESPOSTAS
        driver.findElement(By.id("id_answer_0editable")).sendKeys(jsonObject.get("opicao1").toString());// adiciona a opção 1
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(9).click();
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackOp1").toString());// adiciona o feedback opção 1

        //RESPOSTAS
        driver.findElement(By.id("id_answer_1editable")).sendKeys(jsonObject.get("opicao2").toString());// adiciona a opção 2
        driver.findElement(By.id("id_fraction_1")).findElements(By.tagName("option")).get(9).click();
        driver.findElement(By.id("id_feedback_1editable")).sendKeys(jsonObject.get("feedbackOp2").toString());// adiciona o feedback opção 2

        //RESPOSTAS
        driver.findElement(By.id("id_answer_2editable")).sendKeys(jsonObject.get("opicao3").toString());// adiciona a opção 3
        driver.findElement(By.id("id_fraction_2")).findElements(By.tagName("option")).get(0).click();
        driver.findElement(By.id("id_feedback_2editable")).sendKeys(jsonObject.get("feedbackOp3").toString());// adiciona o feedback opção 3

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP2 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP2){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }
        //driver.findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();
        //driver.findElement(By.xpath("//span/div/div/div/div/a/span")).click();// seleciona + uma nova questão

        driver.findElement(By.xpath("//div[2]/label/input")).click();//V/F
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoVF").toString());// adiciona o nome da questão v/f
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoVF").toString());// adiciona a texto questão v/f
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackVF").toString());// adiciona a feedback v/f

        driver.findElement(By.id("id_correctanswer")).findElements(By.tagName("option")).get(1).click();//

        driver.findElement(By.id("id_feedbacktrueeditable")).sendKeys(jsonObject.get("feedbackVerdadeiro").toString());// adiciona a feedback verdadeiro v/f
        driver.findElement(By.id("id_feedbackfalseeditable")).sendKeys(jsonObject.get("feedbackFalso").toString());// adiciona a feedback falso v/f

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO V/F

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP3 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP3){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }
        Thread.sleep(4000);
        driver.findElement(By.xpath("//div[3]/label/input")).click();//Associação
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoASS").toString());// adiciona o nome da questão associação
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoASS").toString());// adiciona a texto questão associação
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackASS").toString());// adiciona a feedback associação

        //QUESTÃO 1
        driver.findElement(By.id("id_subquestions_0editable")).sendKeys(jsonObject.get("questao1").toString());// adiciona quastão 1
        driver.findElement(By.id("id_subanswers_0")).sendKeys(jsonObject.get("resp1").toString());// adiciona resposta questão 1

        //QUESTÃO 2
        driver.findElement(By.id("id_subquestions_1editable")).sendKeys(jsonObject.get("questao2").toString());// adiciona quastão 2
        driver.findElement(By.id("id_subanswers_1")).sendKeys(jsonObject.get("resp2").toString());// adiciona resposta questão 2

        //QUESTÃO 3
        // driver.findElement(By.id("id_subquestions_2editable")).sendKeys(jsonObject.get("questao2").toString());// adiciona quastão 3
        driver.findElement(By.id("id_subanswers_2")).sendKeys(jsonObject.get("resp2").toString());// adiciona resposta questão 3

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO ASSOCIAÇÃO

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP4 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP4){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }

        driver.findElement(By.xpath("//div[4]/label/input")).click();//Respopsta curta
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoResp").toString());// adiciona o nome da questão resposta curta
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoResp").toString());// adiciona a texto questão resposta curta
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackResp").toString());// adiciona a feedback resposta curta

        //RESPOSTA 1
        driver.findElement(By.id("id_answer_0")).sendKeys(jsonObject.get("respCurta").toString());// adiciona resposta curta
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackResposta1").toString());// adiciona feedback resposta
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(1).click();

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO RESPOSTA CURTA

        driver.findElement(By.className("content")).findElement(By.className("last-add-menu")).findElement(By.tagName("a")).click();

        List<WebElement> listaOP5 =driver.findElements(By.className("dropdown-menu"));
        for (WebElement item : listaOP5){
            if (item.getText().contains("uma nova questão")){
                item.findElement(By.tagName("a")).click();

                break;
            }
        }

        driver.findElement(By.xpath("//div[5]/label/input")).click();//Numerico
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[3]/input")).click();// adicionar questão

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeQuestaoNum").toString());// adiciona o nome da questão resposta curta
        driver.findElement(By.id("id_questiontexteditable")).sendKeys(jsonObject.get("textoQuestaoNum").toString());// adiciona a texto questão resposta curta
        driver.findElement(By.id("id_generalfeedbackeditable")).sendKeys(jsonObject.get("feedbackNum").toString());// adiciona a feedback resposta curta

        driver.findElement(By.id("id_answer_0")).sendKeys(jsonObject.get("respNum1").toString());
        driver.findElement(By.id("id_tolerance_0")).sendKeys(jsonObject.get("erroNum1").toString());
        driver.findElement(By.id("id_fraction_0")).findElements(By.tagName("option")).get(1).click();
        driver.findElement(By.id("id_feedback_0editable")).sendKeys(jsonObject.get("feedbackNum01").toString());

        Thread.sleep(3000);
        driver.findElement(By.id("id_submitbutton")).click();//ENVIAR QUESTÃO NUMERICO

        //VOLTAR PARA O QUESTIONÁRIO
        Thread.sleep(4000);
        driver.findElement(By.xpath("//div[2]/nav/ul/li/a")).click();
        driver.findElement(By.xpath("//form/button")).click();

    }

    @Test
    @DisplayName("Teste misturar questões ")
    public void testeMisturarQuestoes() throws InterruptedException {
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
        driver.findElement(By.xpath("//div[2]/nav/ul/li[3]/a")).click();
        driver.findElement(By.xpath("//span/input")).click();

        //String resultado = driver.findElement(By.xpath("//tr[2]/td")).getText();
        //System.out.println(resultado);
        //assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));


    }

}
