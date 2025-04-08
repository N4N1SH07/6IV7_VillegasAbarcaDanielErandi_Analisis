import numpy as np
import pandas as pd
from scipy.spatial import distance

# Definir las coordenadas de los puntos
puntos = {
    'Punto A': (2, 3),
    'Punto B': (5, 4),
    'Punto C': (1, 1),
    'Punto D': (6, 7),
    'Punto E': (3, 5),
    'Punto F': (8, 2),
    'Punto G': (4, 6),
    'Punto H': (2, 1)
}

# Convertir a DataFrame
df_puntos = pd.DataFrame(puntos).T
df_puntos.columns = ['X', 'Y']
print('Coordenadas de los puntos:')
print(df_puntos)

# Inicializar DataFrames para resultados
distancias_euclid = pd.DataFrame(index=df_puntos.index, columns=df_puntos.index)
distancias_manhattan = pd.DataFrame(index=df_puntos.index, columns=df_puntos.index)
distancias_chebyshev = pd.DataFrame(index=df_puntos.index, columns=df_puntos.index)

# Calcular todas las distancias
for i in df_puntos.index:
    for j in df_puntos.index:
        distancias_euclid.loc[i,j] = distance.euclidean(df_puntos.loc[i], df_puntos.loc[j])
        distancias_manhattan.loc[i,j] = distance.cityblock(df_puntos.loc[i], df_puntos.loc[j])
        distancias_chebyshev.loc[i,j] = distance.chebyshev(df_puntos.loc[i], df_puntos.loc[j])

# Función para encontrar los pares más cercanos y lejanos
def analizar_distancias(df_distancias, nombre_metrica):
    # Excluir la diagonal (distancia consigo mismo)
    mask = ~np.eye(df_distancias.shape[0], dtype=bool)
    valores = df_distancias.where(mask)
    
    min_val = valores.min().min()
    max_val = valores.max().max()
    
    min_pares = [(i,j) for i in df_distancias.index for j in df_distancias.columns 
                if abs(df_distancias.loc[i,j] - min_val) < 1e-6 and i != j]
    
    max_pares = [(i,j) for i in df_distancias.index for j in df_distancias.columns 
                if abs(df_distancias.loc[i,j] - max_val) < 1e-6 and i != j]
    
    print(f"\nAnálisis de distancias {nombre_metrica}:")
    print(f"Distancia mínima: {min_val:.4f} entre los pares: {min_pares}")
    print(f"Distancia máxima: {max_val:.4f} entre los pares: {max_pares}")

# Mostrar resultados
print("\nDistancias Euclidianas:")
print(distancias_euclid)
analizar_distancias(distancias_euclid, "Euclidianas")

print("\nDistancias Manhattan:")
print(distancias_manhattan)
analizar_distancias(distancias_manhattan, "Manhattan")

print("\nDistancias Chebyshev:")
print(distancias_chebyshev)
analizar_distancias(distancias_chebyshev, "Chebyshev")