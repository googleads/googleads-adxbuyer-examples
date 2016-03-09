DoubleClick Ad Exchange Buyer REST API Python Samples
=======================================================
These samples demonstrate basic usage of the DoubleClick Ad Exchange Buyer REST
API.

Setup
=======================================================
To run these samples, you'll need to do the following:

1. Download and install the **Google API Python Client** with either
   easy_install or pip:

  * Using easy_install:

      ```
      $ easy_install --upgrade google-api-python-client
      ```

  * Using pip:

      ```
      $ pip install --upgrade google-api-python-client
      ```

1. If you haven't done so already, you should read about the OAuth2
  [Service Account Flow](https://developers.google.com/accounts/docs/OAuth2ServiceAccount)
  because that is the flow used by these samples for authorization to access
  the DoubleClick Ad Exchange Buyer REST API.
1. Go to the [Google Developers Console](https://console.developers.google.com/)
  and open the menu. Click the `API Manager` option and then the `Credentials`
  tab. From here, you can either follow `Create credentials` to create a new
  Service Account for use with this project, or find an existing one under
  `Manage service accounts`.
1. In the `Manage service accounts` page, you can generate a new key for the
  Service Account by clicking the ellipses in the proper row and selecting
  `Create key` in the resulting menu. For now, this library only supports the
  usage of .p12 key files, so select that as the `Key type`. Click the `create`
  button and the file will be generated and downloaded to your computer. Place
  the .p12 file in the sample directory.
1. To authorize a Service Account to access your Ad Exchange account via the
  API, go to the [DoubleClick Ad Exchange UI](https://adx.google.com) and click
  the gear icon in the upper-right corner of the page to bring up a menu. Click
  the `Account Settings` option and then the `Account users` tab under
  `User management`. Click the `+Service Account` button on this page and enter
  the Service Account email to associate it with your DoubleClick Ad Exchange
  Account.
1. Open **samples_util.py** and update the `SERVICE_ACCOUNT_EMAIL` field to
  match your Service Account's email address. Update the `KEY_FILE` field in
  **samples_util.py** to represent the path to the key file (.p12) you
  downloaded earlier. If you placed it in the sample directory, this should
  just be the filename.
1. Before attempting to run any of the samples, you should update any fields
  containing default values for data sent in API requests. Alternatively, you
  can also provide values for these fields at run-time as command-line
  arguments.

You should now be able to start any of the samples by running them from the
command line, for example:

```
$ python list_accounts.py
```
