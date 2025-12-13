<div  align="center" id="about">
    <h1 align="center">
        Virtual Memory Page Replacement Simulator
    </h1>
    <p align="center">
        Este projeto foi desenvolvido como um simulador do sistema de gerenciamento de Memória Virtual utilizando Paginação por Demanda em um único processo. O foco principal é a simulação e comparação do desempenho de quatro políticas distintas de substituição de páginas (FIFO, RAND, LRU e MIN/OPT), contabilizando o número de Page Faults para cada cenário, bem como o tempo decorrido em cada processamento. Desenvolvido como trabalho prático acadêmico, o projeto oferece uma implementação completa com gerador automático de casos de teste e scripts de automação para compilação e execução.
    </p>
    <img 
        src="./resources\banner.png"
        alt="Virtual Memory Paging Simulator"
    />
</div>
<br>
<div align="center">
    <a href="https://www.oracle.com/java/" target="_blank">
        <img src="https://img.shields.io/badge/feito_com-Java-ED8B00" alt="Made with Java">
    </a>
    <a href="https://www.python.org/" target="_blank">
        <img src="https://img.shields.io/badge/gerador_de_testes-Python-3776AB" alt="Test Generator with Python">
    </a>
    <a href="https://github.com/arturbomtempo-dev/virtual-memory-page-replacement-simulator" target="_blank">
        <img src="https://img.shields.io/badge/status-concluído-brightgreen" alt="Status: Completed">
    </a>
    <a href="https://github.com/arturbomtempo-dev/virtual-memory-page-replacement-simulator/blob/main/LICENSE.md" target="_blank">
        <img src="https://img.shields.io/badge/license-MIT-red" alt="MIT License">
    </a>
</div>

---

<br>

<div id="table-of-contents"></div>

## 📋 Tabela de conteúdos

