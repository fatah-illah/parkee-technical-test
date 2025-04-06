# Analisis Manajemen Memori pada PairSum.java

## Pertanyaan 1: Bagaimana memori untuk list dialokasikan dan dikelola dalam metode createList?

Berdasarkan kode yang diberikan dalam PairSum.java, tidak ada metode createList yang eksplisit disebutkan. Namun, jika kita mengacu pada penggunaan HashMap dalam metode countPairs:

- Saat `new HashMap<>()` dipanggil, Java mengalokasikan memori dari heap untuk objek HashMap
- Setiap kali `freqMap.put()` dipanggil, memori tambahan dialokasikan untuk menyimpan key-value pair
- HashMap secara otomatis mengelola internal array dan collision handling ketika ada pertambahan elemen

## Pertanyaan 2: Apa yang akan terjadi pada memori yang dialokasikan untuk list setelah metode createList selesai dieksekusi?

Karena tidak ada metode createList dalam kode yang diberikan, saya akan menjawab berdasarkan metode countPairs:

- Setelah metode countPairs selesai dieksekusi, variabel lokal freqMap menjadi tidak dapat diakses (out of scope)
- Garbage Collector (GC) Java akan mendeteksi bahwa objek HashMap yang direferensikan oleh freqMap tidak lagi dapat dijangkau
- Memori yang dialokasikan untuk HashMap akan ditandai untuk garbage collection dan nantinya akan dibebaskan saat GC berjalan

## Pertanyaan 3: Apakah ada potensi kebocoran memori dalam kode di atas? Jelaskan jawaban Anda.

Dalam kode PairSum.java yang diberikan, tidak terdapat potensi kebocoran memori yang signifikan karena:

1. Java memiliki Garbage Collector yang secara otomatis mengelola memori
2. Tidak ada referensi siklik yang kompleks dalam kode ini
3. HashMap yang digunakan memiliki scope lokal dalam metode dan akan dihapus oleh GC setelah metode selesai
4. Tidak ada static reference atau singleton pattern yang menyimpan referensi objek dalam jangka waktu lama
5. Tidak ada resource eksternal (seperti file, database connection, atau network socket) yang perlu ditutup secara eksplisit

Java secara otomatis akan membersihkan memori yang tidak terpakai melalui proses garbage collection, sehingga dalam kasus ini, semua memori yang digunakan oleh HashMap akan dibebaskan setelah metode countPairs selesai dan tidak ada referensi lain yang menahannya.