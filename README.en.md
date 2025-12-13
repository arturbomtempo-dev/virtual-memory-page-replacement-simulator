<div  align="center" id="about">
    <h1 align="center">
        Virtual Memory Page Replacement Simulator
    </h1>
    <p align="center">
        This project was developed as a Virtual Memory management system simulator using Demand Paging in a single process. The main focus is the simulation and comparison of the performance of four distinct page replacement policies (FIFO, RAND, LRU, and MIN/OPT), counting the number of Page Faults for each scenario, as well as the elapsed time in each processing. Developed as an academic practical assignment, the project offers a complete implementation with an automatic test case generator and automation scripts for compilation and execution.
    </p>
    <img 
        src="./resources\banner-en.png"
        alt="Virtual Memory Paging Simulator"
    />
</div>
<br>
<div align="center">
    <a href="https://www.oracle.com/java/" target="_blank">
        <img src="https://img.shields.io/badge/made_with-Java-ED8B00" alt="Made with Java">
    </a>
    <a href="https://www.python.org/" target="_blank">
        <img src="https://img.shields.io/badge/test_generator-Python-3776AB" alt="Test Generator with Python">
    </a>
    <a href="https://github.com/arturbomtempo-dev/virtual-memory-page-replacement-simulator" target="_blank">
        <img src="https://img.shields.io/badge/status-completed-brightgreen" alt="Status: Completed">
    </a>
    <a href="https://github.com/arturbomtempo-dev/virtual-memory-page-replacement-simulator/blob/main/LICENSE.md" target="_blank">
        <img src="https://img.shields.io/badge/license-MIT-red" alt="MIT License">
    </a>
</div>

---

<div align="center">
    <p>🇧🇷 <a href="https://github.com/arturbomtempo-dev/virtual-memory-page-replacement-simulator/blob/main/README.md" target="_blank"><strong>Versão em Português disponível aqui</strong></a></p>
</div>

<br>

<div id="table-of-contents"></div>

## 📋 Table of Contents

