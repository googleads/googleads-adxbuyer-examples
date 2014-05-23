DoubleClick Ad Exchange Buyer REST API PHP Samples
=======================================================
These samples demonstrate basic usage of the DoubleClick Ad Exchange Buyer REST
API.

Setup
=======================================================
To run these samples, you'll need to do the following:

1. If you haven't done so already, download and install
  [google-api-php-client](https://github.com/google/google-api-php-client).
2. If you haven't done so already, contact your TAM to set up a
  [Service Account](https://developers.google.com/accounts/docs/OAuth2ServiceAccount)
  to use with the DoubleClick Ad Exchange Buyer REST API.
3. Go to the [Google Developers Console](https://console.developers.google.com/)
  and click the `APIs & auth` and then the `Credentials` tab and find the
  Service Account.
4. Generate a new key for the Service Account and place the .p12 file in your
  project directory.
5. Open **index.php** and update the template include path to point to the
  **src** directory of google-api-php-client.
6. Update the `client_id` field in index.php to match your Service Account's
  Client ID.
7. Update the `service_account_name` field in index.php to match your Service
  Account's Email Address.
8. Update the `key_file_location` field in index.php to match the path to the
  .p12 file in your project directory.

You should now be able to start the sample by running the following command in
the project directory:

```
$ php -S localhost:8080
```

To run the sample, visit localhost:8080 in your browser.

