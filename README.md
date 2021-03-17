# Rijksmuseum Masterpieces

It is a simple app showing the list of masterpieces, which are exhibited in Rijksmuseum sorted by author or principal. The first screen shows a paginable list of paintings, the second - detailed information about the selected painting.  

Depending on the locale, the information from the server is downloaded in Dutch or English (of cause if the server returns correctly localized texts). The change of the device's locale while the app is running is not handled, because no such requirement was made. But this improvement is easy to do.

## Technologies

- Architecture - simplified version of Clean
- Presentation layer architecture - MVVM (based on Android Architecture Components)
- DI - Dagger
- Multithreading - RxJava
- Unit tests - MockK, Kotest

|         |          | 
| ------------- |:-------------:|
| <a href="https://ibb.co/nnbc5tG"><img src="https://i.ibb.co/rcm61Nj/first-page.jpg" alt="first-page" border="0" width="350"></a>     | <a href="https://ibb.co/Pwrz4R4"><img src="https://i.ibb.co/ZWf1K3K/second-page.jpg" alt="second-page" border="0" width="350"></a>| 
