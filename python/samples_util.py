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

"""Common utilities used by the DoubleClick Ad Exchange Buyer API Samples."""

import urllib2

from apiclient.discovery import build
from apiclient.discovery import build_from_document
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
ADEXCHANGEBUYER_VERSIONS = ('v1.2', 'v1.3', 'v1.4')
ADEXCHANGEBUYERII_VERSIONS = ('v2beta1',)
ADEXCHANGEBUYERII_SERVICE_NAME = 'adexchangebuyer.googleapis.com'
# Set the default version of Ad Exchange Buyer REST API to use.
DEFAULT_VERSION = ADEXCHANGEBUYER_VERSIONS[-1]


def GetService(version=DEFAULT_VERSION):
  """Builds the adexchangebuyer service used for the REST API."""
  http = httplib2.Http()

  credentials = client.SignedJwtAssertionCredentials(
      SERVICE_ACCOUNT_EMAIL,
      open(KEY_FILE).read(),
      scope=SCOPE)

  http = credentials.authorize(http)

  if version in ADEXCHANGEBUYER_VERSIONS:
    # Initialize client for Ad Exchange Buyer API
    service = build('adexchangebuyer', version, http=http)
  elif version in ADEXCHANGEBUYERII_VERSIONS:
    discovery_url = ('https://%s/$discovery/rest?version=%s'
                     % (ADEXCHANGEBUYERII_SERVICE_NAME, version))
    discovery_doc = urllib2.urlopen(discovery_url).read()
    service = build_from_document(service=discovery_doc, http=http)
  else:
    raise ValueError('Invalid version provided. Supported versions are: %s'
                     % ', '.join(ADEXCHANGEBUYER_VERSIONS +
                                 ADEXCHANGEBUYERII_VERSIONS))

  return service

