# Rota-Kocaeli: Kocaeli Ulaşım Rota Planlama Sistemi

## Açıklama
Kocaeli Ulaşım Rota Planlama Sistemi, kullanıcıların mevcut konumlarından belirli bir hedef noktaya en uygun rotayı belirlemelerini sağlayan bir uygulamadır. Sistem, otobüs ve tramvay hatlarını entegre bir şekilde kullanarak, maliyet, süre ve mesafe gibi faktörlere göre optimize edilmiş güzergahlar sunar. Proje, Nesne Yönelimli Programlama (OOP) ve SOLID prensiplerine uygun olarak geliştirilmiştir.

## Özellikler
- **Nesne Yönelimli Tasarım:** Modüler ve genişletilebilir yapı.
- **Rota Planlama Algoritması:** Kullanıcının başlangıç ve hedef noktaları arasındaki en uygun rotayı belirler.
- **Farklı Yolcu Tipleri için İndirimler:** Öğrenci, öğretmen ve yaşlı bireyler için özel indirimler.
- **Kullanıcı Dostu Arayüz:** Harita üzerinden etkileşimli kullanıcı deneyimi.

## Kurulum
### Gereksinimler
- Java 8 veya üzeri
- Maven

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

## İletişim
Proje ile ilgili sorularınız için [muratemrebicici@gmail.com](mailto:muratemrebicici@gmail.com) veya [celebibehic@gmail.com](mailto:celebibehic@gmail.com) adresleriyle iletişime geçebilirsiniz.

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
