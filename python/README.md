# Authorized Buyers Ad Exchange Buyer API II Python Samples

These samples demonstrate basic usage of the Authorized Buyers Ad Exchange
Buyer II API.

# Setup

To run these samples, you'll need to do the following:

1. Download and install the dependencies used in these samples by running the
   following command:

   ```
   $ pip install -r requirements.txt
   ```
   **Note:** These samples are intended to be run with Python 3.7 or higher.
   If you don't have Python 3.7 installed, you can find it available for
   download [here](https://www.python.org/downloads/).
1. If you haven't done so already, you should read about the OAuth2
   [Service Account Flow](https://developers.google.com/accounts/docs/OAuth2ServiceAccount)
   because that is the flow used by these samples for authorization to access
   the Authorized Buyers Ad Exchange Buyer API.
1. Go to the [Google Developers Console](https://console.developers.google.com/)
   and open the menu. Click the `API Manager` option and then the `Credentials`
   tab. From here, you can either follow `Create credentials` to create a new
   Service Account for use with this project, or find an existing one under
   `Manage service accounts`.
1. In the `Manage service accounts` page, you can generate a new key for the
   Service Account by clicking the ellipses in the proper row and selecting
   `Create key` in the resulting menu. This library only supports the JSON key
   files, so select that as the `Key type`. Click the `create` button and the
   file will be generated and downloaded to your computer. Place the JSON file
   in the sample directory.
1. To authorize a Service Account to access your Authorized Buyers account via
   the API, go to the [Authorized Buyers UI](https://www.google.com/authorizedbuyers)
   and click the gear icon in the upper-right corner of the page to bring up a
   menu. Click the `Account Settings` option and then the `Account users` tab
   under `User management`. Click the `+Service Account` button on this page
   and enter the Service Account email to associate it with your Authorized
   Buyers Account.
1. Open **samples_util.py** and update the `KEY_FILE` field to represent the
   path to the JSON key file you downloaded earlier. If you placed it in the
   sample directory, this should just be the filename.
1. Before attempting to run any of the samples, you should update any fields
   containing default values for data sent in API requests. Alternatively, you
   can also provide values for these fields at run-time as command-line
   arguments.

You should now be able to start any of the samples by running them from the
command line, for example:

```
$ python list_client_buyers.py -a $ACCOUNT_ID
```

