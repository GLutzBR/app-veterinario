# Regras de Negócio

## Propósito da aplicação

- Gestão de clínica veterinária.

## Funcionalidades

- Gestão de Clientes
    - Cadastro;
    - Listagem;
    - Edição;
    - Exclusão*;
    - Busca.
- Gestão de Animais
    - Cadastro;
    - Listagem;
    - Edição;
    - Exclusão*;
    - Busca.
- Gestão de Usuários
    - Cadastro;
    - Listagem;
    - Edição;
    - Inativação;
    - Busca.
- Gestão de Prontuários
    - Cadastro;
    - Listagem;
    - Arquivamento;
    - Busca por paciente.

## Models

### Usuários

#### Usuário Comum

- [X] **Características**:
    - Usuário de login;
    - Senha;
    - Nome;
    - Sobrenome;
    - Identificação de se está ativo;
    - Nível de permissão de acesso;

#### Usuários Veterinários

- [X] **Características**:
    - Mesmas características do usuário comum
    - Especialidade (como um texto mesmo);
    - Identificação de estado do conselho regional de veterinária;
    - Número do conselho regional de veterinária;

### Clientes

- Um cliente pode possuir vários animais;
- [X] **Características**:
    - Nome;
    - Data de nascimento;
    - Endereço;
    - Telefone;
    - Animais de estimação.

### Animais

- Um animal possui um único dono;
- [X] **Características**:
    - Nome;
    - Idade;
    - Raça;
    - Dono.

### Prontuários

- [X] **Características**:
    - Veterinário que realizou o atendimento;
    - Animal atendido;
    - Data de atendimento;
    - Um campo de observações gerais sobre o atendimento.

## Permissões de acesso

- [X] **ROLE_ADMIN (Administrador)**:
    - Usuário tem permissão de superusuário para administrar todas as funcionalidades da ferramenta.

- [X] **ROLE_USER (Usuário Comum)**:
    - Listar/Detalhar/Cadastrar/Editar/Desabilitar usuários;
    - Listar/Detalhar/Cadastrar/Editar/Excluir clientes;
    - Listar/Detalhar/Cadastrar/Editar/Excluir animais;
    - Listar/Detalhar atendimentos;

- [X] **ROLE_VET (Usuário Veterinário)**:
  - Listar/Detalhar/Cadastrar/Editar/Desabilitar usuários;
    - Listar/Detalhar/Cadastrar/Editar/Excluir clientes;
    - Listar/Detalhar/Cadastrar/Editar/Excluir animais;
    - Listar/Detalhar/Cadastrar/Arquivar atendimentos;

## UI/UX

- [X] **Cliente**:
    - [X] **Cadastrar**:
        - [X] O cliente pode ser inserido sem vincular um pet;
        - [X] Deve ser possível adicionar um pet após inserir um Cliente.
    - [X] **Listar**:
    - [X] **Detalhar**:
    - [X] **Editar**:
        - [X] É possível após realizar as alterações alterar todos os pets vinculados a este cliente.
            - [X] Caso o cliente não possua nenhum animal vinculado será redirecionado para inclusão de animais.
    - [X] **Buscar**:
    - [X] **Excluir**:
        - [X] Caso o cliente possua algum animal com prontuário registrado não é possível excluir o seu cadastro.

- [X] **Pet**:
    - [X] **Cadastrar**:
        - [X] É permitido inserir um pet apenas se estiver vinculado a um Cliente.
        - [X] Caso o cadastro seja em sequência após o cadastro de um cliente ou pet o formulário deve vincular
          automaticamente o dono selecionado no formulário anterior e não permitir a edição;
    - [X] **Listar**:
    - [X] **Detalhar**:
    - [X] **Editar**:
    - [X] **Buscar**:
    - [X] **Excluir**:
        - [X] Caso o animal possua algum prontuário registrado não é possível excluir o seu cadastro.

- [X] **Prontuários**:
    - [X] **Registrar**:
    - [X] **Listar**:
    - [X] **Detalhar**:
    - [X] **Editar**:
    - [X] **Buscar**:
    - [X] **Arquivar**:
    - [X] **Excluir**:
        - [X] Exclusão permitida somente pelo administrador.

- [X] **Roles**:
    - [X] **Listar**:
    - [X] **Detalhar**:
    - [X] **Buscar**:

- [X] **Usuários**:
  - [X] **Cadastrar**:
  - [X] **Listar**:
  - [X] **Detalhar**:
  - [X] **Editar**:
  - [X] **Buscar**:
  - [X] **Inativar**:

## Observações

- Nenhuma entidade Cliente ou Animal podem ser excluídas após ser registrado um atendimento;
    - Caso não tenha relação com prontuário,
        - um animal pode ser excluído e desvinculado do dono;
        - um cliente pode ser excluído e com ele será excluído todos seus pets;
    - Caso o prontuário seja excluído por um administrador é possível realizar a exclusão do cliente/animal.
- Prontuários não podem ser excluídos*;
    - Prontuários podem ser excluídos apenas por usuários administradores.
- Usuários não podem ser excluídos, apenas desabilitados.