import pandas as pd

# Daftar file yang akan digabungkan
files = ['branch_a.csv', 'branch_b.csv', 'branch_c.csv']

# Membaca dan menggabungkan semua file CSV
dataframes = []
for file in files:
    df = pd.read_csv(file)
    dataframes.append(df)

# Menggabungkan semua DataFrame
combined_df = pd.concat(dataframes, ignore_index=True)

print("Data setelah digabungkan:")
print(combined_df.head())
print(f"Total baris: {len(combined_df)}")
print("\n")

# 1. Hapus baris yang memiliki nilai NaN pada kolom transaction_id, date, dan customer_id
cleaned_df = combined_df.dropna(subset=['transaction_id', 'date', 'customer_id']).copy()

print("Data setelah membersihkan nilai NaN:")
print(cleaned_df.head())
print(f"Total baris setelah pembersihan: {len(cleaned_df)}")
print("\n")

# 2. Ubah format kolom date menjadi tipe datetime
cleaned_df['date'] = pd.to_datetime(cleaned_df['date'])

print("Data setelah mengubah format date:")
print(cleaned_df.dtypes)
print("\n")

# 3. Hilangkan duplikat berdasarkan transaction_id, pilih data berdasarkan date terbaru
# Urutkan berdasarkan date (descending) untuk memastikan kita mengambil yang terbaru
cleaned_df = cleaned_df.sort_values('date', ascending=False)
cleaned_df = cleaned_df.drop_duplicates(subset=['transaction_id'], keep='first')
# Urut kembali berdasarkan transaction_id untuk mempermudah pembacaan
cleaned_df = cleaned_df.sort_values('transaction_id')

print("Data setelah menghilangkan duplikat (diurutkan berdasarkan transaction_id):")
print(cleaned_df)
print(f"Total baris setelah menghilangkan duplikat: {len(cleaned_df)}")
print("\n")

# 4. Hitung total penjualan per cabang
# Total penjualan dihitung dari price * quantity
cleaned_df['total_per_transaction'] = cleaned_df['price'] * cleaned_df['quantity']

# Groupby branch dan sum total_per_transaction
total_sales_per_branch = cleaned_df.groupby('branch')['total_per_transaction'].sum().reset_index()
total_sales_per_branch.columns = ['branch', 'total']

print("Total penjualan per cabang:")
print(total_sales_per_branch)

# Simpan hasil ke CSV
total_sales_per_branch.to_csv('total_sales_per_branch.csv', index=False)
print("\nHasil disimpan ke total_sales_per_branch.csv")
