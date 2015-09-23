#!/usr/bin/python
#
# Copyright 2015 Google Inc. All Rights Reserved.
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

"""Common utilities used by the DoubleClick Ad Exchange Buyer API Samples."""

__author__ = 'api.msaniscalchi@gmail.com (Mark Saniscalchi)'

from apiclient.discovery import build
import httplib2
from oauth2client import client

# Update these with the values for your Service Account found in the Google
# Developers Console.
SERVICE_ACCOUNT_EMAIL = 'yourapp@developer.gserviceaccount.com'
KEY_FILE = 'path_to_key'  # Path to *.p12 file containing your private key.

# The maximum number of results to be returned in a page for any list response.
MAX_PAGE_SIZE = 50
# Ad Exchange Buyer REST API authorization scope.
SCOPE = 'https://www.googleapis.com/auth/adexchange.buyer'
VERSION = 'v1.4'  # Version of Ad Exchange Buyer REST API to use.


def GetService():
  """Builds the adexchangebuyer service used for the REST API."""
  http = httplib2.Http()

  credentials = client.SignedJwtAssertionCredentials(
      SERVICE_ACCOUNT_EMAIL,
      open(KEY_FILE).read(),
      scope=SCOPE)

  http = credentials.authorize(http)

  service = build('adexchangebuyer', VERSION, http=http)

  return service
