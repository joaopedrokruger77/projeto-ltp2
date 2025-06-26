// Dados simulados do sistema
let clientes = [
    { id: 1, nome: "João Silva", email: "joao@email.com", tipo: "Nacional", documento: "123.456.789-00", telefone: "(11) 99999-9999" },
    { id: 2, nome: "Maria Santos", email: "maria@email.com", tipo: "Nacional", documento: "987.654.321-00", telefone: "(11) 88888-8888" },
    { id: 3, nome: "John Smith", email: "john@email.com", tipo: "Estrangeiro", documento: "US1234567", telefone: "+1 555-0123" }
];

let pacotes = [
    { id: 1, nome: "Paris Romântica", destino: "Paris, França", preco: 4500.00, vagas: 15, dataInicio: "2024-07-15", dataFim: "2024-07-22" },
    { id: 2, nome: "Tokyo Experience", destino: "Tokyo, Japão", preco: 6800.00, vagas: 12, dataInicio: "2024-08-01", dataFim: "2024-08-10" },
    { id: 3, nome: "New York City", destino: "Nova York, EUA", preco: 5200.00, vagas: 20, dataInicio: "2024-09-20", dataFim: "2024-09-27" }
];

let contratos = [
    { id: 1, clienteId: 1, pacoteId: 1, dataContrato: "2024-06-15" },
    { id: 2, clienteId: 2, pacoteId: 2, dataContrato: "2024-06-20" }
];

let nextClienteId = 4;
let nextPacoteId = 4;

// Funções de modal
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'block';
    // Reset sections
    hideAllSections();
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

function hideAllSections() {
    const sections = ['cadastroCliente', 'listagemClientes', 'buscaCliente', 'cadastroPacote', 'listagemPacotes'];
    sections.forEach(section => {
        const element = document.getElementById(section);
        if (element) element.style.display = 'none';
    });
}

function showSection(sectionId) {
    hideAllSections();
    document.getElementById(sectionId).style.display = 'block';
    
    // Atualizar conteúdo dinâmico
    if (sectionId === 'listagemClientes') {
        atualizarTabelaClientes();
    } else if (sectionId === 'listagemPacotes') {
        atualizarTabelaPacotes();
    }
}

// Funções de cliente
function toggleDocumento() {
    const tipo = document.getElementById('clienteTipo').value;
    const docNacional = document.getElementById('documentoNacional');
    const docEstrangeiro = document.getElementById('documentoEstrangeiro');
    
    if (tipo === 'Nacional') {
        docNacional.style.display = 'block';
        docEstrangeiro.style.display = 'none';
        document.getElementById('clienteCPF').required = true;
        document.getElementById('clientePassaporte').required = false;
        document.getElementById('clienteNacionalidade').required = false;
    } else if (tipo === 'Estrangeiro') {
        docNacional.style.display = 'none';
        docEstrangeiro.style.display = 'block';
        document.getElementById('clienteCPF').required = false;
        document.getElementById('clientePassaporte').required = true;
        document.getElementById('clienteNacionalidade').required = true;
    } else {
        docNacional.style.display = 'none';
        docEstrangeiro.style.display = 'none';
    }
}

function cadastrarCliente(event) {
    event.preventDefault();
    
    const nome = document.getElementById('clienteNome').value;
    const email = document.getElementById('clienteEmail').value;
    const telefone = document.getElementById('clienteTelefone').value;
    const tipo = document.getElementById('clienteTipo').value;
    
    let documento = '';
    if (tipo === 'Nacional') {
        documento = document.getElementById('clienteCPF').value;
        if (!validarCPF(documento)) {
            alert('CPF inválido!');
            return;
        }
    } else {
        documento = document.getElementById('clientePassaporte').value;
    }
    
    // Simular cadastro
    const novoCliente = {
        id: nextClienteId++,
        nome: nome,
        email: email,
        telefone: telefone,
        tipo: tipo,
        documento: documento
    };
    
    clientes.push(novoCliente);
    
    // Mostrar mensagem de sucesso
    const successDiv = document.getElementById('clienteSuccess');
    successDiv.innerHTML = `
        <div class="success-message">
            ✅ Cliente "${nome}" cadastrado com sucesso!<br>
            ID: ${novoCliente.id} | Tipo: ${tipo} | Documento: ${documento}
        </div>
    `;
    successDiv.style.display = 'block';
    
    // Limpar formulário
    event.target.reset();
    hideAllSections();
    
    setTimeout(() => {
        successDiv.style.display = 'none';
    }, 5000);
}