- [About](#about)
- [Table of Contents](#table-of-contents)
- [Features](#features)
- [Page Replacement Policies](#page-replacement-policies)
- [Application Demo](#application-demonstration)
- [Setup and Run the Application](#setup-and-run-the-application)
- [Technologies](#technologies)
- [Authors](#authors)
- [License](#license)

<div id="features"></div>

## 📝 Features

- [x] Virtual memory simulation with demand paging
- [x] Implementation of 4 page replacement policies (FIFO, RAND, LRU, MIN/OPT)
- [x] Automatic calculation of derived parameters (page size, number of frames, swap size)
- [x] Page fault counting for each policy
- [x] Execution time measurement for each simulation
- [x] Swap state tracking at the end of each processing
- [x] Support for multiple page request sequences
- [x] Automatic test case generator (small, medium, and large)
- [x] Automation scripts for compilation and execution (Windows and Linux/macOS)

<div id="page-replacement-policies"></div>

## 🔄 Page Replacement Policies

The simulator implements the four classic page replacement policies studied in Operating Systems:

### FIFO (First-In, First-Out)

Replaces the page that has been in memory the longest, i.e., the first page to enter is the first to leave. It is the simplest policy to implement, using a queue to control the order of page arrivals.

### RAND (Random)

Replaces a randomly chosen frame from physical memory. Although not efficient in practice, it serves as a baseline for comparison with other policies and is useful for statistical analysis.

### LRU (Least Recently Used)

Replaces the page whose last access occurred the furthest in the past. It is based on the principle of temporal locality: pages used recently tend to be used again soon.

### MIN/OPT (Belady's Optimal)

Replaces the page that will not be used for the longest period in the future. It is a theoretical policy that requires prior knowledge of the entire request sequence, serving as a lower bound for the number of page faults.

<div id="application-demonstration"></div>

## 📲 Application Demo

The project consists of two main parts: the **test case generator** and the **paging simulator**.

### Step 1: Generate test cases

Inside the `test-case-generator/` folder, there is a complete tutorial (`README.md`) explaining how to run the test case generator. This Python script allows you to create input files with different levels of complexity.

> ⚠️ **Prerequisite:** Python 3.x installed on your machine.

### Step 2: Prepare input files

After generating the test cases, copy the generated `.txt` files to the `paging-sim/input/` folder.

### Step 3: Run the simulator

Open the terminal and navigate to the project folder:

```bash
cd paging-sim
```

Run the appropriate script for your operating system:

**Linux/macOS:**

```bash
./run.sh
```

**Windows:**

```cmd
./run.bat
```

> ⚠️ **Prerequisite:** Java JDK 21+ installed on your machine.

### Step 4: View the results

The simulation results will be generated in the `paging-sim/output/` folder, with a corresponding output file for each processed input file.

<div id="setup-and-run-the-application"></div>

## 📁 Setup and Run the Application

### ⚙️ Prerequisites

Before you begin, you need to have the following tools installed on your machine:

- [Git](https://git-scm.com) - To clone the repository
- [Java JDK 21+](https://www.oracle.com/java/technologies/downloads/) - To compile and run the simulator
- [Python 3.x](https://www.python.org/downloads/) - To run the test case generator

It is also recommended to use a code editor such as [Visual Studio Code](https://code.visualstudio.com/).

### 🚀 How to Run the Application Locally

```bash
# Clone this repository
$ git clone https://github.com/arturbomtempo-dev/virtual-memory-page-replacement-simulator.git

# Access the project folder
$ cd virtual-memory-page-replacement-simulator

# Generate test cases (optional - examples already exist in the input folder)
$ cd test-case-generator
$ python3 main.py -s 5 --min_req 50 --max_req 100 -p 32 > small.txt

# Go back and run the simulator
$ cd ../paging-sim

# Linux/macOS
$ ./run.sh

# Windows
$ ./run.bat

# Results will be available in the output/ folder
```

### 📂 Project Structure

```
virtual-memory-page-replacement-simulator/
├── paging-sim/                    # Main simulator
│   ├── src/                       # Java source code
│   │   ├── app/                   # Main application
│   │   ├── model/                 # Data models
│   │   ├── parser/                # Input parser
│   │   ├── policy/                # Replacement policies
│   │   ├── validation/            # Input validation
│   │   └── exception/             # Custom exceptions
│   ├── bin/                       # Compiled classes
│   ├── input/                     # Input files
│   ├── output/                    # Output files
│   ├── run.sh                     # Execution script (Linux/macOS)
│   └── run.bat                    # Execution script (Windows)
│
└── test-case-generator/           # Test case generator
    ├── main.py                    # Main script
    ├── README.md                  # Generator documentation
    ├── small.txt                  # Small output example
    ├── medium.txt                 # Medium output example
    └── large.txt                  # Large output example
```

<div id="technologies"></div>

## 💻 Technologies

The following tools and languages were used in the development of this project:

- [**Java**](https://docs.oracle.com/en/java/): Main language used to implement the virtual memory simulator, including page replacement policies, data structures, and simulation logic.
- [**Python**](https://docs.python.org/3/): Language used to develop the automatic test case generator, allowing the creation of inputs with different sizes and complexities.
- [**Shell Script (Bash)**](https://www.gnu.org/software/bash/manual/): Automation script for compiling and running the project on Linux and macOS systems.
- [**Batch Script (CMD)**](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/windows-commands): Automation script for compiling and running the project on Windows systems.

<div id="authors"></div>

## 👨🏻‍💻 Authors

---

| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/96635074?v=4" width=115><br><sub>Artur Bomtempo</sub>](https://arturbomtempo.dev/) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/159597766?v=4" width=115><br><sub>Eduarda Vieira</sub>](https://www.linkedin.com/in/eduarda-vieira-gon%C3%A7alves-01a584297/) |
| :--------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |

Developed by Artur Bomtempo and Eduarda Vieira 👋🏻. Get in touch:

**Artur Bomtempo:**  
[![Gmail Badge](https://img.shields.io/badge/-arturbcolen@gmail.com-D14836?style=flat-square&logo=Gmail&logoColor=white&link=mailto:arturbcolen@gmail.com)](mailto:arturbcolen@gmail.com)
[![LinkedIn Badge](https://img.shields.io/badge/-Artur%20Bomtempo-0A66C2?style=flat-square&logo=LinkedIn&logoColor=white&link=https://www.linkedin.com/in/artur-bomtempo/)](https://www.linkedin.com/in/artur-bomtempo/)
[![Instagram Badge](https://img.shields.io/badge/-@arturbomtempo.dev-E4405F?style=flat-square&logo=Instagram&logoColor=white&link=https://www.instagram.com/arturbomtempo.dev/)](https://www.instagram.com/arturbomtempo.dev/)

**Eduarda Vieira:**  
[![Gmail Badge](https://img.shields.io/badge/-eduarda.vieira.goncalves7@gmail.com-D14836?style=flat-square&logo=Gmail&logoColor=white&link=mailto:eduarda.vieira.goncalves7@gmail.com)](mailto:eduarda.vieira.goncalves7@gmail.com)
[![LinkedIn Badge](https://img.shields.io/badge/-Eduarda%20Vieira-0A66C2?style=flat-square&logo=LinkedIn&logoColor=white&link=https://www.linkedin.com/in/eduarda-vieira-gon%C3%A7alves-01a584297/)](https://www.linkedin.com/in/eduarda-vieira-gon%C3%A7alves-01a584297/)
[![Instagram Badge](https://img.shields.io/badge/-@eduardavieira.dev-E4405F?style=flat-square&logo=Instagram&logoColor=white&link=https://www.instagram.com/eduardavieira.dev/)](https://www.instagram.com/eduardavieira.dev/)

<div id="license"></div>

## 📜 License

Copyright (c) 2025 Artur Bomtempo Colen

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
