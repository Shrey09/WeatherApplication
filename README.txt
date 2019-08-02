Created by - Shrey Amin (B00822245,shrey.amin@dal.ca)
Android Studio API level-23
Internet connection is required to make call to the API
Volley is used to make HTTP GET request to the API.
There is no additional requirement for installing the application.

This android application is used to get the current weather information of a city.
User has to enter city name and the application will fetch weather data using openWeatherMap API.

The project has 2 main files: Layout file-activity_main.xml and MainActivity.java.

activity_main.xml- This is the layout resource file that creates user interface for the application
MainActivity.java- This file has the main logic of the application. There is function getWeather() that takes the city name entered by the                         user,makes call to the API and displays weather data obtianed by parsing the JSON object.



