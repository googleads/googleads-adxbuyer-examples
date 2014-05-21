#DoubleClick Ad Exchange Buyer REST API DotNet Samples
These samples demonstrate basic usage of the DoubleClick Ad Exchange Buyer REST API.

The DoubleClick Ad Exchange Buyer API DotNet Client Library makes it easier to
write .NET clients to programmatically access Ad Exchange Buyer accounts. 
The complete documentation for the DoubleClick Ad Exchange Buyer API docs are available from <https://developers.google.com/ad-exchange/buyer-rest/>.

##Features

- Support for .NET SDK 4.0 and above.

##Announcements and updates

For API and client library updates and news, please follow our Google+ Ads Developers page: <https://plus.google.com/+GoogleAdsDevelopers/posts> 
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

###Setup Security
The API uses OAuth2 for security, you can read about the options for connecting
 at <https://developers.google.com/accounts/docs/OAuth2>

We will focus on using a Service Account; samples are included for prompting 
 the user and using a refresh token

If you don't already have a Service Account and corresponding .p12 key file

 * Launch the Google Developers Console <https://console.developers.google.com>
 * select a project
 * click **APIs & auth**
 * click the **Credentials** tab
 * click **Create a new client ID**
 * select **Service Account**
 * this will create a new account and download a new keyfile.p12 file;
    keep this file safe!
 * copy the email address and password from the console for the next step

Open the ExamplesConfig.cs file and fill in the following fields

* ServiceAccountEmail
* ServiceKeyFilePath
* ServiceKeyFilePassword

###Dependancies
The DoubleClick Ad Exchange Buyer API .Net Client Library is hosted on nuget.org

If nuget is configured you can skip to **Run the examples** and it will 
 automatically download the required libraries

Otherwise you can locate the client library at
 <https://www.nuget.org/packages?q=Google.Apis.adexchangebuyer&prerelease=true&sortOrder=relevance>

###Run the examples
When you are ready, hit F5

By default this will print out a usage method and show how to run a paricular example

If you know which example you want to run you may choose to set
Project|Properties|Startup Object and then hit F5

**Note:** Some of the examples need parameters configured for them to work, those parameters appear in the first few lines of the Run method in each case.