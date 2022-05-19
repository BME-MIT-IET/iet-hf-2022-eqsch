# JUnit tesztek és kódlefedettség    

## JUnit tesztek
- A kiválasztott projekt már eredetileg is rendelekezett számos saját implementálású teszttel, így azokat is fel tudtuk használni a JUnit tesztelés során.
- Ebből kiindulva először azokat ellenőriztük, hogy helyesen lefutnak-e, illetve, hogy a várt eredményeket kapjuk-e.
- Miután ez megtörtént, az IntelliJ IDEA beépített code-coverage mutatújával ellenőriztük, hogy a kódot milyen mértékben sikerült lefedni.
- A lefedettség alapján tudtunk tovább menni, hogy milyen teszteket kell még írni. Ezeket megírtuk, amíg el nem értünk egy mindenki számára elfogadható szintet a tesztlefedettségben.

## Kódlefedettség
- Az IntelliJ IDEA beépített code-coverage mutatója alapján hamar egyértelműsödött, hogy a javaFX-szel megvalósított UI-t nehéz lesz tesztelni JUnit tesztekkel, így erre jobb megoldásnak tartottuk, hogy ezeket a részeket manuális, exploratory tesztekkel ellenőrizzük.
- Ez a megoldás végül meg is hozta a gyümölcsét, ugynais pár hibát a manuális teszt hozott csak napvilágra.

## Tanulságok
- Beláttuk, hogy egy fajta tesztelési módszerrel szinte lehetetlen teljesen lefedni egy programot, több fajta módszer kombinálásával viszont sokkal effektívebben lehet tesztelni.
- Bár a feladattól kicsit független tanulság, de gyakran egy adott feladat megoldására az eszköz rég a kezünkben van. Erre példa az IntelliJ IDEA code-coverage mutatója, ami helyett először elkezdtünk keresni egy külön erre specifikált szoftvert, de végül ez is teljesen ellátta a feladatot.

## Egyéb
- Bár tanulságnak nem nevezném, szerintem rendkívül hasznos volt, hogy a gyakorlatban is megtapasztalhattuk a tesztelés fontosságát, azt, hogy mennyi hiba is lehet egy programban, annak ellenére, hogy az elsőre nem látszik vagy rövid távú használatban nem jön elő. 
