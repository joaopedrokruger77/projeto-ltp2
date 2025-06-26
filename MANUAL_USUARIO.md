# Manual do Usuário - Sistema de Gerenciamento de Agência de Turismo

## Índice
1. [Sobre o Sistema](#sobre-o-sistema)
2. [Requisitos do Sistema](#requisitos-do-sistema)
3. [Instalação](#instalação)
4. [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
5. [Executando o Sistema](#executando-o-sistema)
6. [Funcionalidades](#funcionalidades)
7. [Guia de Uso](#guia-de-uso)
8. [Solução de Problemas](#solução-de-problemas)
9. [Suporte](#suporte)

## Sobre o Sistema

O Sistema de Gerenciamento de Agência de Turismo é uma aplicação desktop desenvolvida em Java com interface gráfica Swing e banco de dados MySQL. O sistema permite gerenciar clientes, pacotes de viagem, serviços adicionais e contratos de forma integrada e eficiente.

### Principais Funcionalidades:
- ✅ Cadastro e gerenciamento de clientes nacionais e estrangeiros
- ✅ Gerenciamento de pacotes de viagem
- ✅ Cadastro de serviços adicionais
- ✅ Contratação de pacotes por clientes
- ✅ Consulta de contratos e relatórios
- ✅ Validações automáticas de dados
- ✅ Interface gráfica intuitiva

## Requisitos do Sistema

### Software Necessário:
- **Java 11** ou superior
- **MySQL 8.0** ou superior
- **Maven 3.6** ou superior (para compilação)
- **Sistema Operacional**: Windows 10+, macOS 10.14+, ou Linux

### Hardware Mínimo:
- **RAM**: 4 GB
- **Espaço em Disco**: 500 MB
- **Processador**: Dual-core 2.0 GHz

## Instalação

### 1. Instalação do Java
```bash
# Verificar se o Java está instalado
java -version

# Se não estiver instalado, baixe do site oficial:
# https://adoptium.net/
```

### 2. Instalação do MySQL
```bash
# Download do MySQL: https://dev.mysql.com/downloads/mysql/
# Ou usando Docker:
docker run --name mysql-agencia -e MYSQL_ROOT_PASSWORD=senha123 -p 3306:3306 -d mysql:8.0
```

### 3. Clone ou Download do Projeto
```bash
# Se usar Git:
git clone [URL_DO_REPOSITORIO]
cd sistema-agencia-turismo

# Ou extraia o arquivo ZIP baixado
```

## Configuração do Banco de Dados

### 1. Criação do Banco de Dados
```sql
-- Execute os scripts na seguinte ordem:

-- 1. Conecte-se ao MySQL como root
mysql -u root -p

-- 2. Execute o script de criação
source database/script_criacao_banco.sql

-- 3. Execute o script de povoamento (opcional)
source database/script_povoamento.sql
```

### 2. Configuração da Conexão
Edite o arquivo `src/main/java/com/agenciaturismo/util/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/agencia_turismo";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

## Executando o Sistema

### Opção 1: Usando Maven (Desenvolvimento)
```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn exec:java -Dexec.mainClass="com.agenciaturismo.gui.TelaPrincipal"
```

### Opção 2: Gerando JAR Executável
```bash
# Gerar JAR com dependências
mvn clean package

# Executar o JAR gerado
java -jar target/sistema-agencia-turismo-1.0.0-jar-with-dependencies.jar
```

### Opção 3: Execução Direta (IDE)
1. Importe o projeto na sua IDE (IntelliJ, Eclipse, NetBeans)
2. Configure o Java 11+ como JDK do projeto
3. Execute a classe `com.agenciaturismo.gui.TelaPrincipal`

## Funcionalidades

### 1. Gerenciamento de Clientes

#### Cadastro de Clientes
- **Localização**: Menu Clientes → Cadastrar Cliente
- **Funcionalidades**:
  - Cadastro de clientes nacionais (com CPF)
  - Cadastro de clientes estrangeiros (com passaporte)
  - Validação automática de CPF
  - Validação de formato de e-mail
  - Campos obrigatórios destacados

#### Listagem de Clientes
- **Localização**: Menu Clientes → Listar Clientes
- **Funcionalidades**:
  - Visualização de todos os clientes cadastrados
  - Opção de exclusão de clientes
  - Atualização automática da lista

#### Busca de Clientes
- **Localização**: Menu Clientes → Buscar Cliente
- **Funcionalidades**:
  - Busca por nome (parcial)
  - Resultados em tabela
  - Busca em tempo real

### 2. Gerenciamento de Pacotes

#### Cadastro de Pacotes
- **Localização**: Menu Pacotes → Cadastrar Pacote
- **Funcionalidades**:
  - Cadastro completo de pacotes de viagem
  - Validação de datas (fim > início)
  - Validação de preços (maior que zero)
  - Controle de vagas disponíveis

#### Listagem de Pacotes
- **Localização**: Menu Pacotes → Listar Pacotes
- **Funcionalidades**:
  - Visualização de todos os pacotes
  - Exclusão de pacotes (com validação de contratos)
  - Informações de preço e disponibilidade

#### Busca de Pacotes
- **Localização**: Menu Pacotes → Buscar Pacote
- **Funcionalidades**:
  - Busca por destino
  - Filtros de pesquisa
  - Resultados detalhados

### 3. Gerenciamento de Serviços

#### Cadastro de Serviços
- **Localização**: Menu Serviços → Cadastrar Serviço
- **Funcionalidades**:
  - Cadastro de serviços adicionais
  - Categorização de serviços
  - Precificação flexível

#### Listagem de Serviços
- **Localização**: Menu Serviços → Listar Serviços
- **Funcionalidades**:
  - Visualização por categoria
  - Gerenciamento de preços
  - Exclusão de serviços

### 4. Gerenciamento de Contratos

#### Contratação de Pacotes
- **Localização**: Menu Contratos → Contratar Pacote
- **Funcionalidades**:
  - Seleção de cliente e pacote
  - Visualização de detalhes do pacote
  - Validação de disponibilidade
  - Confirmação de contratação

#### Consulta de Contratos
- **Localização**: Menu Contratos → Consultar Contratos
- **Funcionalidades**:
  - Pacotes por cliente
  - Clientes por pacote
  - Relatórios detalhados

## Guia de Uso

### Fluxo Básico de Operação

#### 1. Primeiro Acesso
1. Execute o sistema
2. Verifique a conexão com o banco de dados
3. Se necessário, cadastre dados de teste

#### 2. Cadastrando um Cliente Nacional
1. Acesse: Menu Clientes → Cadastrar Cliente
2. Preencha os dados obrigatórios:
   - Nome completo
   - E-mail válido
   - Telefone
   - Data de nascimento (formato dd/MM/yyyy)
3. Selecione "Nacional"
4. Informe o CPF (será validado automaticamente)
5. Clique em "Salvar"

#### 3. Cadastrando um Pacote de Viagem
1. Acesse: Menu Pacotes → Cadastrar Pacote
2. Preencha as informações:
   - Nome do pacote
   - Destino
   - Descrição detalhada
   - Preço (use ponto ou vírgula para decimais)
   - Datas de início e fim
   - Número de vagas
3. Clique em "Salvar"

#### 4. Realizando uma Contratação
1. Acesse: Menu Contratos → Contratar Pacote
2. Selecione o cliente na lista
3. Selecione o pacote desejado
4. Revise os detalhes mostrados
5. Confirme a contratação

#### 5. Consultando Contratos
1. Acesse: Menu Contratos → Consultar Contratos
2. Escolha o tipo de consulta:
   - "Pacotes por Cliente": mostra todos os pacotes de um cliente
   - "Clientes por Pacote": mostra todos os clientes de um pacote
3. Selecione o cliente ou pacote
4. Clique em "Consultar"

### Dicas de Uso

#### Validações Importantes
- **CPF**: Deve ter 11 dígitos e ser válido
- **Passaporte**: Entre 6 e 15 caracteres
- **E-mail**: Formato válido (usuario@dominio.com)
- **Datas**: Formato dd/MM/yyyy
- **Preços**: Valores positivos, use vírgula ou ponto para decimais

#### Navegação
- Use **Tab** para navegar entre campos
- **Enter** em campos de busca executa a pesquisa
- **Esc** fecha janelas de diálogo
- Clique duplo em tabelas para mais detalhes

#### Gerenciamento de Dados
- Sempre confirme exclusões importantes
- Use a função "Atualizar" para recarregar listas
- Pacotes com clientes não podem ser excluídos
- Dados são salvos automaticamente no banco

## Solução de Problemas

### Problemas Comuns

#### 1. Erro de Conexão com Banco de Dados
**Problema**: "Erro ao conectar com o banco de dados"

**Soluções**:
```bash
# Verificar se o MySQL está rodando
sudo systemctl status mysql  # Linux
brew services list | grep mysql  # macOS

# Testar conexão manual
mysql -u root -p -h localhost agencia_turismo

# Verificar configurações em DatabaseConnection.java
```

#### 2. Erro "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Problema**: Driver MySQL não encontrado

**Solução**:
```bash
# Reinstalar dependências
mvn clean install

# Verificar se o mysql-connector-java está no classpath
```

#### 3. Problemas de Validação de CPF
**Problema**: CPF válido sendo rejeitado

**Solução**:
- Use apenas números (sem pontos ou traços)
- Verifique se tem exatamente 11 dígitos
- Teste com CPFs conhecidamente válidos

#### 4. Interface Não Responsiva
**Problema**: Aplicação trava ou não responde

**Soluções**:
```bash
# Aumentar memória da JVM
java -Xmx512m -jar aplicacao.jar

# Verificar logs de erro no console
# Reiniciar a aplicação
```

#### 5. Caracteres Especiais
**Problema**: Acentos não aparecem corretamente

**Solução**:
- Verificar encoding do banco (UTF-8)
- Configurar JVM: -Dfile.encoding=UTF-8

### Logs e Depuração

#### Ativando Logs Detalhados
```bash
# Executar com logs detalhados
java -Djava.util.logging.level=FINE -jar aplicacao.jar
```

#### Verificando Estado do Banco
```sql
-- Verificar tabelas criadas
SHOW TABLES;

-- Verificar dados de teste
SELECT COUNT(*) FROM clientes;
SELECT COUNT(*) FROM pacotes_viagem;
SELECT COUNT(*) FROM contratos;
```

## Suporte

### Informações Técnicas
- **Versão do Sistema**: 1.0.0
- **Linguagem**: Java 11+
- **Framework GUI**: Java Swing
- **Banco de Dados**: MySQL 8.0+
- **Padrão de Arquitetura**: DAO (Data Access Object)

### Contato
- **E-mail Técnico**: suporte@agenciaturismo.com
- **Documentação**: Consulte este manual
- **Código Fonte**: Disponível no repositório do projeto

### Atualizações
Para obter atualizações do sistema:
1. Verifique o repositório oficial
2. Baixe a versão mais recente
3. Execute os scripts de migração de banco se necessário
4. Faça backup dos dados antes de atualizar

---

**Desenvolvido com ❤️ para facilitar o gerenciamento de agências de turismo** 