var moduloNovo = (function() {

	var telefones = [];
	var indiceTelefone = 0;

	function adiciona() {
		var tabelaTelefones = document.getElementById('tabelaTelefones');
		var telefone = {};
		telefone['ddd'] = document.getElementById('ddd').value;
		telefone['numero'] = document.getElementById('telefone').value;
		telefone['tipo'] = document.getElementById('tipo').value;
		telefones[indiceTelefone++] = telefone;
		if (tabelaTelefones == null) {
			var divTabela = document.getElementById('telefones');
			divTabela.innerHTML = '<table id=\"tabelaTelefones\" class=\"w3-table w3-bordered w3-striped w3-card-4\"><thead><tr class=\"w3-blue\"><th>DDD<\/th><th>N\u00FAmero<\/th><th>Tipo<\/th><th colspan=\"2\" style=\"width:10%;\">A\u00E7\u00F5es<\/th><\/tr><\/thead><\/table>';
			tabelaTelefones = document.getElementById('tabelaTelefones');
			tabelaTelefones.createTBody();
		}
		var corpoTabela = tabelaTelefones.tBodies[0];
		var linha = corpoTabela.insertRow(-1);
		var celulaDdd = linha.insertCell(0);
		var celulaNumero = linha.insertCell(1);
		var celulaTipo = linha.insertCell(2);
		var celulaEditar = linha.insertCell(3);
		var celulaExcluir = linha.insertCell(4);
		celulaDdd.innerHTML = telefone.ddd;
		celulaNumero.innerHTML = telefone.numero;
		celulaTipo.innerHTML = telefone.tipo;
		celulaEditar.innerHTML = '<button type="button" class=\"w3-button w3-teal\" onclick="moduloNovo.editaTelefone(this)">Editar<\/button>';
		celulaExcluir.innerHTML = '<button type="button" class=\"w3-button w3-red\" onclick="moduloNovo.removeTelefone(this)">Remover<\/button>';
		document.getElementById('ddd').value = '';
		document.getElementById('telefone').value = '';
		document.getElementById('tipo').selectedIndex = 0;
	}

	function edita(gatilho) {
		var linha = gatilho.parentNode.parentNode;
		var telefone = telefones[linha.rowIndex - 1]
		console.log(telefone.id);
		document.getElementById('ddd').value = telefone.ddd;
		document.getElementById('telefone').value = telefone.numero;
		document.getElementById('tipo').value = telefone.tipo;
		telefones.splice(linha.rowIndex - 1, 1);
		linha.parentNode.removeChild(linha);
		indiceTelefone--;
	}

	function envia() {
		var tabelaTelefones = document.getElementById('tabelaTelefones');
		var contato = {};
		contato['nome'] = document.getElementById('nome').value;
		var formNovo = document.getElementById('formNovo');
		var inputJson = document.getElementById('inputJson');
		if (inputJson == null) {
			inputJson = document.createElement('input');
			inputJson.type = 'hidden';
			inputJson.name = 'json';
			inputJson.id = 'inputJson';
			formNovo.appendChild(inputJson);
		}
		contato['telefones'] = telefones;
		inputJson.value = JSON.stringify(contato);
		console.log('Elemento sendo submetido: ' + inputJson.value);
		formNovo.submit();
	}

	function remove(gatilho) {
		var linha = gatilho.parentNode.parentNode;
		telefones.splice(linha.rowIndex - 1, 1);
		linha.parentNode.removeChild(linha);
		indiceTelefone--;

		if (indiceTelefone == 0) {
			var divTabela = document.getElementById('telefones');
			divTabela.removeChild(divTabela.children[0]);
		}
	}

	return {
		adicionaTelefone : function() {
			adiciona();
		},
		editaTelefone : function(gatilho) {
			edita(gatilho);
		},
		enviaContato : function() {
			envia();
		},
		removeTelefone : function(gatilho) {
			remove(gatilho);
		}
	}

})();
