import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import sys

try:
    df = pd.read_excel("proyecto1.xlsx")
except Exception as e:
    print("Error al leer el archivo:", e)
    sys.exit()

#print("Columnas disponibles en el DataFrame:", df.columns)

if 'B_mes' in df.columns:
    df['B_mes'] = pd.to_datetime(df['B_mes'], dayfirst=True, errors='coerce')

ventas_totales = df['ventas_tot'].sum()
print("Total de ventas del comercio:", ventas_totales)

df_con_adeudo = df[df['B_adeudo'].str.strip().str.lower() == "con adeudo"]
df_sin_adeudo = df[df['B_adeudo'].str.strip().str.lower() == "sin adeudo"]

socios_con_deuda = df_con_adeudo['no_clientes'].sum()
socios_sin_deuda = df_sin_adeudo['no_clientes'].sum()
total_socios = df['no_clientes'].sum()

porcentaje_con_deuda = (socios_con_deuda / total_socios) * 100 if total_socios else 0
porcentaje_sin_deuda = (socios_sin_deuda / total_socios) * 100 if total_socios else 0

print(f"Socios con adeudo: {socios_con_deuda} ({porcentaje_con_deuda:.2f}%)")
print(f"Socios sin adeudo: {socios_sin_deuda} ({porcentaje_sin_deuda:.2f}%)")

if 'B_mes' in df.columns and df['B_mes'].notnull().any():
    ventas_por_bmes = df.groupby(df['B_mes'].dt.date)['ventas_tot'].sum()
    plt.figure(figsize=(10, 6))
    ventas_por_bmes.plot(kind='bar', color='royalblue')
    plt.title("Ventas Totales Respecto del Tiempo")
    plt.xlabel("B_mes")
    plt.ylabel("Ventas Totales")
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()
else:
    print("No hay datos de B_mes para graficar.")

if 'B_mes' in df.columns and df['B_mes'].notnull().any():
    std_pagos_por_bmes = df.groupby(df['B_mes'].dt.date)['pagos_tot'].std()
    plt.figure(figsize=(10, 6))
    std_pagos_por_bmes.plot(kind='bar', color='darkorange')
    plt.title("Desviación Estándar de los Pagos Totales por mes")
    plt.xlabel("B_mes")
    plt.ylabel("Desviación Estándar")
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()
else:
    print("No hay datos para graficar.")

deuda_total = df['adeudo_actual'].sum()
print("Deuda total de los clientes:", deuda_total)

if ventas_totales != 0:
    porcentaje_utilidad = ((ventas_totales - deuda_total) / ventas_totales) * 100
else:
    porcentaje_utilidad = 0
print(f"Porcentaje de utilidad del comercio: {porcentaje_utilidad:.2f}%")

ventas_por_sucursal = df.groupby('id_sucursal')['ventas_tot'].sum()
plt.figure(figsize=(8, 8))
ventas_por_sucursal.plot(kind='pie', autopct='%1.1f%%', startangle=90, colors=['gold', 'lightcoral', 'skyblue', 'lightgreen'])
plt.title("Distribución de Ventas entre Sucursales")
plt.ylabel("")
plt.tight_layout()
plt.show()

datos_sucursal = df.groupby('id_sucursal').agg({
    'adeudo_actual': 'sum',
    'ventas_tot': 'sum'
}).reset_index()

def calc_margen_utilidad(row):
    if row['ventas_tot'] != 0:
        return (row['ventas_tot'] - row['adeudo_actual']) / row['ventas_tot'] * 100
    else:
        return 0

datos_sucursal['margen_utilidad'] = datos_sucursal.apply(calc_margen_utilidad, axis=1)

plt.figure(figsize=(10, 6))
plt.scatter(datos_sucursal['adeudo_actual'], datos_sucursal['margen_utilidad'], s=100, alpha=0.7, color='purple')

for i, sucursal in enumerate(datos_sucursal['id_sucursal']):
    x = datos_sucursal['adeudo_actual'].iloc[i]
    y = datos_sucursal['margen_utilidad'].iloc[i]
    plt.annotate(sucursal, (x, y), textcoords="offset points", xytext=(5, 5), ha='center', fontname="Comic Sans MS")

plt.title("Relación entre Deuda y Margen de Utilidad")
plt.xlabel("Deuda Total")
plt.ylabel("Porcentaje de Utilidad")
plt.grid(True)
plt.tight_layout()
plt.show()
