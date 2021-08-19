# Authorized Buyers Ad Exchange Buyer II API Ruby Samples

These samples demonstrate basic usage of the Authorized Buyers Ad Exchange
Buyer II API.

# Migrating

These samples have been updated to work with Ruby 2.0 and 0.9.x of the
[google-api-ruby-client](https://github.com/google/google-api-ruby-client)
library. If you have used these samples previously and want to use the same
Service Account going forward, follow these steps to generate your existing
credentials as a JSON keyfile:

1. Go to the [Google Developer's Console](https://console.developers.google.com/).
2. Click the **API Manager** item on the navigation menu.
3. Click **Credentials** menu option.
4. Click the link titled **Manage service accounts**.
5. You should now see a table listing the Service Accounts associated with the
  current project. Find the Service Account you had been using previously, and
  open the menu.
6. Select the **Create key** menu item.
7. Click the **JSON** key type option and click **CREATE**.
8. Set the path to the downloaded JSON file as the **KEY_FILE** value in
  **samples_util.rb**.

# Setup

To run these samples, you'll need to do the following:

1. If you haven't done so already, download and install the
  **Google API Ruby Client** by running the following command from the
  project's root directory:

```
bundle
```

2. Go to the [Google Developers Console](https://console.developers.google.com/)
  and click the **APIs & auth** and then the **Credentials** tab.
  If you don't have one already, you may create a Service Account by clicking
  the **CREATE SERVICE ACCOUNT** button--note that you will need to select JSON
  as the key type. You will download a JSON key file, which you will need in a
  later step.
3. If you haven't done so already, you will need to associate your Service
  Account with Authorized Buyers. Go to the
  [Authorized Buyers UI](https://www.google.com/authorizedbuyers) and click the gear icon
  next to your email and Customer ID. Click **Account Settings** in the pop-up
  menu. Under **User management**, click the **Account users** tab. Click the
  **+Service Account** button to associate a new Service Account by providing
  its email address found in the
  [Google Developers Console](https://console.developers.google.com/).
4. Open **samples_util.rb**.
5. Update the `KEY_FILE` field in samples_util.rb to represent the path to the
  JSON key file you downloaded earlier. If you placed it in the sample
  directory, this should just be the filename.
6. Before attempting to run any of the samples, you may update any fields
  containing a template value or set them via keyword arguments.

You should now be able to start any of the samples by running them from the
command line, for example:

```
$ ruby list_accounts.rb
```

