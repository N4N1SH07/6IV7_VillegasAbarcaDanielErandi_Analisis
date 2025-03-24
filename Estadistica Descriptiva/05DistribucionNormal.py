import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

np.random.seed(0)


media = 0
sigma1 = 1
sigma2 = 2
sigma3 = 3
n_muestras = 1000


data1 = np.random.normal(media, sigma1, n_muestras)
data2 = np.random.normal(media, sigma2, n_muestras)
data3 = np.random.normal(media, sigma3, n_muestras)


plt.figure(figsize=(10, 6))  


plt.hist(data1, bins=30, color='blue', density=True, label='Desviacion Estandar : 1', alpha=0.5)
plt.hist(data2, bins=30, color='red', density=True, label='Desviacion Estandar : 2', alpha=0.5)
plt.hist(data3, bins=30, color='green', density=True, label='Desviacion Estandar : 3', alpha=0.5)




plt.title('Comparacion de distribuciones estandar con una semilla random')
plt.xlabel('Valor')
plt.ylabel('Densidad')
plt.axhline(0, color='black', linewidth=0.5, ls='--')
plt.legend()
plt.show()
