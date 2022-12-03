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
    	<th><img src="screenshots/splash.png"/></th>
    	<th><img src="screenshots/main_0.png"/></th>
    	<th><img src="screenshots/permission.png"/></th>
	</tr>
	<tr>
    	<th><img src="screenshots/main_1.png"/></th>
    	<th><img src="screenshots/main_2.png"/></th>
    	<th><img src="screenshots/detail.png"/></th>
	</tr>
	<tr>
    	<th><img src="screenshots/manage.png"/></th>
    	<th><img src="screenshots/manage_4.png"/></th>
    	<th><img src="screenshots/manage_5.png"/></th>
	</tr>
	<tr>
    	<th><img src="screenshots/manage_2.png"/></th>
    	<th><img src="screenshots/manage_3.png"/></th>
    	<th><img src="screenshots/setting_1.png"/></th>
	</tr>
	<tr>
    	<th><img src="screenshots/setting_2.png"/></th>
	</tr>
</table>

## Features
- Current weather.
- Background color change by weather or time.
- Forecast weather.
- Management cities.
- Auto update weather per 30 minutes.

## To-do
- There are around 20% code coverage, so we need write more test in future.
- Update animation when add and delete city in management screen.
- ...
