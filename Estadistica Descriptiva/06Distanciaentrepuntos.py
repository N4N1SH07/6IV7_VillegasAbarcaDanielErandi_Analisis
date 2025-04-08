import numpy as np
import pandas as pd
from scipy.spatial import distance

#Defiinir coordenadas de nuestros sistemas de tiendas
tiendas = {
    'Tiena A': (1,1),
    'Tiena B': (1,5),
    'Tiena C': (7,1),
    'Tiena D': (3,3),
    'Tiena E': (4,8)
}

#converit las coordenadas en un frame para facilitar el calculo
df_tiendas = pd.DataFrame(tiendas).T
df_tiendas.columns = ['X', 'Y']
print('Coordenas de las tiendas: ')
print(df_tiendas)

#Inicializar los data frame de lo que vamos a obtener para el calculo de distancias
distancias_punto1 = pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)
distancias_punto2 = pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)
distancias_punto3 = pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)

#Vamos a calcular las distancias 
for i in df_tiendas.index:
    for j in df_tiendas.index:
        #Defino las distancia euclidiana del primero punto
        distancias_punto1.loc[i,j] = distance.euclidean(df_tiendas.loc[i], df_tiendas.loc[j])
        distancias_punto2.loc[i,j] = distance.cityblock(df_tiendas.loc[i], df_tiendas.loc[j])
        distancias_punto3.loc[i,j] = distance.chebyshev(df_tiendas.loc[i], df_tiendas.loc[j])
        
#Mostrar resultados 
print('/n Distancia Euclidiana entre cada una de las tiendas: ')
print(distancias_punto1)

print('/n Distancia Manhattan entre cada una de las tiendas: ')
print(distancias_punto2)

print('/n Distancia Chebyshev entre cada una de las tiendas: ')
print(distancias_punto3)