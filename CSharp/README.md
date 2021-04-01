# Authorized Buyers Ad Exchange API DotNet Samples

These samples demonstrate basic usage of the Authorized Buyers Ad Exchange API.

The Authorized Buyers Ad Exchange API DotNet Client Library makes it easier to
write .NET clients to programmatically access Authorized Buyer accounts.
The complete documentation for the Authorized Buyers Ad Exchange API docs are
available from <https://developers.google.com/authorized-buyers/apis/>.

## Features

- .NET Framework 4.5+
- .NET Standard 1.3 and .NET Standard 2.0; providing .NET Core support.

The code examples have been compiled for .NET SDK 4.5.2, .NET Core App 3.1, .NET 5.0.

## Announcements and updates

For API and client library updates and news, please follow the Google Ads Developers blog: <http://googleadsdeveloper.blogspot.com/>.

For questions and support look at our forum page: <https://groups.google.com/forum/#!forum/authorized-buyers-api>.

## Running the examples

### Download the repository contents

To download the contents of the repository, you can use the command

```
git clone https://github.com/googleads/googleads-adxbuyer-examples
```

or browse to <https://github.com/googleads/googleads-adxbuyer-examples> and
 download a zip.

### Setup Security

The API uses OAuth2 for security, you can read about the options for connecting
 at <https://developers.google.com/accounts/docs/OAuth2>

We will focus on using a Service Account; samples are included for prompting
 the user and using a refresh token

If you don't already have a Service Account and corresponding JSON key file

 * Launch the Google Developers Console <https://console.developers.google.com>
 * If you are prompted to select a project, choose the one you are using to
   access the API.
 * Click **Credentials** menu option.
 * Click the link titled **Manage service accounts**.
 * You should now see a table listing the Service Accounts associated with the
  current project. Find the Service Account you had been using previously, and
  open the menu.
 * Select the **Manage keys** menu item.
 * Click the **ADD KEY** button, and select the **Create new key** item from
   the drop-down menu.
 * Click the **JSON** key type option and click **CREATE**.
 * Set the path to the downloaded JSON file as the **ServiceKeyFilePath** value
  in **ExamplesConfig.cs**.

### Run the examples

To build the project from the command line, run the following command:

```
dotnet build Google.Apis.AdExchangeBuyer.Examples.csproj
```

To run an example from the command line, run a command such as the following:

```
dotnet run --framework net5.0 $EXAMPLE_PATH
```

For example, to run the ListAccounts.cs example for the Ad Exchange Buyer API,
run the following:

```
dotnet run --framework net5.0 v1_x.ListAccounts
```

**Note:** Some of the examples need parameters configured for them to work,
those parameters appear in the first few lines of the Run method in each case.

