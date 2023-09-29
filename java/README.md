# Authorized Buyers Ad Exchange Buyer II API Java Samples
These samples demonstrate basic usage of the Authorized Buyers Ad Exchange
Buyer II API.

The Authorized Buyers Ad Exchange Buyer II API Java Client Library makes it
easier to write Java clients to programmatically access Authorized Buyer
accounts. The complete documentation for the Authorized Buyers Ad Exchange
Buyer II API is available from <https://developers.google.com/authorized-buyers/apis/>.

**Note:** The Ad Exchange Buyer II API is deprecated! The only API resources
supported at this time are for
[RTB Troubleshooting](https://developers.google.com/authorized-buyers/apis/guides/v2/rtb-troubleshooting).

## Prerequisites
- [`Java 6+`](http://java.com)
- [`Maven`](http://maven.apache.org)

## Announcements and updates

For API and client library updates and news, please follow the Google Ads
Developers blog: <http://googleadsdeveloper.blogspot.com/>.

For questions and support look at our forum page: <https://groups.google.com/forum/#!forum/authorized-buyers-api>.


## Running the examples

### Download the repository contents

To download the contents of the repository, you can use the command

```
git clone https://github.com/googleads/googleads-adxbuyer-examples
```

or browse to <https://github.com/googleads/googleads-adxbuyer-examples> and
 download a zip.

### Authorization Setup
The API uses OAuth2 for security, you can read about the options for connecting
 at <https://developers.google.com/accounts/docs/OAuth2>

We will focus on using a Service Account; samples are included for prompting
 the user and using a refresh token

If you don't already have a Service Account and corresponding JSON key file

 * Launch the Google Developers Console <https://console.developers.google.com>
 * Click the **API Manager** item on the navigation menu.
 * Click **Credentials** menu option.
 * Click the link titled **Manage service accounts**.
 * You should now see a table listing the Service Accounts associated with the
  current project. Find the Service Account you had been using previously, and
  open the menu.
 * Select the **Create key** menu item.
 * Click the **JSON** key type option and click **CREATE**.
 * Set the path to the downloaded JSON file as the **JSON_FILE** value in
  **AdExchangeBuyerSample.java**.

## Setup the environment
### Via the command line

1. Execute the following command:

    ```Batchfile
    $ mvn compile
    ```

### Via Eclipse

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

