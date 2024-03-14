# Challenge Backend 1 - Alura


## 💻 Sobre o projeto

### Plataforma para compartilhamento de vídeos:

- A plataforma deve permitir ao usuário criar playlists com links para seus vídeos preferidos, organizados por categorias.
- A persistência das informações será realizada utilizando o MySQL.
- Serviço login e autenticação para acesso às rotas GET, POST, PUT e DELETE.

### Entidades
- Videos
- Categorias
- Users

### Validações / Regras de Negócio
- As entidades Vídeos e Categorias não podem ter campos nulos.
- O título da Categoria não pode ser repetido.
- Adição de novos usuários com ou sem papéis (Roles).
- Adicionar Role padrão USER_ROLE para novos usuários sem roles.
- Verificações para garantir a existência das Roles antes do cadastro.
- Apenas usuários com permissões de ADMIN podem cadastrar novos usuários.

### Teste
- Testes Unitários
- Testes de Integração

