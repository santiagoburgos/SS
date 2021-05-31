import matplotlib.pyplot as plt
import numpy as np

filepath = 'DynamicO.txt'
file = open(filepath)
step = float(file.readline())
file.close()

time = np.arange(0, 5, step)
aux = 0
auxTime = []

filepath = "originalR.txt"
file = open(filepath)
originalElements = []
lines = file.readlines()
for line in lines:
   if aux >= 100 and aux < 110:
      originalElements.append(float(line))
      auxTime.append(time[aux])
   #originalElements.append(float(line))
   aux+=1
file.close()

aux = 0

filepath = "verletR.txt"
file = open(filepath)
verletElements = []
lines = file.readlines()
for line in lines:
   if aux >= 100 and aux < 110:
      verletElements.append(float(line))
   #verletElements.append(float(line))
   aux+=1
file.close()

aux = 0

filepath = "beemannR.txt"
file = open(filepath)
beemanElements = []
lines = file.readlines()
for line in lines:
   if aux >= 100 and aux < 110:
      beemanElements.append(float(line))
   #beemanElements.append(float(line))
   aux+=1
file.close()

aux = 0

filepath = "gpco5R.txt"
file = open(filepath)
gpcoElements = []
lines = file.readlines()
for line in lines:
   if aux >= 100 and aux < 110:
      gpcoElements.append(float(line))
   #gpcoElements.append(float(line))
   aux+=1
file.close()


plt.plot(auxTime, originalElements, label='Analítica')
plt.plot(auxTime, verletElements, label='Verlet')
plt.plot(auxTime, beemanElements, label='Beeman')
plt.plot(auxTime, gpcoElements, label='GPCO5')
plt.xlabel('Tiempo (segundos)')
plt.ylabel('Amplitud (metros)')
plt.legend()
plt.show()