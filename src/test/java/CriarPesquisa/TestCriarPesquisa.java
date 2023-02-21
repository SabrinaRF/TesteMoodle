package CriarPesquisa;

import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.io.IOException;

@DisplayName("Teste criar pesquisa perspectiva docente")
public class TestCriarPesquisa {
    private static JSONObject jsonObject;
    private WebDriver driver;

    @BeforeEach
    public void setUP() throws IOException, ParseException {
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\sabri\\IdeaProjects\\TestesMoodle\\src\\test\\java\\CriarPesquisa\\dados.json"));
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown(){//driver.quit();
    }

    @Test
    @DisplayName("Criar pesquisa")
    public void testCriarPesquisa() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa1").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa1").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback1").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        String resultado = driver.findElement(By.className("mr-auto")).findElement(By.className("page-context-header")).findElement(By.tagName("h1")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));
    }

    @Test
    @DisplayName("Criar pergunta resposta curta")
    public void testCriarPerguntaRespCurta() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa2").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa2").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        Thread.sleep(3000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback2").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(9).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao2").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo2").toString());

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        String resultado = driver.findElement(By.className("itemtitle")).findElement(By.className("itemname")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado2").toString()));
    }

    @Test
    @DisplayName("Criar pergunta ckechbox")
    public void testCriarPerguntaCkeck() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa3").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa3").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        Thread.sleep(3000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback3").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(4).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao3").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo3").toString());
        driver.findElement(By.id("id_hidenoselect")).findElements(By.tagName("option")).get(1).click();//escolher sim
        driver.findElement(By.id("id_values")).sendKeys(jsonObject.get("OP1").toString());
        driver.findElement(By.id("id_values")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("id_values")).sendKeys(jsonObject.get("OP2").toString());
        driver.findElement(By.id("id_values")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("id_values")).sendKeys(jsonObject.get("OP3").toString());
        driver.findElement(By.id("id_values")).sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        String resultado = driver.findElement(By.className("itemtitle")).findElement(By.className("itemname")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado3").toString()));
    }

    @Test
    @DisplayName("Criar pergunta resposta longa")
    public void testCriarPerguntaRespLong() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa4").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa4").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        Thread.sleep(3000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback4").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(6).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao4").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo4").toString());
        driver.findElement(By.id("id_itemwidth")).findElements(By.tagName("option")).get(45).click();

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        String resultado = driver.findElement(By.className("itemtitle")).findElement(By.className("itemname")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado4").toString()));
    }

    @Test
    @DisplayName("Criar pergunta numerica")
    public void testCriarPerguntaNum() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa5").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa5").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        Thread.sleep(3000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback5").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(7).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao5").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo5").toString());
        driver.findElement(By.id("id_rangefrom")).sendKeys(jsonObject.get("valorInicio5").toString());
        driver.findElement(By.id("id_rangeto")).sendKeys(jsonObject.get("valorFim5").toString());


        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        String resultado = driver.findElement(By.className("itemtitle")).findElement(By.className("itemname")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado5").toString()));
    }

    @Test
    @DisplayName("Criar pergunta rótulo")
    public void testCriarPerguntaRotulo() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa6").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa6").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        Thread.sleep(3000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback5").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(8).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_presentation_editoreditable")).sendKeys(jsonObject.get("descricaoRotulo6").toString());

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        String resultado = driver.findElement(By.className("no-overflow")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado6").toString()));
    }

    @Test
    @DisplayName("Visualizar questões")
    public void testVisuQuestoes() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa1").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa1").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback1").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        // PRIMEIRA PERGUNTA
        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(9).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao2").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo2").toString());

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        // SEGUNDA PERGUNTA

        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(4).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao3").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo3").toString());
        driver.findElement(By.id("id_hidenoselect")).findElements(By.tagName("option")).get(1).click();//escolher sim
        driver.findElement(By.id("id_values")).sendKeys(jsonObject.get("OP1").toString());
        driver.findElement(By.id("id_values")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("id_values")).sendKeys(jsonObject.get("OP2").toString());
        driver.findElement(By.id("id_values")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("id_values")).sendKeys(jsonObject.get("OP3").toString());
        driver.findElement(By.id("id_values")).sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        //TERCEIRA PERGUNTA

        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(6).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao4").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo4").toString());
        driver.findElement(By.id("id_itemwidth")).findElements(By.tagName("option")).get(45).click();

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        //QUARTA PERGUNTA

        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(7).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao5").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo5").toString());
        driver.findElement(By.id("id_rangefrom")).sendKeys(jsonObject.get("valorInicio5").toString());
        driver.findElement(By.id("id_rangeto")).sendKeys(jsonObject.get("valorFim5").toString());

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();
        Thread.sleep(2000);//espera um tempinho
        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(1).click();

        String resultado = driver.findElement(By.id("region-main")).findElement(By.tagName("h2")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado7").toString()));
    }

    @Test
    @DisplayName("Excluir pesquisa")
    public void testExcluirPesquisa() throws InterruptedException {
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
        List<WebElement> listaTest = driver.findElements(By.className("activity"));
        for(WebElement topico : listaTopicos) {
            if(topico.getText().contains("Biologia")){
                for(WebElement test : listaTest) {
                    if(test.getText().contains("Pesquisa")) {
                        test.findElement(By.className("activity-basis")).findElement(By.className("activity-actions")).click();
                        break;
                    }
                }
                break;
            }
        }
        driver.findElement(By.xpath("//li[2]/div/div/div[2]/div/div/div/div/div/a[6]/span")).click();
        Thread.sleep(4000);//espera um tempinho
        //driver.findElement(By.xpath("//li/div[2]/button")).click();
        driver.findElement(By.className("modal-content")).findElement(By.className("modal-footer")).findElements(By.tagName("button")).get(1).click();




        //String resultado = driver.findElement(By.className("mr-auto")).findElement(By.className("page-context-header")).findElement(By.tagName("h1")).getText();
        //System.out.println(resultado);
        //assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));
    }

    @Test
    @DisplayName("Criar pergunta utilizado template 1")
    public void testCriarPerguntaUtilizandoTemplate1() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa8").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa8").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        Thread.sleep(3000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback8").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("jump")).findElements(By.tagName("option")).get(1).click();//editar pergunta
        driver.findElement(By.id("feedback_template_course_table_r1_c0")).findElement(By.tagName("a")).click();//escolher tipo de resposta
        driver.findElement(By.className("row")).findElements(By.className("navitem")).get(1).findElement(By.tagName("button")).click();//editar pergunta
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.className("modal-footer")).findElements(By.tagName("button")).get(1).click();//
        Thread.sleep(4000);//espera um pouquinho

        String resultado = driver.findElement(By.id("user-notifications")).findElement(By.className("alert")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado8").toString()));
    }

    @Test
    @DisplayName("Criar pergunta utilizado template 2")
    public void testCriarPerguntaUtilizandoTemplate2() throws InterruptedException {
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
            if (item.getText().contains("Pesquisa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomePesquisa8").toString());///nome da pesquisa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoPesquisa8").toString());//descrição da pesquisa
        driver.findElement(By.id("id_showdescription")).click();//checkbox descrição

        driver.findElement(By.id("id_timinghdr")).findElement(By.className("position-relative")).click();//expande a disponibilidade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeopen")).findElement(By.id("id_timeopen_enabled")).click();//checkbox de disponibilidade
        Thread.sleep(3000);//espera um pouquinho
        driver.findElement(By.id("id_timinghdrcontainer")).findElement(By.id("fitem_id_timeclose")).findElement(By.id("id_timeclose_enabled")).click();//checkbox de disponibilidade

        driver.findElement(By.id("id_feedbackhdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande a configurações
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_anonymous")).findElements(By.tagName("option")).get(1).click();//seleciona alguma conf

        driver.findElement(By.id("id_aftersubmithdr")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande o após envio
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_publish_stats")).findElements(By.tagName("option")).get(1).click();//mostar pagina de analise
        driver.findElement(By.id("id_page_after_submit_editoreditable")).sendKeys(jsonObject.get("feedback8").toString());//descrição feedback

        driver.findElement(By.id("id_activitycompletionheader")).findElement(By.className("position-relative")).findElement(By.tagName("a")).click();//expande conclusao atividade
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_completion")).findElements(By.tagName("option")).get(0).click();//acompanhamento de conclusão

        driver.findElement(By.id("id_submitbutton")).click();//enviar form

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(0).click();//editar pergunta
        driver.findElement(By.name("jump")).findElements(By.tagName("option")).get(1).click();//editar pergunta
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("feedback_template_course_table_r1_c0")).findElement(By.tagName("a")).click();//escolher tipo de resposta
        driver.findElement(By.className("row")).findElements(By.className("navitem")).get(1).findElement(By.tagName("button")).click();//editar pergunta
        Thread.sleep(4000);//espera um pouquinho
        //driver.findElement(By.className("mform")).findElements(By.className("form-group")).get(2).findElement(By.className("form-check")).click();
        driver.findElement(By.xpath("//div[4]/div[2]/div/label/input")).click();
        driver.findElement(By.className("modal-footer")).findElements(By.tagName("button")).get(1).click();//
        Thread.sleep(4000);//espera um pouquinho

        driver.findElement(By.className("container-fluid")).findElements(By.className("navitem")).get(1).click();//editar pergunta
        driver.findElement(By.name("typ")).findElements(By.tagName("option")).get(9).click();//escolher tipo de resposta
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.id("id_required")).click();//checkbox obrigatorio
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("questao9").toString());
        driver.findElement(By.id("id_label")).sendKeys(jsonObject.get("rotulo9").toString());
        driver.findElement(By.id("id_position")).findElements(By.tagName("option")).get(0).click();//id_position

        driver.findElement(By.xpath("//span/input")).click();//salvar alterações

        String resultado = driver.findElement(By.className("itemtitle")).findElement(By.className("itemname")).getText();
        System.out.println(resultado);
        assertTrue(resultado.contains(jsonObject.get("resultado9").toString()));
    }

}
