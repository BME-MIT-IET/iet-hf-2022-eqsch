# Cucumber

## Részfeladat
A Cucumbert azért választottuk, mivel már Rendszermodellezés tárgyból volt tapasztalatunk vele, bár nem indult zökkenőmentesen a tesztek írása így se. A fő probléma a gradle file-ba illesztett két sor körül voltak, mivel nehéz volt azokat összeszinkronizálni a JDK-val. 

## Munka 
Miután sikerült a set-up, ezen a feladaton ketten kezdtünk el dolgozni. Egyikünk felelt a tesztek kitalálásért, illetve a feature file-ok megírásáért, másikunk meg a stepdefinition-ök implementálásáért. Összesen 34 tesztesetünk lett. A felosztás a kezdő pár teszt után felborult és mindketten foglalkoztunk az implementálással, mivel sok probléma felmerült, főleg amiatt, mert nem teljesen ismertük a kódbázist, csak a funkciót. 

## Eredmények
A legtöbb tesztek által kihozott hiba is pontosan ebből adódott, de ezeket gyorsan ki tudtuk szűrni, és ki tudtuk javítani. Tényleges hibát csak 1-2-t találtunk, melyek mind értékadás elmaradása miatt voltak (és emiatt nullpointerexceptiont kaptunk).

## Tanulságok
Bármennyire is ismerős volt számunkra a feladat, nagyon fontos a kódbázis jó ismerete, mivel nagyon sok időt megspórolhattunk volna magunknak, hogy ha teljesen tisztában vagyunk a programmal. Emellett a Cucumber használata nem volt rossz tapasztalat, és jó volt látni élőben a működését, hogy hogyan tud valami "megrendelő"/"laikus" közeli teszt lenni