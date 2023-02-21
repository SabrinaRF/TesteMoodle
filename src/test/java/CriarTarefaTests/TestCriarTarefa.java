package CriarTarefaTests;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste criar tarefa")
public class TestCriarTarefa {
    private static JSONObject jsonObject;
    private WebDriver driver;


    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException, ParseException {
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\sabri\\OneDrive\\Área de Trabalho\\GITLAB\\marco02-grupo04\\Testes da Sabrina\\src\\test\\java\\CriarTarefaTests\\dados.json"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

    }
    @AfterEach
    public void tearDown() {
       driver.quit();
    }

    @Test
    @DisplayName("Validar criação de tarefa")
    public void TestCriarTarefa() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        driver.findElement(By.id("id_showdescription")).click();//clica no checkbox
        driver.findElement(By.className("fm-empty-container")).click();//clica no botão para adicionar arquivo

        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("caminhoArquivo").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        driver.findElement(By.id("id_submissionattachments")).click();//clica no checkbox
        driver.findElement(By.name("assignsubmission_onlinetext_enabled")).click();//clica para habilitar o texto online

        driver.findElement(By.id("id_submitbutton")).click();

        String resultado = driver.findElement(By.className("mr-auto")).findElement(By.className("page-context-header")).findElement(By.tagName("h1")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado1").toString()));
    }

