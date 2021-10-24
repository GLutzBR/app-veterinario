# Regras de Negócio

## Controle de acesso

Os usuários serão segmentados em 3 níveis de acesso: Admin, Vet, User, onde se traduzem nos respectivos cargos: Gerente,
Veterinário e Demais Cargos.

A atribuição de acesso se dá pelo cadastro do funcionário onde é identificado o seu cargo.

A área de administração da solução é acessível apenas para Gestores e Veterinários, mas o controle de cargos é acessível
apenas para Gestores.

As demais funcionalidades são acessíveis por qualquer um autenticado, exceto a página de atendimento que é de
responsabilidade apenas do Veterinário.

O usuário insere uma senha quando é realizado o seu cadastro e somente pode alterar quando acessa o seu próprio perfil.

## Controle de Clientes

O Controle de Clientes serve para controlar os atendimentos realizados pelos veterinários, mas os estes não terão acesso
à “login” na plataforma.

- Um cliente não precisa ter um animal de estimação vinculado ao seu cadastro, mas todo animal precisa ser vinculado a um
cliente para ser cadastrado.
- Um cliente que não possua animais associados pode ser excluído normalmente;
- Um cliente não pode ser excluído caso um de seus animais tenha sido atendido;
- Um animal não pode ser excluído, caso tenha sido atendido.

## Controle de Atendimento

Este é o principal recurso da plataforma, que serve para gerenciar os prontuários de atendimento dos animais que são
atendidos pelos veterinários da instituição.