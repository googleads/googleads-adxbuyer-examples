#DoubleClick Ad Exchange Buyer REST API Java Samples
These samples demonstrate basic usage of the DoubleClick Ad Exchange Buyer
REST API.

The DoubleClick Ad Exchange Buyer API Java Client Library makes it easier to
write Java clients to programmatically access Ad Exchange Buyer accounts.
The complete documentation for the DoubleClick Ad Exchange Buyer API is
available from <https://developers.google.com/ad-exchange/buyer-rest/>.

##Prerequisites
- [`Java 6+`](http://java.com)
- [`Maven`](http://maven.apache.org)

##Announcements and updates

For API and client library updates and news, please follow our Google+ Ads
Developers page: <https://plus.google.com/+GoogleAdsDevelopers/posts>
and our Google Ads Developers blog: <http://googleadsdeveloper.blogspot.com/>.

For questions and support look at our forum page: <https://groups.google.com/forum/#!forum/google-doubleclick-ad-exchange-buyer-api>.


## Running the examples

###Download the repository contents

To download the contents of the repository, you can use the command

```
git clone https://github.com/googleads/googleads-adxbuyer-examples
```

or browse to <https://github.com/googleads/googleads-adxbuyer-examples> and
 download a zip.

###Authorization Setup
The API uses OAuth2 for security, you can read about the options for connecting
 at <https://developers.google.com/accounts/docs/OAuth2>

We will focus on using a Service Account; samples are included for prompting
 the user and using a refresh token

If you don't already have a Service Account and corresponding .p12 key file:

 * launch the Google Developers Console <https://console.developers.google.com>
 * select a project
 * open the menu (icon in the upper-left corner of page)
 * click **API Manager**
 * click the **Credentials** tab
 * click **Create credentials**
 * select **Service account key** from the drop-down menu
 * under **Service account** select the **New service account** option
 * under **Key type** select the **P12** option for use with these samples
 * click the **Create** button
 * this will create a new account and download a new keyfile.p12 file;
    keep this file safe!
 * click **Manage service accounts** to view more information about the service
    account
 * copy the email address from the console for the next steps.
 * go to the [DoubleClick Ad Exchange UI](https://www.adx.google.com)
 * click the gear icon in the upper-right corner of the page
 * click **Account Settings** from the drop-down menu.
 * on the next page, click the **Account users** tab under **User management**
 * click the **+Service Account** button
 * enter the service account email in the dialog to associate it with your
   DoubleClick Ad Exchange Account.
 * Open the AdExchangeBuyerSample.java file and fill in the following fields
   * `SERVICE_ACCOUNT_EMAIL`
   * `INSERT_PATH_TO_P12_FILE`
   * `APPLICATION_NAME`

##Setup the environment##
### Via the command line ###

1. Execute the following command:

    ```Batchfile
    $ mvn compile
    ```

### Via Eclipse ###

1. Setup Eclipse preferences:
    1. Window > Preferences .. (or on Mac, Eclipse > Preferences)
    2. Select Maven
    3. Select "Download Artifact Sources"
    4. Select "Download Artifact JavaDoc"
2. Import the sample project
    1. File > Import...
    2. Select General > Existing Project into Workspace and click "Next"
    3. Click "Browse" next to "Select root directory", find the sample directory
    and click "Next"
    4. Click "Finish"

## Running the Examples

Once you've checked out the code:

1. Run AdExchangeBuyerSample.java
    1. Via the command line, execute the following command:

        ```Batchfile
        $ mvn -q exec:java
        ```
    2. Via eclipse, right-click on the project and select Run As > Java
    Application