function atualizarTabelaClientes() {
    const tbody = document.getElementById('tabelaClientes');
    tbody.innerHTML = '';
    
    clientes.forEach(cliente => {
        const row = tbody.insertRow();
        row.innerHTML = `
            <td>${cliente.id}</td>
            <td>${cliente.nome}</td>
            <td>${cliente.email}</td>
            <td>${cliente.tipo}</td>
            <td>${cliente.documento}</td>
            <td><span class="status-badge status-ativo">Ativo</span></td>
        `;
    });
}

function buscarCliente() {
    const termo = document.getElementById('buscaNome').value.toLowerCase();
    const resultadoDiv = document.getElementById('resultadoBusca');
    
    if (termo.length < 2) {
        resultadoDiv.innerHTML = '';
        return;
    }
    
    const resultados = clientes.filter(cliente => 
        cliente.nome.toLowerCase().includes(termo)
    );
    
    if (resultados.length === 0) {
        resultadoDiv.innerHTML = '<p>Nenhum cliente encontrado.</p>';
        return;
    }
    
    let html = '<table class="table"><thead><tr><th>Nome</th><th>Email</th><th>Tipo</th><th>Documento</th></tr></thead><tbody>';
    
    resultados.forEach(cliente => {
        html += `
            <tr>
                <td>${cliente.nome}</td>
                <td>${cliente.email}</td>
                <td>${cliente.tipo}</td>
                <td>${cliente.documento}</td>
            </tr>
        `;
    });
    
    html += '</tbody></table>';
    resultadoDiv.innerHTML = html;
}

// Funções de pacote
function cadastrarPacote(event) {
    event.preventDefault();
    
    const nome = document.getElementById('pacoteNome').value;
    const destino = document.getElementById('pacoteDestino').value;
    const descricao = document.getElementById('pacoteDescricao').value;
    const preco = parseFloat(document.getElementById('pacotePreco').value);
    const vagas = parseInt(document.getElementById('pacoteVagas').value);
    const dataInicio = document.getElementById('pacoteDataInicio').value;
    const dataFim = document.getElementById('pacoteDataFim').value;
    
    // Validar datas
    if (new Date(dataFim) <= new Date(dataInicio)) {
        alert('A data de fim deve ser posterior à data de início!');
        return;
    }
    
    // Simular cadastro
    const novoPacote = {
        id: nextPacoteId++,
        nome: nome,
        destino: destino,
        descricao: descricao,
        preco: preco,
        vagas: vagas,
        dataInicio: dataInicio,
        dataFim: dataFim
    };
    
    pacotes.push(novoPacote);
    
    // Mostrar mensagem de sucesso
    const successDiv = document.getElementById('pacoteSuccess');
    successDiv.innerHTML = `
        <div class="success-message">
            ✅ Pacote "${nome}" cadastrado com sucesso!<br>
            Destino: ${destino} | Preço: R$ ${preco.toFixed(2)} | Vagas: ${vagas}
        </div>
    `;
    successDiv.style.display = 'block';
    
    // Limpar formulário
    event.target.reset();
    
    setTimeout(() => {
        successDiv.style.display = 'none';
    }, 5000);
}

