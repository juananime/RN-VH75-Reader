
# react-native-vh75-reader

## Getting started

`$ npm install react-native-vh75-reader --save`

### Mostly automatic installation

`$ react-native link react-native-vh75-reader`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-vh75-reader` and add `FabacusVh75Reader.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libFabacusVh75Reader.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.FabacusVh75ReaderPackage;` to the imports at the top of the file
  - Add `new FabacusVh75ReaderPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-vh75-reader'
  	project(':react-native-vh75-reader').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-vh75-reader/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-vh75-reader')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `FabacusVh75Reader.sln` in `node_modules/react-native-vh75-reader/windows/FabacusVh75Reader.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Cl.Json.FabacusVh75Reader;` to the usings at the top of the file
  - Add `new FabacusVh75ReaderPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import FabacusVh75Reader from 'react-native-vh75-reader';

// TODO: What do with the module?
FabacusVh75Reader;
```
  