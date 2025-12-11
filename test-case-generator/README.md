# Test Case Generator for Virtual Memory Page Replacement Simulator

## Overview

This Python script generates randomized test input files for the **Virtual Memory Page Replacement Simulator** located in the `paging-sim` directory. It automates the creation of test cases with varying complexity levels (small, medium, and large) to validate and benchmark the performance of different page replacement policies (FIFO, LRU, RAND, MIN/OPT).

## Purpose

The test case generator serves several critical purposes:

- **Automated Testing**: Generates multiple test scenarios without manual input creation
- **Performance Benchmarking**: Creates workloads of different sizes to measure simulator performance
- **Validation**: Helps verify correct implementation of page replacement algorithms
- **Stress Testing**: Produces large-scale inputs to test system behavior under heavy load
- **Reproducibility**: Allows consistent test case generation for comparative analysis

## How It Works

The generator creates input files following the exact format expected by the main simulator:

1. **System Configuration**: Physical memory size, virtual memory size, architecture, and number of pages
2. **Request Sequences**: Multiple sequences of page requests with randomized:
   - Number of sequences (configurable)
   - Number of requests per sequence (within min/max bounds)
   - Page indices (randomly selected from 0 to P-1)

## Integration with Main Simulator

```
test-case-generator/          paging-sim/
    │                             │
    ├── main.py            →      ├── input/
    ├── small.txt                 │   └── test.txt
    ├── medium.txt                │
    └── large.txt                 └── src/
                                      └── app/Application.java
```

**Workflow:**

1. Generate test case using `main.py`
2. Redirect output to file: `python3 main.py [options] > test.txt`
3. Move generated file to `paging-sim/input/` directory
4. Run simulator: `cd paging-sim && ./run.sh`
5. View results in `paging-sim/output/` directory

## Prerequisites

- **Python 3.x** (tested with Python 3.7+)
- No external dependencies required (uses only standard library)

## Command-Line Arguments

### Required Arguments

None - all arguments have sensible defaults

### Optional Arguments

| Argument          | Short | Default | Description                                                         |
| ----------------- | ----- | ------- | ------------------------------------------------------------------- |
| `--num_pages`     | `-p`  | 16      | Total number of virtual pages (P). Page indices range from 0 to P-1 |
| `--num_sequences` | `-s`  | 3       | Number of request sequences to generate                             |
| `--max_req`       |       | 100     | **Maximum** number of requests per sequence                         |
| `--min_req`       |       | 10      | **Minimum** number of requests per sequence                         |
| `--physical_mem`  |       | 4096    | Physical memory size (M) in bytes                                   |
| `--virtual_mem`   |       | 16384   | Virtual memory size (V) in bytes (must be ≥ M)                      |
| `--architecture`  |       | x86     | System architecture (choices: x86, x64, 286, 264)                   |

## Usage Examples

### 1. Small Test Case

Generates 5 sequences with 50-100 requests each, using 32 pages:

```bash
python3 main.py -s 5 --min_req 50 --max_req 100 -p 32 > small.txt
```

**Characteristics:**

- **Use Case**: Quick validation, unit testing
- **Sequences**: 5
- **Requests per sequence**: 50-100
- **Pages**: 32
- **Frames**: 4 (calculated: 4096 / 1024 = 4)

### 2. Medium Test Case

Generates 10 sequences with 100-1000 requests each, using 128 pages and larger memory:

```bash
python3 main.py -s 10 --min_req 100 --max_req 1000 -p 128 --physical_mem 131072 --virtual_mem 262144 > medium.txt
```

**Characteristics:**

- **Use Case**: Integration testing, performance evaluation
- **Sequences**: 10
- **Requests per sequence**: 100-1000
- **Pages**: 128
- **Frames**: 64 (calculated: 131072 / 2048 = 64)

### 3. Large Test Case

Generates 10 sequences with 1000-10000 requests each, using 32768 pages:

```bash
python3 main.py -s 10 --min_req 1000 --max_req 10000 -p 32768 --physical_mem 2097152 --virtual_mem 8388608 > large.txt
```

**Characteristics:**

- **Use Case**: Stress testing, scalability analysis
- **Sequences**: 10
- **Requests per sequence**: 1000-10000
- **Pages**: 32768
- **Frames**: 8192 (calculated: 2097152 / 256 = 8192)

