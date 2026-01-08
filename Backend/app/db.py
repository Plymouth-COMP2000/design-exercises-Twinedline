import pyodbc

server = 'dist-6-505.uopnet.plymouth.ac.uk'
database = 'COMP2001_KCunningham'
username = 'KCunningham'
password = 'FuwG463'
driver = '{ODBC Driver 17 for SQL Server}'

conn_str = (
    f'DRIVER={driver};'
    f'SERVER={server};'
    f'DATABASE={database};'
    f'UID={username};'
    f'PWD={password};'
    'Encrypt=yes;'
    'TrustServerCertificate=yes;'
    'Connection Timeout=30;'
    'Trusted_Connection=no'
)

# This is where your code was failing before
conn = pyodbc.connect(conn_str, autocommit=True)
cursor = conn.cursor()