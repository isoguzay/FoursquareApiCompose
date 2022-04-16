# Adyen Android Assignment

This repository contains the coding challenge for candidates applying for a Senior Android role at Adyen.
It consists of two unrelated parts:

## 1. Cash Register
Your first task is to implement a cash register. See the `cashregister` module.

Criteria:
- The `CashRegister` gets initialized with some `Change`.
- When performing a transaction, it either returns a `Change` object or fails with a `TransactionException`.
- The `CashRegister` keeps track of the `Change` that's in it.

Bonus points:
- The cash register returns the minimal amount of change (i.e. the minimal amount of coins / bills).

## 2. App
Your second task is to implement a small app using the Foursquare Places API. See the `app` module.

The app should show a list of venues around the user’s current location.
Decide yourself which venue details should be relevant to the user. You have full freedom on how to present data on screen.
We've already added some code to make it a bit easier for you, but you are free to change any part of it.
We are going to check your implementation for understanding Android specifics (like handling configuration changes), UX choices, and overall architecture.
You are free to add any feature or code you want, but we also value quality over quantity, so whatever you decide to do, try to show us your best.

### Setup
Add your Foursquare client ID and secret to `local.gradle`. See `local.gradle.example` for details.
Tip: You can verify your credentials with `src/test/java/com/adyen/android/assignment/PlacesUnitTest.kt`# FoursquareApiCompose
# FoursquareApiCompose
