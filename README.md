# anhebi-event-scheduler

Trabalho simples em java para a Anhembi Morumbi

## Projeto: Sistema de Gerenciamento de Eventos (Anhembi Morumbi)

Este projeto é uma aplicação Java de console desenvolvida como parte de um trabalho para a Anhembi Morumbi. Ele implementa um Sistema de Gerenciamento de Eventos projetado para permitir que usuários registrem, gerenciem e participem de diversos eventos. O sistema foca em proporcionar uma experiência de usuário interativa via linha de comando.

### Funcionalidades Principais:

- **Registro e Autenticação de Usuários:** Usuários podem criar novas contas com IDs únicos, fazer login e gerenciar suas sessões.
- **Criação de Eventos:** Usuários podem registrar novos eventos, fornecendo detalhes como nome, endereço, categoria (ex: festas, eventos esportivos, shows), data, hora e descrição.
- **Listagem e Filtragem de Eventos:** O sistema permite aos usuários visualizar todos os eventos registrados. Ele pode ordenar eventos por proximidade da hora atual, identificar eventos em andamento e listar eventos passados.
- **Participação em Eventos:** Usuários podem expressar interesse em participar de eventos. (Nota: A implementação completa para confirmação de participação e cancelamento é uma melhoria futura).
- **Persistência de Dados:** Todos os dados de usuários e eventos são automaticamente salvos em arquivos `users.data` e `events.data`, respectivamente, garantindo que os dados sejam preservados entre as sessões da aplicação.

### Como Compilar e Executar a Aplicação:

**Pré-requisitos:**

- Java Development Kit (JDK) 8 ou superior instalado em seu sistema.

**Instruções:**

1.  **Navegue até a Raiz do Projeto:**
    Abra seu terminal ou prompt de comando e navegue até o diretório raiz do projeto:

    ```bash
    cd \event-scheduler
    ```

2.  **Compile os Arquivos Java:**
    Você precisa compilar todos os arquivos `.java`.

    ```bash
    javac -d bin -cp "lib/*" src/br/anhembi/eventos/controller/*.java src/br/anhembi/eventos/model/*.java src/br/anhembi/eventos/model/enums/*.java src/br/anhembi/eventos/services/*.java src/br/anhembi/eventos/utils/*.java src/br/anhembi/eventos/view/*.java
    ```

3.  **Execute a Aplicação:**
    Após a compilação bem-sucedida, você pode executar a aplicação a partir do diretório `bin`.

    ```bash
    java -cp "bin;lib/*" br.anhembi.eventos.controller.SistemaEventos
    ```
