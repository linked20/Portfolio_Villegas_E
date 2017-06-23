#Eduardo Villegas Edvill26@gmail.com


import socket
import os
import sys
import time
import commands


def recvAll(sock, numBytes):
	# The buffer
	recvBuff = ""
	
	# The temporary buffer
	tmpBuff = ""
	
	# Keep receiving till all is received
	while len(recvBuff) < numBytes:
		# Attempt to receive bytes
		tmpBuff =  sock.recv(numBytes)

		# The other side has closed the socket
		if not tmpBuff:
			break
		
		# Add the received bytes to the buffer
		recvBuff += tmpBuff

	return recvBuff

#gets the command from the terminal
def get_command():
	print("\n")
	cmd = raw_input("ftp>")
	return cmd


def sndAll(sock, sting_sent):
	# Send the data!
	numSent = 0
	
	while len(sting_sent) > numSent:
		numSent += sock.send(sting_sent[numSent:])

	return
	

# Read cmd 
def handle_command(sk , cmd,serverAddr):
	# The number of bytes sent
	numSent = 0
	# The file data
	fileData = None

	# command to quit
	if cmd == "quit":
		fileData = cmd
	
		# Get the size of the data read n convert it to string
		dataSizeStr = str(len(fileData))
		argmnts = ""

		# Prepend 0's to the size string until size is 10 bytes
		while len(dataSizeStr) < 10:
			dataSizeStr = "0" + dataSizeStr 
		
		# Prepend the size of the data to the command. <bytes><quit>
		fileData = dataSizeStr + fileData + argmnts

		sndAll(sk, fileData)
		
		response = recvAll(sk,7)
		
		if response == "SUCCESS":
			print("Now closing client")
		else:
			print("Error Occurred")
		
		sk.close()	
		sys.exit(1)
		
	elif cmd[0:3] == "get":
		argmnts = ""

		# Create a socket
		try:
			dataSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		except:
			print("Get Socket Failed!\n")
			sys.exit(1)
			

		dataSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		# Bind the socket to port 0
		try:
			dataSocket.bind(('',0))
			dataSocket.listen(1)
		except socket.error:
			dataSocket.close()
			print("Error bind or listen\n")
			sys.exit(1)
		
		fileName = cmd[4:]

		cmd = cmd + " " + str(dataSocket.getsockname()[1])
		
		# Get the size of the data read and convert it to string
		dataSizeStr = str(len(cmd))
		
		# Prepend 0's to the size string until the size is 10 bytes
		while len(dataSizeStr) < 10:
			dataSizeStr = "0" + dataSizeStr 
		
		message = dataSizeStr + cmd + argmnts

		# The number of bytes sent
		numSent = 0
		sndAll(sk, message)
		
		print("Waiting for Data Connection")
		dataSocket_transfer,clnt_addr_data_connection = dataSocket.accept()
		
		print("Waiting for server response")
		
		response = recvAll(sk, 10)
		
		if response == "0000000000":
			print("No such file exists. \n")
		else:
			#new_file = "file_downloaded.txt"
			new_file = "dwnld_" + fileName
			file = open(new_file, 'wb')
			#with open(new_file, 'wb') as file:
			# The buffer
			recvBuff = ""
				
			# The temporary buffer
			tmpBuff = ""
			numBytes = int(response)
			print("Receiving...")
			# Keep receiving till all is received
			while len(recvBuff) < numBytes:
				# Attempt to receive bytes
				tmpBuff =  dataSocket_transfer.recv(numBytes)
								
				# The other side has closed the socket
				if not tmpBuff:
					break
					
				file.write(tmpBuff)
				# Add the received bytes to the buffer
				recvBuff += tmpBuff			
			file.close()	
				
			print( fileName  + str(numBytes)+ " bytes")

		dataSocket_transfer.close()

	elif cmd[0:3] == "put":
		argmnts = ""
		# Create a socket
		try:
			put_dataSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		except:
			print("Put Socket Failed! \n")
			sys.exit(1)

		try:
			put_dataSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
			put_dataSocket.bind(('',0))
			put_dataSocket.listen(1)
		except:
			put_dataSocket.close()
			print("Bind or Listen Failed!\n")
			sys.exit(1)
		
		cmd = cmd + " " + str(put_dataSocket.getsockname()[1])
	
		split_cmd = cmd.split()
		fileName = split_cmd[1]
		
		if os.path.isfile(fileName):
			file = open(fileName, 'r')
			
			upload_fileSize = str(os.fstat(file.fileno()).st_size)
			file.close()

			cmd = cmd + " " + upload_fileSize
			

			# Get the size of the data read and convert it to string
			dataSizeStr = str(len(cmd))
			
			# Prepend 0's to the size string until the size is 10 bytes
			while len(dataSizeStr) < 10:
				dataSizeStr = "0" + dataSizeStr 

			# Prepend the size of the data to the command. <bytes><put><filename><port>
			message = dataSizeStr + cmd + argmnts 

			# The number of bytes sent
			numSent = 0
			sndAll(sk, message)


			dataSocket_transfer,clnt_addr_data_connection = put_dataSocket.accept()
			print("upload")
					
			new_file = fileName
			file = open(new_file, 'rb')
					
			for line in file:
				dataSocket_transfer.send(line)
				# Send 				
				numSent = 0
			print("File uploaded")
			response = recvAll(sk, 7)
			file.close()
			dataSocket_transfer.close()
			print("Put " + str(new_file)  +str(upload_fileSize)) + " bytes"
		else:
			cmd = cmd + " " + "0000000000"

			# Get the size of the data read and convert it to string
			dataSizeStr = str(len(cmd))

			# Prepend 0's to the size string until the size is 10 bytes
			while len(dataSizeStr) < 10:
				dataSizeStr = "0" + dataSizeStr 

			# Prepend the size of the data to the command. <bytes><put><filename><port>
			message = dataSizeStr + cmd + argmnts # + " " + upload_fileSize
			
			# Send
			sndAll(sk, message)
			
			print("No such file exists. \n")			
		put_dataSocket.close()

	elif cmd == "ls":
		# Run ls command, get output, and print it
		argmnts = ""
		# Create a socket
		try:
			dataSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		except:
			print("ls fail")
			sys.exit(1)
		
		try:
			
			dataSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
			# Bind the socket to port 0
			dataSocket.bind(('',0))
			dataSocket.listen(1)
		except:
			dataSocket.close()
			print("Bind or Listen Failed\n")
			sys.exit(1)
		
		cmd = cmd + " " + str(dataSocket.getsockname()[1])
		
		# Get the size of the data read and convert it to string
		dataSizeStr = str(len(cmd))
		
		# Prepend 0's to the size string until the size is 10 bytes
		while len(dataSizeStr) < 10:
			dataSizeStr = "0" + dataSizeStr 
		
		# Prepend the size of the data to the command. <bytes><get><filename><port>
		message = dataSizeStr + cmd + argmnts
		
		# The number of bytes sent
		numSent = 0
		sndAll(sk, message)
		
		dataSocket_transfer,clnt_addr_data_connection = dataSocket.accept()
		
		
		response = recvAll(sk, 10)

		if response == "0000000000":
			print("No files exist \n")
		else:
			# The buffer
			recvBuff = ""
			# The temporary buffer
			tmpBuff = ""
			numBytes = int(response)
			print("Receiving  ls  output from server(" + str(numBytes) + " bytes): \n")
			# Keep receiving till all is received
			while len(recvBuff) < numBytes:
				# Attempt to receive bytes
				tmpBuff =  dataSocket_transfer.recv(numBytes)
				# The other side has closed the socket
				if not tmpBuff:
					break
				print(tmpBuff)
				# Add the received bytes to the buffer
				recvBuff += tmpBuff
			print("Done.")

		dataSocket_transfer.close()

	elif cmd == "lls":		
		os.system('ls')
		#os.system('dir c:\\')

	else:
		print("Please type <command> <file name if neccessary>")
		
#first function called		
def main():	
	# Command line checks
	if len(sys.argv) < 3:
		print("ERROR - Usage: python client.py <ServerMachine> <PORT NUMBER> ")
		sys.exit(1)

	# Server address
	serverAddr = sys.argv[1]
	# Server port
	serverPort = int(sys.argv[2])
	

	try:
		connSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	except socket.error:
		connSock = None
		sys.exit(1)
	
	# Connect to the server with command connection
	try:
		connSock.connect((serverAddr, serverPort))
	except socket.error:
		connSock.close()
		sys.exit(1)


	while True:	
		command = get_command()
		handle_command(connSock,command, serverAddr)	

#Start of the entire program
if __name__ == "__main__":
	main()


