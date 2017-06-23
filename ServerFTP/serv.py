##Eduardo Villegas Edvill26@gmail.com


import socket
import sys
import os
import commands
import time
import subprocess


def recvAll(sock, numBytes):
	# The buffer
	recvBuff = ""
	# The temporary buffer
	tmpBuff = ""
	# Keep rec
	while len(recvBuff) < numBytes:
		# Attempt to receive bytes
		tmpBuff =  sock.recv(numBytes)
		# The other side has closed the socket
		if not tmpBuff:
			break
		# Add the received bytes to the buffer
		recvBuff += tmpBuff
	return recvBuff


def sndAll(sock, sting_sent):
	# Send the data!
	numSent = 0

	while len(sting_sent) > numSent:
		numSent += sock.send(sting_sent[numSent:])

	return

def handle_command(sk , cmd , client_addr):
	client_addr = client_addr[0]
	if cmd == "quit":
		# send server connection to quit
		print("Quiting")
		try:
			sndAll(sk, "SUCCESS")
			print("Succes")
		except:
			print("Fail")
	elif cmd[0:3] == "get":
		try:
			
			split_cmd = cmd.split()
			fileName = split_cmd[1]
			time.sleep(3)

			try:
				wlcmeSockt = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
			except:
				print("Unable to get socket")
				sys.exit(1)

			print( "\n" )
			
			wlcmeSockt.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
			try:
				wlcmeSockt.connect((client_addr, int(split_cmd[2]) ))
			except:
				wlcmeSockt.close()
				print("Failed to connect")
				sys.exit(1)

			print("Connect")

			# The number of bytes sent
			numSent = 0
			if os.path.isfile(fileName):
				file = open(fileName,'rb')
				if 1 == 1:
					dataSizeStr = str(os.fstat(file.fileno()).st_size)
					while len(dataSizeStr) < 10:
						dataSizeStr = "0" + dataSizeStr
					# Send the data!
					while len(dataSizeStr) > numSent:
						numSent += sk.send(dataSizeStr[numSent:])

					print("Sending Data File")
					for line in file:
						# Send the data!
						numSent = 0
						while len(line) > numSent:
							numSent += wlcmeSockt.send(line[numSent:])
					file.close()
					print("Success")
			else:
				sndAll(sk, "0000000000")
				print("File does not exist")
				print("Fail")
			wlcmeSockt.close()
		except:
			print("Fail")

		print("\n \nWaiting")
	elif cmd[0:3] == "put":
		try:
			split_cmd = cmd.split()
			#<put><filename><port><upload_fileSize>

			if split_cmd[3] != "0000000000":
				fileName = split_cmd[1]
				time.sleep(3)

				try:
					put_Sockt = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
				except:
					print("Put Fail")
					sys.exit(1)

				try:
					
					put_Sockt.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
					put_Sockt.connect((client_addr, int(split_cmd[2]) ))
				except:
					put_Sockt.close()
					print("Connecting Failed!")
					sys.exit(1)

				print("Connected...")

				fileName = "upload_" + fileName
				#fileName = "uploaded.txt"
				file = open(fileName,'wb')
				if 1 == 1:
					# The temporary buffer
					tmpBuff = ""
					recvBuff = ""
					numBytes = int(split_cmd[3])
					# Keep receiving till all is received
					print("Receiving...")

					while len(recvBuff) < numBytes:
						# Attempt to receive bytes
						tmpBuff =  put_Sockt.recv(numBytes)
						# The other side has closed the socket
						if not tmpBuff:
							break
						file.write(tmpBuff)
						recvBuff += tmpBuff
					print("Uploaded")

					
					sndAll(sk, "SUCCESS")

				put_Sockt.close()
				print("Success")
		except:
			
			print("Fail")
		print("\n \nWaiting")
	elif cmd[0:2] == "ls":
		try:
			
			split_cmd = cmd.split()
			time.sleep(3)
			try:
				wlcmeSockt = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
			except:
				print("ls Socket Failed!")
				sys.exit(1)

			print( "\n" )

			try:
				
				wlcmeSockt.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
				wlcmeSockt.connect((client_addr, int(split_cmd[1]) ))
			except:
				wlcmeSockt.close()
				print("Connecting Failed!")
				sys.exit(1)

			print("Connect")

			# The number of bytes sent
			numSent = 0

			proc=subprocess.Popen('ls', shell=True, stdout=subprocess.PIPE, )
			direct_output=proc.communicate()[0]

			dataSizeStr = str(len(direct_output))
			while len(dataSizeStr) < 10:
				dataSizeStr = "0" + dataSizeStr

			# The number of bytes sent
			sndAll(sk, dataSizeStr)

			if len(direct_output) > 0:
				print("Sending \'ls\' output.")
				# Send the data!
				sndAll(wlcmeSockt, direct_output)
			else:
				dataSizeStr = "0000000000"
				# Send the data!
				sndAll(sk, dataSizeStr)
			wlcmeSockt.close()
		except:
			print("FAIL")

		
		print("Success")
		print("\n \nWaiting")

	else:
			try:
				sk.send("FAI")
			except:
				pass
			print("Please enter <comand> <file name if needed>")




#first function called
def main():
	# The port on which to listen specified
	if len(sys.argv) < 2:
		print("ERROR - Usage: python serv.py <PORT NUMBER> ")
		exit(1)

	port_arg = int(sys.argv[1])
	listenPort = port_arg


	try:
		# Create a welcome socket.
		welcomeSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	except socket.error:
		welcomeSock = None
		sys.exit(1)
	print("Waiting for connection")
	try:
		# Bind the socket to the port
		welcomeSock.bind(('', listenPort))
		welcomeSock.listen(2)
	except socket.error:
		welcomeSock.close()
		sys.exit(1)

	# while True:
	clientSock, addr = welcomeSock.accept()
	connected = True
	while connected :
		# The size of the incoming file
		fileSize = 0

		# Buffer containing file size gets the "string #" from the header
		fileSizeBuff = ""

		# Receive the first 10 bytes indicating size of the file/command
		fileSizeBuff = recvAll(clientSock, 10)

		# Get the file size by converting
		try:
			fileSize = int(fileSizeBuff)
			
			fileData = recvAll(clientSock, fileSize)
			handle_command(clientSock , fileData , addr)

		except: 
			connected = False
			clientSock.close()
			print("A Client Closed..")
	


#Start of the entire program
if __name__ == "__main__":
	main()
