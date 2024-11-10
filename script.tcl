# Create a new simulator object
set ns [new Simulator]

# Open a trace file to log the simulation output
set tracefile [open unicast.tr w]
$ns trace-all $tracefile

# Open a NAM trace file for visualization
set namfile [open unicast.nam w]
$ns namtrace-all $namfile

# Define the 'finish' procedure to close the simulation
proc finish {} {
    global ns tracefile namfile
    $ns flush-trace
    close $tracefile
    close $namfile
    exec nam unicast.nam &
    exit 0
}

# Create nodes for the simulation (6 nodes)
set n0 [$ns node]; # Source node
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node];  # Destination node

# Set up links between nodes
$ns duplex-link $n0 $n1 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail
$ns duplex-link $n2 $n3 1Mb 10ms DropTail

# Define node positions for NAM visualization
$ns duplex-link-op $n0 $n1 orient right
$ns duplex-link-op $n1 $n2 orient right
$ns duplex-link-op $n2 $n3 orient right

# Set up TCP connection between n0 (source) and n3 (destination)
set tcp [new Agent/TCP/Newreno]
$ns attach-agent $n0 $tcp
set sink [new Agent/TCPSink/DelAck]
$ns attach-agent $n3 $sink
$ns connect $tcp $sink

# Set up FTP application over TCP
set ftp [new Application/FTP]
$ftp attach-agent $tcp

# Set simulation time and events
$ns at 0.1 "$ftp start";       # Start FTP transfer at 0.1s
$ns at 4.0 "finish" ;           # End the simulation at 4.0s

# Run the simulation
$ns run
