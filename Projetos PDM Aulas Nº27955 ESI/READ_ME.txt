============================================================
              ENTREGA DE PROJETOS ANDROID (KOTLIN)
============================================================

Este arquivo descreve a estrutura das pastas entregues e as
funcionalidades implementadas em cada projeto.

ESTRUTURA DE PASTAS:

[1] PASTA: Calculadora
------------------------------------------------------------
Descrição: Aplicação de calculadora desenvolvida em Jetpack Compose.
Estado: Completa e Funcional.
Detalhes Técnicos:
   - Arquitetura organizada em componentes (View, Brain, Buttons).
   


[2] PASTA: NewsApp (Projeto Principal)
------------------------------------------------------------
Descrição: Aplicação completa de listagem de produtos/notícias com
integração de Backend (Firebase) e Base de Dados Local (Room).

Funcionalidades Implementadas:

   1. API:
      - Consumo de dados externos para listar os produtos/artigos 
   2. Firebase Authentication:
      - Ecrã de Login e Registo de novos utilizadores.
      - Verificação de sessão (Auto-login se o utilizador já entrou).

   3. Firebase Firestore (Carrinho de Compras):
      - Funcionalidade para adicionar itens ao Carrinho.
      - Os dados são salvos na nuvem (Cloud Firestore) associados ao 
        ID do utilizador logado.
      - Página de Perfil que lista os itens do carrinho em tempo real.

   4. Android Room (Favoritos/Offline):
      - Implementação de base de dados local (SQLite) interna do telemóvel.
      - Permite "Favoritar" produtos clicando no ícone de coração.
      - Funciona offline e persiste os dados mesmo sem internet.

============================================================
============================================================