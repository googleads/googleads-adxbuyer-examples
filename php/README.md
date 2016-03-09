DoubleClick Ad Exchange Buyer REST API PHP Samples
=======================================================
These samples demonstrate basic usage of the DoubleClick Ad Exchange Buyer REST
API.

Setup
=======================================================
**Note: These instructions changed significantly in February 2016**, if you used the examples prior to this date you will need to run through them again!

To run these samples, you'll need to do the following:

1. If you haven't done so already, download and install
  [google-api-php-client](https://github.com/google/google-api-php-client).
  * **You can no longer clone the github repo as a means of using this library**
  
2. Setup a service account:

  * **If you do not have a service account:**
  * launch the Google Developers Console <https://console.developers.google.com>
  * select a project
  * open the menu (icon in the upper-left corner of page)
  * click **API Manager**
  * click the **Credentials** tab
  * click **Create credentials**
  * select **Service account key** from the drop-down menu
  * under **Service account** select the **New service account** option
  * under **Key type** select the **JSON** option for use with these samples
  * click the **Create** button
  * The downloaded JSON file is used in Step 5

  * **If you do have a service account but have a p12 file:**
  * launch the Google Developers Console <https://console.developers.google.com>
  * select the project containing your service account
  * open the menu (icon in the upper-left corner of page)
  * click **API Manager**
  * click the **Credentials** tab
  * click **Manage Service Accounts**
  * open the menu on the right of your service account (three dots)
  * select create key
  * ensure the key type is JSON and click the **Create** button
  * The downloaded JSON file is used in Step 5

3. Allow your service account to access your data:

 * go to the [DoubleClick Ad Exchange UI](https://www.adx.google.com)
 * click the gear icon in the upper-right corner of the page
 * click **Account Settings** from the drop-down menu.
 * on the next page, click the **Account users** tab under **User management**
 * click the **+Service Account** button
 * enter the service account email in the dialog to associate it with your
   DoubleClick Ad Exchange Account

5. Open **index.php** and update the template include path per the instructions at [google-api-php-client](https://github.com/google/google-api-php-client).
  * If you are using composer:
```
require_once '/path/to/your-project/vendor/autoload.php';
```

  * If you are using the zip:
```
require_once '/path/to/google-api-php-client/vendor/autoload.php';
```

6. Update the `key_file_location` field in index.php to match the path to the
  .json file from step 2

You should now be able to start the sample by running the following command in
the project directory:

```
$ php -S localhost:8080
```

To run the sample, visit localhost:8080 in your browser.

