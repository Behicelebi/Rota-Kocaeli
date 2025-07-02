# Rota-Kocaeli: Kocaeli Ulaşım Rota Planlama Sistemi

## Açıklama
Kocaeli Ulaşım Rota Planlama Sistemi, kullanıcıların mevcut konumlarından belirli bir hedef noktaya en uygun rotayı belirlemelerini sağlayan bir uygulamadır. Sistem, otobüs ve tramvay hatlarını entegre bir şekilde kullanarak, maliyet, süre ve mesafe gibi faktörlere göre optimize edilmiş güzergahlar sunar. Proje, Nesne Yönelimli Programlama (OOP) ve SOLID prensiplerine uygun olarak geliştirilmiştir.

## 📌 Özellikler
- **Nesne Yönelimli Tasarım:** Modüler ve genişletilebilir yapı.
- **Rota Planlama Algoritması:** Kullanıcının başlangıç ve hedef noktaları arasındaki en uygun rotayı belirler.
- **Farklı Yolcu Tipleri için İndirimler:** Öğrenci, öğretmen ve yaşlı bireyler için özel indirimler.
- **Kullanıcı Dostu Arayüz:** Harita üzerinden etkileşimli kullanıcı deneyimi.

## Kurulum
### Gereksinimler
- Java 8 veya üzeri
- Maven
<h2>🌟 Temel Sınıflar</h2>
<ul><li><p class="ds-markdown-paragraph"><strong><code>com.project.transportation</code></strong>: Araç yönetimi (Taksi, Scooter)</p></li><li><p class="ds-markdown-paragraph"><strong><code>com.project.passenger</code></strong>: Yolcu tipleri ve indirim yönetimi</p></li><li><p class="ds-markdown-paragraph"><strong><code>com.project.payment</code></strong>: Ödeme yöntemleri</p></li><li><p class="ds-markdown-paragraph"><strong><code>com.project.routing</code></strong>: Rota hesaplama algoritmaları</p></li><li><p class="ds-markdown-paragraph"><strong><code>com.project.util</code></strong>: Yardımcı fonksiyonlar ve sınır yönetimi</p></li></ul>

<h2>✨ Temel Özellikler</h2>h2>
<ul><li><p class="ds-markdown-paragraph"><strong>Çok Kriterli Rota Optimizasyonu</strong> (En ucuz, en hızlı, en kısa)</p></li><li><p class="ds-markdown-paragraph"><strong>Entegre Ulaşım Ağı</strong> (Otobüs, tramvay, taksi, scooter)</p></li><li><p class="ds-markdown-paragraph"><strong>Akıllı Aktarma Yönetimi</strong></p></li><li><p class="ds-markdown-paragraph"><strong>Kişiselleştirilmiş İndirimler</strong> (Öğrenci, öğretmen, 65+)</p></li><li><p class="ds-markdown-paragraph"><strong>Çoklu Ödeme Seçenekleri</strong> (Nakit, kredi kartı, Kentkart)</p></li><li><p class="ds-markdown-paragraph"><strong>Mesafe Tabanlı Araç Yönlendirme</strong> (3km eşik değeri)</p></li><li><p class="ds-markdown-paragraph"><strong>Kullanıcı Dostu Arayüz</strong> (Harita entegrasyonlu)</p></li></ul>
### Bağımlılıklar
Gradle:
```groovy
dependencies {
    implementation 'com.google.code.gson:gson:2.13.1'
}
```

Maven:
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.13.1</version>
</dependency>
```

## Kullanım
1. Projeyi indirin ve gerekli bağımlılıkları yükleyin.
2. `veriseti.json` dosyasını yükleyin.
3. Kullanıcı arayüzünü başlatın ve başlangıç ve varış noktalarını seçin.
4. "Rota Hesapla" butonuna tıklayarak en uygun rotayı görüntüleyin.

## Katkıda Bulunma
Katkılarda bulunmak için lütfen mevcut GitHub sorunlarını kontrol edin veya yeni bir sorun açarak önerinizi paylaşın. Proje, bakım modundadır; bu nedenle büyük yeni özellikler eklenmeden önce tartışma yapılması önerilir.

## Lisans
Apache-2.0 lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasını inceleyin.

## ✉️ İletişim
Murat Emre Biçici	muratemrebicici@gmail.com
Behiç Çelebi		celebibehic@gmail.com
Katkıda bulunmak için lütfen pull request gönderiniz.
## Kaynaklar
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [GSON](https://github.com/google/gson)
- [ClassGraph](https://github.com/classgraph/classgraph)


## Screenshots
<table>
 <tr>
  <td>demo1</td>
  <td>demo2</td>
  <td>demo3</td>
  <td>demo4</td>
 </tr>
 <tr>
  <td><img src="https://raw.githubusercontent.com/Behicelebi/Rota-Kocaeli/refs/heads/master/screenshots/c1.jpg"></td>
     </tr>
   <tr>
  <td><img src="https://raw.githubusercontent.com/Behicelebi/Rota-Kocaeli/refs/heads/master/screenshots/c2.jpg"></td>
     </tr>
     <tr>
  <td><img src="https://raw.githubusercontent.com/Behicelebi/Rota-Kocaeli/refs/heads/master/screenshots/c3.jpg"></td>
     </tr>
     <tr>
  <td><img src="https://raw.githubusercontent.com/Behicelebi/Rota-Kocaeli/refs/heads/master/screenshots/c4.png"></td>
     </tr>
</table>
---

Bu proje, ulaşım sistemlerinin nasıl daha verimli ve kullanıcı dostu hale getirilebileceğini göstermektedir. Gelecekte, farklı şehirlerde ve daha büyük ölçekli ulaşım ağlarında kullanılabilir.
