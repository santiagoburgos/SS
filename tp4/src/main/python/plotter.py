filepath = 'test.txt'
file = open(filepath)
lines = file.readlines()
for line in lines:
   print(line.strip())

file.close()