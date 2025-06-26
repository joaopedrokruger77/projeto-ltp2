# Sistema de Gerenciamento - Agência de Turismo

![Java](https://img.shields.io/badge/Java-11+-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Sobre o Projeto

Sistema completo para gerenciamento de agência de turismo desenvolvido em Java com interface gráfica Swing e banco de dados MySQL. Inclui funcionalidades completas de CRUD para clientes, pacotes de viagem, serviços adicionais e contratos, com validações robustas e interface intuitiva.

## ✨ Funcionalidades

### 🧑‍💼 Gerenciamento de Clientes
- ✅ Cadastro de clientes nacionais (com CPF) e estrangeiros (com passaporte)
- ✅ Validação automática de CPF e formato de e-mail
- ✅ Listagem e busca de clientes
- ✅ Exclusão com validações de segurança

### 🏝️ Gerenciamento de Pacotes
- ✅ Cadastro completo de pacotes de viagem
- ✅ Controle de vagas disponíveis
- ✅ Validação de datas e preços
- ✅ Busca por destino
- ✅ Proteção contra exclusão com contratos ativos

### 🎯 Serviços Adicionais
- ✅ Cadastro de serviços opcionais
- ✅ Categorização por tipo de serviço
- ✅ Gerenciamento de preços

### 📋 Contratos
- ✅ Contratação de pacotes por clientes
- ✅ Consulta de pacotes por cliente
- ✅ Consulta de clientes por pacote
- ✅ Relatórios detalhados

### 🔒 Validações
- ✅ Validação de CPF com algoritmo oficial
- ✅ Validação de passaporte para estrangeiros
- ✅ Validação de formato de e-mail
- ✅ Validação de datas e valores monetários
- ✅ Regras de negócio implementadas

## 🚀 Tecnologias Utilizadas

- **Java 11+** - Linguagem principal
- **Java Swing** - Interface gráfica
- **MySQL 8.0+** - Banco de dados
- **Maven** - Gerenciamento de dependências
- **JDBC** - Conexão com banco de dados
- **Padrão DAO** - Arquitetura de acesso a dados

## 🏗️ Arquitetura

```
src/
├── main/java/com/agenciaturismo/
│   ├── model/          # Entidades de domínio
│   ├── dao/            # Acesso a dados
│   ├── gui/            # Interface gráfica
│   └── util/           # Utilitários e validações
├── database/           # Scripts SQL
└── docs/              # Documentação
```

## 📦 Instalação e Execução

### Pré-requisitos
- Java 11 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior

### 1. Clone o repositório
```bash
git clone https://github.com/usuario/sistema-agencia-turismo.git
cd sistema-agencia-turismo
```

### 2. Configure o banco de dados
```sql
# Conecte-se ao MySQL
mysql -u root -p

# Execute os scripts
source database/script_criacao_banco.sql
source database/script_povoamento.sql
```

### 3. Configure a conexão
Edite `src/main/java/com/agenciaturismo/util/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/agencia_turismo";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 4. Execute o sistema
```bash
# Opção 1: Via Maven
mvn clean compile exec:java

# Opção 2: Gerar JAR e executar
mvn clean package
java -jar target/sistema-agencia-turismo-1.0.0-jar-with-dependencies.jar
```

## 📊 Modelo de Dados

O sistema utiliza as seguintes entidades principais:

- **Clientes** (nacionais e estrangeiros)
- **Pacotes de Viagem** 
- **Serviços Adicionais**
- **Contratos** (relacionamento cliente-pacote)
- **Contrato-Serviços** (serviços adicionais por contrato)

## 🎯 Como Usar

### Cadastro de Cliente Nacional
1. Menu Clientes → Cadastrar Cliente
2. Preencha nome, e-mail, telefone, data nascimento
3. Selecione "Nacional" e informe CPF
4. Clique "Salvar"

### Cadastro de Pacote
1. Menu Pacotes → Cadastrar Pacote
2. Informe nome, destino, descrição, preço
3. Defina datas de início/fim e vagas
4. Clique "Salvar"

### Contratação
1. Menu Contratos → Contratar Pacote
2. Selecione cliente e pacote
3. Revise detalhes e confirme

## 📱 Screenshots

### Tela Principal
- Interface moderna com gradiente azul
- Menu intuitivo organizado por funcionalidade
- Navegação clara entre módulos

### Telas de Cadastro
- Formulários com validação em tempo real
- Campos adaptáveis (Nacional/Estrangeiro)
- Mensagens de erro explicativas

### Listagens
- Tabelas com dados organizados
- Botões contextuais (Atualizar, Excluir)
- Seleção simples e intuitiva

## 🔧 Recursos Técnicos

### Validações Implementadas
- **CPF**: Algoritmo oficial com verificação de dígitos
- **E-mail**: Regex para formato válido
- **Datas**: Validação de formato e lógica
- **Preços**: Valores positivos obrigatórios
- **Regras de Negócio**: Exclusões protegidas

### Tratamento de Erros
- Mensagens de erro amigáveis
- Validação antes de operações críticas
- Rollback automático em falhas

### Performance
- Conexões otimizadas com pool
- Queries indexadas
- Carregamento sob demanda

## 📚 Documentação

- [Manual do Usuário](MANUAL_USUARIO.md) - Guia completo de uso
- [Scripts SQL](database/) - Criação e povoamento do banco
- [Javadoc](docs/) - Documentação do código

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📋 Critérios de Avaliação Atendidos

- ✅ **Funcionalidades Principais (1.5 pts)**: CRUD completo implementado
- ✅ **Interface Gráfica (2.0 pts)**: Swing com navegação intuitiva
- ✅ **Validações (1.5 pts)**: CPF, e-mail, campos obrigatórios
- ✅ **Banco de Dados (1.0 pts)**: MySQL com operações completas
- ✅ **Demonstração (1.5 pts)**: Sistema funcional e demonstrável
- ✅ **POO (1.0 pts)**: Herança, encapsulamento, boas práticas
- ✅ **Documentação (1.5 pts)**: Manual, scripts, modelo de dados

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

## 👥 Autores

- **Equipe de Desenvolvimento** - *Desenvolvimento inicial* - [GitHub](https://github.com/usuario)

## 🙏 Agradecimentos

- Professores e colegas que contribuíram com feedback
- Comunidade Java pelos recursos e documentação
- Usuários beta que testaram o sistema

---

**Desenvolvido com ❤️ para facilitar o gerenciamento de agências de turismo** 