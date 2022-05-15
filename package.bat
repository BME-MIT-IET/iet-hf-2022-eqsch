set CURRENTPATH=C:\Users\user\Documents\ford\projlab
set PATH_TO_FX="C:\Users\user\Documents\ford\projlab\javafx-sdk-11.0.2\lib"
set JAR_NAME="AsteroidGame"
cd out & jar xf "%PATH_TO_FX%\javafx.base.jar" & jar xf "%PATH_TO_FX%\javafx.graphics.jar" & jar xf "%PATH_TO_FX%\javafx.fxml.jar"  & jar xf "%PATH_TO_FX%\javafx.controls.jar" & cd ..
copy "%CURRENTPATH%\UI\Components\CustomButtons.css" out
copy "%CURRENTPATH%\UI\Components\CustomButtons.css" out
copy "%PATH_TO_FX%\..\bin\*.dll" out & del out\module-info.class
mkdir libs
jar --create --file=%JAR_NAME%.jar --main-class=Launcher -C out .
Sleep 150