### 4. Custom Configuration

Generate with specific parameters for targeted testing:

```bash
python3 main.py -p 64 -s 20 --min_req 200 --max_req 500 \
    --physical_mem 8192 --virtual_mem 32768 --architecture x64 > custom.txt
```

## Output Format

The generated file follows the simulator's expected input format:

```
<Physical Memory Size>
<Virtual Memory Size>
<Architecture>
<Number of Pages>

<Number of Sequences>

<Number of Requests in Sequence 1>
<Space-separated page indices>

<Number of Requests in Sequence 2>
<Space-separated page indices>

...
```

**Example Output:**

```
4096
16384
x86
8

3

19
5 3 1 6 1 0 4 3 7 6 1 4 5 7 4 5 5 2 4

12
7 6 6 1 5 6 1 4 1 3 6 0

17
0 0 7 2 1 2 1 4 0 4 7 3 0 7 4 5 0
```

## Complete Testing Workflow

### Step 1: Generate Test Cases

```bash
cd test-case-generator

# Generate small test
python3 main.py -s 5 --min_req 50 --max_req 100 -p 32 > small.txt

# Generate medium test
python3 main.py -s 10 --min_req 100 --max_req 1000 -p 128 \
    --physical_mem 131072 --virtual_mem 262144 > medium.txt

# Generate large test
python3 main.py -s 10 --min_req 1000 --max_req 10000 -p 32768 \
    --physical_mem 2097152 --virtual_mem 8388608 > large.txt
```

### Step 2: Move to Simulator Input Directory

```bash
# Copy test files to simulator input folder
cp small.txt ../paging-sim/input/
cp medium.txt ../paging-sim/input/
cp large.txt ../paging-sim/input/
```

### Step 3: Run Simulator

```bash
cd ../paging-sim
./run.sh
```

### Step 4: View Results

```bash
# Check output files
ls -lh output/

# View specific result
cat output/small_saida.txt
```

## Validation

The script includes built-in validation:

- **Argument Consistency**: Ensures `min_req ≤ max_req`
- **Positive Values**: Validates that page count is positive
- **Memory Constraints**: V ≥ M is checked by the simulator
- **Page Size**: Calculated as power of 2 (V / P)

## Tips and Best Practices

1. **Start Small**: Begin with small test cases to verify correctness before scaling up
2. **Realistic Ratios**: Keep physical memory smaller than virtual memory (M < V)
3. **Frame Count**: Ensure enough frames for meaningful testing (at least 2-4)
4. **Request Patterns**: Vary min/max request ranges to test different workload patterns
5. **Reproducibility**: Save command-line arguments used for important tests
6. **Batch Generation**: Create multiple test files with different parameters for comprehensive testing

## Troubleshooting

### Error: "min_req > max_req"

```bash
# ❌ Wrong
python3 main.py --min_req 100 --max_req 50

# ✅ Correct
python3 main.py --min_req 50 --max_req 100
```

### Error: "Number of pages must be positive"

```bash
# ❌ Wrong
python3 main.py -p 0

# ✅ Correct
python3 main.py -p 16
```

### Memory Size Issues

```bash
# Ensure virtual memory ≥ physical memory
python3 main.py --physical_mem 8192 --virtual_mem 4096  # ❌ Will cause issues in simulator
python3 main.py --physical_mem 4096 --virtual_mem 8192  # ✅ Correct
```

## Technical Details

- **Language**: Python 3
- **Dependencies**: None (uses standard library: `argparse`, `random`, `sys`)
- **Randomization**: Uses `random.randint()` for page index generation
- **Output**: Writes to stdout (redirect to file using `>`)
- **Page Index Range**: [0, P-1] inclusive

## Related Files

- `usage.md` - Quick usage examples
- `small.txt` - Pre-generated small test case
- `medium.txt` - Pre-generated medium test case
- `large.txt` - Pre-generated large test case

## License

This tool is part of the Virtual Memory Page Replacement Simulator project.

## Support

For issues or questions about test case generation:

1. Verify Python 3 is installed: `python3 --version`
2. Check command-line arguments are within valid ranges
3. Ensure output is properly redirected to a file
4. Validate generated file format matches simulator expectations
