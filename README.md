# 🍕 Sistema de Gerenciamento de Pizzaria

**Aluno:** Kauã Lorenseti  
**Disciplina:** Tópicos Especiais III — ADS  
**Professor:** Jeangrei Veiga  
**Instituição:** UPF — Universidade de Passo Fundo  

---

## 📋 Sobre o Projeto

Sistema web de gerenciamento para uma pizzaria desenvolvido com **Jakarta EE**, seguindo a arquitetura MVC definida em aula com o template **Manhattan**. O sistema permite o controle completo do ciclo de atendimento: cadastro de clientes, cardápio de pizzas, registro de pedidos e acompanhamento de entregas.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Jakarta EE | 10 |
| GlassFish | 7.0.12 |
| JSF + PrimeFaces | 13.0.6 |
| Template Manhattan | 7.0.0 |
| JPA / EclipseLink | 4.0.2 |
| PostgreSQL | 18 |
| NetBeans IDE | 21 |
| Maven | 3.x |

---

## 🏗️ Arquitetura do Projeto

O projeto segue o padrão definido pelo professor:

```
View (XHTML + PrimeFaces)
        ↓
Controller (CDI @SessionScoped)
        ↓
Facade (EJB @Stateless) ← AbstractFacade
        ↓
Entity (JPA @Entity)
        ↓
Banco de Dados (PostgreSQL)
```

### Estrutura de Pacotes

```
br.com.pizzaria
├── controller      → Beans CDI (SessionScoped)
├── converter       → Converters JSF para entidades
├── entity          → Entidades JPA
├── enumeration     → Enumerações do domínio
├── facade          → EJBs de persistência
└── filter          → Filtro de segurança
```

---

---

## 📌 Casos de Uso

| # | Módulo | Funcionalidades |
|---|---|---|
| 1 | **Clientes** | Cadastrar, editar, excluir e consultar clientes |
| 2 | **Pizzas** | Gerenciar cardápio com tamanho, preço e disponibilidade |
| 3 | **Pedidos** | Registrar pedidos vinculando cliente e pizza, com cálculo automático do valor total |
| 4 | **Entregas** | Controlar entregas com status e endereço de destino |

---

## 🗄️ Banco de Dados

**SGBD:** PostgreSQL  
**Nome do banco:** `pizzaria`  
**Porta:** `5432`  
**Usuário:** `postgres`

### Como configurar

1. Crie o banco de dados `pizzaria` no PostgreSQL.
2. Abra o **pgAdmin** → selecione o banco `pizzaria` → **Query Tool**.
3. Cole e execute o script SQL abaixo (o usuário administrador será criado automaticamente).

### Script SQL de Criação

