import matplotlib.pyplot as plt

# Create corresponding txt files
t = [0.001, 0.0001, 0.00001, 0.000001]

filepath = "verletECMs.txt"
file = open(filepath)
verletECMs = []
lines = file.readlines()
for line in lines:
   verletECMs.append(float(line))
file.close()

filepath = "beemanECMs.txt"
file = open(filepath)
beemanECMs = []
lines = file.readlines()
for line in lines:
   beemanECMs.append(float(line))
file.close()

filepath = "gpcoECMs.txt"
file = open(filepath)
gpcoECMs = []
lines = file.readlines()
for line in lines:
   gpcoECMs.append(float(line))
file.close()


plt.loglog(t, verletECMs, 'ro', label='Verlet')
plt.loglog(t, beemanECMs, 'go', label='Beeman')
plt.loglog(t, gpcoECMs, 'bo', label='GPCO5')
plt.grid()
plt.xlabel('Paso del Tiempo (segundos)')
plt.ylabel('Error Cuatrático Medio (m^2)')
plt.legend()

plt.show()

