import matplotlib.pyplot as plt
import numpy as np

time = np.arange(0, 5, 1)
aux = 0

filepath = "originalR.txt"
file = open(filepath)
originalElements = []
oEPortion = []
lines = file.readlines()
for line in lines:
   if aux % 1000 == 0:
      originalElements.append(float(line))
   aux+=1
file.close()


plt.plot(time, originalElements, 'ro')
plt.show()