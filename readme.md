# agenda-servlet3-maven

Aplicação Web Básica que representa uma agenda de contatos básica.
>Bem básica mesmo.

Essa aplicação não precisa de instalação externa (apenas banco de dados).

Para executar a aplicação faça o seguinte:

-[] Instalar o SGBD, no caso MySQL 8
-[] Criar o banco de dados __agenda__
-[] Atualizar o arquivo __jetty.xml__ para que a configuração do DataSource reflita a configuração do MySQL do ambiente onde a execução será realizada

A aplicação esta configurada para rodar sem a instalação de um servidor local, pois no __pom.xml__ configuramos a execução local com um jetty embarcado.
As tabelas serão criadas automaticamente durante a inicialização da aplicação pois a mesma utiliza [FlyWay](https://flywaydb.org/) para controle da evolução do banco de dados.

Para iniciar a aplicação execute:
> mvn clean install jetty:run

Após isso acesse [http://localhost:8080](http://localhost:8080) para acessar e começar acessar a aplicação.
  
