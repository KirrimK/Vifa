if [ $# -lt 1 ]; then
echo "Mettez le nom de version en argument ex: './package_platforms.sh 1.1.3'"
exit 1
fi

echo "If you have not uncommented the additionnal depencies in the pom.xml file, the packages produced for other platforms than this machine will not work"

mvn package
mkdir ./target/packages

if ! [[ -d ./target/packages/jdk-17.0.1_windows ]]; then
echo "Downloading Windows JVM archive..."

wget https://download.java.net/java/GA/jdk17.0.1/2a2082e5a09d4267845be086888add4f/12/GPL/openjdk-17.0.1_windows-x64_bin.zip -O ./target/packages/openjdk_windows.zip

echo "Extracting Windows JVM archive..."

unzip ./target/packages/openjdk_windows.zip -d ./target/packages

mv ./target/packages/jdk-17.0.1 ./target/packages/jdk-17.0.1_windows

fi

if ! [[ -d ./target/packages/jdk-17_linux ]]; then

echo "Downloading Linux JVM archive..."

wget https://download.java.net/java/GA/jdk17.0.1/2a2082e5a09d4267845be086888add4f/12/GPL/openjdk-17.0.1_linux-x64_bin.tar.gz -O ./target/packages/openjdk_linux.tar.gz

echo "Extracting Linux JVM archive..."

tar xzf ./target/packages/openjdk_linux.tar.gz -C ./target/packages

mv ./target/packages/jdk-17.0.1 ./target/packages/jdk-17_linux

fi

echo "Copying files..."

cp ./target/Vifa-1.0-SNAPSHOT-shaded.jar ./target/packages/Vifa-SNAPSHOT.jar
cp vifa22.conf ./target/packages/vifa22.conf

cp ./package_helpers/run_vifa_linux.sh ./target/packages/run_vifa_linux.sh
cp ./package_helpers/run_vifa_macos.sh ./target/packages/run_vifa_macos.sh
cp ./package_helpers/run_vifa_windows.bat ./target/packages/run_vifa_windows.bat

cp ./package_helpers/how_to_run_macos.txt ./target/packages/how_to_run_macos.txt


cp ./lib/ivyCommunications ./target/packages/ivyCommunications_mac -r

echo "Building pyinstaller version of ivyCommunications for linux..."

cd ./lib/ivyCommunications

pyinstaller ivyCommunications.py

cp dist/ivyCommunications ../ivyCommunications_linux_pyinstaller -r

rm -r dist
rm -r build
rm ivyCommunications.spec

cd ../..

cp ./lib/ivyCommunications_linux_pyinstaller ./target/packages/ivyCommunications_linux -r

echo "Assuming that a pyinstaller version of ivyCommunications has been build for windows..."

cp ./lib/ivyCommunications_windows_pyinstaller ./target/packages/ivyCommunications_windows -r

cd ./target/packages

echo "Packaging for Windows..."

zip Vifa-MAXI_windows_$1.zip Vifa-SNAPSHOT.jar run_vifa_windows.bat vifa22.conf ivyCommunications_windows jdk-17.0.1_windows -r

echo "Packaging for Linux..."

zip Vifa-MAXI_linux_$1.zip Vifa-SNAPSHOT.jar run_vifa_linux.sh vifa22.conf ivyCommunications_linux jdk-17_linux -r

echo "Packaging for MacOS..."

zip Vifa-Mini_macos_$1.zip Vifa-SNAPSHOT.jar run_vifa_macos.sh vifa22.conf ivyCommunications_mac how_to_run_macos.txt -r