```sql
-- =========================================================
-- BANCO DE DADOS - PIZZARIA
-- Aluno: Kauã Lorenseti | ADS - Tópicos Especiais III
-- Professor: Jeangrei Veiga | UPF
-- =========================================================

CREATE TABLE usuario (
    id    SERIAL NOT NULL,
    nome  TEXT   NOT NULL,
    email TEXT   NOT NULL UNIQUE,
    senha TEXT   NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE TABLE cliente (
    id       SERIAL NOT NULL,
    nome     TEXT   NOT NULL,
    email    TEXT   NOT NULL,
    telefone TEXT   NOT NULL,
    endereco TEXT,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);

CREATE TABLE pizza (
    id         SERIAL         NOT NULL,
    nome       TEXT           NOT NULL,
    descricao  TEXT,
    preco      NUMERIC(10, 2) NOT NULL,
    tamanho    TEXT           NOT NULL,
    disponivel BOOLEAN        NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_pizza PRIMARY KEY (id)
);

CREATE TABLE pedido (
    id             SERIAL         NOT NULL,
    id_cliente     INTEGER        NOT NULL,
    id_pizza       INTEGER        NOT NULL,
    datahorapedido TIMESTAMP WITH TIME ZONE NOT NULL,
    quantidade     INTEGER        NOT NULL DEFAULT 1,
    valortotal     NUMERIC(10, 2) NOT NULL,
    status         TEXT           NOT NULL DEFAULT 'PENDENTE',
    CONSTRAINT pk_pedido    PRIMARY KEY (id),
    CONSTRAINT fk_ped_cli   FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    CONSTRAINT fk_ped_pizza FOREIGN KEY (id_pizza)   REFERENCES pizza(id)
);

CREATE TABLE entrega (
    id              SERIAL  NOT NULL,
    id_pedido       INTEGER NOT NULL,
    enderecoentrega TEXT    NOT NULL,
    datahoraenvio   TIMESTAMP WITH TIME ZONE,
    datahoraentrega TIMESTAMP WITH TIME ZONE,
    status          TEXT    NOT NULL DEFAULT 'AGUARDANDO',
    observacao      TEXT,
    CONSTRAINT pk_entrega PRIMARY KEY (id),
    CONSTRAINT fk_ent_ped FOREIGN KEY (id_pedido) REFERENCES pedido(id)
);

INSERT INTO usuario(nome, email, senha)
VALUES ('Administrador', 'admin@pizzaria.com', '123');

## 🗄️ Banco de Dados

**SGBD:** PostgreSQL  
**Nome do banco:** `pizzaria`  
**Porta:** `5432`  
**Usuário:** `postgres`

### Como configurar

1. Crie o banco de dados `pizzaria` no PostgreSQL
2. Abra o **pgAdmin** → selecione o banco `pizzaria` → **Query Tool**
3. Cole e execute o conteúdo do arquivo `script_banco_pizzaria.sql`
4. O usuário administrador será criado automaticamente

### Tabelas

```
usuario   → Usuários do sistema (login)
cliente   → Cadastro de clientes
pizza     → Cardápio de pizzas
pedido    → Registro de pedidos (relaciona cliente e pizza)
entrega   → Controle de entregas (relaciona com pedido)
```

---

## ▶️ Como Executar

### Pré-requisitos

- JDK 21 instalado
- NetBeans 21 com plugin Java EE ativado
- GlassFish 7.0.12 configurado no NetBeans
- PostgreSQL instalado e rodando
- Maven instalado

### Passo a passo

1. **Clone o repositório**
```bash
git clone https://github.com/kaua162/ADS_KauaLorenseti_Pizzaria.git
```

2. **Configure o banco de dados**
   - Crie o banco `pizzaria` no PostgreSQL
   - Execute o `script_banco_pizzaria.sql`

3. **Configure o pool JDBC no GlassFish**  
   O arquivo `glassfish-resources.xml` já está configurado. Certifique-se que a senha do PostgreSQL está correta no arquivo `src/main/webapp/WEB-INF/glassfish-resources.xml`

4. **Abra o projeto no NetBeans**
   - File → Open Project → selecione a pasta do projeto

5. **Instale o JAR do Manhattan** (se necessário)
```bash
mvn install:install-file -Dfile=manhattan-7.0.0.jar -DgroupId=org.primefaces.themes -DartifactId=manhattan -Dversion=7.0.0 -Dpackaging=jar
```

6. **Execute o projeto**
   - Pressione **F6** no NetBeans

7. **Acesse o sistema**
```
http://localhost:8080/pizzaria
```

---

## 🔐 Credenciais de Acesso

| Campo | Valor |
|---|---|
| E-mail | `admin@pizzaria.com` |
| Senha | `123` |

---

## 📁 Estrutura de Arquivos

```
ADS_KauaLorenseti_Pizzaria/
├── src/
│   └── main/
│       ├── java/br/com/pizzaria/
│       │   ├── controller/
│       │   ├── converter/
│       │   ├── entity/
│       │   ├── enumeration/
│       │   ├── facade/
│       │   └── filter/
│       ├── resources/
│       │   └── META-INF/persistence.xml
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── template.xhtml
│           │   ├── sidebar.xhtml
│           │   ├── topbar.xhtml
│           │   ├── web.xml
│           │   ├── beans.xml
│           │   └── glassfish-resources.xml
│           ├── admin/
│           │   ├── cliente.xhtml
│           │   ├── pizza.xhtml
│           │   ├── pedido.xhtml
│           │   └── entrega.xhtml
│           ├── resources/manhattan-layout/
│           └── login.xhtml
├── script_banco_pizzaria.sql
├── pom.xml
└── README.md
```

---

## 🔗 Repositório

[github.com/kaua162/ADS_KauaLorenseti_Pizzaria](https://github.com/kaua162/ADS_KauaLorenseti_Pizzaria)
