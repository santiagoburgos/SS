import numpy as np
import matplotlib.pyplot as plt

# Data for plotting
t = np.arange(0.01, 20.0, 0.01)

plt.loglog(t, 20 * np.exp(-t / 10.0))
plt.grid()

plt.show()