function atualizarTabelaPacotes() {
    const tbody = document.querySelector('#listagemPacotes tbody');
    if (!tbody) return;
    
    tbody.innerHTML = '';
    
    pacotes.forEach(pacote => {
        const row = tbody.insertRow();
        const dataInicio = new Date(pacote.dataInicio).toLocaleDateString('pt-BR');
        const dataFim = new Date(pacote.dataFim).toLocaleDateString('pt-BR');
        
        row.innerHTML = `
            <td>${pacote.id}</td>
            <td>${pacote.nome}</td>
            <td>${pacote.destino}</td>
            <td>R$ ${pacote.preco.toFixed(2)}</td>
            <td>${dataInicio} - ${dataFim}</td>
            <td>${pacote.vagas}</td>
        `;
    });
}

// Funções de validação
function validarCPF(cpf) {
    // Remove caracteres não numéricos
    cpf = cpf.replace(/[^\d]/g, '');
    
    // Verifica se tem 11 dígitos
    if (cpf.length !== 11) return false;
    
    // Verifica se todos os dígitos são iguais
    if (/^(\d)\1{10}$/.test(cpf)) return false;
    
    // Validação do primeiro dígito verificador
    let soma = 0;
    for (let i = 0; i < 9; i++) {
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    }
    let resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(9))) return false;
    
    // Validação do segundo dígito verificador
    soma = 0;
    for (let i = 0; i < 10; i++) {
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    }
    resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(10))) return false;
    
    return true;
}

function validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

function validarPassaporte(passaporte) {
    return passaporte.length >= 6 && passaporte.length <= 15 && /^[A-Z0-9]+$/.test(passaporte.toUpperCase());
}

// Funções de teste das validações
function testarCPF() {
    const cpf = document.getElementById('testeCPF').value;
    const resultado = document.getElementById('resultadoCPF');
    
    if (cpf.length === 0) {
        resultado.innerHTML = '';
        return;
    }
    
    if (validarCPF(cpf)) {
        resultado.innerHTML = '<div style="color: green; margin-top: 10px;">✅ CPF válido!</div>';
    } else {
        resultado.innerHTML = '<div style="color: red; margin-top: 10px;">❌ CPF inválido!</div>';
    }
}

function testarEmail() {
    const email = document.getElementById('testeEmail').value;
    const resultado = document.getElementById('resultadoEmail');
    
    if (email.length === 0) {
        resultado.innerHTML = '';
        return;
    }
    
    if (validarEmail(email)) {
        resultado.innerHTML = '<div style="color: green; margin-top: 10px;">✅ Email válido!</div>';
    } else {
        resultado.innerHTML = '<div style="color: red; margin-top: 10px;">❌ Email inválido!</div>';
    }
}

function testarPassaporte() {
    const passaporte = document.getElementById('testePassaporte').value;
    const resultado = document.getElementById('resultadoPassaporte');
    
    if (passaporte.length === 0) {
        resultado.innerHTML = '';
        return;
    }
    
    if (validarPassaporte(passaporte)) {
        resultado.innerHTML = '<div style="color: green; margin-top: 10px;">✅ Passaporte válido!</div>';
    } else {
        resultado.innerHTML = '<div style="color: red; margin-top: 10px;">❌ Passaporte inválido! (6-15 caracteres alfanuméricos)</div>';
    }
}

// Formatação automática de CPF
document.addEventListener('DOMContentLoaded', function() {
    const cpfInput = document.getElementById('clienteCPF');
    if (cpfInput) {
        cpfInput.addEventListener('input', function() {
            let value = this.value.replace(/\D/g, '');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            this.value = value;
        });
    }
    
    const testeCpfInput = document.getElementById('testeCPF');
    if (testeCpfInput) {
        testeCpfInput.addEventListener('input', function() {
            let value = this.value.replace(/\D/g, '');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            this.value = value;
        });
    }
});

// Fechar modal clicando fora
window.onclick = function(event) {
    const modals = document.getElementsByClassName('modal');
    for (let i = 0; i < modals.length; i++) {
        if (event.target === modals[i]) {
            modals[i].style.display = 'none';
        }
    }
} 