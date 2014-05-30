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

2. If you haven't done so already, contact your Google Technical Account
  Manager to set up a
  [Service Account](https://developers.google.com/accounts/docs/OAuth2ServiceAccount)
  to use with the DoubleClick Ad Exchange Buyer REST API.
3. Go to the [Google Developers Console](https://console.developers.google.com/)
  and click the `APIs & auth` and then the `Credentials` tab and find the
  Service Account.
4. Generate a new key for the Service Account and place the .p12 file in the
  sample directory.
5. Open **util.py**.
6. Update the `SERVICE_ACCOUNT_EMAIL` field in util.py to match your Service
  Account's email address.
7. Update the `KEY_FILE` field in util.py to represent the path to the key
  file (.p12) you downloaded earlier. If you placed it in the sample directory,
  this should just be the filename.
8. Before attempting to run any of the samples, you should update any fields
  containing a template value.

You should now be able to start any of the samples by running them from the
command line, for example:

```
$ python list_accounts.py
```
