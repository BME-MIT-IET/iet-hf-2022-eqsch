## Részfeladat
Az eredeti projekt nem használt semmilyen build keretrendszert, pedig nagyon indokolt lett volna javaFX miatt. Ezt pótolván Gradle-t választottunk,
mivel ebben volt tapasztalatunk. Az eredeti kód JDK 11-t használt, így a dependenciákat ehhez kellett igazítani, amennyiben ez indokolt volt.
Kitúztük célnak ezen kívül hogy GitHub Action-sel automatikus teszteket futassunk, ha a main ágat PR érint.

## Munka 
A fájlokat át kellett migrálni úgy hogy a keretrendszernek megfelelő megszokásokat kövesse, ezen kívül a JavaFx-ses dependenciákat és különlegességeket kezelni kellett.
Problémát okozott ezen kívül az FXML, CSS és PNG kiterjesztésű fájlok használata, ugyanis az eredeti kódban ezek pontos helyen vannak, és hely alapján van hivatkozva rájuk.
Emiatt plusz includokra volt szükség.

A GitHub Actions beüzemelése, viszonylag sok próbálkozásba tellett, de végül sikerült megoldani. Problémát okozott az hogy egy-két online talált példa kód nem az
elvárásoknak megfelelően működött, vagy nem írt ki elég információt, hogy milyen Gradle task hol jár, mit csinál.

## Eredmények
A Gradle-nek hála a projekt sikeres buildelése és futtatása sokkal, de sokkal könnyebb. A GitHubon csak úgy lehet PR-t mergelni hogy azon lefutott egy sikeres build és az
összes (Régi saját keretrendszer, Unit és BDD) teszt. Emiatt annak az esélye hogy hibás kód kerül be a main ágra, meglehetősen csökkent.

## Tanulságok
Bár kellett már Gradle-t használnom, most megtanultam hogy a beüzemelésébe befektett idő a többszörését tudja megspórolni a builddel, futtatással és teszteléssel kapcsolatba.
Foglalkoztam már GitHub Actions-el, de akkor igazából arra használtam hogy Ubuntun buildeljen és töltse fel az eredményt artifactként. Bár okozott problémákat a workflow
beüzemelése, visszatekintve elég kényelmes volt, és sok problémát az okozott hogy migrálni kellett a projektet. Nagyon szimpatikus az hogy milyen könnyedséggel lehet CI-t és
safety practice-ket bevezetni tesztelő workflow-k segítsével, és utána olvasva tényleg nagyon sok mindenre képes. Különösen sok fejvakarást okozott hogy a kód egyes részein
az elérési útvonal '\' karakterekkel volt elválasztva, amitt a Windows elfogadott, de Ubuntun FileNotFoundException-hoz vezetett, szóval ezt is egy életre megtanultam.
