import pandas as pd
import matplotlib.pyplot as plt


df=pd.read_csv('./EstadisticaDescriptiva/housing.csv')

plt.style.use("bmh")
plt.scatter(df["median_house_value"][:10], df["population"][:10])


#Hay que definir a x y y
plt.xlabel('Precio')
plt.ylabel('Popularidad')

plt.title('Grafico de dispercion de Proximidad al oceano vs Precio')
plt.show()
