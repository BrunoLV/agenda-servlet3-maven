var moduloAtualiza = (function() {

    var telefones = [];
    var indiceTelefone = 0;

    var tabela = document.getElementById('tabelaTelefones');
    if (tabela != null) {
	var corpoTabela = tabela.tBodies[0];
	for (var i = 0; i < corpoTabela.rows.length; i++) {
	    var telefoneAux = {};
	    telefoneAux['id'] = corpoTabela.rows[i].children[0].value;
	    telefoneAux['ddd'] = corpoTabela.rows[i].cells[0].innerHTML;
	    telefoneAux['numero'] = corpoTabela.rows[i].cells[1].innerHTML;
	    telefoneAux['tipo'] = corpoTabela.rows[i].cells[2].innerHTML;
	    telefones[indiceTelefone++] = telefoneAux;
	}
    }

    document.getElementById('idTelefone').value = null;
    document.getElementById('ddd').value = null;
    document.getElementById('telefone').value = null;
    document.getElementById('tipo').selectedIndex = 0;

    function adiciona() {
	var tabelaTelefones = document.getElementById('tabelaTelefones');
	var telefone = {};
	telefone['id'] = document.getElementById('idTelefone').value;
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

	var iHidden = document.createElement('input');
	iHidden.type = "hidden";
	iHidden.value = telefone.id;
	linha.append(iHidden);

	var celulaDdd = linha.insertCell(0);
	var celulaNumero = linha.insertCell(1);
	var celulaTipo = linha.insertCell(2);
	var celulaEditar = linha.insertCell(3);
	var celulaExcluir = linha.insertCell(4);

	celulaDdd.innerHTML = telefone.ddd;
	celulaNumero.innerHTML = telefone.numero;
	celulaTipo.innerHTML = telefone.tipo;
	celulaEditar.innerHTML = '<button type="button" class=\"w3-button w3-teal\" onclick="moduloAtualiza.editaTelefone(this)">Editar<\/button>';
	celulaExcluir.innerHTML = '<button type="button" class=\"w3-button w3-red\" onclick="moduloAtualiza.removeTelefone(this)">Remover<\/button>';
	document.getElementById('idTelefone').value = null;
	document.getElementById('ddd').value = null;
	document.getElementById('telefone').value = null;
	document.getElementById('tipo').selectedIndex = 0;
    }

    function edita(gatilho) {
	var linha = gatilho.parentNode.parentNode;
	var telefone = telefones[linha.rowIndex - 1];
	document.getElementById('idTelefone').value = telefone.id;
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
	contato['id'] = document.getElementById('idContato').value;
	contato['nome'] = document.getElementById('nome').value;
	var formAtualiza = document.getElementById('formAtualiza');
	var inputJson = document.getElementById('inputJson');
	if (inputJson == null) {
	    inputJson = document.createElement('input');
	    inputJson.type = 'hidden';
	    inputJson.name = 'json';
	    inputJson.id = 'inputJson';
	    formAtualiza.appendChild(inputJson);
	}
	contato['telefones'] = telefones;
	inputJson.value = JSON.stringify(contato);
	console.log('Elemento sendo submetido: ' + inputJson.value);
	formAtualiza.submit();
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
