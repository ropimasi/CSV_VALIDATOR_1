# CSV File Validator
(dev.ropimasi.csvvalidator1)  

&nbsp;  

### 📋 Sobre o Projeto
  
Este projeto é um utilitário robusto desenvolvido em Java para a validação e triagem automatizada de arquivos no formato .csv.  
Ele atua como um filtro de integridade, garantindo que apenas dados que seguem regras estruturais e de tipagem rigorosas cheguem ao seu destino final.  
O validador foi desenhado para processar arquivos em lote. Ele lê todos os arquivos do tipo .csv de uma pasta de entrada, analisa linha por linha e separa os registros em arquivos de "Sucesso" ou "Erro", sem corromper os arquivos originais.  

&nbsp;  
&nbsp;  
&nbsp;  

### 🛠️ Tecnologias Utilizadas
Linguagem: Java (versão 11 ou superior recomendada).  
I/O: java.nio.file para manipulação eficiente de arquivos.  
Datas: java.time (API moderna de data e hora).  

&nbsp;  
&nbsp;  
&nbsp;  

### ⚙️ Regras de Validação
Para que uma linha seja considerada VÁLIDA, ela deve cumprir simultaneamente os seguintes requisitos:
 - Estrutura: Deve conter exatamente 5 campos separados por ponto e vírgula (;).
 - Preenchimento: Nenhum campo pode estar vazio ou conter apenas espaços em branco.
 - Tipagem Numérica: Os campos 1, 2, 3 e 4 devem ser obrigatoriamente números inteiros (Integer).
 - Tipagem de Data: O campo 5 deve seguir o padrão de data e hora: dd/MM/yyyy HH:mm:ss.  

&nbsp;  
&nbsp;  
&nbsp;  

### 📂 Estrutura de Pastas
O programa gerencia automaticamente os diretórios na raiz do projeto:
 - /PENDENTE: Local onde você deve depositar os arquivos .csv para processamento.
 - /VALIDOS: Contém arquivos gerados apenas com as linhas que passaram em todas as regras.
 - /INVALIDOS: Contém arquivos gerados com as linhas que falharam em algum critério.  

&nbsp;  
&nbsp;  
&nbsp;  

### 🚀 Como Executar
Pré-requisitos:
 - Java JDK 11 ou superior instalado.
 - Uma IDE (Eclipse, IntelliJ, VS Code) ou terminal.
Passo a Passo:
 - Clone este repositório ou copie os arquivos fonte.
 - Certifique-se de que a estrutura de pastas existe, ou execute o programa pela primeira vez para que ele as crie automaticamente.
 - Coloque seus arquivos .csv dentro da pasta PENDENTE.
 - Execute a classe principal ValidadorCSV.java.
 - Verifique os resultados nas pastas VALIDOS e INVALIDOS.  

&nbsp;  
&nbsp;  
&nbsp;  

### 📝 Exemplo de Dados
Linha Válida (Exemplo): 101;202;303;6;25/12/2026 14:30:00
Linha Inválida (Exemplo): 101;abc;303;2;25/12/2026 (Falha: Campo 2 não é número e campo 5 está sem a hora)  

&nbsp;  
&nbsp;  
&nbsp;  

<a name="author"></a>
## Att. Ronaldo Silva.
##### | [https://ropimasi.github.io](https://ropimasi.github.io) | [linkedin @ropimasi](https://linkedin.com/in/ropimasi/) | [x (twitter) @ropimasi](https://x.com/ropimasi/) | [instagram @ropimasi](https://instagram.com/ropimasi/) | [ropimasi@email.com](mailto://ropimasi@email.com) |  

&nbsp;  
&nbsp;  
&nbsp;  
  