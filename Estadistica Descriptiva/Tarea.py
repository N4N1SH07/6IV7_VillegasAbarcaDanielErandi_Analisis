import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

archivo_csv = "./EstadisticaDescriptiva/housing.csv"
datos = pd.read_csv(archivo_csv)

print("Primeras 5 filas:")
print(datos.head().to_string(index=False))
print("\nÚltimas 5 filas:")
print(datos.tail().to_string(index=False))

def obtener_estadisticas(serie):
    return pd.DataFrame({
        "Mínimo": [serie.min()],
        "Máximo": [serie.max()],
        "Media": [serie.mean()],
        "Mediana": [serie.median()],
        "Moda": [serie.mode().iloc[0] if not serie.mode().empty else np.nan],
        "Rango": [serie.max() - serie.min()],
        "Varianza": [serie.var()],
        "Desviación Estándar": [serie.std()]
    })

estadisticas = obtener_estadisticas(datos["median_house_value"])
print("\nEstadísticas de 'median_house_value':")
print(estadisticas.to_string(index=False))

intervalos = pd.cut(datos["median_house_value"], bins=10)
tabla_frecuencias = intervalos.value_counts().sort_index().reset_index()
tabla_frecuencias.columns = ["Intervalo", "Frecuencia"]

print("\nTabla de Frecuencias:")
print(tabla_frecuencias.to_string(index=False))

conteo_rangos = datos["median_house_value"].groupby(pd.cut(datos["median_house_value"], bins=10)).count()
plt.figure(figsize=(12, 6))
conteo_rangos.plot(kind="bar", color="skyblue")
plt.xlabel("Rango de precios")
plt.ylabel("Cantidad de casas")
plt.title("Distribución de casas por rango de precios")
plt.xticks(rotation=45)
plt.show()

promedios = datos.groupby(intervalos)["median_house_value"].mean()
plt.figure(figsize=(12, 6))
promedios.plot(kind="bar", color="green")
plt.xlabel("Rango de precios")
plt.ylabel("Costo Promedio")
plt.title("Costo Promedio de median_house_value por Rango de Precio")
plt.xticks(rotation=45)
plt.show()
