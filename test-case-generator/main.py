import argparse
import random
import sys

def generate_request_sequence(num_requests, max_page):
    """Generates a list of random page indices."""
    sequence = [random.randint(0, max_page - 1) for _ in range(num_requests)]
    return " ".join(map(str, sequence))

def generate_simulator_input(physical_mem, virtual_mem, architecture, num_pages, num_sequences,
                           max_req_per_seq, min_req_per_seq):
    """Generates the complete input content according to specifications."""
    
    output = f"{physical_mem}\n"
    output += f"{virtual_mem}\n"
    output += f"{architecture}\n"
    output += f"{num_pages}\n"
    
    output += "\n"
    
    output += f"{num_sequences}\n"

    output += "\n"
    
    for i in range(num_sequences):
        num_requests = random.randint(min_req_per_seq, max_req_per_seq)
        
        sequence = generate_request_sequence(num_requests, num_pages)
        
        if i > 0:
            output += "\n"
            
        output += f"{num_requests}\n"
        output += f"{sequence}\n"
        
    return output

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description="Script to generate input files for the Virtual Memory simulator.",
        formatter_class=argparse.RawTextHelpFormatter
    )

    parser.add_argument(
        '-p', '--num_pages', type=int, default=16,
        help="Total number of virtual pages (P). Indices range from 0 to P-1. (Default: 16)"
    )
    parser.add_argument(
        '-s', '--num_sequences', type=int, default=3,
        help="Number of request sequences to generate. (Default: 3)"
    )
    parser.add_argument(
        '--max_req', type=int, default=100,
        help="MAXIMUM number of requests per sequence. (Default: 100)"
    )
    parser.add_argument(
        '--min_req', type=int, default=10,
        help="MINIMUM number of requests per sequence. (Default: 10)"
    )
    
    parser.add_argument(
        '--physical_mem', type=int, default=4096,
        help="Physical Memory size (M) in bytes. (Default: 4096)"
    )
    parser.add_argument(
        '--virtual_mem', type=int, default=16384,
        help="Virtual Memory size (V) in bytes. (Default: 16384)"
    )
    parser.add_argument(
        '--architecture', type=str, default='x86', choices=['x86', 'x64', '286', '264'],
        help="Addressing architecture. (Default: x86)"
    )

    args = parser.parse_args()
    
    if args.min_req > args.max_req:
        print("Error: The minimum number of requests (--min_req) cannot be greater than the maximum (--max_req).", file=sys.stderr)
        sys.exit(1)
        
    if args.num_pages <= 0:
        print("Error: The number of pages (--num_pages) must be positive.", file=sys.stderr)
        sys.exit(1)

    input_content = generate_simulator_input(
        physical_mem=args.physical_mem,
        virtual_mem=args.virtual_mem,
        architecture=args.architecture,
        num_pages=args.num_pages,
        num_sequences=args.num_sequences,
        max_req_per_seq=args.max_req,
        min_req_per_seq=args.min_req
    )

    print(input_content, end='')
