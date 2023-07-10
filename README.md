<!DOCTYPE html>
<html>
<head>
  <style>
    h1, h2, h3, h4, h5, h6 {
      background-color: #f2f2f2;
      padding: 10px;
    }
  </style>
</head>
<body>
  <h1>CryptoCurrencyApp</h1>

  <h2>Version: 1.0.0</h2>

  <h3>Introduction:</h3>

  <p>CryptoCurrencyApp is an Android application that provides users with up-to-date cryptocurrency prices using the CoinGecko API. The app allows users to track specific coins and set minimum and maximum price thresholds for notifications. The application leverages the WorkManager Jetpack library to send remote requests every 15 minutes to ensure users receive timely price updates. Additionally, the app implements a cache system to overcome API limitations and internet connectivity issues. The tracked coin prices are stored in a Room database, and users can visualize the price fluctuations over time using a Line-Chart view.</p>

  <h3>Features:</h3>

  <h4>1. Crypto Price Listing:</h4>
  <p>The app provides real-time cryptocurrency prices for various coins. Users can view prices in multiple currencies such as USD, BTC, XRP, and ETH. The information is sourced from the CoinGecko API, ensuring accurate and up-to-date data.</p>

  <h4>2. Price Tracking and Notifications:</h4>
  <p>Users have the ability to track specific coins and set minimum and maximum price thresholds. Whenever the price of a tracked coin falls below the minimum or exceeds the maximum threshold, users receive Push Notifications to stay informed about price changes.</p>

  <h4>3. WorkManager Integration:</h4>
  <p>To ensure users receive regular price updates, the application utilizes the WorkManager Jetpack library. Remote requests to the CoinGecko API are sent automatically every 15 minutes, allowing users to have the latest price information without manual intervention.</p>

  <h4>4. Cache System:</h4>
  <p>The app implements a cache system to address limitations of the CoinGecko API, as well as to handle potential internet connectivity issues. In the event of API request limits or internet problems, the cache system enables users to continue using the app by providing previously fetched data until the connection is restored.</p>

  <h4>5. Price History and Visualization:</h4>
  <p>The tracked coin prices are stored in a Room database, allowing users to access historical price data. Users can view the price fluctuations over time through a Line-Chart view, providing insights into the coin's performance and trends.</p>
