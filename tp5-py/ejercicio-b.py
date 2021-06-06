import matplotlib.pyplot as plt
import math

with open('outA1.csv', 'r') as csv_file:
    lines = csv_file.readlines()

with open('outA2.csv', 'r') as csv_file:
   lines2 = csv_file.readlines()

with open('outA3.csv', 'r') as csv_file:
   lines3 = csv_file.readlines()

with open('outA4.csv', 'r') as csv_file:
   lines4 = csv_file.readlines()

with open('outA5.csv', 'r') as csv_file:
   lines5 = csv_file.readlines()

people = []
people2 = []
people3 = []
people4 = []
people5 = []
time = []

for line in lines:
    data = line.split(',')
    people.append(float(data[1]))
    time.append(float(data[0]))

for line in lines2:
    data = line.split(',')
    people2.append(float(data[1]))

for line in lines3:
    data = line.split(',')
    people3.append(float(data[1]))

for line in lines4:
    data = line.split(',')
    people4.append(float(data[1]))

for line in lines5:
    data = line.split(',')
    people5.append(float(data[1]))

mean = []
desv = []
for x in range(0, len(people)):
   mean.append((people[x] + people2[x] + people3[x] + people4[x] + people5[x]) / 5)
   aux = pow(people[x], 2) + pow(people2[x], 2) + pow(people3[x], 2) + pow(people4[x], 2) + pow(people5[x], 2)
   desv.append(math.sqrt((aux - (5 * pow(mean[x], 2))) / 5))

plt.rcParams.update({'font.size': 14})
plt.errorbar(time, mean, xerr=desv)
plt.xlabel('Tiempo (segundos)')
plt.ylabel('Egresos')
plt.show()