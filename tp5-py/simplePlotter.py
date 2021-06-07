import matplotlib.pyplot as plt
import numpy as np

with open('outA1.csv', 'r') as csv_file:
    lines = csv_file.readlines()

people = []
time= []

for line in lines:
    data = line.split(',')
    people.append(float(data[1]))
    time.append(float(data[0]))

plt.plot(time, people, '-.')
plt.show()