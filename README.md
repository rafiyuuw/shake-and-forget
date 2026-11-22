# SHAKE & FORGET 🎲

> *"Stop overthinking. Let it shake."*

**SHAKE & FORGET** adalah aplikasi Android interaktif yang dirancang untuk membantu pengguna mengambil keputusan sehari-hari secara menyenangkan dan spontan. Cukup tentukan daftar pilihan, kocok smartphone Anda, dan biarkan aplikasi yang memutuskan!

---

## 📱 Fitur-Fitur Utama

- 🏠 **Dashboard Utama (Home):** Tampilan ceria yang menyediakan entri cepat untuk mengambil keputusan, kategori pilihan, dan riwayat penggunaan terakhir.
- 📳 **Shake to Decide:** Fitur unggulan mengocok HP secara fisik untuk memilih satu keputusan secara acak dengan animasi dan konfeti perayaan.
- 📐 **Gyroscope Option Highlight:** Memiringkan HP ke kiri atau kanan untuk menyoroti (*highlight*) kartu pilihan secara interaktif.
- ⚡ **Quick Pick:** Memilih keputusan acak secara cepat berdasarkan kategori yang tersedia (*Food & Dining*, *Activities*, *Quick 50/50*, dll.).
- 📋 **Custom Decision Lists (My Lists):** Membuat dan mengelola daftar keputusan kustom sendiri lengkap dengan validasi minimal 2 opsi pilihan.
- 📜 **Decision History:** Merekam riwayat keputusan yang telah dibuat beserta tanggal dan waktu.
- ⚙️ **Settings & Preferences:** Mengatur tingkat sensitivitas guncangan (*Low, Medium, High*), serta sakelar aktivasi guncangan, efek suara, dan getaran haptic.

---

## 🛠️ Teknologi dan Arsitektur

Aplikasi ini dibangun murni menggunakan **100% Native Android (Kotlin)** dengan arsitektur modern:

- **Bahasa Pemrograman:** Kotlin
- **UI Framework:** Jetpack Compose & Material Design 3
- **Arsitektur:** MVVM (Model-View-ViewModel)
- **Database Lokal:** Room Database (SQLite)
- **Penyimpanan Preferensi:** DataStore Preferences
- **Asynchronous & Reactive State:** Kotlin Coroutines & StateFlow
- **Navigasi:** Navigation Compose
- **Sensor Hardware:** Android `SensorManager`

---

## 🛰️ Sensor yang Digunakan & Fungsinya

1. **Akselerometer (`Sensor.TYPE_ACCELEROMETER`)**
   - **Fungsi:** Mendeteksi besaran percepatan guncangan fisik HP (*acceleration magnitude* / `gForce`).
   - **Penerapan:** Mengaktifkan pengocokan opsi keputusan secara acak saat guncangan mencapai ambang batas sensitivitas dengan mekanisme *cooldown* untuk mencegah pemicuan ganda.

2. **Giroskop (`Sensor.TYPE_GYROSCOPE`)**
   - **Fungsi:** Mendeteksi kecepatan rotasi dan kemiringan sudut HP (*pitch* & *roll*).
   - **Penerapan:** Diperhalus menggunakan *low-pass filter* untuk menyoroti (*highlight*) kartu pilihan secara aktif saat HP dimiringkan.

---

## 🖼️ Tampilan Aplikasi (Screenshots)

*(Bagian screenshot akan ditambahkan kemudian)*

---

## 🚀 Cara Menjalankan Aplikasi

### Persyaratan Sistem
- Android Studio Ladybug (2024.2.1) atau versi lebih baru
- JDK 17
- Android Device atau Emulator dengan Minimum SDK 26 (Android 8.0 Oreo)

### Langkah-Langkah

1. **Clone Repositori:**
   ```bash
   git clone https://github.com/rafiyuuw/shake-and-forget.git
   ```

2. **Buka Project:**
   - Buka Android Studio.
   - Pilih `Open` lalu arahkan ke folder `shake-and-forget`.

3. **Sync Gradle:**
   - Tunggu hingga proses **Gradle Sync** selesai secara otomatis.

4. **Jalankan Aplikasi:**
   - Hubungkan perangkat HP Android fisik atau jalankan Emulator.
   - Klik tombol **Run 'app'** (`Shift + F10`) di Android Studio.
