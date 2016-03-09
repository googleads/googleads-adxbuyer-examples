#!/usr/bin/python
#
# Copyright 2016 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""Sample application that authenticates and makes an API request."""

import pprint
from apiclient.discovery import build
import httplib2
from oauth2client import client

# Update these with the values for your Service Account found in the Google
# Developers Console.
SERVICE_ACCOUNT_EMAIL = 'yourapp@developer.gserviceaccount.com'
KEY_FILE = 'path_to_key'  # Path to *.p12 file containing your private key.

# Ad Exchange Buyer REST API authorization scope.
SCOPE = 'https://www.googleapis.com/auth/adexchange.buyer'
VERSION = 'v1.4'  # Version of Ad Exchange Buyer REST API to use.


def main():
  # Create credentials using the Service email and P12 file.
  oauth_credentials = client.SignedJwtAssertionCredentials(
      SERVICE_ACCOUNT_EMAIL,
      open(KEY_FILE).read(),
      scope=SCOPE)

  # Use the credentials to authorize an Http object
  http = oauth_credentials.authorize(httplib2.Http())

  # Use the http object to create a client for the API service.
  buyer_service = build('adexchangebuyer', VERSION, http=http)

  # Call the Accounts resource on the service to retrieve a list of
  # Accounts for the service account.
  request = buyer_service.accounts().list()

  pprint.pprint(request.execute())


if __name__ == '__main__':
  main()

