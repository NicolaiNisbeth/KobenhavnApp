# About
The app is acting as a client in a distributed system, fetching data from an API and respond accordingly to the 8 fallacies of distributed system.
The app should not be evaluated based on its appearance but rather it's architecture, dataflow and error handling.

Cool features:
- Offline app, allowing to continue one's interaction with the app despite poor internet connection when logged in
- Screen rotation is possible due to the reactive nature of how the data is handled.

# Technologies
This project was developed with the following technologies:
- Retrofit
- RxJava
- Priority Job Queue
- Dagger
- Butterknife
- Room

# Usage
Due to the nature of the app acting as a client, it is dependent upon the server, initially verifying the user credentials at the login screen.
And because the server has been taken down, the way of using the app is by running the server locally and log in on the app using username: root and password: root

The app can be accessed via appetizer: https://appetize.io/app/5ju9u0r276mrxgn8djv9ych684

Or runned locally.
