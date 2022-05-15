set CURRENTPATH=C:\Users\user\IdeaProjects\projlab
set PATH_TO_FX="%CURRENTPATH%\javafx-sdk-11.0.2\lib"
set JAR_NAME="AsteroidGame"
dir /s /b src\*.java > sources.txt & javac --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml -d out @sources.txt &del sources.txt
cd out & jar xf "%PATH_TO_FX%\javafx.base.jar" & jar xf "%PATH_TO_FX%\javafx.graphics.jar" & jar xf "%PATH_TO_FX%\javafx.fxml.jar"  & jar xf "%PATH_TO_FX%\javafx.controls.jar" & cd ..
copy %CURRENTPATH%\src\UI\Components\*.css out\UI\Components\
copy %CURRENTPATH%\src\UI\Layout\Game\*.css out\UI\Layout\Game\
copy %CURRENTPATH%\src\UI\Layout\Game\*.fxml out\UI\Layout\Game\
mkdir out\UI\Layout\Game\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\Resources\*.png out\UI\Layout\Game\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\ActionSidePanel\*.css out\UI\Layout\Game\ActionSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\ActionSidePanel\*.fxml out\UI\Layout\Game\ActionSidePanel\
mkdir out\UI\Layout\Game\ActionSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\ActionSidePanel\Resources\*.png out\UI\Layout\Game\ActionSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\BaseInfoSidePanel\*.css out\UI\Layout\Game\BaseInfoSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\BaseInfoSidePanel\*.fxml out\UI\Layout\Game\BaseInfoSidePanel\
mkdir out\UI\Layout\Game\BaseInfoSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\BaseInfoSidePanel\Resources\*.png out\UI\Layout\Game\BaseInfoSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\CraftSidePanel\*.css out\UI\Layout\Game\CraftSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\CraftSidePanel\*.fxml out\UI\Layout\Game\CraftSidePanel\
mkdir out\UI\Layout\Game\CraftSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\CraftSidePanel\Resources\*.png out\UI\Layout\Game\CraftSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\CurrentAsteroidSidePanel\*.css out\UI\Layout\Game\CurrentAsteroidSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\CurrentAsteroidSidePanel\*.fxml out\UI\Layout\Game\CurrentAsteroidSidePanel\
mkdir out\UI\Layout\Game\CurrentAsteroidSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\CurrentAsteroidSidePanel\Resources\*.png out\UI\Layout\Game\CurrentAsteroidSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\CurrentTeleportSidePanel\*.css out\UI\Layout\Game\CurrentTeleportSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\CurrentTeleportSidePanel\*.fxml out\UI\Layout\Game\CurrentTeleportSidePanel\
mkdir out\UI\Layout\Game\CurrentTeleportSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\CurrentTeleportSidePanel\Resources\*.png out\UI\Layout\Game\CurrentTeleportSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\InventorySidePanel\*.css out\UI\Layout\Game\InventorySidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\InventorySidePanel\*.fxml out\UI\Layout\Game\InventorySidePanel\
mkdir out\UI\Layout\Game\InventorySidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\InventorySidePanel\Resources\*.png out\UI\Layout\Game\InventorySidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\OptionsSidePanel\*.css out\UI\Layout\Game\OptionsSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\OptionsSidePanel\*.fxml out\UI\Layout\Game\OptionsSidePanel\
mkdir out\UI\Layout\Game\OptionsSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\OptionsSidePanel\Resources\*.png out\UI\Layout\Game\OptionsSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\PutBackSidePanel\*.css out\UI\Layout\Game\PutBackSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\PutBackSidePanel\*.fxml out\UI\Layout\Game\PutBackSidePanel\
mkdir out\UI\Layout\Game\PutBackSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\PutBackSidePanel\Resources\*.png out\UI\Layout\Game\PutBackSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\Game\PutDownSidePanel\*.css out\UI\Layout\Game\PutDownSidePanel\
copy %CURRENTPATH%\src\UI\Layout\Game\PutDownSidePanel\*.fxml out\UI\Layout\Game\PutDownSidePanel\
mkdir out\UI\Layout\Game\PutDownSidePanel\Resources
copy %CURRENTPATH%\src\UI\Layout\Game\PutDownSidePanel\Resources\*.png out\UI\Layout\Game\PutDownSidePanel\Resources\
copy %CURRENTPATH%\src\UI\Layout\GameOverMenu\*.css out\UI\Layout\GameOverMenu\
copy %CURRENTPATH%\src\UI\Layout\GameOverMenu\*.fxml out\UI\Layout\GameOverMenu\
mkdir out\UI\Layout\GameOverMenu\Resources
copy %CURRENTPATH%\src\UI\Layout\GameOverMenu\Resources\*.png out\UI\Layout\GameOverMenu\Resources\
copy %CURRENTPATH%\src\UI\Layout\LoadMenu\*.css out\UI\Layout\LoadMenu\
copy %CURRENTPATH%\src\UI\Layout\LoadMenu\*.fxml out\UI\Layout\LoadMenu\
mkdir out\UI\Layout\LoadMenu\Resources
copy %CURRENTPATH%\src\UI\Layout\LoadMenu\Resources\*.png out\UI\Layout\LoadMenu\Resources\
copy %CURRENTPATH%\src\UI\Layout\MainMenu\*.css out\UI\Layout\MainMenu\
copy %CURRENTPATH%\src\UI\Layout\MainMenu\*.fxml out\UI\Layout\MainMenu\
mkdir out\UI\Layout\MainMenu\Resources
copy %CURRENTPATH%\src\UI\Layout\MainMenu\Resources\*.png out\UI\Layout\MainMenu\Resources\
copy %CURRENTPATH%\src\UI\Layout\StartMenu\*.css out\UI\Layout\StartMenu\
copy %CURRENTPATH%\src\UI\Layout\StartMenu\*.fxml out\UI\Layout\StartMenu\
mkdir out\UI\Layout\StartMenu\Resources
copy %CURRENTPATH%\src\UI\Layout\StartMenu\Resources\*.png out\UI\Layout\StartMenu\Resources\
copy %CURRENTPATH%\src\UI\Layout\WonMenu\*.css out\UI\Layout\WonMenu\
copy %CURRENTPATH%\src\UI\Layout\WonMenu\*.fxml out\UI\Layout\WonMenu\
mkdir out\UI\Layout\WonMenu\Resources
copy %CURRENTPATH%\src\UI\Layout\WonMenu\Resources\*.png out\UI\Layout\WonMenu\Resources\
copy "%PATH_TO_FX%\..\bin\*.dll" out & del out\module-info.class
mkdir libs
jar --create --file=%JAR_NAME%.jar --main-class=Launcher -C out .
pause