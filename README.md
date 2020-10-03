# Desafio Java Web

Esse projeto foi desenvolvido como teste prático de seleção para uma vaga como Desenvolvedor Java Web JR. E foi constuída uma aplicação voltada a área de Eventos.

## Tecnologias

Esse projeto foi desenvolvido com as seguintes tecnologias:

- Java 11;
- Spring Boot;
- Spring Data JPA;
- Spring Web;
- H2 Database

## Projeto

Implementação de uma API REST para inscrição de usuários em eventos.

## Estrutura do Projeto

O projeto está estruturado em pacotes, cada um com sua função:

**- Package domain:** contém todas as classes de domínio do projeto;
**- Package repository:** nele estão contidas todas as interfaces correspondentes as classes de domínio do projeto que serão persistidos no banco de dados;
**- Package service:** contém as classes que estão responsáveis pela regra de negócio do projeto, assim também as classes de exceções;
**- Package restcontroller ou controller:** pacote responsavél pelos mapeamentos dos endpoints da API correspondente a cada recurso existente.

## Importando o Projeto

Recomendo o uso da IDE Spring Tools, que foi a IDE usada para desenvolver a aplicação.

Com a IDE Spring Tool, recomendo que o projeto seja importado diretamente do link no github: https://github.com/jeffersonguanabara/desafio-java-web-behoh.

Passos para importar o projeto:

1. Menu - Arquivo/File (Dependendo a linguagem padrão do seu SO);
2. Arquivo/File > Importar/Import;
3. Na caixa de seleção, selecione a pasta Git. Clique na seta ao lado e selecione Projects from Git. E clique no botão Next ou Próximo;
4. Selecione Clone URI, na próxima página, depois clique em Next;
5. No campo URI, informe o link do github. E clique em Next;
6. Clique em Next e informe o diretorio de destino e clicar em next;
7. Na página seguinte informe a forma que o projeto será importado, recomendo a 3ª opção. E pode clicar em finalizar.

Após o projeto ter sido importado, recomendo que se verifique se as dependências da aplicação estão sendo baixadas. Caso contrario, deve-se fazer os seguintes passos:

1. Clicar com o botão direito do mouse sobre o projeto;
2. Buscar e clicar na opção do menu MAVEN;
3. Clicar em Update Project;
4. Selecionar o checkbox "Force Update of Snapshots/Releases";
5. Por fim clicar em OK.

É só esperar que as dependências seja atualizadas ou baixadas. Para que possamos executar a aplicação.

## Executando o projeto

Com todas as dependências já baixadas podemos executar o projeto.

Clicando com botão direto do mouse no projeto, podemos acessar a opção Run As e clicamos em Spring Boot App.

Que executará a aplicação, criando o banco de dados.

A apartir de agora podemos acessar os endpoints da aplicação usando uma ferramenta de envio de requisições HTTP.

As principais ferramentas usadas no mercado são:

- Postman;
- e Insomnia.

### ENDPOINTS

OBS.: A aplicação é executada pelo servidor de aplicação TOMCAT, padão do SPRING BOOT;

Todos os endpoints iniciam-se por: http://localhost:8080/api

**Cadastro de Eventos:** 

- ENDPOINT: http://localhost:8080/api/eventos
- O método HTTP usado é o POST.
- Corpo da requisição deve informar os dados refetentes ao evento.

		{
			"nome": "nome_do_evento ", 
			"vagas": numero de vagas,
			"data_e_hora_de_inicio": "no formato yyyyy-MM-dd HH: mm:ss",
			"data_hora_de_fim": "no formato yyyyy-MM-dd HH: mm:ss".
		}

**Cadastro de usuário:** 

- ENDPOINT: http://localhost:8080/api/usuarios
- O método HTTP usado é o POST.
- Corpo da requisição deve informar os dados refetentes ao evento.

		{ 
			"nome": "nome do usuario"
		}

**Inscrever usuário no Evento:** 

- ENDPOINT: http://localhost:8080/api/eventos/{id}/inscricao
- O método HTTP usado é o POST.
- Corpo da requisição deve informar os dados refetentes ao evento.

**Fazer reserva no Evento:** 

- ENDPOINT: http://localhost:8080/api/eventos/{id}/reserva
- O método HTTP usado é o POST.
- Corpo da requisição deve informar os dados refetentes ao evento.

**Cancelar inscrição no Evento:** 

- ENDPOINT: http://localhost:8080/api/eventos/{id}/reserva
- O método HTTP usado é o DELETE.
- Corpo da requisição deve informar os dados refetentes ao evento.

**Listar inscritos no Evento:** 

- ENDPOINT: http://localhost:8080/api/eventos/{id}/reserva
- O método HTTP usado é o GET.
- Corpo da requisição deve informar os dados refetentes ao evento.

**Listar eventos inscritos do usuario:** 

- ENDPOINT: http://localhost:8080/api/eventos/{id}/reserva
- O método HTTP usado é o GET.
- Corpo da requisição deve informar os dados refetentes ao evento.

**Validando a entrada do usuário no evento:**

- ENDPOINT: http://localhost:8080/api/eventos/{id}/reserva
- O método HTTP usado é o PUT.
- Corpo da requisição deve informar os dados refetentes ao evento.

**Conversão de reservas em inscrições:** 

- ENDPOINT: http://localhost:8080/api/eventos/{id}/reserva
- O método HTTP usado é o PUT.
- Corpo da requisição deve informar os dados refetentes ao evento.
