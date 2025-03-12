import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# Cargar el archivo CSV
df = pd.read_csv("./EstadisticaDescriptiva/housing.csv")

# Mostrar primeras y últimas filas
print("Primeras 5 filas del dataset:\n", df.head().to_string(index=False))
print("\nÚltimas 5 filas del dataset:\n", df.tail().to_string(index=False))

# Estadísticas Descriptivas
def calcular_estadisticas(data):
    estadisticas = {
        "Mínimo": data.min(),
        "Máximo": data.max(),
        "Media": data.mean(),
        "Mediana": data.median(),
        "Moda": data.mode().iat[0] if not data.mode().empty else np.nan,
        "Rango": data.max() - data.min(),
        "Varianza": data.var(),
        "Desviación Estándar": data.std()
    }
    return pd.DataFrame(estadisticas, index=[0])

# Aplicar estadísticas a median_house_value
estadisticas_casas = calcular_estadisticas(df["median_house_value"])
print("\nEstadísticas de 'median_house_value':\n", estadisticas_casas.to_string(index=False))

# Tabla de Frecuencias
df["price_range"] = pd.cut(df["median_house_value"], bins=10)
tabla_frecuencias = df["price_range"].value_counts().sort_index().reset_index()
tabla_frecuencias.columns = ["Intervalo", "Frecuencia"]

print("\nTabla de Frecuencias:\n", tabla_frecuencias.to_string(index=False))

# Menú de opciones
print("\nSelecciona el gráfico que deseas ver:")
print("1. Gráfica de barras: Popularidad de median_house_value")
print("2. Gráfica de barras: median_house_value vs su costo promedio")

eleccion = int(input("\nElige la opción que quieras ver: "))

if eleccion == 1:
    print("Gráfica de barras de Popularidad de median_house_value")
    
    # Pedir al usuario un rango para el gráfico
    inicio = int(input("\nIntroduce el límite inferior del rango de precios: "))
    fin = int(input("Introduce el límite superior del rango de precios: "))

    # Filtrar los datos dentro del rango seleccionado
    df_filtrado = df[(df["median_house_value"] >= inicio) & (df["median_house_value"] <= fin)]

    # Contar cuántas casas hay en cada rango de precios
    df_grouped = df_filtrado["price_range"].value_counts().sort_index()

    # --- Gráfico de Barras ---
    plt.figure(figsize=(12, 6))
    df_grouped.plot(kind="bar", color="skyblue")

    plt.xlabel("Rango de precios de las casas")
    plt.ylabel("Popularidad")
    plt.title("Distribución de casas por rango de precios")

    plt.xticks(rotation=45, fontsize=10)
    plt.show()

elif eleccion == 2:
    print("Gráfica de barras: median_house_value vs su costo promedio")

    # Agrupar por price_range y calcular el promedio de median_house_value
    df_avg_prices = df.groupby("price_range")["median_house_value"].mean()

    # --- Gráfico de Barras ---
    plt.figure(figsize=(12, 6))
    df_avg_prices.plot(kind="bar", color="green")

    plt.xlabel("Rango de precios de las casas")
    plt.ylabel("Costo Promedio de median_house_value")
    plt.title("Costo Promedio de median_house_value por Rango de Precio")

    plt.xticks(rotation=45, fontsize=10)
    plt.show()

else:
    print("Elección no válida")