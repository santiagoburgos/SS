import matplotlib.pyplot as plt
import numpy as np

filepath = 'DynamicO.txt'
file = open(filepath)
step = float(file.readline())
file.close()

time = np.arange(0, 5, step)
aux = 0

filepath = "originalR.txt"
file = open(filepath)
originalElements = []
oEPortion = []
lines = file.readlines()
for line in lines:
   originalElements.append(float(line))
   aux+=1
file.close()

filepath = "verletR.txt"
file = open(filepath)
verletElements = []
lines = file.readlines()
for line in lines:
   verletElements.append(float(line))
file.close()

filepath = "beemannR.txt"
file = open(filepath)
beemanElements = []
lines = file.readlines()
for line in lines:
   beemanElements.append(float(line))
file.close()

filepath = "gpco5R.txt"
file = open(filepath)
gpcoElements = []
lines = file.readlines()
for line in lines:
   gpcoElements.append(float(line))
file.close()


plt.plot(time, originalElements, label='Analítica')
plt.plot(time, verletElements, label='Verlet')
plt.plot(time, beemanElements, label='Beeman')
plt.plot(time, gpcoElements, label='GPCO5')
plt.xlabel('Tiempo (segundos)')
plt.ylabel('Amplitud (metros)')
plt.legend()
plt.show()