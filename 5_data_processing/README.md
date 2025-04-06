# Proyek Pemrosesan Data Transaksi Toko

Proyek Python ini dibuat untuk memproses dan menganalisis data transaksi dari beberapa cabang toko. Script ini menggabungkan beberapa file CSV, membersihkan data, dan menghitung total penjualan per cabang.

## Deskripsi

Script akan melakukan beberapa tugas pemrosesan data berikut:
1. Menggabungkan data dari tiga file CSV berbeda (`branch_a.csv`, `branch_b.csv`, dan `branch_c.csv`)
2. Membersihkan data dengan:
   - Menghapus baris yang memiliki nilai NaN pada kolom tertentu
   - Mengubah format tanggal menjadi tipe datetime
   - Menghilangkan data duplikat berdasarkan transaction_id
3. Menghitung total penjualan per cabang
4. Menyimpan hasil perhitungan ke file output `total_sales_per_branch.csv`

## Persyaratan

- Python 3.x
- pandas

## Instalasi

```bash
# Buat virtual environment (opsional tetapi direkomendasikan)
python -m venv .venv

# Aktifkan virtual environment
# Di Windows:
.venv\Scripts\activate
# Di macOS/Linux:
source .venv/bin/activate

# Instal dependensi
pip install pandas
```

## Struktur File

```
5_data_processing/
│
├── data_processing.py      # Script utama untuk pemrosesan data
├── branch_a.csv            # Data transaksi cabang A
├── branch_b.csv            # Data transaksi cabang B
├── branch_c.csv            # Data transaksi cabang C
└── total_sales_per_branch.csv  # File output (dibuat setelah menjalankan script)
```

## Penggunaan

1. Pastikan semua file CSV input (`branch_a.csv`, `branch_b.csv`, dan `branch_c.csv`) berada dalam direktori yang sama dengan script.
2. Jalankan script dengan perintah:

```bash
python data_processing.py
```

3. Script akan menampilkan informasi tentang proses yang sedang berjalan dan menghasilkan file `total_sales_per_branch.csv` yang berisi total penjualan untuk setiap cabang.

## Format Input

Setiap file CSV input harus memiliki kolom berikut:
- transaction_id
- branch
- date
- product_id
- quantity
- price
- customer_id

## Format Output

File output `total_sales_per_branch.csv` berisi dua kolom:
- branch: Identifikasi cabang (A, B, C, dll.)
- total: Total penjualan untuk cabang tersebut

## Contoh Output

```
branch,total
A,265.0
B,380.0
C,480.0
```

## Catatan

- Script secara otomatis menghapus baris yang memiliki nilai null pada kolom transaction_id, date, dan customer_id.
- Jika ada transaction_id yang duplikat, script akan mempertahankan transaksi dengan tanggal terbaru.
- Total penjualan dihitung dengan mengalikan quantity dan price untuk setiap transaksi.