    @Test
    @DisplayName("Arquivo inexistente")
    public void TestArquivoInex() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }
        driver.findElement(By.className("fm-empty-container")).click();//clica no botão para adicionar arquivo

        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(4000);//espera um pouquinho

        List<WebElement> listaErro =driver.findElements(By.className("moodle-dialogue-base"));
        for (WebElement ERRO : listaErro){
            if (ERRO.getText().contains("Nenhum arquivo anexado")){
                ERRO.findElement(By.className("fp-msg-text")).click();
            }
        }
        String resultado = driver.findElement(By.className("fp-msg-text")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado3").toString()));
    }

    @Test
    @DisplayName("Arquivos do servidor")
    public void TestArquivosServidor() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        driver.findElement(By.id("id_showdescription")).click();//clica no checkbox
        driver.findElement(By.className("fm-empty-container")).click();//clica no botão para adicionar arquivo

        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Arquivos do servidor")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);
        driver.findElement(By.id("reposearch")).sendKeys(jsonObject.get("teste").toString());
        driver.findElement(By.id("reposearch")).sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//span/a/span[2]")).click();
        driver.findElement(By.xpath("//form/div[4]/div/button")).click();
        Thread.sleep(4000);
        driver.findElement(By.id("id_submitbutton")).click();

        String resultado = driver.findElement(By.id("region-main")).findElement(By.className("activity-header")).findElement(By.id("intro")).findElement(By.className("fileuploadsubmission")).getText();
        System.out.println(resultado);
        Thread.sleep(4000);
        assertTrue(resultado.contains(jsonObject.get("resultado4").toString()));

    }

    @Test
    @DisplayName("Arquivo URL")
    public void TestArquivoURL() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        driver.findElement(By.id("id_showdescription")).click();//clica no checkbox
        driver.findElement(By.className("fm-empty-container")).click();//clica no botão para adicionar arquivo

        //cria uma lista com os elemntos do menu de arquivos
        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Utilizar uma URL")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();
            }
        }

        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("fileurl")).sendKeys(jsonObject.get("caminhoArquivoURL").toString());//adiciona o caminho do arquivo

        driver.findElement(By.className("fp-content-center")).findElement(By.className("mdl-align")).findElement(By.className("fp-login-submit")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        driver.findElement(By.xpath("//div[17]/table/tbody/tr/td[2]/span/a/span[2]")).click();
        driver.findElement(By.xpath("//form/div[4]/div/button")).click();
        Thread.sleep(4000);
        driver.findElement(By.id("id_submitbutton")).click();

        String resultado = driver.findElement(By.id("region-main")).findElement(By.className("activity-header")).findElement(By.id("intro")).findElement(By.className("fileuploadsubmission")).getText();
        System.out.println(resultado);
        Thread.sleep(4000);
        assertTrue(resultado.contains(jsonObject.get("resultado5").toString()));

    }

    @Test
    @DisplayName("Excluir Arquivo URL")
    public void TestExcluirArquivoURL() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        driver.findElement(By.id("id_showdescription")).click();//clica no checkbox
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.className("fm-empty-container")).click();//clica no botão para adicionar arquivo

        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Utilizar uma URL")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();
            }
        }

        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("fileurl")).sendKeys(jsonObject.get("caminhoArquivoURLExcluir").toString());//adiciona o caminho do arquivo

        driver.findElement(By.className("fp-content-center")).findElement(By.className("mdl-align")).findElement(By.className("fp-login-submit")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        driver.findElement(By.xpath("//span/a/span[2]")).click();//clica na imagem
        driver.findElement(By.xpath("//form/div[4]/div/button")).click();//clica em salvar
        Thread.sleep(4000);
        driver.findElement(By.xpath("//a/div/div[3]")).click();//clica na imagem
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.xpath("//form/div/button[2]")).click();//clica em excluir

        List<WebElement> listaErro =driver.findElements(By.className("moodle-dialogue-base"));
        for (WebElement ERRO : listaErro){
            if (ERRO.getText().contains("Você tem certeza que quer excluir este arquivo?")){
                ERRO.findElement(By.className("fp-dlg-text")).click();
            }
        }
        String resultado = driver.findElement(By.className("fp-dlg-text")).getText();
        System.out.println(resultado);
        Thread.sleep(4000);//espera um
        driver.findElement(By.className("fp-dlg-butconfirm")).click();//clica no ok
        Thread.sleep(4000);
        assertTrue(resultado.contains(jsonObject.get("resultado6").toString()));

    }

    @Test
    @DisplayName("Nome obrigatório inexistente")
    public void TestNomeObrigatorioInexistente() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefaVazio").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_submitbutton")).click();//clica em enviar
        String resultado = driver.findElement(By.id("id_error_name")).getText();//atribui a maensagem de erro a varioavel resultado

        assertTrue(resultado.contains(jsonObject.get("resultado2").toString()));
    }

    @Test
    @DisplayName("Mensagem do checkbox exibida")
    public void TestCheckbox() throws InterruptedException {//Exibir descrição na página do curso
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa2").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoDiferenciada").toString());// adiciona a descrição
        driver.findElement(By.id("id_showdescription")).click();//clica no checkbox do test
        driver.findElement(By.className("fm-empty-container")).click();//clica no botão para adicionar arquivo
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("caminhoArquivo").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        driver.findElement(By.id("id_submissionattachments")).click();//clica no checkbox
        driver.findElement(By.name("assignsubmission_onlinetext_enabled")).click();//clica para habilitar o texto online
        driver.findElement(By.id("id_submitbutton")).click();//enviar dados

        String resultado =driver.findElement(By.id("region-main")).findElement(By.className("activity-header")).findElement(By.id("intro")).findElement(By.tagName("p")).getText();
        Thread.sleep(4000);

        assertTrue(resultado.contains(jsonObject.get("descricaoDiferenciada").toString()));

    }

    @Test
    @DisplayName("Checkbox do arquivo")
    public void TestCheckboxArquivo() throws InterruptedException {//Exibir descrição na página do curso
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa2").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoDiferenciada").toString());// adiciona a descrição
        driver.findElement(By.id("id_showdescription")).click();//clica no checkbox do test
        driver.findElement(By.className("fm-empty-container")).click();//clica no botão para adicionar arquivo

        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(6000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("caminhoArquivo").toString());//adiciona o caminho do arquivo
        Thread.sleep(6000);//espera um pouquinho
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo2").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        //driver.findElement(By.id("id_submissionattachments")).click();//clica no checkbox do test
        driver.findElement(By.name("assignsubmission_onlinetext_enabled")).click();//clica para habilitar o texto online
        driver.findElement(By.id("id_submitbutton")).click();//enviar dados

        String resultado =driver.findElement(By.id("region-main")).findElement(By.className("activity-header")).findElement(By.id("intro")).findElement(By.className("ygtvitem")).getText();
        System.out.println(resultado);
        Thread.sleep(4000);

        assertTrue(resultado.contains(jsonObject.get("resultado7").toString()));

    }

    @Test
    @DisplayName("Tipo de envio texto")
    public void TestEnvioText() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        Thread.sleep(2000);//espera um tempinho
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        driver.findElement(By.name("assignsubmission_onlinetext_enabled")).click();//clica para habilitar o texto online
        driver.findElement(By.name("assignsubmission_file_enabled")).click();//clica para desabilitar enviar arquivos
        driver.findElement(By.name("assignsubmission_onlinetext_wordlimit_enabled")).click();//clica para habilitar o Limite de palavras
        Thread.sleep(4000);
        driver.findElement(By.id("id_assignsubmission_onlinetext_wordlimit")).sendKeys(jsonObject.get("limiteCaract").toString());//adiciona um limite de caracteres

        driver.findElement(By.id("id_submitbutton")).click();//clica pra criar a tarefa

        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.id("id_onlinetext_editoreditable")).sendKeys(jsonObject.get("AlunoTexTarefa").toString());//adicionando o texto
        driver.findElement(By.name("submitbutton")).click();//enviar

        String resultado =driver.findElement(By.className("earlysubmission")).getText();//pega um texto pra comparar
        Thread.sleep(2000);

        assertTrue(resultado.contains(jsonObject.get("resultado8").toString()));
    }

    @Test
    @DisplayName("Tipo de envio texto ultrapassando o limite de caract.")
    public void TestEnvioTextLimiteC() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        Thread.sleep(2000);//espera um tempinho
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        driver.findElement(By.name("assignsubmission_onlinetext_enabled")).click();//clica para habilitar o texto online
        driver.findElement(By.name("assignsubmission_file_enabled")).click();//clica para desabilitar enviar arquivos
        driver.findElement(By.name("assignsubmission_onlinetext_wordlimit_enabled")).click();//clica para habilitar o Limite de palavras
        Thread.sleep(4000);
        driver.findElement(By.id("id_assignsubmission_onlinetext_wordlimit")).sendKeys(jsonObject.get("limiteCaract2").toString());//adiciona um limite de caracteres

        driver.findElement(By.id("id_submitbutton")).click();//clica pra criar a tarefa

        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.id("id_onlinetext_editoreditable")).sendKeys(jsonObject.get("AlunoTexTarefa").toString());//adicionando o texto
        driver.findElement(By.name("submitbutton")).click();//enviar

        String resultado = driver.findElements(By.className("alert")).get(0).findElement(By.tagName("span")).getText();//pega um texto pra comparar
        System.out.println(resultado);
        Thread.sleep(2000);

        assertTrue(resultado.contains(jsonObject.get("resultado9").toString()));
    }

    @Test
    @DisplayName("Tipo de envio arquivo")
    public void TestEnvioArquivo() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula
        Thread.sleep(4000);//espera um tempinho

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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        Thread.sleep(4000);

        driver.findElement(By.id("id_assignsubmission_file_maxfiles")).sendKeys(jsonObject.get("limiteArquivos").toString());//adiciona um limite para o arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(jsonObject.get("tamMax").toString());//tamanho máximo do arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(Keys.ENTER);//clica no enter
        driver.findElement(By.id("id_submitbutton")).click();
        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.className("fm-empty-container")).click();//clica adicionar aqruivo
        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("arquivoMenor5(1)").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo(1)").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        driver.findElement(By.className("fp-navbar")).findElement(By.className("fp-btn-add")).findElement(By.tagName("a")).click();
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("arquivoMenor5(2)").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo(2)").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        driver.findElement(By.name("submitbutton")).click();//enviar

        String resultado =driver.findElement(By.className("earlysubmission")).getText();//pega um texto pra comparar
        System.out.println(resultado);
        Thread.sleep(2000);

        assertTrue(resultado.contains(jsonObject.get("resultado10").toString()));

    }

    @Test
    @DisplayName("Tipo de envio arquivo ultrapassando o limite de arquivo")
    public void TestEnvioArquivoLimiteA() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula
        Thread.sleep(6000);//espera um tempinho

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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        Thread.sleep(4000);

        driver.findElement(By.id("id_assignsubmission_file_maxfiles")).sendKeys(jsonObject.get("limiteArquivos").toString());//adiciona um limite para o arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(jsonObject.get("tamMax").toString());//tamanho máximo do arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(Keys.ENTER);//clica no enter
        driver.findElement(By.id("id_submitbutton")).click();
        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.className("fm-empty-container")).click();//clica adicionar aqruivo
        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("arquivoMenor5(1)").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo(1)").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        driver.findElement(By.className("fp-navbar")).findElement(By.className("fp-btn-add")).findElement(By.tagName("a")).click();
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("arquivoMenor5(2)").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo(2)").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        try {
            driver.findElement(By.className("fp-navbar")).findElement(By.className("fp-btn-add")).findElement(By.tagName("a")).click();
        }catch (Exception e){

        }
        //driver.findElement(By.className("fp-restrictions")).findElement(By.tagName("span")).getText();//enviar

        //String resultado =driver.findElement(By.className("earlysubmission")).getText();//pega um texto pra comparar
        //System.out.println(resultado);
        //Thread.sleep(2000);

        //assertTrue(resultado.contains(jsonObject.get("resultado11").toString()));

    }

    @Test
    @DisplayName("Tipo de envio arquivo ultrapassando o tamanho de arquivo")
    public void TestEnvioArquivoTamanhoA() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula
        Thread.sleep(8000);//espera um tempinho

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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        Thread.sleep(4000);

        driver.findElement(By.id("id_assignsubmission_file_maxfiles")).sendKeys(jsonObject.get("limiteArquivos").toString());//adiciona um limite para o arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(jsonObject.get("tamMax2").toString());//tamanho máximo do arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(Keys.ENTER);//clica no enter
        driver.findElement(By.id("id_submitbutton")).click();
        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.className("fm-empty-container")).click();//clica adicionar aqruivo
        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("arquivoMenor5(3)").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo(3)").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        List<WebElement> listaErro =driver.findElements(By.className("moodle-dialogue-base"));
        for (WebElement ERRO : listaErro){
            if (ERRO.getText().contains("O arquivo Arquivo 3.odt é muito grande. ")){
                ERRO.findElement(By.className("moodle-exception-message")).click();
            }
        }
        String resultado = driver.findElement(By.className("moodle-exception-message")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado11").toString()));

    }

    @Test
    @DisplayName("Tipo de envio arquivo extensão incorreta PPT")
    public void TestEnvioArquivoExtenPPT() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula
        Thread.sleep(8000);//espera um tempinho

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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        Thread.sleep(4000);

        driver.findElement(By.id("id_assignsubmission_file_maxfiles")).sendKeys(jsonObject.get("limiteArquivos").toString());//adiciona um limite para o arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(jsonObject.get("tamMax2").toString());//tamanho máximo do arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(Keys.ENTER);//clica no enter
        driver.findElement(By.xpath("//fieldset/div/span/input")).click();//entregar em arquivos
        driver.findElement(By.xpath("//div[2]/label/input")).click();//clica no checkbox do arquivo
        driver.findElement(By.xpath("//div[2]/div/div/div[3]/button[2]")).click();//envia a opção selecionada


        driver.findElement(By.id("id_submitbutton")).click();
        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.className("fm-empty-container")).click();//clica adicionar aqruivo
        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("arquivoMenor5(1)").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoArquivo(1)").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        List<WebElement> listaErro =driver.findElements(By.className("moodle-dialogue-base"));
        for (WebElement ERRO : listaErro){
            if (ERRO.getText().contains("Arquivo de texto não é um tipo de arquivo aceito")){
                ERRO.findElement(By.className("moodle-exception-message")).click();
            }
        }
        String resultado = driver.findElement(By.className("moodle-exception-message")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado12").toString()));

    }

    @Test
    @DisplayName("Tipo de envio arquivo extensão incorreta MP3")
    public void TestEnvioArquivoExtenMP3() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula
        Thread.sleep(8000);//espera um tempinho

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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        Thread.sleep(4000);

        driver.findElement(By.id("id_assignsubmission_file_maxfiles")).sendKeys(jsonObject.get("limiteArquivos").toString());//adiciona um limite para o arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(jsonObject.get("tamMax2").toString());//tamanho máximo do arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(Keys.ENTER);//clica no enter
        driver.findElement(By.xpath("//fieldset/div/span/input")).click();
        driver.findElement(By.xpath("//div[3]/label/input")).click();
        driver.findElement(By.xpath("//div[2]/div/div/div[3]/button[2]")).click();

        driver.findElement(By.id("id_submitbutton")).click();
        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.className("fm-empty-container")).click();//clica adicionar aqruivo
        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("caminhoPPT").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoPPT").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        List<WebElement> listaErro =driver.findElements(By.className("moodle-dialogue-base"));
        for (WebElement ERRO : listaErro){
            if (ERRO.getText().contains("Apresentação Powerpoint 2007 ")){
                ERRO.findElement(By.className("moodle-exception-message")).click();
            }
        }
        String resultado = driver.findElement(By.className("moodle-exception-message")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado13").toString()));

    }

    @Test
    @DisplayName("Tipo de envio arquivo extensão incorreta TXT")
    public void TestEnvioArquivoExtenTXT() throws InterruptedException {
        driver.get (jsonObject.get("url").toString());// carrega a URL do moodle
        driver.findElement(By.id("username")).sendKeys(jsonObject.get("matriculaCorreta").toString());// adiciona o username no login
        driver.findElement(By.id("password")).sendKeys(jsonObject.get("senhaCorreta").toString());//adiciona a senha no login
        driver.findElement(By.id("loginbtn")).click();//clica no botão pra enviar e logar
        driver.findElement(By.className("custom-control-input")).click();//clica no botão (ativa o modo de edição)
        Thread.sleep(4000);//espera um tempinho
        driver.findElement(By.cssSelector("a[href='https://gmlunardi.pro.br/moodlerp2/my/courses.php']")).click();//clina no "meus cursos" no nav
        Thread.sleep(6000);//espera um tempinho
        driver.findElement(By.className("multiline")).click();//clica no curso Engenharia Agricula
        Thread.sleep(8000);//espera um tempinho

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
            if (item.getText().contains("Tarefa")){
                item.findElement(By.tagName("a")).click();
                break;
            }
        }

        driver.findElement(By.id("id_name")).sendKeys(jsonObject.get("nomeTarefa").toString());// adiciona o nome da tarefa
        driver.findElement(By.id("id_introeditoreditable")).sendKeys(jsonObject.get("descricaoTarefa").toString());// adiciona a descrição
        driver.findElement(By.id("id_activityeditoreditable")).sendKeys(jsonObject.get("descricaoAtividade").toString());//adiciona a descrição da atividade
        Thread.sleep(4000);

        driver.findElement(By.id("id_assignsubmission_file_maxfiles")).sendKeys(jsonObject.get("limiteArquivos").toString());//adiciona um limite para o arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(jsonObject.get("tamMax").toString());//tamanho máximo do arquivo
        driver.findElement(By.id("id_assignsubmission_file_maxsizebytes")).sendKeys(Keys.ENTER);//clica no enter
        driver.findElement(By.xpath("//fieldset/div/span/input")).click();
        driver.findElement(By.xpath("//div[6]/label/input")).click();
        driver.findElement(By.xpath("//div[2]/div/div/div[3]/button[2]")).click();

        driver.findElement(By.id("id_submitbutton")).click();
        driver.findElement(By.className("usermenu")).findElement(By.tagName("a")).click();//clica no SR menu
        Thread.sleep(2000);
        driver.findElement(By.id("carousel-item-main")).findElements(By.tagName("a")).get(7).click();//clica para trocar pra estudante
        driver.findElement(By.id("region-main")).findElements(By.className("mx-3")).get(1).findElement(By.className("singlebutton")).click();//clicar no estudante
        driver.findElement(By.id("region-main")).findElement(By.className("container-fluid")).findElement(By.className("col-xs-6")).click();//clicar para adicionar um texto

        driver.findElement(By.className("fm-empty-container")).click();//clica adicionar aqruivo
        List<WebElement> listaArquivo =driver.findElements(By.className("fp-repo"));
        for (WebElement OpArquivo : listaArquivo){
            if (OpArquivo.getText().contains("Enviar um arquivo")){
                OpArquivo.findElement(By.className("fp-repo-name")).click();

            }
        }
        Thread.sleep(4000);//espera um pouquinho
        driver.findElement(By.name("repo_upload_file")).sendKeys(jsonObject.get("caminhoMP4").toString());//adiciona o caminho do arquivo
        driver.findElement(By.name("title")).sendKeys(jsonObject.get("textoMP4").toString());//adiciona um titulo para o arquivo
        driver.findElement(By.className("fp-upload-btn")).click();//clica em salvar
        Thread.sleep(6000);//espera um pouquinho

        List<WebElement> listaErro =driver.findElements(By.className("moodle-dialogue-base"));
        for (WebElement ERRO : listaErro){
            if (ERRO.getText().contains("Arquivo de vídeo (MP4) ")){
                ERRO.findElement(By.className("moodle-exception-message")).click();
            }
        }
        String resultado = driver.findElement(By.className("moodle-exception-message")).getText();

        assertTrue(resultado.contains(jsonObject.get("resultado14").toString()));

    }

}