- [Sobre](#about)
- [Tabela de conteúdos](#table-of-contents)
- [Funcionalidades](#features)
- [Políticas de Substituição de Páginas](#page-replacement-policies)
- [Demonstração da aplicação](#application-demonstration)
- [Configuração e Execução da Aplicação](#setup-and-run-the-application)
- [Tecnologias](#technologies)
- [Autores](#authors)
- [Licença](#license)

<div id="features"></div>

## 📝 Funcionalidades

- [x] Simulação de memória virtual com paginação por demanda
- [x] Implementação de 4 políticas de substituição de páginas (FIFO, RAND, LRU, MIN/OPT)
- [x] Cálculo automático de parâmetros derivados (tamanho da página, número de frames, tamanho do swap)
- [x] Contabilização de page faults para cada política
- [x] Medição do tempo de execução de cada simulação
- [x] Rastreamento do estado do swap ao final de cada processamento
- [x] Suporte a múltiplas sequências de requisições de páginas
- [x] Gerador automático de casos de teste (pequenos, médios e grandes)
- [x] Scripts de automação para compilação e execução (Windows e Linux/macOS)

<div id="page-replacement-policies"></div>

## 🔄 Políticas de Substituição de Páginas

O simulador implementa as quatro políticas clássicas de substituição de páginas estudadas em Sistemas Operacionais:

### FIFO (First-In, First-Out)

Substitui a página que está na memória há mais tempo, ou seja, a primeira página a entrar é a primeira a sair. É a política mais simples de implementar, utilizando uma fila para controlar a ordem de chegada das páginas.

### RAND (Aleatório)

Substitui um frame escolhido aleatoriamente da memória física. Embora não seja eficiente na prática, serve como baseline para comparação com outras políticas e é útil para análise estatística.

### LRU (Least Recently Used)

Substitui a página cujo último acesso ocorreu no tempo mais distante. Baseia-se no princípio da localidade temporal: páginas usadas recentemente tendem a ser usadas novamente em breve.

### MIN/OPT (Ótima de Belady)

Substitui a página que não será utilizada pelo período mais longo no futuro. É uma política teórica que requer conhecimento prévio de toda a sequência de requisições, servindo como limite inferior para o número de page faults.

<div id="application-demonstration"></div>

## 📲 Demonstração da aplicação

O projeto é composto por duas partes principais: o **gerador de casos de teste** e o **simulador de paginação**.

### Passo 1: Gerar casos de teste

Dentro da pasta `test-case-generator/` há um tutorial completo (`README.md`) explicando como executar o gerador de casos de teste. Este script Python permite criar arquivos de entrada com diferentes níveis de complexidade.

> ⚠️ **Pré-requisito:** Python 3.x instalado na máquina.

### Passo 2: Preparar os arquivos de entrada

Após gerar os casos de teste, copie os arquivos `.txt` gerados para a pasta `paging-sim/input/`.

### Passo 3: Executar o simulador

Abra o terminal e navegue até a pasta do projeto:

```bash
cd paging-sim
```

Execute o script apropriado para seu sistema operacional:

**Linux/macOS:**

```bash
./run.sh
```

**Windows:**

```cmd
./run.bat
```

> ⚠️ **Pré-requisito:** Java JDK 21+ instalado na máquina.

### Passo 4: Visualizar os resultados

Os resultados das simulações serão gerados na pasta `paging-sim/output/`, com um arquivo de saída correspondente para cada arquivo de entrada processado.

<div id="setup-and-run-the-application"></div>

## 📁 Configuração e Execução da Aplicação

### ⚙️ Pré-requisitos

Antes de começar, você precisa ter as seguintes ferramentas instaladas na sua máquina:

- [Git](https://git-scm.com) - Para clonar o repositório
- [Java JDK 21+](https://www.oracle.com/java/technologies/downloads/) - Para compilar e executar o simulador
- [Python 3.x](https://www.python.org/downloads/) - Para executar o gerador de casos de teste

Também é recomendável utilizar um editor de código como o [Visual Studio Code](https://code.visualstudio.com/).

### 🚀 Como Rodar a Aplicação Localmente

```bash
# Clone este repositório
$ git clone https://github.com/arturbomtempo-dev/virtual-memory-page-replacement-simulator.git

# Acesse a pasta do projeto
$ cd virtual-memory-page-replacement-simulator

# Gerar casos de teste (opcional - já existem exemplos na pasta input)
$ cd test-case-generator
$ python3 main.py -s 5 --min_req 50 --max_req 100 -p 32 > small.txt

# Voltar e executar o simulador
$ cd ../paging-sim

# Linux/macOS
$ ./run.sh

# Windows
$ ./run.bat

# Os resultados estarão disponíveis na pasta output/
```

### 📂 Estrutura do Projeto

```
virtual-memory-page-replacement-simulator/
├── paging-sim/                    # Simulador principal
│   ├── src/                       # Código-fonte Java
│   │   ├── app/                   # Aplicação principal
│   │   ├── model/                 # Modelos de dados
│   │   ├── parser/                # Parser de entrada
│   │   ├── policy/                # Políticas de substituição
│   │   ├── validation/            # Validação de entrada
│   │   └── exception/             # Exceções personalizadas
│   ├── bin/                       # Classes compiladas
│   ├── input/                     # Arquivos de entrada
│   ├── output/                    # Arquivos de saída
│   ├── run.sh                     # Script de execução (Linux/macOS)
│   └── run.bat                    # Script de execução (Windows)
│
└── test-case-generator/           # Gerador de casos de teste
    ├── main.py                    # Script principal
    ├── README.md                  # Documentação do gerador
    ├── small.txt                  # Exemplo de saída pequena
    ├── medium.txt                 # Exemplo de saída média
    └── large.txt                  # Exemplo de saída grande
```

<div id="technologies"></div>

## 💻 Tecnologias

As seguintes ferramentas e linguagens foram utilizadas no desenvolvimento deste projeto:

- [**Java**](https://docs.oracle.com/en/java/): Linguagem principal utilizada para implementar o simulador de memória virtual, incluindo as políticas de substituição de páginas, estruturas de dados e lógica de simulação.
- [**Python**](https://docs.python.org/3/): Linguagem utilizada para desenvolver o gerador automático de casos de teste, permitindo criar entradas de diferentes tamanhos e complexidades.
- [**Shell Script (Bash)**](https://www.gnu.org/software/bash/manual/): Script de automação para compilação e execução do projeto em sistemas Linux e macOS.
- [**Batch Script (CMD)**](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/windows-commands): Script de automação para compilação e execução do projeto em sistemas Windows.

<div id="authors"></div>

## 👨🏻‍💻 Autores

---

| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/96635074?v=4" width=115><br><sub>Artur Bomtempo</sub>](https://arturbomtempo.dev/) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/159597766?v=4" width=115><br><sub>Eduarda Vieira</sub>](https://www.linkedin.com/in/eduarda-vieira-gon%C3%A7alves-01a584297/) |
| :--------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |

Desenvolvido por Artur Bomtempo e Eduarda Vieira 👋🏻. Entre em contato:

**Artur Bomtempo:**  
[![Gmail Badge](https://img.shields.io/badge/-arturbcolen@gmail.com-D14836?style=flat-square&logo=Gmail&logoColor=white&link=mailto:arturbcolen@gmail.com)](mailto:arturbcolen@gmail.com)
[![LinkedIn Badge](https://img.shields.io/badge/-Artur%20Bomtempo-0A66C2?style=flat-square&logo=LinkedIn&logoColor=white&link=https://www.linkedin.com/in/artur-bomtempo/)](https://www.linkedin.com/in/artur-bomtempo/)
[![Instagram Badge](https://img.shields.io/badge/-@arturbomtempo.dev-E4405F?style=flat-square&logo=Instagram&logoColor=white&link=https://www.instagram.com/arturbomtempo.dev/)](https://www.instagram.com/arturbomtempo.dev/)

**Eduarda Vieira:**  
[![Gmail Badge](https://img.shields.io/badge/-eduarda.vieira.goncalves7@gmail.com-D14836?style=flat-square&logo=Gmail&logoColor=white&link=mailto:eduarda.vieira.goncalves7@gmail.com)](mailto:eduarda.vieira.goncalves7@gmail.com)
[![LinkedIn Badge](https://img.shields.io/badge/-Eduarda%20Vieira-0A66C2?style=flat-square&logo=LinkedIn&logoColor=white&link=https://www.linkedin.com/in/eduarda-vieira-gon%C3%A7alves-01a584297/)](https://www.linkedin.com/in/eduarda-vieira-gon%C3%A7alves-01a584297/)
[![Instagram Badge](https://img.shields.io/badge/-@eduardavieira.dev-E4405F?style=flat-square&logo=Instagram&logoColor=white&link=https://www.instagram.com/eduardavieira.dev/)](https://www.instagram.com/eduardavieira.dev/)

<div id="license"></div>

## 📜 Licença

Copyright (c) 2025 Artur Bomtempo Colen

É concedida, gratuitamente, permissão a qualquer pessoa que obtenha uma cópia
deste software e dos arquivos de documentação associados (o “Software”), para
utilizar o Software sem restrições, incluindo, sem limitação, os direitos de
usar, copiar, modificar, mesclar, publicar, distribuir, sublicenciar e/ou vender
cópias do Software, bem como permitir que pessoas a quem o Software seja
fornecido o façam, desde que sujeitas às seguintes condições:

O aviso de copyright acima e esta permissão deverão ser incluídos em todas as
cópias ou partes substanciais do Software.

O SOFTWARE É FORNECIDO “NO ESTADO EM QUE SE ENCONTRA”, SEM GARANTIA DE
QUALQUER TIPO, EXPRESSA OU IMPLÍCITA, INCLUINDO, MAS NÃO SE LIMITANDO, ÀS
GARANTIAS DE COMERCIALIZAÇÃO, ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA E
NÃO VIOLAÇÃO. EM NENHUMA HIPÓTESE OS AUTORES OU DETENTORES DOS DIREITOS
AUTORAIS SERÃO RESPONSÁVEIS POR QUALQUER REIVINDICAÇÃO, DANO OU OUTRA
RESPONSABILIDADE, SEJA EM UMA AÇÃO CONTRATUAL, EXTRACONTRATUAL OU DE
OUTRA NATUREZA, DECORRENTE DE, OU RELACIONADA AO SOFTWARE OU AO USO OU
OUTRAS NEGOCIAÇÕES COM O SOFTWARE.
