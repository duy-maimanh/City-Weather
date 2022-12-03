City Weather Android App
================================
This project is a sample project. That provide forecast weather feature for user. Apply Clean Architecture. Single source of truth implementation.
The weather API get from [here](https://openweathermap.org/)

## Tech stack
* [Room](https://developer.android.com/jetpack/androidx/releases/room) and [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) use to save weather cache, city and other settings.
* [Navigation](https://developer.android.com/guide/navigation) use to navigate between fragment, show dialog and send value between two fragment.
* Retrofit + Moshi use to communicate with web service and convert Json to Object.
* [Dependency Injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android) apply dependency injection easier, one of important library need for Clean architecture.
* Rx Android.
* Coroutines.
* Glide use to load image from internet.
* Jacoco use for report code coverage.

## Project structure
![](screenshots/single_source.png)

All data from internet will be saved to local database and view will observer from there.

* Domain layer: all use cases, entities, value objects.
* Data layer: share preferences, database, remote API etc.
* Presentation layer: UI.

## Video	

## Images
<table>
	<tr>
    	<th>![Splash](screenshots/splash.png)</th>
    	<th>![Dialog ask for permission](screenshots/main_0.png)</th>
    	<th>![Request permission dialog](screenshots/permission.png)</th>
	</tr>
	<tr>
    	<th>![Main screen](screenshots/main_1.png)</th>
    	<th>![Main screen scroll down](screenshots/main_2.png)</th>
    	<th>![Detail weather screen](screenshots/detail.png)</th>
	</tr>
<tr>
    	<th>![Management screen](screenshots/manage.png)</th>
    	<th>![Management screen 2](screenshots/manage_4.png)</th>
    	<th>![Edit city screen](screenshots/manage_5.png)</th>
	</tr>
<tr>
    	<th>![Add city screen](screenshots/manage_2.png)</th>
    	<th>![Search city screen](screenshots/manage_3.png)</th>
    	<th>![Setting city screen](screenshots/setting_1.png)</th>
	</tr>
	<tr>
    	<th>![Setting city ask screen](screenshots/setting_2.png)</th>
	</tr>
</table>

## Features:
- Current weather.
- Forecast weather.
- Management cities.
- Auto update weather per 30 minutes.