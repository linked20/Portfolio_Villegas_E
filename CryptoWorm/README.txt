Implemented 3 different types of worms
Worm 1: The Replicator: A basic worm which scans its local network to detect systems
running SSH service, attempts to break into one of those systems using a dictionary attack
(i.e. password guessing attack), copies itself onto the compromised system, executes itself
on the compromised system, and repeats the same process from the compromised system.
This particular worm carries no malicious payload.
2. Worm 2: The Extorter: This worm is similar to the replicator worm, but in addition
to spreading it also downloads an encryption program and uses it to encrypt user’s files in
the Documents directory and leaves an extortion message on the user’s desktop.
3. Worm 3: The Password File Thief: This worm is similar to the replicator, except
from every infected system it copies the /etc/passwd file to the attacker’s server (that is,
the VM from which the attack was originally initiated)