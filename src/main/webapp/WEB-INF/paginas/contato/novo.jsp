<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/x-icon" href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico" />
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
<title>Agenda Servlet - Novo Contato</title>
</head>
<body>
	<header class="w3-container w3-blue">
	<h1>Agenda Servlet</h1>
	</header>
	<br />
	<div class="w3-container">
		<c:url var="urlSalvar" value="/mvc" />
		<form id="formNovo" action="${urlSalvar}" method="post" onsubmit="moduloNovo.enviaContato();">
			<div class="w3-card-4">
				<header class="w3-container w3-blue">
				<h4>Dados do contato</h4>
				</header>
				<div class="w3-container">
					<input type="hidden" value="salvarContato" name="command" />
					<div class="w3-row-padding" style="margin-top: 20px;">
						<div class="w3-half">
							<label for="nome" class="w3-text-blue"> <c:out value="Nome: " /></label> 
							<input type="text" class="w3-input" value="${contato.nome}" name="nome" id="nome">
						</div>
					</div>
					<div class="w3-row-padding">
						<div class="w3-col m1">
							<label for="ddd" class="w3-text-blue"><c:out value="DDD" /></label> 
							<input type="text" class="w3-input" name="ddd" id="ddd" />
						</div>
						<div class="w3-col m3">
							<label for="telefone" class="w3-text-blue"><c:out value="Número" /></label>
							<input type="text" class="w3-input" name="telefone" id="telefone" />
						</div>
						<div class="w3-col m2">
							<label for="tipo" class="w3-text-blue">Tipo</label> 
							<select name="tipo" class="w3-select" id="tipo">
								<option label="Celular" value="CELULAR"><c:out value="Celular" /></option>
								<option label="Residencial" value="RESIDENCIAL"><c:out value="Residencial" /></option>
								<option label="Comercial" value="COMERCIAL"><c:out value="Comercial" /></option>
							</select>
						</div>
						<div class="w3-col m3">
							<input type="button" style="margin-top: 22px;" onclick="moduloNovo.adicionaTelefone();" class="w3-button w3-green" value="Adicionar">
						</div>
					</div>
					<div id="telefones" style="margin-bottom: 20px; margin-top: 20px;"></div>
					<br /> <input type="submit" value="Salvar" class="w3-button w3-green" style="margin-bottom: 20px;" />
				</div>
			</div>
		</form>
	</div>
	<footer class="w3-container w3-blue w3-margin-top">
	<h5>Agenda Servlet</h5>
	<p>Desenvolvida em 2018 com objetivo de estudo</p>
	</footer>
	<script src="${pageContext.servletContext.contextPath}/resources/js/contato/novo.js" type="text/javascript"></script>
</body>
</html>